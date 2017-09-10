import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Attack {
	public final int targeted = -1;
	
	private BufferedImage attackImg;
    private ArrayList<Integer> angles;
   
    public Attack (String dir, ArrayList<Integer> angles)
    {
    	this.angles = angles;
    	
    	try
		{
			attackImg = ImageIO.read(new File("images//" + dir + ".png"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
    }
    
    public BufferedImage getImg()
    {
    	return attackImg;
    }
    
    public boolean isTargeted()
    {
    	return angles.size() == 0;
    }
    
    public ArrayList<Integer> getAngles()
    {
    	return angles;
    }
}
