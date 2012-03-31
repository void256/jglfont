package org.jglfont.angelcode.line;

import static org.junit.Assert.*;

import org.jglfont.BitmapFontData;
import org.jglfont.angelcode.LineData;
import org.jglfont.angelcode.line.InfoLine;
import org.junit.Test;


public class InfoLineTest {
  private InfoLine infoLine = new InfoLine();
  private LineData line = new LineData();
  private BitmapFontData font = new BitmapFontData();

  @Test
  public void testWithMissingAttribute() {
    assertFalse(infoLine.process(line, font));
    assertTrue(font.getBitmaps().isEmpty());
    assertTrue(font.getCharacters().isEmpty());
    assertNull(font.getName());
  }

  @Test
  public void testComplete() {
    line.put("face", "Arial");

    assertTrue(infoLine.process(line, font));

    assertTrue(font.getBitmaps().isEmpty());
    assertTrue(font.getCharacters().isEmpty());
    assertEquals("Arial", font.getName());
  }
}
