package de.lessvoid.jglfont.loader.angelcode.line;

import java.util.Hashtable;
import java.util.Map;

import de.lessvoid.jglfont.loader.angelcode.Line;

public class LineProcessors {
  private Map<String, Line> lineProcessors = new Hashtable<String, Line>();

  public LineProcessors() {
    lineProcessors.put("line", new CharLine());
    lineProcessors.put("chars", new CharsLine());
    lineProcessors.put("common", new CommonLine());
    lineProcessors.put("info", new InfoLine());
    lineProcessors.put("kerning", new KerningLine());
    lineProcessors.put("kernings", new KerningsLine());
    lineProcessors.put("page", new PageLine());
  }

  public Line get(final String lineId) {
    return lineProcessors.get(lineId);
  }
}
