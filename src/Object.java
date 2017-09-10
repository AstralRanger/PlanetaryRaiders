import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class Object {
	protected Coord topLeft, dim, velocity;
	protected BufferedImage img;
	
	public BufferedImage getImg()
	{
		return img;
	}
	
	public void moveX(double dist)
	{
		this.topLeft.changeX(dist);
	}
	
	public void moveY(double dist)
	{
		this.topLeft.changeY(dist);
	}
	
	public Coord getTopLeft()
	{
		return topLeft;
	}
	
	public Coord getDim()
	{
		return dim;
	}
	
	public Coord getVelocity()
	{
		return velocity;
	}
	
	public void setVelocity(Coord velocity)
	{
		this.velocity = velocity;
	}
	
	public boolean isOutOfBounds()
	{
		return topLeft.getX() > 600 || topLeft.getY() > 600 || topLeft.getX() + dim.getX() < 0 || 
			   topLeft.getY() + dim.getY() < 0;
	}
	
	public boolean isContact (Object other)
	{
		return ((other.topLeft.getX() + other.dim.getX() > topLeft.getX() && 
				 other.topLeft.getX() + other.dim.getX() < topLeft.getX() + dim.getX()) || 
			    (other.topLeft.getX() < topLeft.getX() + dim.getX() &&  
			     other.topLeft.getX() > topLeft.getX())) &&
			   ((other.topLeft.getY() + other.dim.getY() > topLeft.getY() && 
				 other.topLeft.getY() + other.dim.getY() < topLeft.getY() + dim.getY()) || 
			    (other.topLeft.getY() < topLeft.getY() + dim.getY() && 
				 other.topLeft.getY() > topLeft.getY()));
	}
	
	
	public void show(Graphics g)
	{
		g.drawImage(img, topLeft.getX(), topLeft.getY(), null);
	}
	
}
