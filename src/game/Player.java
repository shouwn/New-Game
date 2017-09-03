package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class Player extends GraphicObject{

	private int life = 3;
	private BufferedImage img;
	private double x, y;
	private int degree = 0;
	private boolean up = false, down = false, left = false, right = false;
	private int limitX = 1274;
	private int limitY = 1051;
	private int moveX, moveY;
	private int moveDegree;
	private double speed;

	public Player(double x, double y){

		try {
			img = ImageIO.read(new File("img/Player.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		this.x = x;
		this.y = y;
	}

	public boolean isDead(ArrayList<EnemyBullet> enemyBullets, JButton checkPlayerLife){
		EnemyBullet enemyBullet;

		for(int index = 0; index < enemyBullets.size(); index++){
			enemyBullet = enemyBullets.get(index);

			if(enemyBullet.isCrash(x + img.getWidth()/2, y + img.getHeight()/2)){
				enemyBullets.remove(index--);
				checkPlayerLife.doClick();
				life -= 1;		

				switch(life){
				case 3:
					return false;

				case 2:
					try {
						img = ImageIO.read(new File("img/Player2.png"));
					} catch (IOException e) {
						System.out.println(e.getMessage());
						System.exit(0);
					}
					return false;

				case 1:
					try {
						img = ImageIO.read(new File("img/Player1.png"));
					} catch (IOException e) {
						System.out.println(e.getMessage());
						System.exit(0);
					}
					return false;

				case 0:
					return true;
				}
			}
		}
		return false;
	}

	public double getPointX() {
		return x + img.getWidth() / 2;
	}

	public double getPointY() {
		return y + img.getHeight() / 2;
	}

	public int getDegree() {
		return degree;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public void update() {
		int x = 0, y = 0;

		if (up == true){
			y -= 10;
		}

		if (down == true){
			y += 10;
		}

		if (left == true){
			x -= 10;
		}

		if (right == true){
			x += 10;
		}

		moveX = x;
		moveY = y;

		moveDegree = checkDegree();
		
		x += this.x;
		y += this.y;
		
		if (x >= 0 && x < limitX - img.getWidth())
			this.x = x;
		if (y >= 100 && y < limitY - img.getHeight())
			this.y = y;
	}

	@Override
	public void draw (Graphics g){
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(degree), x + img.getWidth() / 2, y + img.getHeight() /2);
		Graphics2D image = (Graphics2D) (img.getGraphics());
		Graphics2D g2 = (Graphics2D) g;

		AffineTransform old = image.getTransform();
		g2.transform(transform);

		g2.drawImage(img, (int) x, (int) y, null);

		g2.setTransform(old);
	}

	public void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;

		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;

		}

	}

	public void keyReleased(KeyEvent e){
		int keycode = e.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;

		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		}
	}

	public void mouseMoved(MouseEvent e){


		double dX = e.getX() - (x + img.getWidth() / 2);
		double dY = (y + img.getHeight() /2) - e.getY();

		if(dX > 0)
			degree = 90 - ((int) Math.toDegrees(Math.atan(dY/dX)));

		else if (dX < 0)
			degree = 90 - (180 + (int) Math.toDegrees(Math.atan(dY/dX)));

	}

	public int getMoveX() {
		return moveX;
	}

	public int getMoveY() {
		return moveY;
	}

	public int checkDegree(){
		if(moveX == 10){
			if(moveY == 10){
				speed = 10 * Math.sqrt(2);
				return 135;
			}
			else if(moveY == -10){
				speed = 10 * Math.sqrt(2);
				return 45;
			}
			else{
				speed = 10;
				return 90;
			}
		}

		else if(moveX == -10){
			if(moveY == 10){
				speed = 10 * Math.sqrt(2);
				return 225;
			}
			else if(moveY == -10){
				speed = 10 * Math.sqrt(2);
				return 315;
			}
			else{
				speed = 10;
				return 270;
			}
		}
		else{
			if(moveY == 10){
				speed = 10;
				return 180;
			}
			else if(moveY == -10){
				speed = 10;
				return 0;
			}
			else{
				speed = 0;
				return 0;
			}
		}
	}

	public int getMoveDegree() {
		return moveDegree;
	}

	public double getSpeed() {
		return speed;
	}

	public BufferedImage getImg() {
		return img;
	}

	public int getLife() {
		return life;
	}

}


