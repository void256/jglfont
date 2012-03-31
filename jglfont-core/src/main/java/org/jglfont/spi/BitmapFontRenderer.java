package org.jglfont.spi;

public interface BitmapFontRenderer {
  public interface Texture {
  }

  /**
   * Register the bitmap with the given filename and the given key. This is a texture
   * file that contains a page of font glyphs.
   * @param bitmapId the key
   * @param filename the filename
   */
  void registerBitmap(int bitmapId, String filename);

  /**
   * Register a single Character Glyph for later rendering.
   * @param character the character
   * @param xoffset xoffset in the bitmap
   * @param yoffset yoffset in the bitmap
   * @param width the width of the glyph
   * @param height the height of the glyph
   * @param u0 the x texture coordinates of the upper left point
   * @param v0 the y texture coordinates of the upper left point
   * @param u1 the x texture coordinates of the bottom right point
   * @param v1 the y texture coordinates of the bottom right point
   */
  void registerGlyph(char character, int xoffset, int yoffset, int width, int height, float u0, float v0, float u1, float v1);

  /**
   * Activate the Bitmap with the given id
   * @param bitmapId the bitmap id to activate
   */
  void activateBitmap(int bitmapId);

  /**
   * Render the given character at the given position.
   * @param x the x position
   * @param y the y position
   * @param character the character
   */
  void render(int x, int y, char character);
}
