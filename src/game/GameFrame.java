package game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame implements ActionListener{

	private JPanel panel;
	private StartScreen startScreen;
	private int width = 1280;
	private int height = 1080;
	private JButton start;
	private JButton result;
	private JButton restart;
	private int score;

	public GameFrame(){
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation((screenSize.width - width)/2 , (screenSize.height - height)/ 2);
		setTitle("MyFrame");
		setSize(width, height);

		startScreen = new StartScreen();
		panel = startScreen;
		panel.setPreferredSize(new Dimension(1280, 1080));
		panel.setBounds(0, 0, 1280, 1080);
		
		add(panel);
		start = startScreen.getStart();
		
		setResizable(false);
		start.addActionListener(this);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(!(panel instanceof MyPanel)){
			this.getContentPane().removeAll();

			panel = new MyPanel();

			result = ((MyPanel) panel).getFakeButton();
			result.addActionListener(this);

			panel.setBounds(0, 0, 1280, 1080);
			this.add(panel);
			this.getContentPane().revalidate();
			this.getContentPane().repaint();
			panel.requestFocusInWindow();
		}
		else {
			score = ((MyPanel) panel).getCountDeadEnemy();
			this.getContentPane().removeAll();
			
			panel = new ResultPanel(score);
			panel.setBounds(0, 0, 1280, 1080);
			restart = ((ResultPanel) panel).getRestart();
			restart.addActionListener(this);
			
			this.getContentPane().add(panel);
			this.getContentPane().revalidate();
			this.getContentPane().repaint();
			panel.requestFocusInWindow();
		}
	}

}
