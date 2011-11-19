package de.lessvoid.jglfont.loader.angelcode.line;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.BitmapFontCharacterInfo;
import de.lessvoid.jglfont.loader.angelcode.LineData;
import de.lessvoid.jglfont.loader.angelcode.line.CharLine;

public class CharLineTest {
  private CharLine charLine = new CharLine();
  private LineData line = new LineData();
  private BitmapFont font = new BitmapFont();

  @Test
  public void testMissingMandatoryValue() {
    assertFalse(charLine.process(line, font));
    assertTrue(font.getCharacters().isEmpty());
  }

  @Test
  public void testWithMinimumValues() {
    line.put("id", "99");
    line.put("x", "12");
    line.put("y", "13");
    line.put("width", "100");
    line.put("height", "102");
    line.put("page", "1");

    assertTrue(charLine.process(line, font));

    assertTrue(font.getBitmaps().isEmpty());
    assertEquals(1, font.getCharacters().size());
    BitmapFontCharacterInfo charInfo = font.getCharacters().get('c');
    assertEquals(99, charInfo.getId());
    assertEquals(12, charInfo.getX());
    assertEquals(13, charInfo.getY());
    assertEquals(100, charInfo.getWidth());
    assertEquals(102, charInfo.getHeight());
    assertEquals(1, charInfo.getPage());
    assertEquals(0, charInfo.getXoffset());
    assertEquals(0, charInfo.getYoffset());
    assertEquals(0, charInfo.getXadvance());
  }

  @Test
  public void testWithAllValues() {
    line.put("id", "99");
    line.put("x", "12");
    line.put("y", "13");
    line.put("width", "100");
    line.put("height", "102");
    line.put("page", "1");
    line.put("xoffset", "42");
    line.put("yoffset", "43");
    line.put("xadvance", "44");

    assertTrue(charLine.process(line, font));

    assertTrue(font.getBitmaps().isEmpty());
    assertEquals(1, font.getCharacters().size());
    BitmapFontCharacterInfo charInfo = font.getCharacters().get('c');
    assertEquals(99, charInfo.getId());
    assertEquals(12, charInfo.getX());
    assertEquals(13, charInfo.getY());
    assertEquals(100, charInfo.getWidth());
    assertEquals(102, charInfo.getHeight());
    assertEquals(1, charInfo.getPage());
    assertEquals(42, charInfo.getXoffset());
    assertEquals(43, charInfo.getYoffset());
    assertEquals(44, charInfo.getXadvance());
    assertEquals(102 + 43, font.getLineHeight());
  }
}
