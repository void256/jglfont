package org.jglfont.example.lwjgl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;

import org.jglfont.BitmapFont;
import org.jglfont.BitmapFontFactory;
import org.jglfont.example.lwjgl.LwjglInitHelper.RenderLoopCallback;
import org.jglfont.renderer.lwjgl.LwjglDisplayListFontRenderer;

public class ExampleMain {
  private static double scale = 1;
  private static double dir = 1;
  private static long lastTime = System.currentTimeMillis();

  public static void main(final String[] args) throws IOException {
    LwjglInitHelper init = new LwjglInitHelper();
    if (!init.initSubSystems("jglfont example", 1024, 768)) {
      return;
    }

    BitmapFontFactory factory = new BitmapFontFactory(new LwjglDisplayListFontRenderer());
    final BitmapFont bitmapFont = factory.loadFont(
        ExampleMain.class.getResourceAsStream("/verdana-small-regular.fnt"),
        "verdana-small-regular.fnt");

    init.renderLoop(new RenderLoopCallback() {
      @Override
      public void process() {
        glClearColor(0.15f, 0.15f, 0.3f, 1.f);
        glClear(GL_COLOR_BUFFER_BIT);

        bitmapFont.renderText(100, 100, "Hello World!");
        bitmapFont.renderText(100, 100, "Hello World!", (float)scale, (float)scale, 1.f, 0.f, 0.f, 0.4f);

        bitmapFont.renderText(100, 200, "String width: " + bitmapFont.getStringWidth("Hello World!", (float)scale));
        bitmapFont.renderText(100, 200 + bitmapFont.getHeight(), "String height: " + bitmapFont.getHeight());

        long newTime = System.currentTimeMillis();
        while ((newTime - lastTime) < 14) {
          scale += dir * 0.0000001;
          if (scale > 3. || scale <= 1.) {
            dir = -dir;
          }
          newTime = System.currentTimeMillis();
        }
        lastTime = newTime;
      }
    });

    init.destroy();
  }
}
