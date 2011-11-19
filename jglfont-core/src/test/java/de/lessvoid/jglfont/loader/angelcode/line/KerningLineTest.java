package de.lessvoid.jglfont.loader.angelcode.line;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.BitmapFontCharacterInfo;
import de.lessvoid.jglfont.loader.angelcode.LineData;
import de.lessvoid.jglfont.loader.angelcode.line.KerningLine;

public class KerningLineTest {
  private KerningLine kerningLine = new KerningLine();
  private LineData line = new LineData();
  private BitmapFont font = new BitmapFont();

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
