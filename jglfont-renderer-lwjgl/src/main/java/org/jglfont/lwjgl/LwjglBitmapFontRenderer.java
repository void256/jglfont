package org.jglfont.lwjgl;

import java.util.Hashtable;
import java.util.Map;

import org.jglfont.spi.BitmapFontRenderer;

import de.lessvoid.coregl.VBO;
import de.lessvoid.resourceloader.ResourceLoader;

public class LwjglBitmapFontRenderer implements BitmapFontRenderer {
  private static final int VERTEX_COMPONENT_LENGTH = 8;
  private Map<Integer, LwjglBitmapFontImage> images = new Hashtable<Integer, LwjglBitmapFontImage>();
  private float[] vertices;
  private VBO vbo;
  private ResourceLoader resourceLoader;

  public LwjglBitmapFontRenderer(final ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Override
  public void registerBitmap(final int bitmapId, final String filename) {
    System.out.println("registerBitmap: " + bitmapId + ", " + filename);
    images.put(bitmapId, new LwjglBitmapFontImage(resourceLoader, filename, false));
  }

  public void beginInitialize(final int numberOfCharacters) {
    vertices = new float[numberOfCharacters * VERTEX_COMPONENT_LENGTH];
  }

  @Override
  public void registerGlyph(
      final char character,
      final int xoffset,
      final int yoffset,
      final int width,
      final int height,
      final float u0,
      final float v0,
      final float u1,
      final float v1) {
    int offset = character * VERTEX_COMPONENT_LENGTH;
  }

  public void endInitialize() {
  }

  @Override
  public void activateBitmap(final int bitmapId) {
    System.out.println("activateBitmap: " + bitmapId);
    images.get(bitmapId).bind();
  }

  @Override
  public void render(final int x, final int y, final char character) {
    System.out.println("render: " + x + ", " + y + ", " + character);
  }
}
