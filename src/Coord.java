
public class Coord {
	private double x, y;
	
	public Coord(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void changeX(double var)
	{
		x += var;
	}
	
	public void changeY(double var)
	{
		y += var;
	}
	
	public void setX (double x)
	{
		this.x = x;
	}
	
	public void setY (double y)
	{
		this.y = y;
	}
	
	public int getX()
	{
		return (int) Math.round(x);
	}
	
	public int getY()
	{
		return (int) Math.round(y);
	}
	
	public double getXDeci()
	{
		return x;
	}
	
	public double getYDeci()
	{
		return y;
	}
}
