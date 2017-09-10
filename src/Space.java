import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Space {
	private BufferedImage background;
	private Player player;
	private ArrayList<Object> objects;
	private int score, spawnA, spawnB, spawnC;
	public Space(String dir)
	{
		spawnA = 1000;
		spawnB = 2500;
		spawnC = 4700;
		try
		{
			background = ImageIO.read(new File("images//" + dir + ".png"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		objects = new ArrayList<Object>();
		player = new Player (new Coord(272, 500), new Coord(64, 64), "player", 100, 10, 50, new Coord (0,0));
		score = 0;
	}
	
	public void setPlayerVelocity (Coord currentVelocity)
	{
		player.setVelocity(currentVelocity);
	}
	
	public void playerShoot ()
	{
		objects.add(new Projectile(new Coord (player.getTopLeft().getX() + player.getDim().getX()/2,
											  player.getTopLeft().getY() + player.getDim().getY()/5), 
								   new Coord (6, 2), "playerBullet", player.getAttack(), new Coord (0, -3)));
	}
	
	public void update (int elapsed)
	{
		checkCollisions();
		if (!(((player.getVelocity().getX() == 1 && player.getTopLeft().getX() + player.getDim().getX() >= 600) ||
			   (player.getVelocity().getX() == -1 && player.getTopLeft().getX() <= 0)) ||
			  ((player.getVelocity().getY() == 1 && player.getTopLeft().getY() + player.getDim().getY() >= 600) ||
			   (player.getVelocity().getY() == -1 && player.getTopLeft().getY() <= 0))))
		{
			player.moveX(player.getVelocity().getX());
			player.moveY(player.getVelocity().getY());
		}
		
		for (int c = 0; c < objects.size(); c++)
		{
				objects.get(c).moveX(objects.get(c).getVelocity().getXDeci());
				objects.get(c).moveY(objects.get(c).getVelocity().getYDeci());
		}
		attack(elapsed);
		spawnEnemies (elapsed);
	}
	
	public void spawnEnemies (int elapsed)
	{
		if (elapsed % spawnA == 0)
		{
			objects.add(new Enemy(new Coord ((int) (Math.random()*(600-32)), -31), 
					new Coord (32, 32), "enemy1", 20, 0, 15, -1, new Coord (0, 0.5), new ArrayList<Attack>(), 100));
			if (spawnA >= 20)
				spawnA = (int)(Math.random()*10 + spawnA - 10);
		}
		if (elapsed % spawnB == 0)
		{
			ArrayList<Attack> attacks = new ArrayList<Attack>();
			attacks.add(new Attack("e_bullet2", new ArrayList<Integer>()));
			objects.add(new Enemy(new Coord ((int) (Math.random()*(600-32)), -31),
					new Coord (32, 32), "enemy2", 40, 5, 10, 400, new Coord (0, 0.2),
					attacks, 250));
			if (spawnB >= 20)
				spawnB = (int)(Math.random()*10 + spawnB - 10);
		}
		if (elapsed % spawnC == 0)
		{
			ArrayList<Attack> attacks = new ArrayList<Attack>();
			ArrayList<Integer> angles = new ArrayList<Integer>();
			angles.add(240);
			angles.add(270);
			angles.add(300);
			attacks.add(new Attack("e_bullet3", angles));
			objects.add(new Enemy(new Coord ((int) (Math.random()*(600-32)), -31),
					new Coord (32, 32), "enemy3", 60, 10, 30, 800, new Coord (0, 0.1),
					attacks, 500));
			if (spawnC >= 20)	
				spawnC = (int)(Math.random()*10 + spawnC - 10);
		}
	}
	
	public void checkCollisions()
	{
		for (int c = 0; c < objects.size(); c++)
		{
			if (player.isContact(objects.get(c)))
			{
				if (objects.get(c) instanceof Projectile)
				{
					player.damage(((Projectile) objects.get(c)).getDmg());
					objects.remove(c);
					c--;
				}
				else
				{
					player.damage((((Entity) objects.get(c)).getSelfAtk()));
					((Entity) objects.get(c)).damage(player.getSelfAtk());
					if (((Entity) objects.get(c)).isDead())
					{
						objects.remove(c);
						c--;
					}
				}
				System.out.println(player.getHP());
			}
			else
			{
				for (int d = c + 1; (d < objects.size() && c >= 0); d++)
				{
					if (objects.get(c).isContact(objects.get(d)))
					{
						if (objects.get(c) instanceof Projectile && objects.get(d) instanceof Entity)
						{
							((Entity) objects.get(d)).damage(((Projectile) objects.get(c)).getDmg());
							objects.remove(c);
							c--;
							d--;
							if (((Entity) objects.get(d)).isDead())
							{
								objects.remove(d);
								d--;
							}
						}
						else
						if (objects.get(d) instanceof Projectile && objects.get(c) instanceof Entity)
						{
							((Entity) objects.get(c)).damage(((Projectile) objects.get(d)).getDmg());
							objects.remove(d);
							d--;
							if (((Entity) objects.get(c)).isDead())
							{
								score += ((Enemy) objects.get(c)).getScore();
								objects.remove(c);
								c--;
								d--;
							}
						}
					}
				}
			}	
		}
		
	}
	
	public void attack (int elapsed)
	{
		for (int c = 0; c < objects.size(); c++)
		{
			if (objects.get(c) instanceof Enemy)
			{
				if (((Enemy) objects.get(c)).doAttack(elapsed))
				{
					Coord dir = new Coord(0, 0);
					if (((Enemy) objects.get(c)).isTargeted())
					{
						double dx = player.getTopLeft().getX() + player.getDim().getX()/2 - objects.get(c).getTopLeft().getX() - objects.get(c).getDim().getX()/2;
						double dy = player.getTopLeft().getY() + player.getDim().getY()/4 - objects.get(c).getTopLeft().getY() - objects.get(c).getDim().getY();
						double magnitude = Math.sqrt(dx*dx + dy*dy);
						dir.setX(dx/(2*magnitude));
						dir.setY(dy/(2*magnitude));
						objects.add(new Projectile(new Coord (objects.get(c).getTopLeft().getX() + objects.get(c).getDim().getX()/2 - 4,
								  objects.get(c).getTopLeft().getY() + objects.get(c).getDim().getY()), 
					    new Coord (6, 2),  ((Enemy) objects.get(c)).getAttackImg(), ((Enemy) objects.get(c)).getAttack(), dir));
					}
					else
					{
						ArrayList<Integer> angles = ((Enemy) objects.get(c)).getAngles();
						for (int d = 0; d < angles.size(); d++)
						{
							Coord direct = new Coord(0,0);
							direct.setX(Math.cos(Math.toRadians(angles.get(d)))/2);
							direct.setY(-1*Math.sin(Math.toRadians(angles.get(d)))/2);
							objects.add(new Projectile(new Coord (objects.get(c).getTopLeft().getX() + objects.get(c).getDim().getX()/2 - 4,
									  objects.get(c).getTopLeft().getY() + objects.get(c).getDim().getY() + 1), 
						    new Coord (6, 2),  ((Enemy) objects.get(c)).getAttackImg(), ((Enemy) objects.get(c)).getAttack(), direct));
						}
					} 
				}
			}
		}
	}
	
	public int getScore()
	{
		return score;
	}
	
	public boolean gameEnded()
	{
		return player.isDead();	
	}
	
	public void show (Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		player.show(g);
		for (int c = 0; c < objects.size(); c++)
		{
			objects.get(c).show(g);
		}
		
		for (int c = 0; c < objects.size(); c++)
		{
			if (objects.get(c).isOutOfBounds())
			{
				objects.remove(c);
				c--;
			}
		}
		g.drawString("SCORE: " + score, 515, 570);
	}
}
