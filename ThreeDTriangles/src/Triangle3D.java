import java.awt.Color;
import java.awt.Graphics;

public class Triangle3D {

	Point3D[] points = new Point3D[3];
	Color c=Color.DARK_GRAY;
	boolean drawBorder=true;
	public Triangle3D(Point3D p1,Point3D p2,Point3D p3) {
		points[0]=p1;
		points[1]=p2;
		points[2]=p3;
		ThreeDTriangles.triangles.add(this);
	}
	public Triangle3D(Point3D p1,Point3D p2,Point3D p3, Color c) {
		this(p1, p2, p3);
		this.c=c;
	}
	public Triangle3D(Point3D p1,Point3D p2,Point3D p3, Color c, boolean b) {
		this(p1, p2, p3,c);
		this.drawBorder=b;
	}
	public Triangle3D(Point3D p1,Point3D p2,Point3D p3, boolean b) {
		this(p1, p2, p3);
		this.drawBorder=b;
	}
}
