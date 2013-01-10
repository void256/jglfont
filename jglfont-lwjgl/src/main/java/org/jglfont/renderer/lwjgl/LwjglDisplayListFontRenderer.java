package org.jglfont.renderer.lwjgl;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

import org.jglfont.spi.BitmapFontRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Simple OpenGL display list based BitmapFontRenderer implementation. This is meant as a demo implementation and is
 * using outdated OpenGL calls.
 *
 * @author void
 */
public class LwjglDisplayListFontRenderer implements BitmapFontRenderer {
  private Map<String, LwjglBitmapFontImage> textures = new Hashtable<String, LwjglBitmapFontImage>();
  private Map<Character, Integer> displayListMap = new Hashtable<Character, Integer>();
  private LwjglBitmapFontImage currentTexture;

  @Override
  public void registerBitmap(final String bitmapId, final InputStream data, final String filename) {
    textures.put(bitmapId, new LwjglBitmapFontImage(data, filename, false));
  }

  @Override
  public void registerGlyph(
      final String bitmapId,
      final char character,
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
  public void beforeRender() {
    currentTexture = null;

    GL11.glMatrixMode(GL11.GL_MODELVIEW);
    GL11.glPushMatrix();
  }

  @Override
  public void render(
      final String bitmapId,
      final int x,
      final int y,
      final char c,
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
  public void afterRender() {
    GL11.glPopMatrix();
  }
}
