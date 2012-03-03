package org.jglfont.lwjgl;

import org.jglfont.BitmapFontRenderer;

public class LwjglBitmapFontRenderer implements BitmapFontRenderer {

  @Override
  public void registerBitmap(final int bitmapId, final String filename) {
    System.out.println("registerBitmap: " + bitmapId + ", " + filename);
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
    System.out.println("registerGlyph: " + character + ", " + xoffset + ", " + yoffset + ", " + width + ", " + height + ", " + u0 + ", " + v0 + ", " + u1 + ", " + v1);
  }

  @Override
  public void activateBitmap(final int bitmapId) {
    System.out.println("activateBitmap: " + bitmapId);
  }

  @Override
  public void render(final int x, final int y, final char character) {
    System.out.println("render: " + x + ", " + y + ", " + character);
  }
}
