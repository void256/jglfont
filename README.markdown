jglfont
=======

Bitmap Fonts for Java OpenGL suitable to be used in a variety of Java OpenGL implementations and with a variety of
bitmap font formats. At the moment this supports the popular angelcode format only but it is easily extentable with
other formats.

**Maven Dependencies**

    <!-- include the code module -->
    <dependency>
      <groupId>org.jglfont</groupId>
      <artifactId>jglfont-core</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>

    <!-- include the lwjgl connector -->
    <dependency>
      <groupId>org.jglfont</groupId>
      <artifactId>jglfont-lwjgl</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>

**Example**
    
    BitmapFontFactory factory = new BitmapFontFactory(new LwjglDisplayListFontRenderer());
    BitmapFont bitmapFont = factory.loadFont(ExampleMain.class.getResourceAsStream("/verdana-small-regular.fnt"));
    bitmapFont.renderText(100, 100, "Hello World!");