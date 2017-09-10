
public abstract class Entity extends Object {
	
	protected int hp, projAtk, selfAtk;
	
	public int getAttack()
	{
		return projAtk;
	}
	
	public int getSelfAtk()
	{
		return selfAtk;
	}
	
	public void damage (int dmgTake)
	{
		hp -= dmgTake;
	}
	
	public boolean isDead ()
	{
		return hp <= 0;
	}
	
	public int getHP()
	{
		return hp;
	}
	
}
