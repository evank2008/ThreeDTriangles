import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.Timer;

public class Camera implements KeyListener, ActionListener{
	int speed=10;
	HashMap<Character,Boolean> keysPressed=new HashMap<Character,Boolean>(6,1.1f);
//it has to end in a rectangle of display's width and height
	Point3D position;
	Point3D viewTarget;//unit vector
	int fov;
	double fovRadians;
	public Camera() {
		position=new Point3D();
		viewTarget=new Point3D(0,0,1);
		fov=90;
		fovRadians=Math.toRadians(fov);
		keysPressed.put('w', false);
		keysPressed.put('a', false);
		keysPressed.put('s', false);
		keysPressed.put('d', false);
		keysPressed.put('z', false);
		keysPressed.put('x', false);
	}
	public Camera(Point3D pos, Point3D view, int fov) {
		super();
		position=pos;
		viewTarget=view.getUnitVectorFrom(pos);
		this.fov=fov;
		fovRadians=Math.toRadians(fov);
	}
	public void drawTriangle(Graphics g, Triangle3D triangle) {
		//no concern for any  other triangles
		int[]xPoints = new int[3], yPoints=new int[3];
		//cast the 3d triangle to a 2d triangle
		for(int i = 0;i<3;i++) {
			Point3D point = triangle.points[i].subtract(this.position);
			
			Point3D forward=viewTarget, right=new Point3D(0,1,0).cross(viewTarget).getUnitVector(),up=right.cross(forward);
			double xCam=point.dot(right),yCam=point.dot(up),zCam=point.dot(forward);
			if(zCam<=0) return;
			double scale = 1/Math.tan(fovRadians/2);
			double x_ndc = (xCam / zCam) * scale;
			double y_ndc = (yCam / zCam) * scale;
			int screenX = (int)((x_ndc + 1) * 0.5 * Display.width);
			int screenY = (int)((1 - y_ndc) * 0.5 * Display.height);
			xPoints[i]=screenX;
			yPoints[i]=screenY;
		}
		
		//now that that's done...
		
		g.setColor(Color.red);
		g.fillPolygon(xPoints, yPoints, 3);
		g.setColor(Color.black);
		g.drawPolygon(xPoints, yPoints, 3);

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keysPressed.put(e.getKeyChar(), true);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keysPressed.put(e.getKeyChar(), false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
			for(char c: keysPressed.keySet()) {
				if(keysPressed.get(c)) {
					System.out.println("key is pressed, attempting move");
					switch(c) {
					case 'w':
						position.move(speed, viewTarget);
						break;
					case 'a':
						position.move(speed, viewTarget.cross(new Point3D(0,1,0)));
						break;
					case 's':
						position.move(-speed, viewTarget);
						break;
					case 'd':
						position.move(-speed, viewTarget.cross(new Point3D(0,1,0)));
						break;
					case 'z':
						position.move(-speed, viewTarget.cross(new Point3D(0,1,0)).cross(viewTarget));
						break;
					case 'x':
						position.move(speed, viewTarget.cross(new Point3D(0,1,0)).cross(viewTarget));
						break;
					}
				}
		}
	}
}
