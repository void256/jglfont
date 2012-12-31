package org.jglfont.renderer.lwjglcore;

import java.io.IOException;
import java.io.InputStream;

import org.jglfont.BitmapFontException;
import org.jglfont.spi.BitmapFontRenderer;

import de.lessvoid.coregl.CoreRenderToTexture;
import de.lessvoid.coregl.CoreTexture2D;
import de.lessvoid.coregl.CoreTexture2D.ColorFormat;
import de.lessvoid.coregl.CoreTexture2D.ResizeFilter;
import de.lessvoid.coregl.CoreTextureAtlasGenerator;
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
  private final CoreTextureAtlasGenerator generator;
  private CoreRenderToTexture textureAtlas;

  public LwjglCoreFontRenderer(final int textureAtlasWidth, final int textureAtlasHeight) {
    generator = new CoreTextureAtlasGenerator(textureAtlasWidth, textureAtlasHeight);
  }

  public void registerBitmap(final int bitmapId, final InputStream data, final String filename) throws IOException {
    ImageData imageData = loader.load(filename, data, new SimpleImageLoaderConfig().forceAlpha());
    Result addImageResult = generator.addImage(createTexture(imageData), filename, 5);
    if (addImageResult == null) {
      throw new BitmapFontException("failed to add image to texture atlas: " + filename);
    }
    textureAtlas = generator.getDone();
  }

  private CoreTexture2D createTexture(final ImageData imageData) {
    return new CoreTexture2D(ColorFormat.RGBA, imageData.getWidth(), imageData.getHeight(), imageData.getData(), ResizeFilter.Linear);
  }

  public void registerGlyph(
      final int bitmapId,
      final char c,
      final int xoff,
      final int yoff,
      final int w,
      final int h,
      final float u0,
      final float v0,
      final float u1,
      final float v1) {
  }

  public void begin() {
    textureAtlas.bindTexture();
  }

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
  }

  public void end() {
  }
}
