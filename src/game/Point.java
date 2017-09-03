package game;

public class Point {
	private int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point (double x, double y){
		this.x = (int) x;
		this.y = (int) y;
	}
	
	public Point (Point p){
		this.x = p.x;
		this.y = p.y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void add(Point other){
		x += other.getX();
		y += other.getY();
	}
	
	public void add(int x, int y){
		this.x += x;
		this.y += y;
	}
	
	public void rotate(Point center, int degree){
		double r = Math.toRadians(90-degree);
		
		x = (int) ((this.x - center.x)*Math.cos(r) - (-(this.y) + center.y)*Math.sin(r) + center.x);
		y = (int) -((this.x - center.x)*Math.sin(r) + (-(this.y) + center.y)*Math.cos(r) - center.y);
		
	}
	
}
