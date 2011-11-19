package de.lessvoid.jglfont.loader.angelcode.line;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.LineData;
import de.lessvoid.jglfont.loader.angelcode.line.InfoLine;

public class InfoLineTest {
  private InfoLine infoLine = new InfoLine();
  private LineData line = new LineData();
  private BitmapFont font = new BitmapFont();

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
