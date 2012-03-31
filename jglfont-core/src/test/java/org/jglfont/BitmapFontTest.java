package org.jglfont;

import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import org.jglfont.spi.BitmapFontRenderer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BitmapFontTest {
  private BitmapFont bitmapFont;
  private BitmapFontRenderer fontRenderer;

  @Before
  public void before() {
    fontRenderer = createMock(BitmapFontRenderer.class);
  }

  @Test
  public void testCreateTexture() throws Exception {
    initializeFontRenderer();
    replay(fontRenderer);

    bitmapFont = new BitmapFont(fontRenderer, createBitmapFont());
  }

  @Test
  public void testRenderSingleCharacter() throws Exception {
    initializeFontRenderer();
    fontRenderer.activateBitmap(0);
    fontRenderer.render(100, 100, 'a');
    replay(fontRenderer);

    bitmapFont = new BitmapFont(fontRenderer, createBitmapFont());
    bitmapFont.renderText(100, 100, "a", 1.f, 1.f, 1.f, 1.f, 1.f, 1.f);
  }

  @Test
  public void testRenderSingleCharacterShortMethod() throws Exception {
    initializeFontRenderer();
    fontRenderer.activateBitmap(0);
    fontRenderer.render(100, 100, 'a');
    replay(fontRenderer);

    bitmapFont = new BitmapFont(fontRenderer, createBitmapFont());
    bitmapFont.renderText(100, 100, "a");
  }

  @Test
  public void testRenderStringWithKerning() throws Exception {
    initializeFontRenderer();
    fontRenderer.activateBitmap(0);
    fontRenderer.render(100, 100, 'a');
    fontRenderer.render(117, 100, 'b');
    replay(fontRenderer);

    bitmapFont = new BitmapFont(fontRenderer, createBitmapFont());
    bitmapFont.renderText(100, 100, "ab", 1.f, 1.f, 1.f, 1.f, 1.f, 1.f);
  }

  @Test
  public void testRenderStringWithoutKerning() throws Exception {
    initializeFontRenderer();
    fontRenderer.activateBitmap(0);
    fontRenderer.render(100, 100, 'b');
    fontRenderer.render(105, 100, 'a');
    replay(fontRenderer);

    bitmapFont = new BitmapFont(fontRenderer, createBitmapFont());
    bitmapFont.renderText(100, 100, "ba", 1.f, 1.f, 1.f, 1.f, 1.f, 1.f);
  }

  @Test
  public void testRenderStringWithoutKerningAndMissingGlyph() throws Exception {
    initializeFontRenderer();
    fontRenderer.activateBitmap(0);
    fontRenderer.render(100, 100, 'b');
    fontRenderer.render(105, 100, 'a');
    replay(fontRenderer);

    bitmapFont = new BitmapFont(fontRenderer, createBitmapFont());
    bitmapFont.renderText(100, 100, "b@a", 1.f, 1.f, 1.f, 1.f, 1.f, 1.f);
  }

  @After
  public void after() {
    verify(fontRenderer);
  }

  private BitmapFontData createBitmapFont() {
    BitmapFontData font = new BitmapFontData();
    font.setBitmapHeight(256);
    font.setBitmapWidth(256);
    font.setLineHeight(25);
    font.addBitmap(0, "page1.png");
    
    BitmapFontCharacterInfo charA = new BitmapFontCharacterInfo();
    charA.setId(1);
    charA.setPage(0);
    charA.setWidth(5);
    charA.setHeight(10);
    charA.setX(0);
    charA.setY(0);
    charA.setXoffset(0);
    charA.setYoffset(0);
    charA.setXadvance(5);
    charA.addKerning('b', 12);
    font.addCharacter('a', charA);
    
    BitmapFontCharacterInfo charB = new BitmapFontCharacterInfo();
    charB.setId(2);
    charB.setPage(0);
    charB.setWidth(5);
    charB.setHeight(10);
    charB.setX(5);
    charB.setY(0);
    charB.setXoffset(5);
    charB.setYoffset(0);
    charB.setXadvance(5);
    font.addCharacter('b', charB);
    return font;
  }

  private void initializeFontRenderer() {
    fontRenderer.registerBitmap(0, "page1.png");
    fontRenderer.registerGlyph('b', 5, 0, 5, 10, 0.01953125f, 0.0f, 0.0390625f, 0.0390625f);
    fontRenderer.registerGlyph('a', 0, 0, 5, 10, 0.0f, 0.0f, 0.01953125f, 0.0390625f);
  }
}
