package org.jglfont.angelcode.line;

import static org.junit.Assert.*;

import org.jglfont.angelcode.line.CharLine;
import org.jglfont.angelcode.line.CharsLine;
import org.jglfont.angelcode.line.CommonLine;
import org.jglfont.angelcode.line.InfoLine;
import org.jglfont.angelcode.line.KerningLine;
import org.jglfont.angelcode.line.KerningsLine;
import org.jglfont.angelcode.line.LineProcessors;
import org.jglfont.angelcode.line.PageLine;
import org.junit.Test;


public class LineProcessorsTest {
  private LineProcessors lineProcessor = new LineProcessors();

  @Test
  public void testGet() {
    assertTrue(lineProcessor.get("char") instanceof CharLine);
    assertTrue(lineProcessor.get("chars") instanceof CharsLine);
    assertTrue(lineProcessor.get("common") instanceof CommonLine);
    assertTrue(lineProcessor.get("info") instanceof InfoLine);
    assertTrue(lineProcessor.get("kerning") instanceof KerningLine);
    assertTrue(lineProcessor.get("kernings") instanceof KerningsLine);
    assertTrue(lineProcessor.get("page") instanceof PageLine);
    assertNull(lineProcessor.get("unknown"));
  }

}