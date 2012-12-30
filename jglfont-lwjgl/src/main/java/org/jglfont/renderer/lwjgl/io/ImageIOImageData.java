package org.jglfont.renderer.lwjgl.io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

import javax.imageio.ImageIO;

/**
 * An image data provider that uses ImageIO to retrieve image data in a format
 * suitable for creating OpenGL textures. This implementation is used when
 * formats not natively supported by the library are required.
 * 
 * @author kevin
 */
public class ImageIOImageData implements ImageData {
  private static final int COLOR_COMPONENT_COUNT = 3;
  private static final int COLOR_PALETTE_SIZE = 256;
  private static final int COMPONENTS_PER_PIXEL_3 = 3;
  private static final int COMPONENTS_PER_PIXEL_4 = 4;
  private static final int BIT_DEPTH_24 = 24;
  private static final int BIT_DEPTH_32 = 32;

  private static final ColorModel GL_ALPHA_COLOR_MODEL =
      new ComponentColorModel(
          ColorSpace.getInstance(ColorSpace.CS_sRGB),
          new int[] { 8, 8, 8, 8 },
          true,
          false,
          ComponentColorModel.TRANSLUCENT,
          DataBuffer.TYPE_BYTE);

  private static final ColorModel GL_COLOR_MODEL =
      new ComponentColorModel(
          ColorSpace.getInstance(ColorSpace.CS_sRGB),
          new int[] { 8, 8, 8, 0 },
          false,
          false,
          ComponentColorModel.OPAQUE,
          DataBuffer.TYPE_BYTE);

  private int depth;
  private int height;
  private int width;
  private int texWidth;
  private int texHeight;
  private boolean edging = true;

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.nifty.renderer.lwjgl.render.io.ImageData#getDepth()
   */
  public int getDepth() {
    return depth;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.nifty.renderer.lwjgl.render.io.ImageData#getHeight()
   */
  public int getHeight() {
    return height;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.nifty.renderer.lwjgl.render.io.ImageData#getTexHeight()
   */
  public int getTexHeight() {
    return texHeight;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.nifty.renderer.lwjgl.render.io.ImageData#getTexWidth()
   */
  public int getTexWidth() {
    return texWidth;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.lessvoid.nifty.renderer.lwjgl.render.io.ImageData#getWidth()
   */
  public int getWidth() {
    return width;
  }

  public ByteBuffer loadImage(InputStream fis) throws IOException {
    return loadImage(fis, false, null);
  }

  public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
    return loadImage(fis, flipped, false, transparent);
  }

  public ByteBuffer loadImage(
      final InputStream fis,
      final boolean flipped,
      final boolean forceAlpha,
      final int[] transparent) throws IOException {
    boolean alpha = forceAlpha;
    if (transparent != null) {
      alpha = true;
    }

    try {
      BufferedImage bufferedImage = ImageIO.read(fis);
      return imageToByteBuffer(bufferedImage, flipped, alpha, transparent, true, false);
    } finally {
      fis.close();
    }
  }

  public ByteBuffer loadMouseCursorImage(InputStream fis) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(fis);
    return imageToByteBuffer(bufferedImage, true, true, null, false, true);
  }

  public ByteBuffer imageToByteBuffer(
      BufferedImage image,
      boolean flipped,
      boolean forceAlpha,
      int[] transparent,
      boolean powerOfTwoSupport,
      boolean modeARGB) {
    ByteBuffer imageBuffer = null;
    WritableRaster raster;
    BufferedImage texImage;

    int imageWidth = powerOfTwoSupport(image.getWidth(), powerOfTwoSupport);
    int imageHeight = powerOfTwoSupport(image.getHeight(), powerOfTwoSupport);
    this.width = image.getWidth();
    this.height = image.getHeight();
    this.texHeight = imageHeight;
    this.texWidth = imageWidth;

    // create a raster that can be used by OpenGL as a source
    // for a texture
    boolean useAlpha = image.getColorModel().hasAlpha() || forceAlpha;

    if (useAlpha) {
      depth = BIT_DEPTH_32;
      raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, imageWidth, imageHeight, COMPONENTS_PER_PIXEL_4, null);
      texImage = new BufferedImage(GL_ALPHA_COLOR_MODEL, raster, false, new Hashtable());
    } else {
      depth = BIT_DEPTH_24;
      raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, imageWidth, imageHeight, COMPONENTS_PER_PIXEL_3, null);
      texImage = new BufferedImage(GL_COLOR_MODEL, raster, false, new Hashtable());
    }

    // copy the source image into the produced image
    Graphics2D g = (Graphics2D) texImage.getGraphics();

    // only need to blank the image for mac compatibility if we're using alpha
    processUseAlpha(imageWidth, imageHeight, useAlpha, g);
    processFlipped(image, flipped, g);
    processEdging(texImage, imageWidth, imageHeight);

    // build a byte buffer from the temporary image
    // that be used by OpenGL to produce a texture.
    byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
    processTransparent(transparent, data);
    processModeARGB(modeARGB, data);

    imageBuffer = ByteBuffer.allocateDirect(data.length);
    imageBuffer.order(ByteOrder.nativeOrder());
    imageBuffer.put(data, 0, data.length);
    imageBuffer.flip();
    g.dispose();

    return imageBuffer;
  }

