import java.util.ArrayList;

public class Tile 
{
	Tile left;
	Tile right;
	Tile down;
	Tile up;
	ArrayList<Tile> adjacency = new ArrayList<Tile>();
	Location loc;
	int type;
	int ID;
	//0=wall
	//1=path
	//2=gate
	Tile(Location loc, int type)
	{
		this.loc = loc;
		this.type = type;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public ArrayList<Tile> getAdjacency() {
		return adjacency;
	}
	public void setAdjacency(ArrayList<Tile> adjacency) {
		this.adjacency = adjacency;
	}
	public Tile getLeft() {
		return left;
	}
	public void setLeft(Tile left) {
		this.left = left;
	}
	public Tile getRight() {
		return right;
	}
	public void setRight(Tile right) {
		this.right = right;
	}
	public Tile getDown() {
		return down;
	}
	public void setDown(Tile down) {
		this.down = down;
	}
	public Tile getUp() {
		return up;
	}
	public void setUp(Tile up) {
		this.up = up;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
