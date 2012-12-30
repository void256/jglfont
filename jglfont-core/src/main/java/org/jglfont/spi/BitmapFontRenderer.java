package org.jglfont.spi;

/**
 * The interface necessary to let jglfont render a Bitmap. The actual resource loading of bitmap files needs to be done
 * in here. jglfont acts as a provider of the glyph data and BitmapFontRenderer implementations can use whatever way
 * they seem fit to display this data.
 *
 * @author void
 */
public interface BitmapFontRenderer {
  /**
   * Register the bitmap with the given filename and the given key. This is a texture that contains all the font glyph
   * data. It's not necessary that this call directly maps to a single actual texture since implementation can decide
   * to pack multiple textures into a single one.
   *
   * @param bitmapId the key
   * @param filename the filename
   */
  void registerBitmap(int bitmapId, String filename);

  /**
   * Register a single Character Glyph for later rendering.
   * @param c the character
   * @param xoff xoffset in the bitmap
   * @param yoff yoffset in the bitmap
   * @param w the width of the glyph
   * @param h the height of the glyph
   * @param u0 the x texture coordinates of the upper left point
   * @param v0 the y texture coordinates of the upper left point
   * @param u1 the x texture coordinates of the bottom right point
   * @param v1 the y texture coordinates of the bottom right point
   */
  void registerGlyph(int bitmapId, char c, int xoff, int yoff, int w, int h, float u0, float v0, float u1, float v1);

  /**
   * This is called before several render() calls are happening. This method can be used to set up state for upcoming
   * text rendering calls or it might allow the implementation to cache state between render() states
   * (f.i. texture state).
   */
  void begin();
  
  /**
   * Render a single character at the given position with the given color using the given bitmapId.
   *
   * @param bitmapId the bitmapId the corresponding character is on
   * @param x the x position
   * @param y the y position
   * @param c the character to output
   * @param sx x scale factor 
   * @param sy y scale factor
   * @param r red
   * @param g green
   * @param b blue
   * @param a alpha
   */
  void render(int bitmapId, int x, int y, char c, float sx, float sy, float r, float g, float b, float a);

  /**
   * This is called after several render() calls.
   */
  void end();
}
