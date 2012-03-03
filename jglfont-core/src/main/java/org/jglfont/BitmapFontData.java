package org.jglfont;

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

/**
 * A Bitmap Font.
 * @author void
 */
public class BitmapFontData {
  /**
   * Name of the font.
   */
  private String name;

  /**
   * All of the bitmaps this font requires (filenames). These are images
   * that are bitmapWidth * bitmapHeight in size each.
   */
  private Map<Integer, String> bitmaps = new TreeMap<Integer, String>();

  /**
   * Width of a single font bitmap.
   */
  private int bitmapWidth;

  /**
   * Height of a single font bitmap.
   */
  private int bitmapHeight;

  /**
   * Height of a single line.
   */
  private int lineHeight;

  /**
   * CharacterInfo for all characters in the font file.
   */
  private Map<Character, BitmapFontCharacterInfo> characters = new Hashtable<Character, BitmapFontCharacterInfo> ();

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * @return the bitmaps
   */
  public Map<Integer, String> getBitmaps() {
    return bitmaps;
  }

  /**
   * @param bitmaps the bitmaps to set
   */
  public void addBitmap(final int index, final String name) {
    bitmaps.put(index, name);
  }

  /**
   * @return the bitmapWidth
   */
  public int getBitmapWidth() {
    return bitmapWidth;
  }

  /**
   * @param bitmapWidth the bitmapWidth to set
   */
  public void setBitmapWidth(final int bitmapWidth) {
    this.bitmapWidth = bitmapWidth;
  }

  /**
   * @return the bitmapHeight
   */
  public int getBitmapHeight() {
    return bitmapHeight;
  }

  /**
   * @param bitmapHeight the bitmapHeight to set
   */
  public void setBitmapHeight(final int bitmapHeight) {
    this.bitmapHeight = bitmapHeight;
  }

  /**
   * @return the lineHeight
   */
  public int getLineHeight() {
    return lineHeight;
  }

  /**
   * @param lineHeight the lineHeight to set
   */
  public void setLineHeight(final int lineHeight) {
    this.lineHeight = lineHeight;
  }

  /**
   * @return the characters
   */
  public Map<Character, BitmapFontCharacterInfo> getCharacters() {
    return characters;
  }

  /**
   * @param characters the characters to set
   */
  public void addCharacter(final Character character, final BitmapFontCharacterInfo characterInfo) {
    this.characters.put(character, characterInfo);
  }
}
