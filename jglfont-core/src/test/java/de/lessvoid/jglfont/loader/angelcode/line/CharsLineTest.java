package de.lessvoid.jglfont.loader.angelcode.line;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.LineData;
import de.lessvoid.jglfont.loader.angelcode.line.CharsLine;

public class CharsLineTest {
  private CharsLine charsLine = new CharsLine();
  private LineData line = new LineData();
  private BitmapFont font = new BitmapFont();

  @Test
  public void testNoImpl() {
    assertTrue(charsLine.process(line, font));
    assertTrue(font.getBitmaps().isEmpty());
  }
}
