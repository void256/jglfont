#version 150 core

uniform mat4 uMVP;

// vertex attributes
in vec2 aVertex;
in vec2 aUV;

// output
out vec2 vUV;

void main() {
  gl_Position = uMVP * vec4(aVertex.x, aVertex.y, 0.0, 1.0);
  vUV = aUV;
}
