package org.jglfont.format.angelcode.line;

import static org.junit.Assert.*;

import org.jglfont.impl.format.BitmapFontData;
import org.jglfont.impl.format.angelcode.AngelCodeLineData;
import org.jglfont.impl.format.angelcode.line.InfoLine;
import org.junit.Test;


public class InfoLineTest {
  private InfoLine infoLine = new InfoLine();
  private AngelCodeLineData line = new AngelCodeLineData();
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
