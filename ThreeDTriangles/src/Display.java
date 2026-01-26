import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements ActionListener, KeyListener{

	public static int width=600,height=600;
	
	JFrame frame;
	Camera camera;
	Timer moveTimer = new Timer(1000/60,this);

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
		addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
		moveTimer.start();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Triangle3D t:ThreeDTriangles.triangles) {
			camera.drawTriangle(g, t);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==moveTimer) {
			camera.actionPerformed(e);
			repaint();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("key press");
		camera.keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	camera.keyReleased(e);	
	}
}
