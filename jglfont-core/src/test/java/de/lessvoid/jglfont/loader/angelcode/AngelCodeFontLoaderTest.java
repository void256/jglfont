package de.lessvoid.jglfont.loader.angelcode;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.lessvoid.jglfont.BitmapFont;
import de.lessvoid.jglfont.loader.angelcode.AngelCodeFontLoader;
import de.lessvoid.jglfont.loader.angelcode.Line;
import de.lessvoid.jglfont.loader.angelcode.LineData;
import de.lessvoid.jglfont.loader.angelcode.line.LineProcessors;

public class AngelCodeFontLoaderTest {
  private AngelCodeFontLoader fontLoader;
  private LineProcessors lineProcessorsMock;
  private boolean closeCalled = false;
  private boolean lineMockCalled = false;

  @Before
  public void before() {
    closeCalled = false;
    lineMockCalled = false;
    lineProcessorsMock = createMock(LineProcessors.class);
    fontLoader = new AngelCodeFontLoader(lineProcessorsMock);
  }

  @Test
  public void testEmptyNull() throws Exception {
    replay(lineProcessorsMock);
    fontLoader.load(null);
  }

  @Test
  public void testEmpty() throws Exception {
    replay(lineProcessorsMock);
    fontLoader.load(createInputStream(new byte[] {}));
    assertTrue(closeCalled);
  }

  @Test
  public void testInputStreamCloseFailed() throws Exception {
    replay(lineProcessorsMock);
    fontLoader.load(createInputStreamWithCloseError());
    assertTrue(closeCalled);
  }

  @Test
  public void testInputStreamReadFailed() throws Exception {
    replay(lineProcessorsMock);
    fontLoader.load(createInputStreamWithReadError());
    assertTrue(closeCalled);
  }

  @Test
  public void testInputStreamReadAndCloseFailed() throws Exception {
    replay(lineProcessorsMock);
    fontLoader.load(createInputStreamWithReadAndCloseError());
    assertTrue(closeCalled);
  }

  @Test
  public void testEmptyLine() throws Exception {
    replay(lineProcessorsMock);
    fontLoader.load(createInputStream("\n".getBytes("ISO-8859-1")));
    assertTrue(closeCalled);
  }

  @Test
  public void testSimpleLineSuccess() throws Exception {
    expect(lineProcessorsMock.get("stuff")).andReturn(createLineMock(true));
    replay(lineProcessorsMock);
    fontLoader.load(createInputStream("stuff\n".getBytes("ISO-8859-1")));
    assertTrue(closeCalled);
    assertTrue(lineMockCalled);
  }

  @Test
  public void testSimpleLineFailed() throws Exception {
    expect(lineProcessorsMock.get("stuff")).andReturn(createLineMock(false));
    replay(lineProcessorsMock);
    fontLoader.load(createInputStream("stuff\n".getBytes("ISO-8859-1")));
    assertTrue(closeCalled);
    assertTrue(lineMockCalled);
  }

  @After
  public void after() {
    verify(lineProcessorsMock);
  }

  private Line createLineMock(final boolean result) {
    return new Line() {
      @Override
      public boolean process(LineData line, BitmapFont font) {
        lineMockCalled = true;
        return result;
      }};
  }

  private InputStream createInputStreamWithCloseError() {
    InputStream in = new ByteArrayInputStream(new byte[] {}) {

      @Override
      public void close() throws IOException {
        closeCalled = true;
        throw new IOException("this call failed");
      }      
    };
    return in;
  }

  private InputStream createInputStreamWithReadError() throws Exception {
    InputStream in = new ByteArrayInputStream("stuff\n".getBytes("ISO-8859-1")) {
      @Override
      public synchronized int read(byte[] b, int off, int len) {
        throw new NullPointerException();
      }      
      @Override
      public void close() throws IOException {
        super.close();
        closeCalled = true;
      }
    };
    return in;
  }

  private InputStream createInputStreamWithReadAndCloseError() throws Exception {
    InputStream in = new ByteArrayInputStream("stuff\n".getBytes("ISO-8859-1")) {
      @Override
      public synchronized int read(byte[] b, int off, int len) {
        throw new NullPointerException();
      }

      @Override
      public void close() throws IOException {
        closeCalled = true;
        throw new IOException("close failed");
      }
    };
    return in;
  }

  private InputStream createInputStream(final byte[] data) {
    InputStream in = new ByteArrayInputStream(data) {

      @Override
      public void close() throws IOException {
        super.close();
        closeCalled = true;
      }      
    };
    return in;
  }
}
