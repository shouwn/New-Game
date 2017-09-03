package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Enemy {

	private BufferedImage img;
	private double x, y;
	private int degree;
	private Player player;
	private int weight;
	//����ġ
	static int areaX = 0, areaY = 100, areaW = 1274 - 50 - areaX, areaH = 1051 - 50 - areaY;
	private int distance = 5;
	private MyShape shape;
	private int life = 1;
	private int limitX = 1274;
	private int limitY = 1051;

	public Enemy(double x, double y, Player player, int weight) {
		this.player = player;

		try{
			img = ImageIO.read(new File("img/Enemy.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		shape = new MyShape(img);

		degree = checkDegree();
		this.x = x;
		this.y = y;
		this.weight = weight;

	}

	public boolean isDead(ArrayList<Bullet> bullets) {

		Bullet bullet;

		double x = this.x + img.getWidth() / 2;
		double y = this.y + img.getHeight() /2;
		ArrayList<Point> shape = this.shape.site(x, y, this.degree);

		for(Point point : shape){
			for(int index = 0; index < bullets.size(); index++){
				bullet = bullets.get(index);

				if(bullet.isCrash(point)){
					bullets.remove(index--);
					life -= 1;
					if(life <= 0)
						return true;
				}
			}
		}

		return false;

	}

	public void update() {

		degree = checkDegree();
		double dX = distance*Math.sin(Math.toRadians(degree));
		double dY = distance*Math.cos(Math.toRadians(degree));

		if (x + dX >= 0 && x + dX <= limitX - img.getWidth())
			x += dX;
		if (y - dY >= 100 && y - dY <= limitY - img.getHeight())
			y -= dY;

	}

	public void draw(Graphics g) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(degree), x + img.getWidth() / 2, y + img.getHeight() /2);
		Graphics2D image = (Graphics2D) (img.getGraphics());
		Graphics2D g2 = (Graphics2D) g;

		AffineTransform old = image.getTransform();
		g2.transform(transform);

		g2.drawImage(img, (int) x, (int) y, null);

		g2.setTransform(old);

	}

	public int checkDegree() {
		double playerForecastX = player.getPointX();
		double playerForecastY = player.getPointY();
		double limitAngle = 90;
		double dPlayerX, dPlayerY;
		double time;

		int degree = 0;
		double dX = playerForecastX - (x + img.getWidth() / 2);
		double dY = (y + img.getHeight() /2) - playerForecastY;

		double distanceOfPlayer = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

		if(dX > 0)
			degree = (int) Math.toDegrees(Math.atan(dY/dX));

		else if (dX < 0)
			degree = 180 + (int) Math.toDegrees(Math.atan(dY/dX));

		degree = 90 - degree;

		double includedAngle = player.getMoveDegree() - degree + 180;
		includedAngle = includedAngle % 360;

		if(includedAngle > -limitAngle && includedAngle < limitAngle){
			time = distanceOfPlayer / (2 * 10 * Math.cos(Math.toRadians(includedAngle)));
			
			dPlayerX = time*player.getSpeed()*Math.sin(Math.toRadians(player.getMoveDegree()));
			dPlayerY = time*player.getSpeed()*Math.cos(Math.toRadians(player.getMoveDegree()));
			
			playerForecastX += dPlayerX;
			playerForecastY -= dPlayerY;
			
			dX = playerForecastX - (x + img.getWidth() / 2);
			dY = (y + img.getHeight() /2) - playerForecastY;

			if(dX > 0)
				degree = (int) Math.toDegrees(Math.atan(dY/dX));

			else if (dX < 0)
				degree = 180 + (int) Math.toDegrees(Math.atan(dY/dX));
			
			System.out.println(degree);
			
			return 90- degree;
		}
		
		else{
			time = distanceOfPlayer/5;
			
			dPlayerX = time*player.getSpeed()*Math.sin(Math.toRadians(player.getMoveDegree()));
			dPlayerY = time*player.getSpeed()*Math.cos(Math.toRadians(player.getMoveDegree()));

			playerForecastX += dPlayerX;
			if(playerForecastX > limitX)
				playerForecastX = limitX;
			
			playerForecastY -= dPlayerY;
			if(playerForecastY > limitY)
				playerForecastY = limitY;
			
			dX = playerForecastX - (x + img.getWidth() / 2);
			dY = (y + img.getHeight() /2) - playerForecastY;

			if(dX > 0)
				degree = (int) Math.toDegrees(Math.atan(dY/dX));

			else if (dX < 0)
				degree = 180 + (int) Math.toDegrees(Math.atan(dY/dX));
			
			System.out.println(degree);
			return 90- degree;
		}
	}

	static void makeEnemy(ArrayList<Enemy> e, Player player){

		int weightSize = 50;

		int x, y;
		int weight;

		x = (int) (Math.random() * areaW) + areaX;
		y = (int) (Math.random() * areaH) + areaY;
		weight = weightSize/2 - ((int) (Math.random() * weightSize));

		e.add(new Enemy(x, y, player, weight));
	}

	public double getCenterX() {
		return x + img.getWidth()/2;
	}

	public double getCenterY() {
		return y + img.getHeight();
	}

	public int getDegree() {
		return degree;
	}

}
