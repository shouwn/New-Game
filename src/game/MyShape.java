package game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MyShape {
	
	private ArrayList<Point> shape;
	private int width, height;
	private Point point;
	
	public MyShape(BufferedImage img){
		width = img.getWidth();
		height = img.getHeight();
		shape = new ArrayList<Point>();
		for (int r = 0; r < width; r++){
			for (int c = 0; c < height; c++){
				Color color = new Color(img.getRGB(r, c));
				if(color.getRGB() != -1){
					shape.add(new Point(r, c));
				}
				
			}
		}
		point = new Point(width / 2, height /2);
		
	}
	
	public ArrayList<Point> site(Point location, int degree){
		ArrayList<Point> shape = this.shape;
		int dX = location.getX() - point.getX();
		int dY = location.getY() - point.getY();

		for(Point point : shape){
			point.rotate(this.point, degree);
		}
		
		for(Point point : shape){
			point.add(dX, dY);
		}

		return shape;
	}
	
	public ArrayList<Point> site(int locationX, int locationY, int degree){
		ArrayList<Point> shape = this.shape;
		int dX = locationX - point.getX();
		int dY = locationY - point.getY();

		for(Point point : shape){
			point.rotate(this.point, degree);
		}
		
		for(Point point : shape){
			point.add(dX, dY);
		}

		return shape;
	}
	
	public ArrayList<Point> site(double locationX, double locationY, int degree){
		
		ArrayList<Point> shape = new ArrayList<Point>();
		
		for(Point p : this.shape){
			shape.add(new Point(p));
		}
		
		int dX = (int) (locationX - point.getX());
		int dY = (int) (locationY - point.getY());

		for(Point point : shape){
			point.rotate(this.point, degree);
		}
		
		for(Point point : shape){
			point.add(dX, dY);
		}
		
		return shape;
	}

}
