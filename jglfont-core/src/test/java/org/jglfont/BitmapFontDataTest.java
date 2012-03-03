package org.jglfont;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jglfont.BitmapFontCharacterInfo;
import org.jglfont.BitmapFontData;
import org.junit.Test;


public class BitmapFontDataTest {
  BitmapFontData font = new BitmapFontData();

  @Test
  public void testNameDefault() {
    assertNull(font.getName());
  }

  @Test
  public void testName() {
    font.setName("huhu");
    assertEquals("huhu", font.getName());
  }

  @Test
  public void testBitmapWidthDefault() {
    assertEquals(0, font.getBitmapWidth());
  }

  @Test
  public void testBitmapWidth() {
    font.setBitmapWidth(42);
    assertEquals(42, font.getBitmapWidth());
  }

  @Test
  public void testBitmapHeightDefault() {
    assertEquals(0, font.getBitmapWidth());
  }

  @Test
  public void testBitmapHeight() {
    font.setBitmapWidth(42);
    assertEquals(42, font.getBitmapWidth());
  }

  @Test
  public void testLineHeightDefault() {
    assertEquals(0, font.getLineHeight());
  }

  @Test
  public void testLineHeight() {
    font.setLineHeight(42);
    assertEquals(42, font.getLineHeight());
  }

  @Test
  public void testGetCharactersDefault() {
    assertTrue(font.getCharacters().isEmpty());
  }

  @Test
  public void testAddCharacter() {
    BitmapFontCharacterInfo info = new BitmapFontCharacterInfo();
    font.addCharacter('A', info);
    assertEquals(info, font.getCharacters().get('A'));
  }

  @Test
  public void testGetBitmaps() {
    assertTrue(font.getBitmaps().isEmpty());
  }

  @Test
  public void testAddBitmap() {
    font.addBitmap(0, "name");
    assertEquals("name", font.getBitmaps().get(0));
  }

  @Test
  public void testAddBitmaps() {
    font.addBitmap(0, "name-1");
    font.addBitmap(1, "name-2");
    assertEquals("name-1", font.getBitmaps().get(0));
    assertEquals("name-2", font.getBitmaps().get(1));
  }
}
