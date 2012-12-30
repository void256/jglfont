package org.jglfont;

import java.io.IOException;
import java.io.InputStream;

import org.jglfont.impl.BitmapFontImpl;
import org.jglfont.impl.format.BitmapFontLoader;
import org.jglfont.impl.format.angelcode.AngelCodeBitmapFontLoader;
import org.jglfont.impl.format.angelcode.AngelCodeLineProcessors;
import org.jglfont.spi.BitmapFontRenderer;

public class BitmapFontFactory {
  private final BitmapFontLoader fontLoader;
  private final BitmapFontRenderer fontRenderer;

  public BitmapFontFactory(final BitmapFontRenderer fontRenderer) {
    this.fontRenderer = fontRenderer;

    // we currently only support AngelCodeBitmapFont format - if this changes somewhere this would need to be modified
    this.fontLoader = new AngelCodeBitmapFontLoader(new AngelCodeLineProcessors());
  }

  public BitmapFont loadFont(final InputStream stream) throws IOException {
    BitmapFontImpl impl = new BitmapFontImpl(fontRenderer, fontLoader);
    impl.load(stream);
    return impl;
  }
}
