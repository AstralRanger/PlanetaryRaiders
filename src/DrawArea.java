import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

class DrawArea extends JPanel
	    {
			public DrawArea (int width, int height)
			{
			    this.setPreferredSize (new Dimension (width, height)); // size
			}
		
		
			public void paintComponent (Graphics g)
			{

			}
	    }