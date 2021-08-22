#version 460 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;
layout (location = 2) in vec2 textureCoord;

out vec4 passColor;
out vec2 passTextureCoord;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

void main(){
    gl_Position = projection * view * transform * vec4(position, 1.0);
    passColor = vec4(color, 1.0);
    passTextureCoord = textureCoord;
}