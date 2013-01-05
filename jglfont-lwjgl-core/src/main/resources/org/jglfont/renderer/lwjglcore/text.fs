#version 150 core

uniform sampler2D uTex;

in vec2 vUV;
out vec4 color;

void main() {
  color = texture(uTex, vUV);
  color.r = 1.0;
  color.a = 1.0;
}
