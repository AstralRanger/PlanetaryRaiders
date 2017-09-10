import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
	ArrayList <Attack> attacks;
	private int ats, score, spawnMin, spawnMax;
	public Enemy (Coord topLeft, Coord dim, String dir, int hp, int projAtk, int selfAtk, int ats, Coord velocity, ArrayList<Attack> attacks, int score)
	{
		this.topLeft = topLeft;
		this.dim = dim;
		this.hp = hp;
		this.projAtk = projAtk;
		this.selfAtk = selfAtk;
		this.attacks = attacks;
		this.velocity = velocity;
		this.ats = ats;
		this.score = score;
		try
		{
			img = ImageIO.read(new File("images//" + dir + ".png"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public BufferedImage getAttackImg ()
	{
		return attacks.get(0).getImg();
	}
	
	public boolean doAttack (int elapsed)
	{
		if (ats == -1)
			return false;
		return (elapsed % ats == 0);			
	}
	
	public boolean isTargeted ()
	{
		return attacks.get(0).isTargeted();
	}
	
	public ArrayList<Integer> getAngles ()
	{
		return attacks.get(0).getAngles();
	}
	
	public int getScore()
	{
		return score;
	}
}
