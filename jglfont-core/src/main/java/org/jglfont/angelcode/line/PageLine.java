package org.jglfont.angelcode.line;

import org.jglfont.BitmapFontData;
import org.jglfont.angelcode.Line;
import org.jglfont.angelcode.LineData;

/**
 * PageLine
 * @author void
 */
public class PageLine implements Line {
  @Override
  public boolean process(final LineData line, final BitmapFontData font) {
    if (!line.hasValue("id") ||
        !line.hasValue("file")) {
      return false;
    }
    font.addBitmap(line.getInt("id"), line.getString("file"));
    return true;
  }
}