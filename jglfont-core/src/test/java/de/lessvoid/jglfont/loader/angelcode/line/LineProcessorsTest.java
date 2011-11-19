package de.lessvoid.jglfont.loader.angelcode.line;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lessvoid.jglfont.loader.angelcode.line.CharLine;
import de.lessvoid.jglfont.loader.angelcode.line.CharsLine;
import de.lessvoid.jglfont.loader.angelcode.line.CommonLine;
import de.lessvoid.jglfont.loader.angelcode.line.InfoLine;
import de.lessvoid.jglfont.loader.angelcode.line.KerningLine;
import de.lessvoid.jglfont.loader.angelcode.line.KerningsLine;
import de.lessvoid.jglfont.loader.angelcode.line.LineProcessors;
import de.lessvoid.jglfont.loader.angelcode.line.PageLine;

public class LineProcessorsTest {
  private LineProcessors lineProcessor = new LineProcessors();

  @Test
  public void testGet() {
    assertTrue(lineProcessor.get("line") instanceof CharLine);
    assertTrue(lineProcessor.get("chars") instanceof CharsLine);
    assertTrue(lineProcessor.get("common") instanceof CommonLine);
    assertTrue(lineProcessor.get("info") instanceof InfoLine);
    assertTrue(lineProcessor.get("kerning") instanceof KerningLine);
    assertTrue(lineProcessor.get("kernings") instanceof KerningsLine);
    assertTrue(lineProcessor.get("page") instanceof PageLine);
    assertNull(lineProcessor.get("unknown"));
  }

}
