package org.jglfont.angelcode.line;

import org.jglfont.BitmapFontCharacterInfo;
import org.jglfont.BitmapFontData;
import org.jglfont.angelcode.Line;
import org.jglfont.angelcode.LineData;

/**
 * KerningLine
 * @author void
 */
public class KerningLine implements Line {
  @Override
  public boolean process(final LineData line, final BitmapFontData font) {
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