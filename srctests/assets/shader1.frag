uniform sampler2D texture1;

void main() {
	gl_FragColor = vec4(1.0-texture2D(texture1,gl_TexCoord[0].st).rgb,1.0);
}