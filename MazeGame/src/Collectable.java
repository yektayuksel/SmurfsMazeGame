import java.awt.Image;

public class Collectable 
{
	Location loc;
	Image image;
	Tile currentTile;
	Collectable(){}
	Collectable(Location loc)
	{
		this.loc = loc;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Tile getCurrentTile() {
		return currentTile;
	}
	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}
	
}
