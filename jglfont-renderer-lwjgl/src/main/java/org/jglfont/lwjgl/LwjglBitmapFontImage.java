package org.jglfont.lwjgl;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetInteger;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.logging.Logger;

import org.jglfont.lwjgl.io.ImageData;
import org.jglfont.lwjgl.io.ImageIOImageData;
import org.jglfont.lwjgl.io.TGAImageData;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import de.lessvoid.resourceloader.ResourceLoader;


public class LwjglBitmapFontImage {
  private Logger log = Logger.getLogger(LwjglBitmapFontImage.class.getName());

  private int width;
  private int height;
  private int textureWidth;
  private int textureHeight;
  public int textureId;

  public LwjglBitmapFontImage(final ResourceLoader resourceLoader, final String name, final boolean filterParam) {
    try {
      log.fine("loading image: " + name);
      ImageData imageLoader;
      if (name.endsWith(".tga")) {
        imageLoader = new TGAImageData();
      } else {
        imageLoader = new ImageIOImageData();
      }
      ByteBuffer imageData = imageLoader.loadImage(resourceLoader.getResourceAsStream(name));
      imageData.rewind();
      width = imageLoader.getWidth();
      height = imageLoader.getHeight();
      textureWidth = imageLoader.getTexWidth();
      textureHeight = imageLoader.getTexHeight();
      createTexture(imageData, textureWidth, textureHeight, 0, imageLoader.getDepth() == 32 ? GL11.GL_RGBA : GL11.GL_RGB);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getTextureWidth() {
    return textureWidth;
  }

  public int getTextureHeight() {
    return textureHeight;
  }

  public void dispose() {
    GL11.glDeleteTextures(textureId);
    CheckGL.checkGLError("glDeleteTextures");
  }  

  private void createTexture(final ByteBuffer textureBuffer, final int width, final int height, final int filter, final int srcPixelFormat) throws Exception {
    textureId = createTextureID(); 
    int minFilter = GL11.GL_NEAREST;
    int magFilter = GL11.GL_NEAREST;
    bind();

    int componentCount = 1;
     
    IntBuffer temp = BufferUtils.createIntBuffer(16);
    glGetInteger(GL11.GL_MAX_TEXTURE_SIZE, temp);
    CheckGL.checkGLError("glGetInteger");

    int max = temp.get(0);
    if ((width > max) || (height > max)) {
      throw new Exception("Attempt to allocate a texture to big for the current hardware");
    }
    if (width < 0) {
      log.warning("Attempt to allocate a texture with negative width");
      return;
    }
    if (height < 0) {
      log.warning("Attempt to allocate a texture with negative height");
      return;
    }

    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, minFilter); 
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, magFilter); 
    CheckGL.checkGLError("glTexParameteri");

    if (minFilter == GL11.GL_LINEAR_MIPMAP_NEAREST) {
      GLU.gluBuild2DMipmaps(
          GL11.GL_TEXTURE_2D,
          componentCount, 
          width,
          height,
          srcPixelFormat, 
          GL11.GL_UNSIGNED_BYTE,
          textureBuffer);
    } else {
      GL11.glTexImage2D(
          GL11.GL_TEXTURE_2D, 
          0,
          GL11.GL_RGBA8, 
          width, 
          height, 
          0, 
          srcPixelFormat, 
          GL11.GL_UNSIGNED_BYTE, 
          textureBuffer);
    }
    CheckGL.checkGLError("glTexImage2D");
  }
  
  private int createTextureID() {
    IntBuffer textureId = BufferUtils.createIntBuffer(1);
    textureId.rewind();
    glGenTextures(textureId);
    CheckGL.checkGLError("glGenTextures");
    textureId.rewind();
    return textureId.get();
  }

  public void bind() {
    glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    CheckGL.checkGLError("glBindTexture");
  }
}
