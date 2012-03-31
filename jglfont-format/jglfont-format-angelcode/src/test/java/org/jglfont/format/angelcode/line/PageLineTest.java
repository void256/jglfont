package org.jglfont.format.angelcode.line;

import static org.junit.Assert.*;

import org.jglfont.BitmapFontData;
import org.jglfont.format.angelcode.LineData;
import org.jglfont.format.angelcode.line.PageLine;
import org.junit.Test;


public class PageLineTest {
  private PageLine pageLine = new PageLine();
  private LineData line = new LineData();
  private BitmapFontData font = new BitmapFontData();

  @Test
  public void testMissingAttributes() {
    assertFalse(pageLine.process(line, font));
    assertTrue(font.getBitmaps().isEmpty());
    assertTrue(font.getCharacters().isEmpty());
  }

  @Test
  public void testWithAttributes() {
    line.put("id", "1");
    line.put("file", "filename.png");

    assertTrue(pageLine.process(line, font));
    assertTrue(font.getCharacters().isEmpty());
    assertEquals(1, font.getBitmaps().size());
    assertEquals("filename.png", font.getBitmaps().get(1));
  }
}
