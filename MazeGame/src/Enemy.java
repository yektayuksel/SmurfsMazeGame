import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Enemy extends Character
{
	
	int enemyID = ID;
	String enemyName = name;
	String enemyType = type;
			
	int adjacencyMatrix[][];
	private static final int NO_PARENT = -1; 
    static ArrayList<Integer> pathArray;
	File adjFile = new File("adjacencyMatrix.txt");
	Scanner sc;
	ArrayList<Location> path;
	ArrayList<AdjacencyTile> adjacencyList;
	Point p;
	Location firstLoc;
	public Location getFirstLoc() {
		return firstLoc;
	}
	public void setFirstLoc(Location firstLoc) {
		this.firstLoc = firstLoc;
	}
	//DijkstrasAlgorithm dijkstraMethod = new DijkstrasAlgorithm();
	public Enemy(int ID, ArrayList<AdjacencyTile> adjacencyList, Point p)
	{
		super(ID);
		try {
			sc = new Scanner(adjFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.adjacencyList = adjacencyList;
		this.p = p;
		adjacencyMatrix = new int[this.adjacencyList.size()][this.adjacencyList.size()];
		pathArray = new ArrayList<Integer>();
		path = new ArrayList<Location>();
		readAdjMatrix();
		
	}
	public ArrayList<Location> getPath() {
		return path;
	}
	public void setPath(ArrayList<Location> path) {
		this.path = path;
	}

	public void readAdjMatrix()
	{
		int i=0;
		do
		{
		
			String a = sc.nextLine();
			for(int j = 0; j < a.length(); j++)
			{
				adjacencyMatrix[i][j] = a.charAt(j) - '0';
			}
			i++;
			
		}while(sc.hasNextLine());
	}
	
	public void shortestPath(Player player)
	{
		path.clear();
		int enemyLoc = this.getCurrentTile().getID();
		int playerLoc = player.getCurrentTile().getID();
		dijkstra(enemyLoc,playerLoc);
		this.setLoc(path.get(0));
		for(int i = 0; i < path.size(); i++)
		{
			if(adjacencyList.get(i).getTile().getLoc().getX() == path.get(0).getX() &&
			   adjacencyList.get(i).getTile().getLoc().getY() == path.get(0).getY())
			{
				this.setCurrentTile(adjacencyList.get(i).getTile());
			}
		}
		
	}
	public Tile findByID(int ID)
	 {
		 for(int i = 0; i < adjacencyList.size(); i++)
		 {
			 if(adjacencyList.get(i).getTile().getID() == ID)
			 {
				 return adjacencyList.get(i).getTile();
			 }
		 }
		 return null;
	 }
	

	 public void dijkstra(int startVertex, int endVertex) 
	    { 
	    	
	        int nVertices = adjacencyMatrix[0].length; 
	        int[] shortestDistances = new int[nVertices]; 
	        boolean[] added = new boolean[nVertices]; 
	  
	        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
	        { 
	            shortestDistances[vertexIndex] = Integer.MAX_VALUE; 
	            added[vertexIndex] = false; 
	        } 
	          
	        shortestDistances[startVertex] = 0; 
	        int[] parents = new int[nVertices]; 
	        parents[startVertex] = NO_PARENT; 
	  
	        for (int i = 1; i < nVertices; i++) 
	        { 
	  
	            int nearestVertex = -1; 
	            int shortestDistance = Integer.MAX_VALUE; 
	            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
	            { 
	                if (!added[vertexIndex] && shortestDistances[vertexIndex] <  shortestDistance)  
	                { 
	                	
	                    nearestVertex = vertexIndex; 
	                    shortestDistance = shortestDistances[vertexIndex]; 
	                } 
	            } 
	  
	            added[nearestVertex] = true; 
	  
	            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)  
	            { 
	                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex]; 
	                  
	                if (edgeDistance == 1 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex]))  
	                { 
	                    parents[vertexIndex] = nearestVertex; 
	                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance; 
	                } 
	            } 
	        } 
	  
	        returnPath(endVertex, parents); 
	    } 
	  
	   
	  
	    private void returnPath(int currentVertex, int[] parents) 
	    { 
	          
	        if (currentVertex == NO_PARENT) 
	        { 
	            return; 
	        } 
	        returnPath(parents[currentVertex], parents); 
	        path.add(findByID(currentVertex).getLoc());
	        
	    } 
	

}
