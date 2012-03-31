package org.jglfont.format.angelcode.line;

import static org.junit.Assert.*;

import org.jglfont.BitmapFontData;
import org.jglfont.format.angelcode.LineData;
import org.jglfont.format.angelcode.line.CharsLine;
import org.junit.Test;


public class CharsLineTest {
  private CharsLine charsLine = new CharsLine();
  private LineData line = new LineData();
  private BitmapFontData font = new BitmapFontData();

  @Test
  public void testNoImpl() {
    assertTrue(charsLine.process(line, font));
    assertTrue(font.getBitmaps().isEmpty());
  }
}
