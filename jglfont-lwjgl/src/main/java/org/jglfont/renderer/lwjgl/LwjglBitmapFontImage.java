package org.jglfont.renderer.lwjgl;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetInteger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jglfont.renderer.lwjgl.io.ImageData;
import org.jglfont.renderer.lwjgl.io.ImageIOImageData;
import org.jglfont.renderer.lwjgl.io.TGAImageData;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class LwjglBitmapFontImage {
  private Logger log = Logger.getLogger(LwjglBitmapFontImage.class.getName());

  private static final int GL_MAX_TEXTURE_SIZE_BUFFER_SIZE = 16;
  private static final int PIXEL_DEPTH_32 = 32;

  private int width;
  private int height;
  private int textureWidth;
  private int textureHeight;
  private int textureId;

  public LwjglBitmapFontImage(final InputStream resource, final String name, final boolean filterParam) {
    log.fine("loading image: " + name);
    ImageData imageLoader;
    if (name.endsWith(".tga")) {
      imageLoader = new TGAImageData();
    } else {
      imageLoader = new ImageIOImageData();
    }
    try {
      ByteBuffer imageData = imageLoader.loadImage(resource);
      imageData.rewind();
      width = imageLoader.getWidth();
      height = imageLoader.getHeight();
      textureWidth = imageLoader.getTexWidth();
      textureHeight = imageLoader.getTexHeight();
      createTexture(
          imageData,
          textureWidth,
          textureHeight,
          getFilter(filterParam),
          imageLoader.getDepth() == PIXEL_DEPTH_32 ? GL11.GL_RGBA : GL11.GL_RGB);
    } catch (IOException e) {
      log.log(Level.WARNING, "exception while loading texture data: ", e);
    }
  }

  public LwjglBitmapFontImage(ByteBuffer data, int width, int height, String filename, boolean filterParam) {
    this.width = width;
    this.height = height;
    textureWidth = powerOfTwo(width);
    textureHeight = powerOfTwo(height);
    createTexture(data, textureWidth, textureHeight, getFilter(filterParam), GL11.GL_RGBA);
  }

  public int powerOfTwo(int value) {
    int result = 2;
    while (result < value) {
      result *= 2;
    }
    return result;
  }

  private int getFilter(final boolean filterParam) {
    if (filterParam) {
      return GL11.GL_LINEAR;
    } else {
      return GL11.GL_NEAREST;
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getTextureWidth() {
    return textureWidth;
  }

  public int getTextureHeight() {
    return textureHeight;
  }

  public void dispose() {
    GL11.glDeleteTextures(textureId);
    CheckGL.checkGLError("glDeleteTextures");
  }

  private void createTexture(
      final ByteBuffer textureBuffer,
      final int width,
      final int height,
      final int filter,
      final int srcPixelFormat) {
    textureId = createTextureID();
    int minFilter = filter;
    int magFilter = filter;
    bind();

    int componentCount = 1;

    IntBuffer temp = BufferUtils.createIntBuffer(GL_MAX_TEXTURE_SIZE_BUFFER_SIZE);
    glGetInteger(GL11.GL_MAX_TEXTURE_SIZE, temp);
    CheckGL.checkGLError("glGetInteger");

    int max = temp.get(0);
    if ((width > max) || (height > max)) {
      log.warning("Attempt to allocate a texture with negative width");
      return;
    }
    if (width < 0) {
      log.warning("Attempt to allocate a texture with negative width");
      return;
    }
    if (height < 0) {
      log.warning("Attempt to allocate a texture with negative height");
      return;
    }

    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
    CheckGL.checkGLError("glTexParameteri");

    if (minFilter == GL11.GL_LINEAR_MIPMAP_NEAREST) {
      GLU.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, componentCount, width, height, srcPixelFormat, GL11.GL_UNSIGNED_BYTE,
          textureBuffer);
    } else {
      GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, srcPixelFormat, GL11.GL_UNSIGNED_BYTE,
          textureBuffer);
    }
    CheckGL.checkGLError("glTexImage2D");
  }

  private int createTextureID() {
    IntBuffer id = BufferUtils.createIntBuffer(1);
    id.rewind();
    glGenTextures(id);
    CheckGL.checkGLError("glGenTextures");
    id.rewind();
    return id.get();
  }

  public final void bind() {
    glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    CheckGL.checkGLError("glBindTexture");
  }
}
