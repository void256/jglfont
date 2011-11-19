package de.lessvoid.jglfont.loader;

import java.io.IOException;
import java.io.InputStream;

import de.lessvoid.jglfont.BitmapFont;

/**
 * A FontLoader.
 * @author void
 */
public interface FontLoader {
  BitmapFont load(InputStream in) throws IOException;
}
