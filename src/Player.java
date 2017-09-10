import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;

public class Player extends Entity {
	
	public Player (Coord topLeft, Coord dim, String dir, int hp, int projAtk, int selfAtk, Coord velocity)
	{
		this.topLeft = topLeft;
		this.dim = dim;
		this.hp = hp;
		this.projAtk = projAtk;
		this.selfAtk = selfAtk;
		
		try
		{
			img = ImageIO.read(new File("images//" + dir + ".png"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void show (Graphics g)
	{
		if (hp <= 0)
		{
			g.setColor(Color.red);
			g.fillRect(topLeft.getX() - 18, topLeft.getY() + dim.getY() + 10, 100, 5);
		}
		else
		{
			g.setColor(Color.green);
			g.fillRect(topLeft.getX() - 18, topLeft.getY() + dim.getY() + 10, hp, 5);
			g.setColor(Color.red);
			g.fillRect(topLeft.getX() - 18 + hp, topLeft.getY() + dim.getY() + 10, 100 - hp, 5);
		}
		super.show(g);
	}
	
	public boolean isContact (Object other)
	{
		return ((other.topLeft.getX() + other.dim.getX() > topLeft.getX() && 
				 other.topLeft.getX() + other.dim.getX() < topLeft.getX() + dim.getX()) || 
			    (other.topLeft.getX() < topLeft.getX() + dim.getX() &&  
			     other.topLeft.getX() > topLeft.getX())) &&
			   ((other.topLeft.getY() + other.dim.getY() > topLeft.getY() + dim.getY()/4 && 
				 other.topLeft.getY() + other.dim.getY() < topLeft.getY() + dim.getY()) || 
			    (other.topLeft.getY() < topLeft.getY() + dim.getY() && 
				 other.topLeft.getY() > topLeft.getY() + dim.getY()/4));
	}
}
