package org.jglfont.impl;

import org.jglfont.impl.format.JGLFontGlyphInfo;

/**
 * User: iamtakingiteasy
 * Date: 2013-12-31
 * Time: 20:49
 */
public class CharPos {
  private final JGLFontGlyphInfo info;
  private final int ch;
  private final int xPos;

  public CharPos(JGLFontGlyphInfo info, int ch, int xPos) {
    this.info = info;
    this.ch = ch;
    this.xPos = xPos;
  }

  public JGLFontGlyphInfo getInfo() {
    return info;
  }

  public int getCh() {
    return ch;
  }

  public int getxPos() {
    return xPos;
  }
}
