package pl.shockah.glib;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import org.lwjgl.opengl.Display;
import pl.shockah.glib.geom.vector.Vector2i;

public final class AWTWindow extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1673765420259835870L;
	
	protected final Canvas canvas;
	protected boolean closeRequested = false;
	
	public AWTWindow(String title) {
		super(title);
		
		canvas = new Canvas();
		canvas.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				displayResized(canvas.getSize().width,canvas.getSize().height);
			}
		});
		
		add(canvas,BorderLayout.CENTER);
		addWindowListener(this);
	}
	
	public void setup() {
		try {
			Display.setParent(canvas);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public boolean maximized() {
		return getExtendedState() == JFrame.MAXIMIZED_BOTH;
	}
	public void maximize() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	public void unmaximize() {
		setExtendedState(JFrame.NORMAL);
	}
	
	protected void displayResized(int w, int h) {
		Gamelib.displayChange(new Vector2i(w,h));
	}
	
	public void windowClosing(WindowEvent e) {closeRequested = true;}	  
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}