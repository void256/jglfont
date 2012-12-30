package org.jglfont.impl.format;

import java.util.Hashtable;
import java.util.Map;

/**
 * Informations for an individual character in a BitmapFontData.
 * @author void
 */
public class BitmapFontCharacterInfo {
  /**
   * id.
   */
  private int id;

  /**
   * x position.
   */
  private int x;

  /**
   * y position.
   */
  private int y;

  /**
   * width.
   */
  private int width;

  /**
   * height.
   */
  private int height;

  /**
   * xoffset.
   */
  private int xoffset;

  /**
   * yoffset.
   */
  private int yoffset;

  /**
   * xadvance.
   */
  private int xadvance;

  /**
   * page.
   */
  private int page;

  /**
   * kerning information.
   */
  private Map<Character, Integer> kerning = new Hashtable<Character, Integer>();

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(final int id) {
    this.id = id;
  }

  /**
   * @return the x
   */
  public int getX() {
    return x;
  }

  /**
   * @param x the x to set
   */
  public void setX(final int x) {
    this.x = x;
  }

  /**
   * @return the y
   */
  public int getY() {
    return y;
  }

  /**
   * @param y the y to set
   */
  public void setY(final int y) {
    this.y = y;
  }

  /**
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * @param width the width to set
   */
  public void setWidth(final int width) {
    this.width = width;
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * @param height the height to set
   */
  public void setHeight(final int height) {
    this.height = height;
  }

  /**
   * @return the xoffset
   */
  public int getXoffset() {
    return xoffset;
  }

  /**
   * @param xoffset the xoffset to set
   */
  public void setXoffset(final int xoffset) {
    this.xoffset = xoffset;
  }

  /**
   * @return the yoffset
   */
  public int getYoffset() {
    return yoffset;
  }

  /**
   * @param yoffset the yoffset to set
   */
  public void setYoffset(final int yoffset) {
    this.yoffset = yoffset;
  }

  /**
   * @return the xadvance
   */
  public int getXadvance() {
    return xadvance;
  }

  /**
   * @param xadvance the xadvance to set
   */
  public void setXadvance(final int xadvance) {
    this.xadvance = xadvance;
  }

  /**
   * @return the page
   */
  public int getPage() {
    return page;
  }

  /**
   * @param page the page to set
   */
  public void setPage(final int page) {
    this.page = page;
  }

  /**
   * @return the kerning
   */
  public Map<Character, Integer> getKerning() {
    return kerning;
  }

  /**
   * @param kerning the kerning to set
   */
  public void addKerning(final Character character, final Integer kerning) {
    this.kerning.put(character, kerning);
  }
}
