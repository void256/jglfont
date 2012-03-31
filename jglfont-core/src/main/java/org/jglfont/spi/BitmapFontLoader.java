package org.jglfont.spi;

import java.io.IOException;
import java.io.InputStream;

import org.jglfont.BitmapFontData;


/**
 * A BitmapFontLoader.
 * @author void
 */
public interface BitmapFontLoader {

  /**
   * Load a font.
   * @param in InputStream
   * @return BitmapFontData
   * @throws IOException
   */
  BitmapFontData load(final InputStream in) throws IOException;
}
