import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Azman extends Enemy
{
	boolean a;
	public Azman(int ID, ArrayList<AdjacencyTile> adjacencyList, Point p)
	{
		super(ID, adjacencyList, p);
		image = new ImageIcon("Azman.png").getImage();
		a = false;
		this.ID = 1;
	}

	 
	@Override 
	public void shortestPath(Player player)
	{
		path.clear();
		if(this.getLoc().getX() != player.getLoc().getX() || this.getLoc().getY() != player.getLoc().getY())
		{
				int enemyLoc = this.getCurrentTile().getID();
				int playerLoc = player.getCurrentTile().getID();
				dijkstra(enemyLoc,playerLoc);
		}
		if(!path.isEmpty() && a)
		{
			this.setLoc(path.get(1));
			for(int i = 0; i < path.size(); i++)
			{
				if(adjacencyList.get(i).getTile().getLoc().getX() == path.get(0).getX() &&
				   adjacencyList.get(i).getTile().getLoc().getY() == path.get(0).getY())
				{
					this.setCurrentTile(adjacencyList.get(i).getTile());
				}
			}
		}
		a = true;
		
		if(this.getLoc().getX() == player.getLoc().getX() &&
		   this.getLoc().getY() == player.getLoc().getY())
		{
			p.azmanAttacked();
			this.setLoc(this.getFirstLoc());
			for(int i = 0; i < adjacencyList.size(); i++)
			{
				if(adjacencyList.get(i).getTile().getLoc().getX() == this.getLoc().getX() &&
				   adjacencyList.get(i).getTile().getLoc().getY() == this.getLoc().getY())
				{
					this.setCurrentTile(adjacencyList.get(i).getTile());
				}
			}
			a = false;
			this.shortestPath(player);
		}
		
		
		
	}
	
}
