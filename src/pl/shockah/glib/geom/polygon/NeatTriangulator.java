package pl.shockah.glib.geom.polygon;

import pl.shockah.glib.geom.vector.Vector2d;

public class NeatTriangulator implements ITriangulator {
	static final float EPSILON = 1E-006F;
	
	private double pointsX[];
	private double pointsY[];
	private int numPoints;
	private Edge edges[];
	private int V[];
	private int numEdges;
	private Triangle triangles[];
	private int numTriangles;
	private float offset = EPSILON;
	
	public NeatTriangulator() {
		pointsX = new double[100];
		pointsY = new double[100];
		numPoints = 0;
		edges = new Edge[100];
		numEdges = 0;
		triangles = new Triangle[100];
		numTriangles = 0;
	}
	
	public void clear() {
		numPoints = 0;
		numEdges = 0;
		numTriangles = 0;
	}
	
	private int findEdge(int i, int j) {
		int k, l;
		if (i < j) {
			k = i;
			l = j;
		} else {
			k = j;
			l = i;
		}
		for (int i1 = 0; i1 < numEdges; i1++) if (edges[i1].v0 == k && edges[i1].v1 == l) return i1;
		return -1;
	}
	
	private void addEdge(int i, int j, int k) {
		int l1 = findEdge(i,j), j1, k1;
		Edge edge;
		if (l1 < 0) {
			if (numEdges == edges.length) {
				Edge aedge[] = new Edge[edges.length*2];
				System.arraycopy(edges,0,aedge,0,numEdges);
				edges = aedge;
			}
			j1 = k1 = -1;
			l1 = numEdges++;
			edge = edges[l1] = new Edge();
		} else {
			edge = edges[l1];
			j1 = edge.t0;
			k1 = edge.t1;
		}
		int l, i1;
		if (i < j) {
			l = i;
			i1 = j;
			j1 = k;
		} else {
			l = j;
			i1 = i;
			k1 = k;
		}
		edge.v0 = l;
		edge.v1 = i1;
		edge.t0 = j1;
		edge.t1 = k1;
		edge.suspect = true;
	}
	
	void markSuspect(int i, int j, boolean flag) throws InternalException {
		int k;
		if (0 > (k = findEdge(i,j))) {
			throw new InternalException("Attempt to mark unknown edge");
		} else {
			edges[k].suspect = flag;
			return;
		}
	}
	
	private static boolean insideTriangle(double f, double f1, double f2, double f3, double f4, double f5, double f6, double f7) {
		double f8 = f4-f2;
		double f9 = f5-f3;
		double f10 = f-f4;
		double f11 = f1-f5;
		double f12 = f2-f;
		double f13 = f3-f1;
		double f14 = f6-f;
		double f15 = f7-f1;
		double f16 = f6-f2;
		double f17 = f7-f3;
		double f18 = f6-f4;
		double f19 = f7-f5;
		double f22 = f8*f17-f9*f16;
		double f20 = f12*f15-f13*f14;
		double f21 = f10*f19-f11*f18;
		return f22 >= 0f && f21 >= 0f && f20 >= 0f;
	}
	
	private boolean snip(int i, int j, int k, int l) {
		double f = pointsX[V[i]];
		double f1 = pointsY[V[i]];
		double f2 = pointsX[V[j]];
		double f3 = pointsY[V[j]];
		double f4 = pointsX[V[k]];
		double f5 = pointsY[V[k]];
		if (1E-006f > (f2-f)*(f5-f1)-(f3-f1)*(f4-f)) return false;
		for (int i1 = 0; i1 < l; i1++) if(i1 != i && i1 != j && i1 != k) {
			double f6 = pointsX[V[i1]];
			double f7 = pointsY[V[i1]];
			if (insideTriangle(f, f1, f2, f3, f4, f5, f6, f7)) return false;
		}
		return true;
	}
	
	private double area() {
		double f = 0f;
		int i = numPoints-1;
		for (int j = 0; j < numPoints;) {
			f += pointsX[i]*pointsY[j]-pointsY[i]*pointsX[j];
			i = j++;
		}
		return f*.5d;
	}
	
	public void basicTriangulation() throws InternalException {
		int i = numPoints;
		if (i < 3) return;
		numEdges = 0;
		numTriangles = 0;
		V = new int[i];
		
		if (0d < area()) {
			for (int k = 0; k < i; k++) V[k] = k;
		} else {
			for (int l = 0; l < i; l++) V[l] = numPoints-1-l;
		}
		int k1 = 2*i;
		int i1 = i-1;
		while(i > 2)  {
			if (0 >= k1--) throw new InternalException("Bad polygon");
			
			int j = i1;
			if (i <= j) j = 0;
			i1 = j + 1;
			if (i <= i1) i1 = 0;
			int j1 = i1+1;
			if (i <= j1) j1 = 0;
			if (snip(j,i1,j1,i)) {
				int l1 = V[j];
				int i2 = V[i1];
				int j2 = V[j1];
				if (numTriangles == triangles.length) {
					Triangle atriangle[] = new Triangle[triangles.length*2];
					System.arraycopy(triangles,0,atriangle,0,numTriangles);
					triangles = atriangle;
				}
				triangles[numTriangles] = new Triangle(l1,i2,j2);
				addEdge(l1,i2,numTriangles);
				addEdge(i2,j2,numTriangles);
				addEdge(j2,l1,numTriangles);
				numTriangles++;
				int k2 = i1;
				for (int l2 = i1 + 1; l2 < i; l2++) {
					V[k2] = V[l2];
					k2++;
				}

				i--;
				k1 = 2*i;
			}
		}
		V = null;
	}
	
	public boolean triangulate() {
		try {
			basicTriangulation();
			return true;
		} catch (InternalException e) {
			numEdges = 0;
		}
		return false;
	}
	
	public void addPolyPoint(Vector2d point) {
		for (int i=0; i < numPoints; i++) {
			if ((pointsX[i] == point.x) && (pointsY[i] == point.y)) {
				point = new Vector2d(point.x,point.y+offset);
				offset += EPSILON;
			}
		}
		
		if (numPoints == pointsX.length) {
			double af[] = new double[numPoints*2];
			System.arraycopy(pointsX,0,af,0,numPoints);
			pointsX = af;
			af = new double[numPoints*2];
			System.arraycopy(pointsY,0,af,0,numPoints);
			pointsY = af;
		}
		
		pointsX[numPoints] = point.x;
		pointsY[numPoints] = point.y;
		numPoints++;
	}
	
	class Triangle {
		int v[];
		
		Triangle(int i, int j, int k) {
			v = new int[3];
			v[0] = i;
			v[1] = j;
			v[2] = k;
		}
	}
	
	class Edge {
		int v0, v1, t0, t1;
		boolean suspect;
		
		Edge() {
			v0 = v1 = t0 = t1 = -1;
		}
	}
	
	class InternalException extends Exception {
		private static final long serialVersionUID = 4126578960568998025L;
		
		public InternalException(String msg) {
			super(msg);
		}
	}
	
	public int getTriangleCount() {
		return numTriangles;
	}
	
	public Vector2d getTrianglePoint(int tri, int i) {
		double xp = pointsX[triangles[tri].v[i]];
		double yp = pointsY[triangles[tri].v[i]];
		return new Vector2d(xp,yp);
	}
	
	public void startHole() {}
}
