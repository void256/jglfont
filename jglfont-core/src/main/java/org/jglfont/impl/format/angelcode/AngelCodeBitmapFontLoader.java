package org.jglfont.impl.format.angelcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jglfont.impl.format.BitmapFontData;
import org.jglfont.impl.format.BitmapFontLoader;


/**
 * A BitmapFontDataLoader implementation for AngelCode Font file.
 * @author void
 */
public class AngelCodeBitmapFontLoader implements BitmapFontLoader {
  private final static Logger log = Logger.getLogger(AngelCodeBitmapFontLoader.class.getName());
  private final AngelCodeLineParser parser = new AngelCodeLineParser();
  private final AngelCodeLineData parsed = new AngelCodeLineData();
  private final AngelCodeLineProcessors lineProcessors;

  public AngelCodeBitmapFontLoader(final AngelCodeLineProcessors lineProcessors) {
    this.lineProcessors = lineProcessors;
  }

  public BitmapFontData load(final InputStream in) throws IOException {
    BitmapFontData result = new BitmapFontData();
    load(in, result);
    return result;
  }

  private void load(final InputStream in, final BitmapFontData bitmapFont) throws IOException {
    if (in == null) {
      throw new IOException("InputStream is null");
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    try {
      while (true) {
        String line = reader.readLine();
        if (line == null) {
          break;
        }

        String[] split = line.split(" ");
        if (split[0].length() == 0) {
          break;
        }

        parser.parse(line, parsed);

        AngelCodeLine processor = lineProcessors.get(split[0]);
        if (processor != null) {
          if (!processor.process(parsed, bitmapFont)) {
            log.warning("parsing error for line [" + line + "] using " + processor + " with " + parsed);
          }
        }
      }
    } catch (Exception e) {
      log.log(Level.WARNING, "error while parsing font file: ", e);
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
      }
    }
  }
}
