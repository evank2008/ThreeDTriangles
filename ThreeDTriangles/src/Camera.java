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
	double rotSpeed=Math.PI/100;
	HashMap<Integer,Boolean> keysPressed=new HashMap<Integer,Boolean>(10,1.1f);
//it has to end in a rectangle of display's width and height
	Point3D position;
	Point3D viewTarget;//unit vector
	Point3D up, right;
	int fov;
	double fovRadians;
	public Camera() {
		position=new Point3D();
		viewTarget=new Point3D(0,0,1);
		fov=90;
		fovRadians=Math.toRadians(fov);
		keysPressed.put(KeyEvent.VK_W, false);
		keysPressed.put(KeyEvent.VK_A, false);
		keysPressed.put(KeyEvent.VK_S, false);
		keysPressed.put(KeyEvent.VK_D, false);
		keysPressed.put(KeyEvent.VK_Z, false);
		keysPressed.put(KeyEvent.VK_X, false);
		keysPressed.put(KeyEvent.VK_UP, false);
		keysPressed.put(KeyEvent.VK_DOWN, false);
		keysPressed.put(KeyEvent.VK_LEFT, false);
		keysPressed.put(KeyEvent.VK_RIGHT, false);
		Point3D worldUp = new Point3D(0,1,0);
		right = worldUp.cross(viewTarget).getUnitVector();
		 up = viewTarget.cross(right).getUnitVector();
	}
	public Camera(Point3D pos, Point3D view, int fov) {
		super();
		position=pos;
		viewTarget=view.getUnitVectorFrom(pos);
		this.fov=fov;
		fovRadians=Math.toRadians(fov);
		Point3D worldUp = new Point3D(0,1,0);
		 right = worldUp.cross(viewTarget).getUnitVector();
		 up    = viewTarget.cross(right).getUnitVector();
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
		keysPressed.put(e.getKeyCode(), true);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keysPressed.put(e.getKeyCode(), false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
			for(int c: keysPressed.keySet()) {
				if(keysPressed.get(c)) {
					switch(c) {
					case KeyEvent.VK_W:
						position.move(speed, viewTarget);
						break;
					case KeyEvent.VK_A:
						position.move(speed, viewTarget.cross(new Point3D(0,1,0)));
						break;
					case KeyEvent.VK_S:
						position.move(-speed, viewTarget);
						break;
					case KeyEvent.VK_D:
						position.move(-speed, viewTarget.cross(new Point3D(0,1,0)));
						break;
					case KeyEvent.VK_Z:
						position.move(-speed, viewTarget.cross(new Point3D(0,1,0)).cross(viewTarget));
						break;
					case KeyEvent.VK_X:
						position.move(speed, viewTarget.cross(new Point3D(0,1,0)).cross(viewTarget));
						break;
					case KeyEvent.VK_UP:
						//rotate up
						viewTarget = viewTarget.rotateAroundAxis(right, rotSpeed);
						up = up.rotateAroundAxis(right, rotSpeed);
						right = up.cross(viewTarget).getUnitVector();
						up    = viewTarget.cross(right).getUnitVector();
						break;
					case KeyEvent.VK_DOWN:
						//rotate down
					    viewTarget = viewTarget.rotateAroundAxis(viewTarget.cross(new Point3D(0,1,0)).getUnitVector(), rotSpeed);
					    right = up.cross(viewTarget).getUnitVector();
					    up    = viewTarget.cross(right).getUnitVector();
					    break;
					case KeyEvent.VK_LEFT:
						//rotate left
					    viewTarget = viewTarget.rotateAroundAxis(viewTarget.cross(new Point3D(0,1,0)).cross(viewTarget).getUnitVector(), -rotSpeed);
					    right = up.cross(viewTarget).getUnitVector();
					    up    = viewTarget.cross(right).getUnitVector();
						break;
					case KeyEvent.VK_RIGHT:
						//rotate right
					    viewTarget = viewTarget.rotateAroundAxis(viewTarget.cross(new Point3D(0,1,0)).cross(viewTarget).getUnitVector(), rotSpeed);
					    right = up.cross(viewTarget).getUnitVector();
					    up    = viewTarget.cross(right).getUnitVector();
					    break;
					}
				}
		}
	}
}
