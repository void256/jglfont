package org.jglfont.format.angelcode.line;

import static org.junit.Assert.*;

import org.jglfont.impl.format.BitmapFontData;
import org.jglfont.impl.format.angelcode.AngelCodeLineData;
import org.jglfont.impl.format.angelcode.line.CharsLine;
import org.junit.Test;


public class CharsLineTest {
  private CharsLine charsLine = new CharsLine();
  private AngelCodeLineData line = new AngelCodeLineData();
  private BitmapFontData font = new BitmapFontData();

  @Test
  public void testNoImpl() {
    assertTrue(charsLine.process(line, font));
    assertTrue(font.getBitmaps().isEmpty());
  }
}
