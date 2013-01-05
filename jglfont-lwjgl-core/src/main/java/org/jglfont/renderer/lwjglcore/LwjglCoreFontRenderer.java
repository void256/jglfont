package org.jglfont.renderer.lwjglcore;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jglfont.BitmapFontException;
import org.jglfont.spi.BitmapFontRenderer;

import de.lessvoid.coregl.CoreElementVBO;
import de.lessvoid.coregl.CoreMatrixFactory;
import de.lessvoid.coregl.CoreRender;
import de.lessvoid.coregl.CoreShader;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreTextureAtlasGenerator;
import de.lessvoid.coregl.CoreVAO;
import de.lessvoid.coregl.CoreVBO;
import de.lessvoid.simpleimageloader.ImageData;
import de.lessvoid.simpleimageloader.SimpleImageLoader;
import de.lessvoid.simpleimageloader.SimpleImageLoaderConfig;
import de.lessvoid.textureatlas.TextureAtlasGenerator.Result;

/**
 * Optimized LWJGL Core Profile BitmapFontRenderer.
 * @author void
 */
public class LwjglCoreFontRenderer implements BitmapFontRenderer {
  private final SimpleImageLoader loader = new SimpleImageLoader();
  private final CoreTextureAtlasGenerator atlas;
  private final Map<Integer, Result> textureInfos = new HashMap<Integer, Result>();
  private final List<Float> vertexData = new ArrayList<Float>();
  private final Map<Character, Integer> characterIndices = new Hashtable<Character, Integer>();
  private CoreShader shader;
  private CoreVAO vao;
  private CoreVBO vbo;
  private CoreElementVBO elementBuffer;
  private IntBuffer elementBufferData;
  private int elementBufferCount;

  /**
   * Create a new LwjglCoreFontRenderer using a texture atlas of the given width x height to store the font pages. If
   * your font only uses a single page it's perfectly fine to use the same size of the fonts bitmap as the width and
   * height.
   *
   * @param textureAtlasWidth width of the texture atlas
   * @param textureAtlasHeight height of the texture atlas
   */
  public LwjglCoreFontRenderer(final int textureAtlasWidth, final int textureAtlasHeight) {
    atlas = new CoreTextureAtlasGenerator(textureAtlasWidth, textureAtlasHeight);
  }

  /**
   * Add the font textures to an already existing CoreTextureAtlasGenerator. Very useful! :)
   *
   * @param atlas the target texture atlas
   */
  public LwjglCoreFontRenderer(final CoreTextureAtlasGenerator atlas) {
    this.atlas = atlas;
  }

  @Override
  public void registerBitmap(final int bitmapId, final InputStream data, final String filename) throws IOException {
    ImageData imageData = loader.load(filename, data, new SimpleImageLoaderConfig().forceAlpha());
    Result result = atlas.addImage(createTexture(imageData), filename, 0);
    if (result == null) {
      throw new BitmapFontException("failed to add image to texture atlas: " + filename);
    }
    textureInfos.put(bitmapId, result);
  }

  @Override
  public void registerGlyph(
      final int bitmapId,
      final char c,
       int xoff,
       int yoff,
       int w,
       int h,
       float u0,
       float v0,
       float u1,
       float v1) {
    Result textureInfo = textureInfos.get(bitmapId);
    float atlasX0 = textureInfo.getX();
    float atlasY0 = textureInfo.getY();
    float atlasImageW = textureInfo.getOriginalImageWidth();
    float atlasImageH = textureInfo.getOriginalImageHeight();

    u0 = (atlasX0 + u0 * atlasImageW) / (float) atlas.getWidth();
    u1 = (atlasX0 + u1 * atlasImageW) / (float) atlas.getWidth();
    v0 = (atlasY0 + v0 * atlasImageH) / (float) atlas.getHeight();
    v1 = (atlasY0 + v1 * atlasImageH) / (float) atlas.getHeight();

    int index = vertexData.size() / 4;
    characterIndices.put(c, index);
    xoff += 100;
    yoff += 100;
    System.out.println(c + " => " + index + ": " + u0 + ", " + v0 + ", " + u1 + ", " + v1);

    w = 100;
    h = 100;
    vertexData.add((float) xoff + 0);
    vertexData.add((float) yoff + 0);
    vertexData.add(u0);
    vertexData.add(v0);

    vertexData.add((float) xoff + w);
    vertexData.add((float) yoff + 0);
    vertexData.add(u1);
    vertexData.add(v0);

    vertexData.add((float) xoff + w);
    vertexData.add((float) yoff + h);
    vertexData.add(u1);
    vertexData.add(v1);

    vertexData.add((float) xoff + 0);
    vertexData.add((float) yoff + h);
    vertexData.add(u0);
    vertexData.add(v1);
  }

