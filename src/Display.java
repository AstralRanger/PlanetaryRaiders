import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JFrame implements ActionListener, KeyListener
{
	DrawArea content;
	Space space;
	static Timer t;
	int elapsed;
	Coord currentDir;
	public Display()
	{
		elapsed = 0;
		
		int x = (int) (Math.random()*2);
		if (x == 0)
			space = new Space("background1");
		else
			space = new Space("background2");
		
		t = new Timer (5, this);
		t.addActionListener(this);
		t.start();
		currentDir = new Coord (0, 0);
		
		content = new DrawArea (600, 600);
		content.addKeyListener(this);
		
		setContentPane (content);
		pack ();
		setTitle ("PlanetaryRaiders");
		setSize (600, 600);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setResizable (false);
		setLocationRelativeTo (null);
	}

	class DrawArea extends JPanel
    {
		public DrawArea (int width, int height)
		{
			this.setFocusable(true);
		    this.setPreferredSize (new Dimension (width, height)); 
		}
	
	
		public void paintComponent (Graphics g)
		{
			space.show(g);
		}
    }
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			if (currentDir.getY() != -1)
				currentDir.changeY(-1);
		}
		else
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if (currentDir.getY() != 1)
				currentDir.changeY(1);
		}
		else
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if (currentDir.getX() != -1)
				currentDir.changeX(-1);
		}
		else
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (currentDir.getX() != 1)
				currentDir.changeX(1);
		}
		else
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			space.playerShoot();
		}
		
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			if (currentDir.getY() != 1)
				currentDir.changeY(1);
		}
		else
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if (currentDir.getY() != -1)
				currentDir.changeY(-1);
		}
		else
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{	
			if (currentDir.getX() != 1)
				currentDir.changeX(1);
		}
		else
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (currentDir.getX() != -1)
				currentDir.changeX(-1);
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		elapsed++;
		space.setPlayerVelocity(currentDir);
		space.update(elapsed);
		repaint();
		if (space.gameEnded())
		{
			t.stop();
			JOptionPane.showMessageDialog(this, "You died. Game over. Your score was " + space.getScore() + ".", "RIP", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
}
