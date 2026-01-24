import java.awt.Graphics;

public class Camera {
//it has to end in a rectangle of display's width and height
	Point3D position;
	Point3D viewTarget;//unit vector
	int fov;
	public Camera() {
		position=new Point3D();
		viewTarget=new Point3D(0,0,1);
		fov=90;
	}
	public Camera(Point3D pos, Point3D view, int fov) {
		position=pos;
		viewTarget=view.getUnitVectorFrom(pos);
		this.fov=fov;
	}
	public void drawTriangle(Graphics g, Triangle3D triangle) {
		//no concern for any  other triangles
		int[]xPoints = new int[3], yPoints=new int[3];
		//cast the 3d triangle to a 2d triangle
		
		g.fillPolygon(xPoints, yPoints, 3);
	}
}
