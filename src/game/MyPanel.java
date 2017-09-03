package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements KeyListener, ActionListener, MouseMotionListener{

	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyBullet> enemyBullets;
	private Lock lockEnemies;
	private Lock lockBullets;
	private Lock lockEnemyBullets;
	private boolean gameOver = false;
	private JButton fakeButton;
	private int countDeadEnemy;
	private long makeEnemyTime = 20000;
	private double term = 0.1;
	private JButton checkPlayerLife;
	private JButton checkEnemyDie;

	public MyPanel(){
		super();

		lockEnemies = new ReentrantLock();
		lockBullets = new ReentrantLock();
		lockEnemyBullets = new ReentrantLock();

		player = new Player(640, 800);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		enemyBullets = new ArrayList<EnemyBullet>();
		fakeButton = new JButton();
		checkPlayerLife = new JButton();
		checkEnemyDie = new JButton();
		countDeadEnemy = 0;
		
		setDoubleBuffered(true);
		addKeyListener(this);
		addMouseMotionListener(this);
		
		this.requestFocusInWindow();
		setFocusable(true);
		setLayout(null);

		JPanel topSide = new TopSide(checkPlayerLife, checkEnemyDie, player, this);
		topSide.setBounds(0,  0,  1280, 100);
		
		add(topSide);

		Thread draw = new Thread(new Draw());
		Thread myEvent = new Thread(new MyEvent());
		Thread enemyEvent = new Thread(new EnemyEvent());
		Thread enemyDamaged = new Thread(new EnemyDamaged());
		Thread playerDamaged = new Thread(new PlayerDamaged());
		Thread enemyShoot = new Thread(new EnemyShoot());
		draw.start();
		myEvent.start();
		enemyEvent.start();
		enemyDamaged.start();
		playerDamaged.start();
		enemyShoot.start();
		
		
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		player.draw(g);

		lockBullets.lock();
		try{
			for(Bullet b : bullets){
				b.draw(g);
			}
		}finally{
			lockBullets.unlock();
		}

		lockEnemies.lock();
		try{
			for(Enemy e : enemies){
				e.draw(g);
			}
		}finally{
			lockEnemies.unlock();
		}

		lockEnemyBullets.lock();
		try{
			for(EnemyBullet eB : enemyBullets){
				eB.draw(g);
			}
		}finally{
			lockEnemyBullets.unlock();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		Bullet.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
		Bullet.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}


	private class Draw implements Runnable{

		@Override
		public void run() {

			while (!gameOver) {

				player.update();
				lockBullets.lock();
				try{
					for(int index = 0; index < bullets.size(); index++){
						if(bullets.get(index).update())
							bullets.remove(index--);
					}
				}finally{
					lockBullets.unlock();
				}

				lockEnemies.lock();
				try{
					for(Enemy e : enemies){
						e.update();
					}
				}finally{
					lockEnemies.unlock();
				}

				lockEnemyBullets.lock();
				try{
					for(int index = 0; index < enemyBullets.size(); index++){
						if(enemyBullets.get(index).update())
							enemyBullets.remove(index--);
					}
				}finally{
					lockEnemyBullets.unlock();
				}

				repaint();

				makeEnemyTime = (long) (makeEnemyTime - term);
				
				try {
					Thread.sleep(40);
				}catch (InterruptedException e){

				}
			}
		}

	}
	
	private class EnemyShoot implements Runnable {
		
		@Override
		public void run() {
			while(!gameOver){
				lockEnemies.lock();
				try{
					for(Enemy e : enemies){
						lockEnemyBullets.lock();
						try{
							EnemyBullet.makeBullet(e.getCenterX(), e.getCenterY(), e.getDegree(), enemyBullets);
						}finally{
							lockEnemyBullets.unlock();
						}
					}
				}finally{
					lockEnemies.unlock();
				}
				
				try {
					Thread.sleep(500);
				}catch (InterruptedException e){

				}
			}
		}
	}

	private class MyEvent implements Runnable {

		@Override
		public void run() {
			while(!gameOver){
				lockBullets.lock();
				try{
					Bullet.makeBullet(player.getX() + 25, player.getY() + 25, player.getDegree(), bullets);
				}finally{
					lockBullets.unlock();
				}

				try {
					Thread.sleep(300);
				}catch (InterruptedException e){

				}
			}
		}

	}

	private class EnemyEvent implements Runnable {

		@Override
		public void run() {
			while(!gameOver) {
				lockEnemies.lock();
				try{
					Enemy.makeEnemy(enemies, player);
				}finally{
					lockEnemies.unlock();
				}

				try{
					Thread.sleep(makeEnemyTime);;
				} catch (InterruptedException e){

				}
			}
		}

	}

	private class EnemyDamaged implements Runnable {

		@Override
		public void run() {
			
			while(!gameOver){
				lockEnemies.lock();
				try{
					Enemy enemy;


					for(int index = 0; index < enemies.size(); index++){
						enemy = enemies.get(index);

						lockBullets.lock();
						try{
							if(enemy.isDead(bullets)){
								enemies.remove(index--);
								countDeadEnemy++;
								checkEnemyDie.doClick();
							}
						}finally{
							lockBullets.unlock();
						}
					}
				} finally {
					lockEnemies.unlock();
				}

				try{
					Thread.sleep(10);
				} catch (InterruptedException e){

				}

			}

		}

	}

	private class PlayerDamaged implements Runnable {

		@Override
		public void run() {

			while(!gameOver){

				lockEnemyBullets.lock();
				try{
					if(player.isDead(enemyBullets, checkPlayerLife)){
						gameOver = true;
						fakeButton.doClick();
					}

				}finally{
					lockEnemyBullets.unlock();
				}
				try{
					Thread.sleep(10);
				} catch (InterruptedException e){

				}
			}
		}

	}

	public Player getPlayer() {
		return player;
	}

	public Lock getLockEnemies() {
		return lockEnemies;
	}

	public Lock getLockBullets() {
		return lockBullets;
	}

	public Lock getLockEnemyBullets() {
		return lockEnemyBullets;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public ArrayList<EnemyBullet> getEnemyBullets() {
		return enemyBullets;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		player.mouseMoved(e);
		
	}

	public JButton getFakeButton() {
		return fakeButton;
	}

	public int getCountDeadEnemy() {
		return countDeadEnemy;
	}
	
}

