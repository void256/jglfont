package de.lessvoid.jglfont.loader.angelcode.line;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.BitmapFontCharacterInfo;
import de.lessvoid.jglfont.loader.angelcode.Line;
import de.lessvoid.jglfont.loader.angelcode.LineData;

/**
 * KerningLine
 * @author void
 */
public class KerningLine implements Line {
  @Override
  public boolean process(final LineData line, final BitmapFont font) {
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