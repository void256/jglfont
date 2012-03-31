package org.jglfont.format.angelcode.line;

import static org.junit.Assert.assertTrue;

import org.jglfont.BitmapFontData;
import org.jglfont.format.angelcode.LineData;
import org.jglfont.format.angelcode.line.KerningsLine;
import org.junit.Test;


public class KerningsLineTest {
  private KerningsLine kerningsLine = new KerningsLine();
  private LineData line = new LineData();
  private BitmapFontData font = new BitmapFontData();

  @Test
  public void testNoImpl() {
    assertTrue(kerningsLine.process(line, font));
    assertTrue(font.getBitmaps().isEmpty());
    assertTrue(font.getCharacters().isEmpty());
  }
}
