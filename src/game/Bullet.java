package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Bullet{

	private double x, y;
	private int degree;
	protected static boolean space = false;
	protected int size = 8;
	protected int distance = 30;
	protected Color color = Color.RED;
	private int limitX = 1274, limitY = 1051;
	
	public Bullet (double x, double y, int degree){
		this.x = x;
		this.y = y;
		this.degree = degree;
	}
	
	
	public int getSize() {
		return size;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean update() {
		if(x < 0 || x > limitX || y < 0 || y > limitY)
			return true;
		else{
		x += distance*Math.sin(Math.toRadians(degree));
		y -= distance*Math.cos(Math.toRadians(degree));
		return false;
		}
	}
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillOval((int) (x - size/2), (int) (y - size/2), size, size);
		
	}
	
	public static void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			space = true;
	}
	
	public static void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			space = false;
	}
	
	@SuppressWarnings("unchecked")
	public static void makeBullet(double x, double y, int degree, ArrayList<? extends Bullet> bullets) {
		
		if(space == true){
			((ArrayList<Bullet>) bullets).add(new Bullet(x, y, degree));
		}
	}
	
	public boolean isCrash(Point point){
		
		double distance = Math.sqrt(Math.pow((x - point.getX()), 2) + Math.pow((y - point.getY()), 2));

		if (distance <= size*2/2){
			return true;
		}
		else
			return false;
		
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
