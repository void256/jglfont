package de.lessvoid.jglfont.loader.angelcode.line;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.LineData;
import de.lessvoid.jglfont.loader.angelcode.line.PageLine;

public class PageLineTest {
  private PageLine pageLine = new PageLine();
  private LineData line = new LineData();
  private BitmapFont font = new BitmapFont();

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
