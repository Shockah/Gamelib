package tests.random;

import java.util.Random;
import pl.shockah.Math2;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.polygon.Triangle;
import pl.shockah.glib.geom.polygon.TriangleFan;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class ChristmasTree extends State {
	public static void main(String[] args) {
		ChristmasTree test = new ChristmasTree();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	Random rand = new Random();
	Image img;
	
	protected void onCreate() {
		try {
			img = new Image(Texture.load("assets/ptFuzzy.png"));
			img.center();
		} catch (Exception e) {e.printStackTrace();}
		
		final int w = 300, h = 500;
		Vector2i size = getDisplaySize();
		final int x = (size.x-w)/2, y = (size.y-h)/2;
		
		new EntityRenderable(){
			double tick = 0;
			
			protected void onRender(Graphics g) {
				tick += 1;
				Color c1, c2;
				
				c1 = Color.DarkRed; c2 = c1.interpolate(Color.Black,2d/3d);
				new Rectangle(x+w/2-20,y+h-100,40,100).drawMulticolor(g,true,c1,c2,c1,c2);
				
				c1 = Color.DarkGreen; c2 = c1.interpolate(Color.Black,.5d);
				int[] ars = new int[]{100,150,200};
				int[] aryy = new int[]{0,80,200};
				for (int i = 2; i >= 0; i--) {
					new Triangle(x+w/2,y+aryy[i],x+w/2-ars[i],y+aryy[i]+ars[i],x+w/2+ars[i],y+aryy[i]+ars[i]).drawMulticolor(g,true,c1,c2,c2);
				}
				
				Polygon p = new Polygon();
				Vector2d bpos = new Vector2d(x+w/2,y);
				double sin = (Math.sin(Math.toRadians(tick*Math.PI))*.5d+.5d)*.5d+.5d;
				g.setColor(Color.Yellow.interpolate(Color.Orange,.5d));
				g.draw(img,bpos,new Vector2d(sin*160,sin*160).div(img.getSize()));
				for (int i = 0; i < 10; i++) {
					p.addPoint(Vector2d.make(30*(i%2 == 0 ? 1d : .5d),360d/10d*i-tick).add(bpos));
				}
				g.draw(p);
			}
		}.create();
		
		for (int i = 0; i < 10; i++) {
			Vector2d v = Vector2d.make(70,360d/10d*i);
			new EntityRenderable(){
				Color c;
				
				protected void onCreate() {
					c = Color.fromHSB(rand.nextFloat(),255,255);
				}
				
				protected void onRender(Graphics g) {
					drawThatThingy(g,pos.x,pos.y,8,c);
				}
				
				public void drawThatThingy(Graphics g, double x, double y, double radius, Color c) {
					TriangleFan tf = new TriangleFan();
					Color c2 = c.interpolate(Color.Black,.5d);
					tf.addPoint(x,y,c);
					for (int i = 0; i <= 360; i += 10) {
						double delta = i < 180 ? 1d : 1d+(Math.pow(1d-(Math.abs(Math2.deltaAngle(270,i))/90d),3)*1.2d);
						tf.addPoint(Vector2d.make(delta*radius,i).add(x,y),c2);
					}
					tf.draw(g);
					
					g.setColor(Color.Black);
					GL.setThickness(2);
					tf.asPolygon().draw(g,false);
					GL.setThickness(1);
				}
			}.create(x+w/2+v.x,y+h/3*2+v.y);
		}
	}
}