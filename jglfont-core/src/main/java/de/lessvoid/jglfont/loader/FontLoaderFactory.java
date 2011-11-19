package de.lessvoid.jglfont.loader;

import de.lessvoid.jglfont.loader.angelcode.AngelCodeFontLoader;
import de.lessvoid.jglfont.loader.angelcode.line.LineProcessors;

public class FontLoaderFactory {
  public AngelCodeFontLoader getAngelCodeFontLoader() {
    return new AngelCodeFontLoader(new LineProcessors());
  }
}
