package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultPanel extends JPanel{
	
	private BufferedImage imgGameOver;
	private JButton restart;
	private JLabel scoreLabel;
	private Font font;
	
	public ResultPanel (int score){
		
		setLayout(null);
		try {
			imgGameOver = ImageIO.read(new File("img/GameOver.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = new Font("Terminal",Font.PLAIN , 90);
	
		scoreLabel = new JLabel("" + score);
		scoreLabel.setBounds(756, 510, 367, 72);
		scoreLabel.setFont(font);
		scoreLabel.setForeground(new Color(224, 214, 249));
		
		restart = new JButton(new ImageIcon("img/RestartButton.png"));
		restart.setPressedIcon(new ImageIcon("img/RestartButtonPressed.png"));
		
		restart.setBorderPainted(false);
		restart.setContentAreaFilled(false);
		restart.setFocusPainted(false);
		
		restart.setBounds(377, 784, 506, 68);
		
		add(restart);
		add(scoreLabel);
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(imgGameOver, 0, 0 , 1280, 1080, null);
	}

	public JButton getRestart() {
		return restart;
	}

}
