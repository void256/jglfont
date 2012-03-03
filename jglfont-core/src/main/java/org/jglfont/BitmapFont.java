package org.jglfont;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The core BitmapFont class represents a bitmap font :) that can render text.
 * To create a BitmapFont instance you need a BitmapFontRenderer implementation.
 * @author void
 */
public class BitmapFont {
  private BitmapFontRenderer fontRenderer;
  private BitmapFontLoader fontLoader;
  private BitmapFontData fontData;

  /**
   * Create a BitmapFont using the given BitmapFontRenderer and using the BitmapFontLoader
   * to load bitmap fonts.
   * @param fontRenderer font renderer
   * @param fontLoader font loader
   */
  public BitmapFont(final BitmapFontRenderer fontRenderer, final BitmapFontLoader fontLoader)  {
    this.fontLoader = fontLoader;
    this.fontRenderer = fontRenderer;
  }

  /**
   * Create a BitmapFont using the given BitmapFontRenderer and the BitmapFontData.
   * @param fontRenderer font renderer
   * @param fontData font data
   */
  public BitmapFont(final BitmapFontRenderer fontRenderer, final BitmapFontData fontData)  {
    this.fontRenderer = fontRenderer;
    this.fontData = fontData;
    initalize();
  }

  public void load(final InputStream fontStream) throws IOException {
    if (fontLoader == null) {
      throw new IllegalArgumentException("no fontLoader availabe");
    }
    this.fontData = fontLoader.load(fontStream);
    initalize();
  }

  public void renderText(
      final int startX,
      final int startY,
      final String text) {
    renderText(startX, startY, text, 1.f, 1.f, 1.f, 1.f, 1.f, 1.f);
  }

  public void renderText(
      final int startX,
      final int startY,
      final String text,
      final float sizeX,
      final float sizeY,
      final float r,
      final float g,
      final float b,
      final float a) {
    int activeTextureIdx = -1;
    int x = startX;
    int y = startY;

    for (int i = 0; i < text.length(); i++) {
      char currentCharacter = text.charAt(i);
      char nextCharacter = getNextCharacter(text, i);

      BitmapFontCharacterInfo characterInfo = fontData.getCharacters().get(currentCharacter);
      if (characterInfo != null) {
        int characterTexturePage = characterInfo.getPage();
        if (activeTextureIdx != characterTexturePage) {
          activeTextureIdx = characterTexturePage;
          fontRenderer.activateBitmap(activeTextureIdx);
        }
        fontRenderer.render(x, y, currentCharacter);
        x += (float) getCharacterWidth(currentCharacter, nextCharacter, sizeX);
      }
    }
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

  private int getCharacterWidth(final char currentCharacter, final char nextCharacter, final float size) {
    BitmapFontCharacterInfo currentCharacterInfo = fontData.getCharacters().get(currentCharacter);
    return (int) ((currentCharacterInfo.getXadvance() + getKerning(currentCharacterInfo, nextCharacter)) * size);
  }

  private int getKerning(final BitmapFontCharacterInfo currentCharacterInfo, final char nextCharacter) {
    Integer kern = currentCharacterInfo.getKerning().get(Character.valueOf(nextCharacter));
    if (kern != null) {
      return kern.intValue();
    }
    return 0;
  }
}
