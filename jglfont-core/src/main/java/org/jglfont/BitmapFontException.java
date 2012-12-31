package org.jglfont;


public class BitmapFontException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public BitmapFontException(final String message) {
    super(message);
  }

  public BitmapFontException(final Exception e) {
    super (e);
  }
}
