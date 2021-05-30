
public class Point extends Player
{
	Player player;
	
	
	int score;
	public Point(Player player)
	{
		this.player = player;
	
		
		score = 20;
	}
	
	public void azmanAttacked()
	{
		this.score -= 5;
	}
	public void gargamelAttacked()
	{
		this.score -= 15;
	}
	public void coinPicked()
	{
		this.score += 5;
		
	}
	
	public void mushroomPicked()
	{
		this.score += 50;
	}
	
	
	@Override
	public int showPoints()
	{
		return this.score;
	}
}
	
