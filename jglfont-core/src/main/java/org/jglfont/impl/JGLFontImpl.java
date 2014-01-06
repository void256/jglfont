package org.jglfont.impl;

import org.jglfont.JGLFont;
import org.jglfont.impl.format.JGLAbstractFontData;
import org.jglfont.impl.format.JGLFontGlyphInfo;

import java.util.*;

/**
 * The core JGLFont class represents a bitmap font :) that can render text.
 * To create a JGLFont instance you need a JGLFontRenderer and a JGLFontLoader or
 * the plan JGLAbstractFontData.
 *
 * @author void
 */
public class JGLFontImpl implements JGLFont {
  private final JGLAbstractFontData fontData;

  /**
   * Create a JGLFont using the given JGLAbstractFontData.
   * @param fontData font data
   */
  public JGLFontImpl(final JGLAbstractFontData fontData)  {
    this.fontData = fontData;
  }

  /* (non-Javadoc)
   * @see org.jglfont.impl.JGLFont#renderText(int, int, java.lang.String)
   */
  @Override
  public void renderText(
      final int x,
      final int y,
      final String text) {
    renderText(x, y, text, 1.f, 1.f, 1.f, 1.f, 1.f, 1.f);
  }

  /* (non-Javadoc)
   * @see org.jglfont.impl.JGLFont#renderText(int, int, java.lang.String, float, float, float, float, float, float)
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

    List<CharPos> charPoses = new ArrayList<CharPos>();

    for (int offset = 0; offset < text.length(); /* no increment */) {
      offset = fontData.getRenderer().preProcess(text, offset);
      if (offset >= text.length()) {
        break;
      }
      int currentCodepoint = text.codePointAt(offset);
      int nextCodePoint = getNextCodepoint(text, offset);

      offset += Character.charCount(currentCodepoint);

      JGLFontGlyphInfo characterInfo = fontData.getGlyphs().get(currentCodepoint);
      if (characterInfo != null) {
        charPoses.add(new CharPos(characterInfo, currentCodepoint, xPos));
        xPos += (float) getCharacterWidth(currentCodepoint, nextCodePoint, sizeX);
      }
    }

    Collections.sort(charPoses, new Comparator<CharPos>() {
      @Override
      public int compare(CharPos o1, CharPos o2) {
        return o1.getInfo().getPage().compareTo(o2.getInfo().getPage());
      }
    });

    Iterator<CharPos> iterator = charPoses.iterator();
    CharPos pos = null;
    CharPos npos;
    boolean leftover = false;
    while (iterator.hasNext() || leftover) {
      fontData.getRenderer().beforeRender(fontData.getName());
      if (pos != null) {
        fontData.getRenderer().render(fontData.getName(), pos.getInfo().getPage(), pos.getxPos(), y, pos.getCh(), sizeX, sizeY, r, g, b, a);
        leftover = false;
      }
      while (iterator.hasNext()) {
        npos = iterator.next();
        if (pos != null && !pos.getInfo().getPage().equals(npos.getInfo().getPage())) {
          pos = npos;
          leftover = true;
          break;
        }
        pos = npos;
        fontData.getRenderer().render(fontData.getName(), npos.getInfo().getPage(), npos.getxPos(), y, npos.getCh(), sizeX, sizeY, r, g, b, a);
      }
      fontData.getRenderer().afterRender(fontData.getName());
    }
  }

  @Override
  public int getCharacterWidth(final int currentCharacter, final int nextCharacter) {
    return getCharacterWidth(currentCharacter, nextCharacter, 1.f);
  }

  @Override
  public int getCharacterWidth(final int currentCharacter, final int nextCharacter, final float size) {
    JGLFontGlyphInfo currentCharacterInfo = fontData.getGlyphs().get(currentCharacter);
    if (currentCharacterInfo == null) {
      return 0;
    }
    return (int) ((currentCharacterInfo.getXadvance() + getKerning(currentCharacterInfo, nextCharacter)) * size);
  }

  @Override
  public int getStringWidth(final String text) {
    return getStringWidth(text, 1.f);
  }

  @Override
  public int getStringWidth(final String text, final float size) {
    int length = 0;

    for (int offset = 0; offset < text.length(); /* no increment */) {
      offset = fontData.getRenderer().preProcessForLength(text, offset);
      if (offset >= text.length()) {
        break;
      }
      int currentCodepoint = text.codePointAt(offset);
      int nextCodepoint = getNextCodepoint(text, offset);

      offset += Character.charCount(currentCodepoint);

      int w = getCharacterWidth(currentCodepoint, nextCodepoint, size);
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

  private int getNextCodepoint(final String text, final int currentIndex) {
    int nextCodepoint = 0;
    if (currentIndex < text.length() - 1) {
      int currentCodepoint = text.codePointAt(currentIndex);
      nextCodepoint = text.codePointAt(currentIndex + Character.charCount(currentCodepoint));
    }
    return nextCodepoint;
  }

  private int getKerning(final JGLFontGlyphInfo currentCharacterInfo, final int nextCharacter) {
    Integer kern = currentCharacterInfo.getKerning().get(nextCharacter);
    if (kern != null) {
      return kern;
    }
    return 0;
  }
}
