package org.jglfont.example;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;

import org.jglfont.BitmapFont;
import org.jglfont.angelcode.AngelCodeBitmapFontLoader;
import org.jglfont.example.LwjglInitHelper.RenderLoopCallback;
import org.jglfont.lwjgl.LwjglBitmapFontRenderer;


public class ExampleMain {
  public static void main(final String[] args) throws IOException {
    LwjglInitHelper init = new LwjglInitHelper();
    if (!init.initSubSystems("jglfont example", 1024, 768)) {
      return;
    }

    final BitmapFont bitmapFont = new BitmapFont(new LwjglBitmapFontRenderer(), new AngelCodeBitmapFontLoader());
    bitmapFont.load(ExampleMain.class.getResourceAsStream("verdana-small-regular.fnt"));
    bitmapFont.renderText(100, 100, "Hello World");

    init.renderLoop(new RenderLoopCallback() {
      @Override
      public void process() {
        glClearColor(0.15f, 0.15f, 0.3f, 1.f);
        glClear(GL_COLOR_BUFFER_BIT);

        bitmapFont.renderText(100, 100, "Hello World");
      }
    });

    init.destroy();
  }
}
