package org.jglfont.example.lwjgl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;

import org.jglfont.BitmapFont;
import org.jglfont.example.lwjgl.LwjglInitHelper.RenderLoopCallback;
import org.jglfont.format.angelcode.AngelCodeBitmapFontLoader;
import org.jglfont.renderer.lwjgl.LwjglBitmapFontRenderer;

import de.lessvoid.resourceloader.ResourceLoader;


public class ExampleMain {
  public static void main(final String[] args) throws IOException {
    LwjglInitHelper init = new LwjglInitHelper();
    if (!init.initSubSystems("jglfont example", 1024, 768)) {
      return;
    }

    ResourceLoader resourceLoader = new ResourceLoader();
    BitmapFont bitmapFont = new BitmapFont(new LwjglBitmapFontRenderer(resourceLoader), new AngelCodeBitmapFontLoader());
    bitmapFont.load(resourceLoader.getResourceAsStream("verdana-small-regular.fnt"));
    bitmapFont.renderText(100, 100, "Hello World");
/*
    init.renderLoop(new RenderLoopCallback() {
      @Override
      public void process() {
        glClearColor(0.15f, 0.15f, 0.3f, 1.f);
        glClear(GL_COLOR_BUFFER_BIT);

        bitmapFont.renderText(100, 100, "Hello World");
      }
    });
*/
    init.destroy();
  }
}
