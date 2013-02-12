package org.jglfont.renderer.lwjglcore.example;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.jglfont.BitmapFont;
import org.jglfont.BitmapFontFactory;
import org.jglfont.renderer.lwjglcore.LwjglCoreFontRenderer;

import de.lessvoid.coregl.CoreLwjglSetup;
import de.lessvoid.coregl.CoreLwjglSetup.RenderLoopCallback;

public class ExampleMain {
  private static double scale = 1;
  private static double dir = 1;
  private static long lastTime = System.currentTimeMillis();

  public static void main(final String[] args) throws Exception {
    CoreLwjglSetup init = new CoreLwjglSetup();
    init.initializeLogging();
    init.initialize("jglfont core example", 1024, 768);

    BitmapFontFactory factory = new BitmapFontFactory(new LwjglCoreFontRenderer(1024, 1024));
    final BitmapFont bitmapFont = factory.loadFont(
        ExampleMain.class.getResourceAsStream("/verdana-small-regular.fnt"),
        "verdana-small-regular.fnt");

    init.renderLoop(new RenderLoopCallback() {
      @Override
      public boolean render(final float deltaTime) {
        glClearColor(0.15f, 0.15f, 0.3f, 1.f);
        glClear(GL_COLOR_BUFFER_BIT);

        bitmapFont.renderText(100, 100, "H");
//        bitmapFont.renderText(100, 100, "Hello World!", (float)scale, (float)scale, 1.f, 0.f, 0.f, 0.4f);

//        bitmapFont.renderText(100, 200, "String width: " + bitmapFont.getStringWidthInternal("Hello World!", (float)scale));
//        bitmapFont.renderText(100, 200 + bitmapFont.getHeight(), "String height: " + bitmapFont.getHeight());

        long newTime = System.currentTimeMillis();
        while ((newTime - lastTime) < 14) {
          scale += dir * 0.0000001;
          if (scale > 3. || scale <= 1.) {
            dir = -dir;
          }
          newTime = System.currentTimeMillis();
        }
        lastTime = newTime;
        return false;
      }
    });

    init.destroy();
  }
}
