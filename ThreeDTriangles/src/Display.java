import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JPanel{

	public static int width=600,height=600;
	JFrame frame;
	Camera camera;
	//x is left to right
	//y is up and down
	//z in back and forth
	public Display() {
		super();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width,height);
		frame.add(this);
		frame.setVisible(true);
		
		camera=new Camera(new Point3D(width/2,height/2,-100),new Point3D(width/2,height/2,1),90);
		new Triangle3D(
				new Point3D(300,100,200),
				new Point3D(500,300,200),
				new Point3D(100,300,200)
				);
		this.addKeyListener(camera);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Triangle3D t:ThreeDTriangles.triangles) {
			camera.drawTriangle(g, t);
		}
	}
}
