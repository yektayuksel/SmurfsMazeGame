
public class Player extends Character
{
	int score;
	int playerID;
	String playerName;
	String playerType;
	Player()
	{
		playerID = ID;
		playerType = type;
		score = 20;
	}
	Player(int ID, String type)
	{
		super();
		score = 20;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
		
	public int showPoints()
	{
		return this.getScore();
	}
	
}
