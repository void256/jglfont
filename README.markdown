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
    
    JGLFontFactory factory = new JGLFontFactory(new LwjglDisplayListFontRenderer());
    JGLFont jglFont = factory.loadFont(ExampleMain.class.getResourceAsStream("/verdana-small-regular.fnt"));
    jglFont.renderText(100, 100, "Hello World!");
	
**License**

```
	Copyright (c) 2012, Jens Hohmuth
	All rights reserved.

	Redistribution and use in source and binary forms, with or without
	modification, are permitted provided that the following conditions are met: 

	1. Redistributions of source code must retain the above copyright notice, this
	   list of conditions and the following disclaimer. 
	2. Redistributions in binary form must reproduce the above copyright notice,
	   this list of conditions and the following disclaimer in the documentation
	   and/or other materials provided with the distribution. 

	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
	ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
	DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
	ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

	The views and conclusions contained in the software and documentation are those
	of the authors and should not be interpreted as representing official policies, 
	either expressed or implied, of the FreeBSD Project.
```