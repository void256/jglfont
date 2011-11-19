package de.lessvoid.jglfont.loader.angelcode.line;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.Line;
import de.lessvoid.jglfont.loader.angelcode.LineData;

/**
 * CommonLine
 * @author void
 */
public class CommonLine implements Line {
  @Override
  public boolean process(final LineData line, final BitmapFont font) {
    if (!line.hasValue("scaleW") &&
        !line.hasValue("scaleH")) {
      return false;
    }
    font.setBitmapWidth(line.getInt("scaleW"));
    font.setBitmapHeight(line.getInt("scaleH"));
    return true;
  }
}