package org.jglfont.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.jglfont.BitmapFont;
import org.jglfont.impl.format.BitmapFontCharacterInfo;
import org.jglfont.impl.format.BitmapFontData;
import org.jglfont.spi.BitmapFontRenderer;

/**
 * The core BitmapFont class represents a bitmap font :) that can render text.
 * To create a BitmapFont instance you need a BitmapFontRenderer and a BitmapFontLoader or
 * the plan BitmapFontData.
 *
 * @author void
 */
public class BitmapFontImpl implements BitmapFont {
  private BitmapFontRenderer fontRenderer;
  private BitmapFontData fontData;

  /**
   * Create a BitmapFont using the given BitmapFontRenderer and the BitmapFontData.
   * @param fontRenderer font renderer
   * @param fontData font data
   */
  public BitmapFontImpl(final BitmapFontRenderer fontRenderer, final BitmapFontData fontData)  {
    this.fontRenderer = fontRenderer;
    this.fontData = fontData;
    initalize();
  }

  /* (non-Javadoc)
   * @see org.jglfont.impl.BitmapFont#renderText(int, int, java.lang.String)
   */
  @Override
  public void renderText(
      final int x,
      final int y,
      final String text) {
    renderText(x, y, text, 1.f, 1.f, 1.f, 1.f, 1.f, 1.f);
  }

  /* (non-Javadoc)
   * @see org.jglfont.impl.BitmapFont#renderText(int, int, java.lang.String, float, float, float, float, float, float)
   */
  @Override
  public void renderText(
      final int x,
      final int y,
      final String text,
      final float sizeX,
      final float sizeY,
      final float r,
      final float g,
      final float b,
      final float a) {
    if (text.length() == 0) {
      return;
    }

    int xPos = x;
    int yPos = y;
    fontRenderer.begin();
    for (int i = 0; i < text.length(); i++) {
      char currentCharacter = text.charAt(i);
      char nextCharacter = getNextCharacter(text, i);

      BitmapFontCharacterInfo characterInfo = fontData.getCharacters().get(currentCharacter);
      if (characterInfo != null) {
        int characterTexturePage = characterInfo.getPage();
        fontRenderer.render(characterTexturePage, xPos, yPos, currentCharacter, sizeX, sizeY, r, g, b, a);
        xPos += (float) getCharacterWidth(currentCharacter, nextCharacter, sizeX);
      }
    }
    fontRenderer.end();
  }

  @Override
  public int getCharacterWidth(final char currentCharacter, final char nextCharacter) {
    return getCharacterWidth(currentCharacter, nextCharacter, 1.f);
  }

  @Override
  public int getCharacterWidth(final char currentCharacter, final char nextCharacter, final float size) {
    BitmapFontCharacterInfo currentCharacterInfo = fontData.getCharacters().get(currentCharacter);
    return (int) ((currentCharacterInfo.getXadvance() + getKerning(currentCharacterInfo, nextCharacter)) * size);
  }

  @Override
  public int getStringWidthInternal(final String text) {
    return getStringWidthInternal(text, 1.f);
  }

  @Override
  public int getStringWidthInternal(final String text, final float size) {
    int length = 0;

    for (int i = 0; i < text.length(); i++) {
      char currentCharacter = text.charAt(i);
      char nextCharacter = getNextCharacter(text, i);

      int w = getCharacterWidth(currentCharacter, nextCharacter, size);
      if (w != -1) {
        length += w;
      }
    }
    return length;
  }

  @Override
  public int getHeight() {
    return fontData.getLineHeight();
  }

  private void initalize() {
    // load textures of font into array
    for (Entry<Integer, String> entry : this.fontData.getBitmaps().entrySet()) {
      this.fontRenderer.registerBitmap(entry.getKey(), entry.getValue());
    }

    initGlyphs();
  }

  private void initGlyphs() {
    for (Map.Entry<Character, BitmapFontCharacterInfo> entry : fontData.getCharacters().entrySet()) {
      Character c = entry.getKey();
      BitmapFontCharacterInfo charInfo = entry.getValue();
      if (charInfo != null) {
        fontRenderer.registerGlyph(
            charInfo.getPage(),
            c,
            charInfo.getXoffset(),
            charInfo.getYoffset(),
            charInfo.getWidth(),
            charInfo.getHeight(),
            charInfo.getX() / (float) fontData.getBitmapWidth(),
            charInfo.getY() / (float) fontData.getBitmapHeight(),
            (charInfo.getX() + charInfo.getWidth()) / (float) fontData.getBitmapWidth(),
            (charInfo.getY() + charInfo.getHeight()) / (float) fontData.getBitmapHeight());
      }
    }
  }

  private char getNextCharacter(final String text, final int currentIndex) {
    char nextChar = 0;
    if (currentIndex < text.length() - 1) {
      nextChar = text.charAt(currentIndex + 1);
    }
    return nextChar;
  }

  private int getKerning(final BitmapFontCharacterInfo currentCharacterInfo, final char nextCharacter) {
    Integer kern = currentCharacterInfo.getKerning().get(Character.valueOf(nextCharacter));
    if (kern != null) {
      return kern.intValue();
    }
    return 0;
  }
}
