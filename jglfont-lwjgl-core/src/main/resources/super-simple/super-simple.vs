#version 150 core

// vertex attributes
in vec2 vVertex;
in vec4 vColor;

// output
out vec4 c;

void main() {
  gl_Position = vec4(vVertex.x, vVertex.y, 0.0, 1.0);
  c = vColor;
}
