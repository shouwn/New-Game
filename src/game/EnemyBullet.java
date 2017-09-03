package game;

import java.awt.Color;
import java.util.ArrayList;

public class EnemyBullet extends Bullet{

	private int playerSize = 7;

	public EnemyBullet(double x, double y, int degree) {
		super(x, y, degree);
		super.setColor(Color.BLUE);
		super.setDistance(10);
		super.setSize(8);
	}

	@Override
	public boolean isCrash(Point point){
		double limit = super.getSize() + playerSize;

		double distance = Math.sqrt(Math.pow((super.getX() - point.getX()), 2) + Math.pow(super.getY() - point.getY(), 2));

		if(distance < limit)
			return true;
		else
			return false;

	}

	public boolean isCrash(double playerX, double playerY){
		double limit = super.getSize() + playerSize;

		double distance = Math.sqrt(Math.pow((super.getX() - playerX), 2) + Math.pow(super.getY() - playerY, 2));

		if(distance < limit)
			return true;
		else
			return false;

	}

	@SuppressWarnings("unchecked")
	public static void makeBullet(double x, double y, int degree, ArrayList<? extends Bullet> enemyBullets) {

		((ArrayList<EnemyBullet>) enemyBullets).add(new EnemyBullet(x, y, degree));

	}

}
