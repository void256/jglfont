package org.jglfont.impl.format;

import org.jglfont.spi.JGLFontRenderer;
import org.jglfont.spi.ResourceLoader;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * User: iamtakingiteasy
 * Date: 2013-12-29
 * Time: 03:25
 */
public class JGLAwtFontData extends JGLAbstractFontData {
  public static int side = 1;
  public static int product = side * side;
;
  private Font font;

  private FontMetrics fontMetrics;

  private int glyphWidth;
  private int glyphHeight;

  public JGLAwtFontData(final JGLFontRenderer renderer, final ResourceLoader resourceLoader, final Font font, int glyphSide) {
    super(renderer, resourceLoader);
    this.font = font;

    Canvas canvas = new Canvas();
    fontMetrics = canvas.getFontMetrics(font);

    glyphWidth = fontMetrics.getMaxAdvance();
    glyphHeight = fontMetrics.getHeight();

    setLineHeight(glyphHeight);

    setBitmapWidth(glyphWidth * side);
    setBitmapHeight(glyphHeight * side);

    setName(font.getName() + "-" + font.getSize() + "-" + font.getStyle());
  }

  public Graphics2D createGraphics(BufferedImage image) {
    Graphics2D glyphGraphics = image.createGraphics();
    glyphGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    glyphGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    glyphGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    glyphGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    glyphGraphics.setComposite(AlphaComposite.Clear);
    glyphGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    glyphGraphics.setFont(font);
    glyphGraphics.setComposite(AlphaComposite.SrcOver);

    return glyphGraphics;
  }

  @Override
  public void init() {
  }

  private void loadPage(final int page) {
    ByteBuffer texture = ByteBuffer.allocateDirect(getBitmapWidth() * getBitmapHeight() * 4);
    texture.order(ByteOrder.LITTLE_ENDIAN);

    BufferedImage glyphImage = new BufferedImage(bitmapWidth, bitmapHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D glyphGraphics = createGraphics(glyphImage);

    String bitmapId = font.getName() + "-" + font.getSize() + "-" + font.getStyle() + "-" + page;

    glyphGraphics.setColor(Color.white);
    for (int i = 0; i < product; i++) {
      int ch = page * product + i;
      char[] codepoint = Character.toChars(ch);

      GlyphVector glyphVector = font.layoutGlyphVector(glyphGraphics.getFontRenderContext(), codepoint, 0, codepoint.length, Font.LAYOUT_LEFT_TO_RIGHT);

      int xPos = i%side;
      int yPos = i/side;

      int x = xPos * glyphWidth;
      int y = yPos * glyphHeight;

      glyphGraphics.drawString(new String(codepoint), x, y + (fontMetrics.getHeight() - fontMetrics.getDescent()));

      JGLFontGlyphInfo info = new JGLFontGlyphInfo();
      info.setPage(bitmapId);
      info.setX(x);
      info.setY(y);
      info.setWidth(glyphWidth);
      info.setHeight(glyphHeight);
      info.setXadvance((int) glyphVector.getGlyphMetrics(0).getAdvanceX());
      info.setXoffset(0);
      info.setYoffset(0);
      characters.put(ch, info);
    }
    texture.asIntBuffer().put((int[]) glyphImage.getRaster().getDataElements(0, 0, getBitmapWidth(), getBitmapHeight(), null));

    glyphGraphics.dispose();

    try {
      getRenderer().registerBitmap(getName(), bitmapId, texture, getBitmapWidth(), getBitmapHeight(), bitmapId);
      for (int i = 0; i < product; i++) {
        int ch = page * product + i;
        JGLFontGlyphInfo info = characters.get(ch);
        getRenderer().registerGlyph(
            getName(),
            info.getPage(),
            ch,
            info.getXoffset(),
            info.getYoffset(),
            info.getWidth(),
            info.getHeight(),
            info.getX()/(float)getBitmapWidth(),
            info.getY()/(float)getBitmapHeight(),
            (info.getX() + info.getWidth()) / (float)getBitmapWidth(),
            (info.getY() + info.getHeight()) / (float)getBitmapHeight()
        );
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void preProcessGlyph(Integer codepoint) {
    if (!characters.containsKey(codepoint)) {
      int page = codepoint / product;
      loadPage(page);
    }
  }
}
