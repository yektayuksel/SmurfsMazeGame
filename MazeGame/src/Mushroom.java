import javax.swing.ImageIcon;

public class Mushroom extends Collectable
{
	Mushroom(Location loc)
	{
		super(loc);
		image = new ImageIcon("Mantar.png").getImage();
	}
}