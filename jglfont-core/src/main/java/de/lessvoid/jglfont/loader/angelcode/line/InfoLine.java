package de.lessvoid.jglfont.loader.angelcode.line;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.Line;
import de.lessvoid.jglfont.loader.angelcode.LineData;

/**
 * InfoLine
 * @author void
 */
public class InfoLine implements Line {
  @Override
  public boolean process(final LineData line, final BitmapFont font) {
    if (!line.hasValue("face")) {
      return false;
    }
    font.setName(line.getString("face"));
    return true;
  }
}