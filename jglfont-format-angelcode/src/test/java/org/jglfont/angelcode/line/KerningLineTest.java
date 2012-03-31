package org.jglfont.angelcode.line;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jglfont.BitmapFontCharacterInfo;
import org.jglfont.BitmapFontData;
import org.jglfont.angelcode.LineData;
import org.jglfont.angelcode.line.KerningLine;
import org.junit.Test;


public class KerningLineTest {
  private KerningLine kerningLine = new KerningLine();
  private LineData line = new LineData();
  private BitmapFontData font = new BitmapFontData();

  @Test
  public void testWithMissingAttribute() {
    assertFalse(kerningLine.process(line, font));
    assertTrue(font.getBitmaps().isEmpty());
    assertTrue(font.getCharacters().isEmpty());
  }

  @Test
  public void testMissingFirstChar() {
    line.put("first", "99"); // c
    line.put("second", "100"); // d
    line.put("amount", "42");

    assertFalse(kerningLine.process(line, font));
    assertTrue(font.getBitmaps().isEmpty());
    assertTrue(font.getCharacters().isEmpty());
  }

  @Test
  public void testComplete() {
    font.addCharacter('c', new BitmapFontCharacterInfo());

    line.put("first", "99"); // c
    line.put("second", "100"); // d
    line.put("amount", "42");

    assertTrue(kerningLine.process(line, font));

    assertTrue(font.getBitmaps().isEmpty());
    assertEquals(1, font.getCharacters().size());
    BitmapFontCharacterInfo charInfo = font.getCharacters().get('c');
    assertEquals(42, charInfo.getKerning().get('d'));
  }
}
