package org.jglfont.angelcode.line;

import org.jglfont.BitmapFontData;
import org.jglfont.angelcode.Line;
import org.jglfont.angelcode.LineData;

/**
 * InfoLine
 * @author void
 */
public class InfoLine implements Line {
  @Override
  public boolean process(final LineData line, final BitmapFontData font) {
    if (!line.hasValue("face")) {
      return false;
    }
    font.setName(line.getString("face"));
    return true;
  }
}