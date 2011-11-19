package de.lessvoid.jglfont.loader.angelcode;

import de.lessvoid.jglfont.BitmapFont;

/**
 * A Line interface implementation handles exactly one line type
 * from the Angelcode font file and extracts the data of the line
 * into the given BitmapFont.
 * @author void
 */
public interface Line {
  /**
   * Take the line which represents the current line with all data
   * split into key/value types and fill the corresponding properties
   * of the given BitmapFont font.
   * @param line the line
   * @param font the font
   */
  boolean process(LineData line, BitmapFont font);
}
