package org.jglfont.impl.format.angelcode.line;

import org.jglfont.impl.format.BitmapFontCharacterInfo;
import org.jglfont.impl.format.BitmapFontData;
import org.jglfont.impl.format.angelcode.AngelCodeLine;
import org.jglfont.impl.format.angelcode.AngelCodeLineData;

/**
 * CharLine
 * @author void
 */
public class CharLine implements AngelCodeLine {

  @Override
  public boolean process(final AngelCodeLineData line, final BitmapFontData font) {
    if (!line.hasValue("id") ||
        !line.hasValue("x") ||
        !line.hasValue("y") ||
        !line.hasValue("width") ||
        !line.hasValue("height") ||
        !line.hasValue("page")) {
      return false;
    }
    BitmapFontCharacterInfo c = new BitmapFontCharacterInfo();
    c.setId(line.getInt("id"));
    c.setX(line.getInt("x"));
    c.setY(line.getInt("y"));
    c.setWidth(line.getInt("width"));
    c.setHeight(line.getInt("height"));
    c.setXoffset(line.getInt("xoffset"));
    c.setYoffset(line.getInt("yoffset"));
    c.setXadvance(line.getInt("xadvance"));
    c.setPage(line.getInt("page"));

    font.addCharacter(Character.valueOf((char) c.getId()), c);
    font.setLineHeight(Math.max(c.getHeight() + c.getYoffset(), font.getLineHeight()));

    return true;
  }
}