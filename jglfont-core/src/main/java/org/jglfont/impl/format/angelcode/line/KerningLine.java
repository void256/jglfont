package org.jglfont.impl.format.angelcode.line;

import org.jglfont.impl.format.BitmapFontCharacterInfo;
import org.jglfont.impl.format.BitmapFontData;
import org.jglfont.impl.format.angelcode.AngelCodeLine;
import org.jglfont.impl.format.angelcode.AngelCodeLineData;

/**
 * KerningLine
 * @author void
 */
public class KerningLine implements AngelCodeLine {

  @Override
  public boolean process(final AngelCodeLineData line, final BitmapFontData font) {
    if (!line.hasValue("first") ||
        !line.hasValue("second") ||
        !line.hasValue("amount")) {
      return false;
    }
    int first = line.getInt("first");
    int second = line.getInt("second");
    int amount = line.getInt("amount");

    BitmapFontCharacterInfo info = font.getCharacters().get(Character.valueOf((char)first));
    if (info == null) {
      return false;
    }
    info.getKerning().put(Character.valueOf((char)second), amount);
    return true;
  }
}