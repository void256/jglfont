package de.lessvoid.jglfont.loader;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.lessvoid.jglfont.loader.FontLoaderFactory;
import de.lessvoid.jglfont.loader.angelcode.AngelCodeFontLoader;

public class FontLoaderFactoryTest {
  private FontLoaderFactory factory = new FontLoaderFactory();

  @Test
  public void test() {
    assertTrue(factory.getAngelCodeFontLoader() instanceof AngelCodeFontLoader);
  }
}
