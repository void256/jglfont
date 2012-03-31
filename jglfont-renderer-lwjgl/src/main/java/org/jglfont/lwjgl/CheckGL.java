package org.jglfont.lwjgl;

import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

public class CheckGL {
  private static Logger log = Logger.getLogger(CheckGL.class.getName());

  public static void checkGLError(final String message) {
    int error = glGetError();
    while (error != GL_NO_ERROR) {
      String glerrmsg = GLU.gluErrorString(error);
      log.warning("OpenGL Error: (" + error + ") " + glerrmsg + " {" + message + "}");
      error = glGetError();
    }
  }
}
