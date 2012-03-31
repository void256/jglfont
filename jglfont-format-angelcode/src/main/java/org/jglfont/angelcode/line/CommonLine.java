package org.jglfont.angelcode.line;

import org.jglfont.BitmapFontData;
import org.jglfont.angelcode.Line;
import org.jglfont.angelcode.LineData;

/**
 * CommonLine
 * @author void
 */
public class CommonLine implements Line {
  @Override
  public boolean process(final LineData line, final BitmapFontData font) {
    if (!line.hasValue("scaleW") &&
        !line.hasValue("scaleH")) {
      return false;
    }
    font.setBitmapWidth(line.getInt("scaleW"));
    font.setBitmapHeight(line.getInt("scaleH"));
    return true;
  }
}