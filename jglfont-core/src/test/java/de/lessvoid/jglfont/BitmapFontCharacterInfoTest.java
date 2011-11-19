package de.lessvoid.jglfont;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lessvoid.jglfont.BitmapFontCharacterInfo;

public class BitmapFontCharacterInfoTest {
  private BitmapFontCharacterInfo charInfo = new BitmapFontCharacterInfo();

  @Test
  public void testDefaults() {
    assertEquals(0, charInfo.getId());
    assertEquals(0, charInfo.getWidth());
    assertEquals(0, charInfo.getHeight());
    assertEquals(0, charInfo.getPage());
    assertEquals(0, charInfo.getX());
    assertEquals(0, charInfo.getY());
    assertEquals(0, charInfo.getXadvance());
    assertEquals(0, charInfo.getXoffset());
    assertEquals(0, charInfo.getYoffset());
    assertTrue(charInfo.getKerning().isEmpty());
  }

  @Test
  public void testKerning() throws Exception {
    charInfo.addKerning('A', 2);
    assertEquals(1, charInfo.getKerning().size());
    assertEquals(2, charInfo.getKerning().get('A'));
  }

  @Test
  public void testModify() throws Exception {
    charInfo.setId(12);
    charInfo.setWidth(13);
    charInfo.setHeight(14);
    charInfo.setPage(15);
    charInfo.setX(16);
    charInfo.setY(17);
    charInfo.setXadvance(18);
    charInfo.setXoffset(19);
    charInfo.setYoffset(20);

    assertEquals(12, charInfo.getId());
    assertEquals(13, charInfo.getWidth());
    assertEquals(14, charInfo.getHeight());
    assertEquals(15, charInfo.getPage());
    assertEquals(16, charInfo.getX());
    assertEquals(17, charInfo.getY());
    assertEquals(18, charInfo.getXadvance());
    assertEquals(19, charInfo.getXoffset());
    assertEquals(20, charInfo.getYoffset());
  }
}
