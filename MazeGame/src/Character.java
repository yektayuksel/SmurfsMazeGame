import java.awt.Image;

public class Character 
{
	int ID;
	String name;
	String type;
	Image image;
	Location loc;
	Tile currentTile;
	public Tile getCurrentTile() {
		return currentTile;
	}
	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}
	Character(){}
	Character(int ID)
	{
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
	
}
