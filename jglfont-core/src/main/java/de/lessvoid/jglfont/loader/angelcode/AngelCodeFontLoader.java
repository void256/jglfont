package de.lessvoid.jglfont.loader.angelcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.line.LineProcessors;

/**
 * A FontLoader implementation for AngelCode Font file.
 * @author void
 */
public class AngelCodeFontLoader implements de.lessvoid.jglfont.loader.FontLoader {
  private Logger log = Logger.getLogger(AngelCodeFontLoader.class.getName());
  private LineProcessors lineProcessors;
  private LineParser parser = new LineParser();
  private LineData parsed = new LineData();

  public AngelCodeFontLoader(final LineProcessors lineProcessors) {
    this.lineProcessors = lineProcessors;
  }

  @Override
  public BitmapFont load(final InputStream in) throws IOException {
    BitmapFont result = new BitmapFont();
    load(in, result);
    return result;
  }

  private void load(final InputStream in, final BitmapFont bitmapFont) throws IOException {
    if (in == null) {
      return;
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

        Line processor = lineProcessors.get(split[0]);
        if (!processor.process(parsed, bitmapFont)) {
          log.warning("parsing error for line [" + line + "]");
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
