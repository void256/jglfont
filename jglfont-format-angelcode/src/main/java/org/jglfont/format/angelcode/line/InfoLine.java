package org.jglfont.format.angelcode.line;

import org.jglfont.BitmapFontData;
import org.jglfont.format.angelcode.Line;
import org.jglfont.format.angelcode.LineData;

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