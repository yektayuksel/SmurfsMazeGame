
public class Location 
{
	static final int UNIT_SIZE = 70;
	int x,y;
	Location(int x, int y)
	{
		this.x = UNIT_SIZE*x;
		this.y = UNIT_SIZE*y;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = UNIT_SIZE*x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = UNIT_SIZE*y;
	}
	
}
