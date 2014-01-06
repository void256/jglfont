package org.jglfont.renderer.lwjgl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Hashtable;
import java.util.Map;

import org.jglfont.spi.JGLFontRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Simple OpenGL display list based JGLFontRenderer implementation. This is meant as a demo implementation and is
 * using outdated OpenGL calls.
 *
 * @author void
 */
public class LwjglDisplayListFontRenderer implements JGLFontRenderer {
  private Map<String, LwjglBitmapFontImage> textures = new Hashtable<String, LwjglBitmapFontImage>();
  private Map<Integer, Integer> displayListMap = new Hashtable<Integer, Integer>();
  private LwjglBitmapFontImage currentTexture;

  @Override
  public void registerBitmap(final String fontName, final String bitmapId, final InputStream data, final String filename) {
    textures.put(bitmapId, new LwjglBitmapFontImage(data, filename, false));
  }

  @Override
  public void registerBitmap(final String fontName, String bitmapId, ByteBuffer data, int width, int height, String filename) throws IOException {
    textures.put(bitmapId, new LwjglBitmapFontImage(data, width, height, filename, false));
  }

  @Override
  public void registerGlyph(
      final String fontName,
      final String bitmapId,
      final int character,
      final int xoffset,
      final int yoffset,
      final int width,
      final int height,
      final float u0,
      final float v0,
      final float u1,
      final float v1) {
    int listId = GL11.glGenLists(1);
    displayListMap.put(character, listId);

    GL11.glNewList(listId, GL11.GL_COMPILE);
    GL11.glBegin(GL11.GL_QUADS);

      GL11.glTexCoord2f(u0, v0);
      GL11.glVertex2f(xoffset, yoffset);

      GL11.glTexCoord2f(u0, v1);
      GL11.glVertex2f(xoffset, yoffset + height);

      GL11.glTexCoord2f(u1, v1);
      GL11.glVertex2f(xoffset + width, yoffset + height);

      GL11.glTexCoord2f(u1, v0);
      GL11.glVertex2f(xoffset + width, yoffset);

    GL11.glEnd();
    GL11.glEndList();
  }

  @Override
  public void prepare() {
  }

  @Override
  public void beforeRender(final String fontName) {
    currentTexture = null;

    GL11.glMatrixMode(GL11.GL_MODELVIEW);
    GL11.glPushMatrix();
  }

  @Override
  public int preProcess(final String text, final int offset) {
    return offset;
  }

  @Override
  public void render(
      final String fontName,
      final String bitmapId,
      final int x,
      final int y,
      final int c,
      final float sx,
      final float sy,
      final float r,
      final float g,
      final float b,
      final float a) {
    LwjglBitmapFontImage texture = textures.get(bitmapId);
    if (currentTexture != texture) {
      texture.bind();
      currentTexture = texture;
    }

    GL11.glLoadIdentity();
    GL11.glTranslatef(x, y, 0.0f);
    GL11.glScalef(sx, sy, 1.f);
    GL11.glColor4f(r, g, b, a);
    GL11.glCallList(displayListMap.get(c));
  }

  @Override
  public void afterRender(final String fontName) {
    GL11.glPopMatrix();
  }

  @Override
  public int preProcessForLength(final String text, final int offset) {
    return offset;
  }
}