  private void processModeARGB(boolean modeARGB, byte[] data) {
    if (!modeARGB) {
      return;
    }

    for (int i = 0; i < data.length; i += 4) {
      byte rr = data[i + 0];
      byte gg = data[i + 1];
      byte bb = data[i + 2];
      byte aa = data[i + 3];
      data[i + 0] = bb;
      data[i + 1] = gg;
      data[i + 2] = rr;
      data[i + 3] = aa;
    }
  }

  private void processTransparent(final int[] transparent, final byte[] data) {
    if (transparent == null) {
      return;
    }

    for (int i = 0; i < data.length; i += 4) {
      boolean match = true;
      for (int c = 0; c < COLOR_COMPONENT_COUNT; c++) {
        int value = data[i + c] < 0 ? COLOR_PALETTE_SIZE + data[i + c] : data[i + c];
        if (value != transparent[c]) {
          match = false;
        }
      }

      if (match) {
        data[i + 3] = 0;
      }
    }
  }

  private void processEdging(BufferedImage texImage, int imageWidth, int imageHeight) {
    if (!edging) {
      return;
    }

    if (height < imageHeight - 1) {
      copyArea(texImage, 0, 0, width, 1, 0, imageHeight - 1);
      copyArea(texImage, 0, height - 1, width, 1, 0, 1);
    }
    if (width < imageWidth - 1) {
      copyArea(texImage, 0, 0, 1, height, imageWidth - 1, 0);
      copyArea(texImage, width - 1, 0, 1, height, 1, 0);
    }
  }

  private void processFlipped(BufferedImage image, boolean flipped, Graphics2D g) {
    if (flipped) {
      g.scale(1, -1);
      g.drawImage(image, 0, -height, null);
    } else {
      g.drawImage(image, 0, 0, null);
    }
  }

  private void processUseAlpha(int imageWidth, int imageHeight, boolean useAlpha, Graphics2D g) {
    if (!useAlpha) {
      return;
    }

    g.setColor(new Color(0f, 0f, 0f, 0f));
    g.fillRect(0, 0, imageWidth, imageHeight);
  }

  private int powerOfTwoSupport(final int originalValue, final boolean powerOfTwoSupport) {
    if (!powerOfTwoSupport) {
      return originalValue;
    }
    int value = 2;
    while (value < originalValue) {
      value *= 2;
    }
    return value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.lessvoid.nifty.renderer.lwjgl.render.io.ImageData#getImageBufferData()
   */
  public ByteBuffer getImageBufferData() {
    throw new UnsupportedOperationException("ImageIOImageData doesn't store it's image.");
  }

  private void copyArea(BufferedImage image, int x, int y, int width, int height, int dx, int dy) {
    Graphics2D g = (Graphics2D) image.getGraphics();
    g.drawImage(image.getSubimage(x, y, width, height), x + dx, y + dy, null);
  }
}
