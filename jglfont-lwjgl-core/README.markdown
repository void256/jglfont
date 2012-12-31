LWJGL based OpenGL Core BitmapFontRenderer implementation
=========================================================

This implementation will use a texture atlas to put all pages of your font texture into a single texture. This will
allow us to keep the same texture active when rendering (no texture state switches when rendering fonts that require
multiple pages).
