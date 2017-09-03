package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartScreen extends JPanel implements KeyListener, ActionListener{

	private BufferedImage img, helpImg;
	private JButton start, help;
	private boolean popHelpImg = false;

	public StartScreen(){
		try {
			img = ImageIO.read(new File("img/StartScreen.gif"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		try {
			helpImg = ImageIO.read(new File("img/help.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		start = new JButton(new ImageIcon("img/StartButton.png"));
		start.setPressedIcon(new ImageIcon("img/StartButtonPressed.png"));

		help = new JButton(new ImageIcon("img/HelpButton.png"));
		help.setPressedIcon(new ImageIcon("img/HelpButtonPressed.png"));

		setLayout(null);

		start.setBounds(640 - 350, 550, 700, 150);
		help.setBounds(640 - 350, 750, 700, 150);

		start.setBorderPainted(false);
		start.setContentAreaFilled(false);
		start.setFocusPainted(false);

		help.setBorderPainted(false);
		help.setContentAreaFilled(false);
		help.setFocusPainted(false);

		help.addActionListener(this);
		
		help.addKeyListener(this);
		
		add(start);
		add(help);
	}

	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, null);
		
		if(popHelpImg == true){
			g2.drawImage(helpImg,0,0,null);
		}

	}

	public JButton getStart() {
		return start;
	}

	public JButton getHelp() {
		return help;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		
		if(keycode == KeyEvent.VK_C){
			popHelpImg = false;
			start.setBounds(640 - 350, 550, 700, 150);
			help.setBounds(640 - 350, 750, 700, 150);
			repaint();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		popHelpImg = true;
		start.setBounds(-2000, 550, 700, 150);
		help.setBounds(-2000, 750, 700, 150);
		
		repaint();
	}
}
