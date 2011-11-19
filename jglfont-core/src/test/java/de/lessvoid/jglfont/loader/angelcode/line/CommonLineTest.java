package de.lessvoid.jglfont.loader.angelcode.line;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.LineData;
import de.lessvoid.jglfont.loader.angelcode.line.CommonLine;

public class CommonLineTest {
  private CommonLine commonLine = new CommonLine();
  private LineData line = new LineData();
  private BitmapFont font = new BitmapFont();

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
