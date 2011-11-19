package de.lessvoid.jglfont.loader.angelcode.line;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.Line;
import de.lessvoid.jglfont.loader.angelcode.LineData;

/**
 * PageLine
 * @author void
 */
public class PageLine implements Line {
  @Override
  public boolean process(final LineData line, final BitmapFont font) {
    if (!line.hasValue("id") ||
        !line.hasValue("file")) {
      return false;
    }
    font.addBitmap(line.getInt("id"), line.getString("file"));
    return true;
  }
}