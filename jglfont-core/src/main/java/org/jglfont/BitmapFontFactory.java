package org.jglfont;

import java.io.IOException;
import java.io.InputStream;

import org.jglfont.impl.BitmapFontImpl;
import org.jglfont.impl.ClasspathResourceLoader;
import org.jglfont.impl.format.BitmapFontLoader;
import org.jglfont.impl.format.angelcode.AngelCodeBitmapFontLoader;
import org.jglfont.impl.format.angelcode.AngelCodeLineProcessors;
import org.jglfont.spi.BitmapFontRenderer;
import org.jglfont.spi.ResourceLoader;

public class BitmapFontFactory {
  private final BitmapFontRenderer fontRenderer;
  private final ResourceLoader resourceLoader;
  private final BitmapFontLoader fontLoader;

  public BitmapFontFactory(final BitmapFontRenderer fontRenderer) {
    this.fontRenderer = fontRenderer;
    this.resourceLoader = new ClasspathResourceLoader();

    // we currently only support AngelCodeBitmapFont format - if this changes later this would need to be modified
    this.fontLoader = new AngelCodeBitmapFontLoader(new AngelCodeLineProcessors());
  }

  public BitmapFontFactory(final BitmapFontRenderer fontRenderer, final ResourceLoader resourceLoader) {
    this.fontRenderer = fontRenderer;
    this.resourceLoader = resourceLoader;

    // we currently only support AngelCodeBitmapFont format - if this changes later this would need to be modified
    this.fontLoader = new AngelCodeBitmapFontLoader(new AngelCodeLineProcessors());
  }

  public BitmapFont loadFont(final InputStream stream) throws IOException {
    return new BitmapFontImpl(fontRenderer, resourceLoader, fontLoader.load(stream));
  }
}