  @Override
  public void prepare() {
    shader = CoreShader.newShaderWithVertexAttributes("aPos", "aUV");
    shader.vertexShader(LwjglCoreFontRenderer.class.getResourceAsStream("text.vs"));
    shader.fragmentShader(LwjglCoreFontRenderer.class.getResourceAsStream("text.fs"));
    shader.link();
    shader.activate();
    shader.setUniformi("uTex", 0);
    shader.setUniformMatrix4f("uMVP", CoreMatrixFactory.createOrtho(0, 1024, 768, 0));

    vao = new CoreVAO();
    vao.bind();
    vbo = CoreVBO.createStaticAndSend(toFloatArray(vertexData));
    vao.enableVertexAttributef(0, 2, 4, 0);
    vao.enableVertexAttributef(1, 2, 4, 2);
    elementBuffer = CoreElementVBO.createDynamic(new int[1024]);
    elementBufferData = elementBuffer.getBuffer();
  }

  @Override
  public void beforeRender() {
    elementBufferData.rewind();
    elementBufferCount = 0;
  }

  @Override
  public void render(
      final int bitmapId,
      final int x,
      final int y,
      final char c,
      final float sx,
      final float sy,
      final float r,
      final float g,
      final float b,
      final float a) {
    int index = characterIndices.get(c);
    elementBufferData.put(index + 0);
    elementBufferData.put(index + 1);
    elementBufferData.put(index + 2);
    elementBufferData.put(index + 3);
    elementBufferData.put(0xFFFF);
    elementBufferCount += 5;
  }

  @Override
  public void afterRender() {
    atlas.getTargetTexture().bindTexture();

    elementBuffer.bind();
    elementBufferData.rewind();
    elementBuffer.send();
    elementBuffer.enablePrimitiveRestart(0xFFFF);
    vao.bind();
    shader.activate();
/*
    CoreShader shader = CoreShader.newShaderWithVertexAttributes("vVertex", "vColor");
    shader.vertexShader("super-simple/super-simple.vs");
    shader.fragmentShader("super-simple/super-simple.fs");
    shader.link();

    CoreVAO vao = new CoreVAO();
    vao.bind();

    CoreVBO.createStaticAndSend(new float[] {
        -0.5f, -0.5f,    1.0f, 0.0f, 0.0f, 1.0f,
        -0.5f,  0.5f,    0.0f, 1.0f, 0.0f, 1.0f,
         0.5f, -0.5f,    0.0f, 0.0f, 1.0f, 1.0f,
         0.5f,  0.5f,    1.0f, 1.0f, 1.0f, 1.0f,
    });

    // parameters are: index, size, stride, offset
    // this will use the currently active VBO to store the VBO in the VAO
    vao.enableVertexAttributef(0, 2, 6, 0);
    vao.enableVertexAttributef(1, 4, 6, 2);

    // we only use a single shader and a single vao so we can activate both here
    // and let them stay active the whole time.
    shader.activate();
    vao.bind();

    CoreRender.renderTriangleStrip(4);
    */
    CoreRender.renderTriangleFanIndexed(elementBufferCount);
    elementBuffer.disablePrimitiveRestart();
    elementBuffer.unbind();
  }

  private CoreTexture2D createTexture(final ImageData imageData) {
    return new CoreTexture2D(ColorFormat.RGBA, imageData.getWidth(), imageData.getHeight(), imageData.getData(), ResizeFilter.Linear);
  }

  private float[] toFloatArray(final List<Float> src) {
    float[] result = new float[src.size()];
    for (int index = 0; index < src.size(); index++) {
      result[index] = src.get(index);
    }
    return result;
  }

  private static class TextureInfo {
    private float u0;
    private float v0;
    private int w;
    private int h;

    public TextureInfo(final float u0, final float v0, final int w, final int h) {
      this.u0 = u0;
      this.v0 = v0;
      this.w = w;
      this.h = h;
    }

    public float getU0() {
      return u0;
    }

    public float getV0() {
      return v0;
    }

    public int getW() {
      return w;
    }

    public int getH() {
      return h;
    }
  }
}
