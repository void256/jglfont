package org.jglfont.angelcode.line;

import static org.junit.Assert.*;

import org.jglfont.BitmapFontData;
import org.jglfont.angelcode.LineData;
import org.jglfont.angelcode.line.CommonLine;
import org.junit.Test;


public class CommonLineTest {
  private CommonLine commonLine = new CommonLine();
  private LineData line = new LineData();
  private BitmapFontData font = new BitmapFontData();

  @Test
  public void testMandatoryAttributesMissing() {
    assertFalse(commonLine.process(line, font));

    assertTrue(font.getBitmaps().isEmpty());
    assertTrue(font.getCharacters().isEmpty());
    assertEquals(0, font.getBitmapWidth());
    assertEquals(0, font.getBitmapHeight());
  }

  @Test
  public void testWithAttributes() {
    line.put("scaleW", "1000");
    line.put("scaleH", "700");

    assertTrue(commonLine.process(line, font));

    assertTrue(font.getBitmaps().isEmpty());
    assertTrue(font.getCharacters().isEmpty());
    assertEquals(1000, font.getBitmapWidth());
    assertEquals(700, font.getBitmapHeight());
  }
}
