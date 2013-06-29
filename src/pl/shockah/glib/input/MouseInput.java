package pl.shockah.glib.input;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2i;

public class MouseInput {
	public static final int
		ANYBUTTON = -1;
	
	protected Vector2i pos;
	protected boolean onPress = false;
	protected boolean[] btnPressedOld, btnPressed, btnReleasedOld, btnReleased;
	
	public MouseInput() {
		btnPressedOld = new boolean[4];
		btnPressed = new boolean[4];
		btnReleasedOld = new boolean[4];
		btnReleased = new boolean[4];
	}
	
	public void update() {
		for (int i = 0; i < btnPressed.length; i++) {
			btnPressedOld[i] = btnPressed[i];
			btnReleasedOld[i] = btnReleased[i];
			
			btnPressed[i] = false;
			btnReleased[i] = false;
		}
		
		onPress = false;
		pos = new Vector2i(Mouse.getX(),Display.getHeight()-Mouse.getY()-1);
		
		while (Mouse.next()) {
			if (Mouse.getEventButton() < 0) continue;
			
			boolean[] ar = Mouse.getEventButtonState() ? btnPressed : btnReleased;
			ar[Mouse.getEventButton()] = true;
			ar[getSpecialButton(ANYBUTTON)] = true;
			
			if (!onPress) {
				pos = new Vector2i(Mouse.getEventX(),Display.getHeight()-Mouse.getEventY()-1);
				if (ar == btnPressed) onPress = true;
			}
		}
	}
	
	public Vector2i getPos() {
		return pos;
	}
	
	public boolean isPressed(int btn) {
		return btnPressed[getSpecialButton(btn)];
	}
	public boolean isReleased(int btn) {
		return btnReleased[getSpecialButton(btn)];
	}
	public boolean isDown(int btn) {
		return btnPressed[getSpecialButton(btn)] || Mouse.isButtonDown(getSpecialButton(btn));
	}
	
	protected int getSpecialButton(int btn) {
		return btn >= 0 ? btn : btnPressed.length+btn;
	}
	
	public boolean inRectangle(Rectangle rect) {
		return pos.x >= rect.pos.x && pos.y >= rect.pos.y && pos.x < rect.pos.x+rect.size.x && pos.y < rect.pos.y+rect.size.y;
	}
}