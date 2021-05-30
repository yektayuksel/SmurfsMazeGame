
public class AdjacencyTile 
{
	int i;
	int j;
	Tile tile;
	AdjacencyTile(int i, int j, Tile tile)
	{
		this.i=i;
		this.j=j;
		this.tile=tile;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public Tile getTile() {
		return tile;
	}
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
}
