import javax.swing.ImageIcon;

public class Coin extends Collectable
{
	final static int numOfCoins = 5;
	Coin(Location loc)
	{
		super(loc);
		
		image = new ImageIcon("Coin.png").getImage();
	}
	public int getNumOfCoins() {
		return numOfCoins;
	}
	
	
}
