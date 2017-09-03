package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopSide extends JPanel implements ActionListener{
	
	private JButton checkPlayerLife;
	private JButton checkEnemyDie;
	private Player player;
	private MyPanel panel;
	private BufferedImage backGround, playerImg;
	private JLabel score;
	
	public TopSide(JButton checkPlayerLife, JButton checkEnemyDie, Player player, MyPanel panel){
		
		setLayout(null);
		
		try {
			backGround = ImageIO.read(new File("img/TopSide.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.checkPlayerLife = checkPlayerLife;
		this.checkEnemyDie = checkEnemyDie;
		this.playerImg = player.getImg();
		
		checkPlayerLife.addActionListener(this);
		checkEnemyDie.addActionListener(this);
		
		this.player = player;
		this.panel = panel;
		
		this.score = new JLabel("0");
		score.setBounds(1130, 15, 120, 75);
		score.setForeground(Color.WHITE);
		score.setFont(new Font("Terminal",Font.PLAIN , 50));
		
		add(score);
		
		setPreferredSize(new Dimension(1280, 100));
		setBackground(Color.BLACK);
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(backGround, 0, 0, null);
		
		for(int i = 0; i < player.getLife(); i++){
			g2.drawImage(playerImg, 610  + i* 90, 25, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		score.setText("" + panel.getCountDeadEnemy());
		
		repaint();
	}

}
