package pl.shockah.glib;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import pl.shockah.glib.geom.vector.Vector2i;

public final class AWTWindow extends Frame implements WindowListener {
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
		
		add(canvas);
		addWindowListener(this);
	}
	
	public void setup() {
		try {
			Display.setParent(canvas);
		} catch (Exception e) {e.printStackTrace();}
	}
	public void updateSize(final DisplayMode dm) {
		try {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					setPreferredSize(new Dimension(dm.getWidth(),dm.getHeight()));
					pack();
					setLocationRelativeTo(null);
				}
			});
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public boolean maximized() {
		return getExtendedState() == JFrame.MAXIMIZED_BOTH;
	}
	public void maximize() {
		setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
	}
	public void unmaximize() {
		setExtendedState(getExtendedState()&(~JFrame.MAXIMIZED_BOTH));
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