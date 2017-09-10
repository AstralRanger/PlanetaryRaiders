import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Projectile extends Object{
	
	private int dmg;
	
	public Projectile (Coord topLeft, Coord dim, String dir, int dmg, Coord velocity)
	{
		this.topLeft = topLeft;
		this.dim = dim;
		this.dmg = dmg;
		this.velocity = velocity;
		
		try
		{
			img = ImageIO.read(new File("images//" + dir + ".png"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}	
	
	public Projectile (Coord topLeft, Coord dim, BufferedImage img, int dmg, Coord velocity)
	{
		this.topLeft = topLeft;
		this.dim = dim;
		this.dmg = dmg;
		this.velocity = velocity;
		this.img = img;
	}
	
	public int getDmg()
	{
		return dmg;
	}
	
}
