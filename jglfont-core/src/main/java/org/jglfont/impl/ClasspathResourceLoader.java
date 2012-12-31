package org.jglfont.impl;

import java.io.InputStream;

import org.jglfont.spi.ResourceLoader;

public class ClasspathResourceLoader implements ResourceLoader {

  @Override
  public InputStream load(final String filename) {
    return Thread.currentThread().getClass().getResourceAsStream("/" + filename);
  }
}
