package org.jglfont.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.io.IOException;
import java.io.InputStream;

import org.jglfont.BitmapFont;
import org.jglfont.impl.format.BitmapFontCharacterInfo;
import org.jglfont.impl.format.BitmapFontData;
import org.jglfont.spi.BitmapFontRenderer;
import org.jglfont.spi.ResourceLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BitmapFontTest {
  private BitmapFont bitmapFont;
  private BitmapFontRenderer fontRenderer;
  private ResourceLoader resourceLoader;
  private InputStream inputStreamMock;

  @Before
  public void before() {
    fontRenderer = createMock(BitmapFontRenderer.class);

    inputStreamMock = createMock(InputStream.class);
    replay(inputStreamMock);

    resourceLoader = createMock(ResourceLoader.class);
    expect(resourceLoader.load("page1.png")).andReturn(inputStreamMock);
    replay(resourceLoader);
  }

  @Test
  public void testCreateTexture() throws Exception {
    initializeFontRenderer();
    replay(fontRenderer);

    bitmapFont = new BitmapFontImpl(fontRenderer, resourceLoader, createBitmapFont());
  }

  @Test
  public void testRenderSingleCharacter() throws Exception {
    initializeFontRenderer();
    fontRenderer.beforeRender();
    fontRenderer.render("name-0", 100, 100, 'a', 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
    fontRenderer.afterRender();
    replay(fontRenderer);

    bitmapFont = new BitmapFontImpl(fontRenderer, resourceLoader, createBitmapFont());
    bitmapFont.renderText(100, 100, "a", 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
  }

  @Test
  public void testRenderSingleCharacterShortMethod() throws Exception {
    initializeFontRenderer();
    fontRenderer.beforeRender();
    fontRenderer.render("name-0", 100, 100, 'a', 1.f, 1.f, 1.f, 1.f, 1.f, 1.f);
    fontRenderer.afterRender();
    replay(fontRenderer);

    bitmapFont = new BitmapFontImpl(fontRenderer, resourceLoader, createBitmapFont());
    bitmapFont.renderText(100, 100, "a");
  }

  @Test
  public void testRenderStringWithKerning() throws Exception {
    initializeFontRenderer();
    fontRenderer.beforeRender();
    fontRenderer.render("name-0", 100, 100, 'a', 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
    fontRenderer.render("name-0", 117, 100, 'b', 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
    fontRenderer.afterRender();
    replay(fontRenderer);

    bitmapFont = new BitmapFontImpl(fontRenderer, resourceLoader, createBitmapFont());
    bitmapFont.renderText(100, 100, "ab", 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
  }

  @Test
  public void testRenderStringWithoutKerning() throws Exception {
    initializeFontRenderer();
    fontRenderer.beforeRender();
    fontRenderer.render("name-0", 100, 100, 'b', 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
    fontRenderer.render("name-0", 105, 100, 'a', 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
    fontRenderer.afterRender();
    replay(fontRenderer);

    bitmapFont = new BitmapFontImpl(fontRenderer, resourceLoader, createBitmapFont());
    bitmapFont.renderText(100, 100, "ba", 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
  }

  @Test
  public void testRenderStringWithoutKerningAndMissingGlyph() throws Exception {
    initializeFontRenderer();
    fontRenderer.beforeRender();
    fontRenderer.render("name-0", 100, 100, 'b', 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
    fontRenderer.render("name-0", 105, 100, 'a', 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
    fontRenderer.afterRender();
    replay(fontRenderer);

    bitmapFont = new BitmapFontImpl(fontRenderer, resourceLoader, createBitmapFont());
    bitmapFont.renderText(100, 100, "b@a", 1.f, 1.f, 1.f, 0.9f, 0.8f, 0.7f);
  }

  @After
  public void after() {
    verify(fontRenderer);
    verify(resourceLoader);
    verify(inputStreamMock);
  }

  private BitmapFontData createBitmapFont() {
    BitmapFontData font = new BitmapFontData();
    font.setName("name");
    font.setBitmapHeight(256);
    font.setBitmapWidth(256);
    font.setLineHeight(25);
    font.addBitmap(0, "page1.png");
    
    BitmapFontCharacterInfo charA = new BitmapFontCharacterInfo();
    charA.setId(1);
    charA.setPage("name-0");
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
    charB.setPage("name-0");
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

  private void initializeFontRenderer() throws IOException {
    fontRenderer.registerBitmap("name-0", inputStreamMock, "page1.png");
    fontRenderer.registerGlyph("name-0", 'b', 5, 0, 5, 10, 0.01953125f, 0.0f, 0.0390625f, 0.0390625f);
    fontRenderer.registerGlyph("name-0", 'a', 0, 0, 5, 10, 0.0f, 0.0f, 0.01953125f, 0.0390625f);
    fontRenderer.prepare();
  }
}
