import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener
{
	static final int UNIT_SIZE = 70;
	static int gameWidth = 13;
	static int gameHeight = 11;
	static final int GAME_WIDTH= gameWidth*UNIT_SIZE;
	static final int GAME_HEIGHT = gameHeight*UNIT_SIZE;
	static final int COIN_APPEARANCE_RATE= 5000;
	static final int MUSHROOM_APPEARANCE_RATE= 7000;
	Random rand = new Random();
	int MUSHROOM_SPAWN_RATE;
	int COIN_SPAWN_RATE;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	int[][] mapLocArray;
	ArrayList<Coin> Coins;
	ArrayList<Mushroom> Mushrooms;
	ArrayList<Tile> tileMap;
	Scanner sc;
	ArrayList<AdjacencyTile> adjacencyList = new ArrayList<AdjacencyTile>();
	int[][]adjacencyMatrix;
	File file;
	Player player;
	Timer coinSpawnTimer;
	Timer coinAppearanceTimer;
	Timer mushroomAppearanceTimer;
	Timer mushroomSpawnTimer;
	Enemy enemy1,enemy2;
	File adjFile;
	FileWriter adjWriter;
	PrintWriter adjPrinter;
	int movementSpeed;
	Point points;
	Location e1FirstLoc;
	Location e2FirstLoc;
	Image sirine = new ImageIcon("Sirine.png").getImage();
	GamePanel(int playerType)
	{
		if(playerType == 0)
		{
			player = new Gozluklu();
			movementSpeed = 1;
		}
		else if(playerType == 1)
		{
			player = new Guclu();
			movementSpeed = 2;
		}
		
		player.setLoc(new Location(6,5));
		tileMap = new ArrayList<Tile>();	
		file = new File("harita.txt");
		adjFile = new File("adjacencyMatrix.txt");
		try {
			adjWriter = new FileWriter(adjFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		adjPrinter = new PrintWriter(adjWriter);
		try {
			sc  = new Scanner(file);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		mapLocArray = new int[gameHeight][gameWidth];
		Coins = new ArrayList<Coin>();
		Mushrooms = new ArrayList<Mushroom>();
		String e1 = sc.nextLine();
		String e2 = sc.nextLine();
		points = new Point(player);
		getMap();
		teachSurroundings();
		findTheEdges();
		createAdjacencyMatrix();
		adjWriteMethod();
		getEnemys(e1,e2);
		setCurrTileOfPlayer();
		setCurrTileOfEnemys();
		enemy1.shortestPath(player);
		enemy2.shortestPath(player);
		
		
		
		coinAppearanceTimer = new Timer(COIN_APPEARANCE_RATE,this);
		COIN_SPAWN_RATE = rand.nextInt(5000) + 5000;
		coinSpawnTimer = new Timer(COIN_SPAWN_RATE,this);
		coinSpawnTimer.start();
		mushroomAppearanceTimer = new Timer(MUSHROOM_APPEARANCE_RATE, this);
		MUSHROOM_SPAWN_RATE = rand.nextInt(13000) + 7000;
		mushroomSpawnTimer = new Timer(MUSHROOM_SPAWN_RATE, this);
		mushroomSpawnTimer.start();
		
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		this.setPreferredSize(SCREEN_SIZE);
		this.setBackground(Color.white);
		
	}
	
	
	
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2D = (Graphics2D)g;
		
		
		
		for(int i = 0; i < gameHeight; i++)
		{
			for(int j = 0; j < gameWidth; j++)
			{
				if(mapLocArray[i][j] == 0)
				{
					
					g2D.setPaint(Color.gray);
					g2D.fillRect(j*UNIT_SIZE, i*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
				else if(mapLocArray[i][j] == 1) 
				{
					if(i == 0 || j == 0 || i == gameHeight-1 || j == gameWidth-1)
					{
						if(i == 7 && j == 12)
						{
							g2D.setPaint(Color.blue);
						}
						else
						{
							g2D.setPaint(Color.red);
						}
						
					}
					else
					{
						g2D.setPaint(Color.white);
					}
					g2D.fillRect(j*UNIT_SIZE, i*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
				
			}
		}
		g2D.setPaint(Color.green);
		
		for(int a = enemy1.getID(); a < enemy1.getPath().size(); a++)
		{
			g2D.fillRect(enemy1.getPath().get(a).getX(),enemy1.getPath().get(a).getY() ,UNIT_SIZE, UNIT_SIZE);
		}
		g2D.setPaint(Color.cyan);
		for(int b = enemy2.getID(); b < enemy2.getPath().size(); b++)
		{
			g2D.fillRect(enemy2.getPath().get(b).getX(),enemy2.getPath().get(b).getY() ,UNIT_SIZE, UNIT_SIZE);
		}
		
		g2D.drawImage(enemy1.getImage(), enemy1.getLoc().getX(), enemy1.getLoc().getY(), UNIT_SIZE,UNIT_SIZE,null);
		g2D.drawImage(enemy2.getImage(), enemy2.getLoc().getX(), enemy2.getLoc().getY(), UNIT_SIZE,UNIT_SIZE,null);
		
		g2D.setPaint(Color.black);
		g2D.setStroke(new BasicStroke(2));
		for(int i = 0; i < GAME_WIDTH; i++)
		{
			g2D.drawLine(0, i*UNIT_SIZE, GAME_WIDTH, i*UNIT_SIZE);
		}
		for(int i = 0; i < GAME_WIDTH; i++)
		{
			g2D.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,GAME_HEIGHT);
		}
		g2D.drawImage(player.getImage(), player.getLoc().getX(), player.getLoc().getY(), UNIT_SIZE,UNIT_SIZE,null);
		
		for(int i = 0; i < Coins.size(); i++)
		{
			g2D.drawImage(Coins.get(i).getImage(),Coins.get(i).getLoc().getX(), Coins.get(i).getLoc().getY(),UNIT_SIZE,UNIT_SIZE,null);
		}
		for(int i = 0; i < Mushrooms.size(); i++)
		{
			g2D.drawImage(Mushrooms.get(i).getImage(),Mushrooms.get(i).getLoc().getX(), Mushrooms.get(i).getLoc().getY(),UNIT_SIZE,UNIT_SIZE,null);
		}
		g2D.setColor(Color.white);
		g2D.setFont(new Font("Consolas", Font.BOLD, 50));
		if(player.getScore() < 100)
		{
			g2D.drawString("Skor: " + points.showPoints() , 10*UNIT_SIZE-10, 11*UNIT_SIZE-20);
		}
		else
		{
			g2D.drawString("Skor: " +  points.showPoints() , 9*UNIT_SIZE+35, 11*UNIT_SIZE-20);
		}
		g2D.drawImage(sirine, 12*UNIT_SIZE, 7*UNIT_SIZE, UNIT_SIZE,UNIT_SIZE, null);
		if(isGameOver())
		{
			SwingUtilities.getWindowAncestor(this).dispose();
			if(points.showPoints() <= 0)
				new GameoverFrame(1);
			else
				new GameoverFrame(0);
		}
		
	}
	
	
	
	public void getEnemys(String enemy1, String enemy2)
	{
		
		//A x: 3 y: 0
		//B x: 10: y: 0
		//C x: 0 y: 5
		//D x: 3 y: 10
		if(enemy1.charAt(9) == 'G' && enemy1.charAt(23) == 'A')
		{
			e1FirstLoc = new Location(3,0);
			this.enemy1 = new Gargamel(1,adjacencyList,points);
			this.enemy1.setLoc(e1FirstLoc);
			
		}
		else if(enemy1.charAt(9) == 'G' && enemy1.charAt(23) == 'B')
		{
			e1FirstLoc = new Location(10,0);
			this.enemy1 = new Gargamel(1,adjacencyList,points);
			this.enemy1.setLoc(e1FirstLoc);
		}
		else if(enemy1.charAt(9) == 'G' && enemy1.charAt(23) == 'C')
		{
			e1FirstLoc = new Location(0,5);
			this.enemy1 = new Gargamel(1,adjacencyList,points);
			this.enemy1.setLoc(e1FirstLoc);
		}
		else if(enemy1.charAt(9) == 'G' && enemy1.charAt(23) == 'D')
		{
			e1FirstLoc = new Location(3,10);
			this.enemy1 = new Gargamel(1,adjacencyList,points);
			this.enemy1.setLoc(e1FirstLoc);
		}
		if(enemy2.charAt(9) == 'G' && enemy2.charAt(23) == 'A')
		{
			e2FirstLoc = new Location(3,0);
			this.enemy2 = new Gargamel(2,adjacencyList,points);
			this.enemy2.setLoc(e2FirstLoc);
		}
		else if(enemy2.charAt(9) == 'G' && enemy2.charAt(23) == 'B')
		{
			e2FirstLoc = new Location(10,0);
			this.enemy2 = new Gargamel(2,adjacencyList,points);
			this.enemy2.setLoc(e2FirstLoc);
		}
		else if(enemy2.charAt(9) == 'G' && enemy2.charAt(23) == 'C')
		{
			e2FirstLoc = new Location(0,5);
			this.enemy2 = new Gargamel(2,adjacencyList,points);
			this.enemy2.setLoc(e2FirstLoc);
		}
		else if(enemy2.charAt(9) == 'G' && enemy2.charAt(23) == 'D')
		{
			e2FirstLoc = new Location(3,10);
			this.enemy2 = new Gargamel(2,adjacencyList,points);
			this.enemy2.setLoc(e2FirstLoc);
		}
		if(enemy1.charAt(9) == 'A' && enemy1.charAt(20) == 'A')
		{
			e1FirstLoc = new Location(3,0);
			this.enemy1 = new Azman(1,adjacencyList,points);
			this.enemy1.setLoc(e1FirstLoc);
		}
		else if(enemy1.charAt(9) == 'A' && enemy1.charAt(20) == 'B')
		{
			e1FirstLoc = new Location(10,0);
			this.enemy1 = new Azman(1,adjacencyList,points);
			this.enemy1.setLoc(e1FirstLoc);
		}
		else if(enemy1.charAt(9) == 'A' && enemy1.charAt(20) == 'C')
		{
			e1FirstLoc = new Location(0,5);
			this.enemy1 = new Azman(1,adjacencyList,points);
			this.enemy1.setLoc(e1FirstLoc);
		}
		else if(enemy1.charAt(9) == 'A' && enemy1.charAt(20) == 'D')
		{
			e1FirstLoc = new Location(3,10);
			this.enemy1 = new Azman(1,adjacencyList,points);
			this.enemy1.setLoc(e1FirstLoc);
		}
		if(enemy2.charAt(9) == 'A' && enemy2.charAt(20) == 'A')
		{
			e2FirstLoc = new Location(3,0);
			this.enemy2 = new Azman(2,adjacencyList,points);
			this.enemy2.setLoc(e2FirstLoc);
		}
		else if(enemy2.charAt(9) == 'A' && enemy2.charAt(20) == 'B')
		{
			e2FirstLoc = new Location(10,0);
			this.enemy2 = new Azman(2,adjacencyList,points);
			this.enemy2.setLoc(e2FirstLoc);
		}
		else if(enemy2.charAt(9) == 'A' && enemy2.charAt(20) == 'C')
		{
			e2FirstLoc = new Location(0,5);
			this.enemy2 = new Azman(2,adjacencyList,points);
			this.enemy2.setLoc(e2FirstLoc);
		}
		else if(enemy2.charAt(9) == 'A' && enemy2.charAt(20) == 'D')
		{
			e2FirstLoc = new Location(3,10);
			this.enemy2 = new Azman(2,adjacencyList,points);
			this.enemy2.setLoc(e2FirstLoc);
		}
		this.enemy1.setFirstLoc(e1FirstLoc);
		this.enemy2.setFirstLoc(e2FirstLoc);
		
	}
	public void getMap()
	{
		int j = 0;
		for(int i = 0; i < gameHeight; i++)
		{
			j = 0;
			String k = sc.nextLine(); 
			for(int l = 0; l < k.length(); l++)
			{
				if(k.charAt(l) != 9)
				{
					mapLocArray[i][j] = k.charAt(l) - 48;
					if((i == 0 && j == 3) || (i == 0 && j == 10) || (i==5 && j == 0) || (i==10 && j==3))
					{
						tileMap.add(new Tile(new Location(j,i), 2));
					}
					else if(mapLocArray[i][j] == 0)
					{
						tileMap.add(new Tile(new Location(j,i), 0));
					}
					else if(mapLocArray[i][j] == 1 )
					{
						tileMap.add(new Tile(new Location(j,i), 1));
					}
					j++;
					
				}
			}
			
		}
	}
	public void teachSurroundings()
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			tileMap.get(i).setRight(tileToTheRight(tileMap.get(i)));
			tileMap.get(i).setLeft(tileToTheLeft(tileMap.get(i)));
			tileMap.get(i).setUp(tileToTheUp(tileMap.get(i)));
			tileMap.get(i).setDown(tileToTheDown(tileMap.get(i)));
		}
	}
	public Tile tileToTheRight(Tile tile)
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			if(tileMap.get(i).getLoc().getX() == tile.getLoc().getX() + UNIT_SIZE &&  tile.getLoc().getX() + UNIT_SIZE < 13*UNIT_SIZE 
				&& tileMap.get(i).getLoc().getY() == tile.getLoc().getY()) 
			{
				return tileMap.get(i);
			}
		}
		return null;
		
	}
	public Tile tileToTheLeft(Tile tile)
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			if(tileMap.get(i).getLoc().getX() == tile.getLoc().getX() - UNIT_SIZE  && tile.getLoc().getX() - UNIT_SIZE  >= 0
				&& tileMap.get(i).getLoc().getY() == tile.getLoc().getY()) 
			{
				return tileMap.get(i);
			}
		}
		return null;
	}
	public Tile tileToTheUp(Tile tile)
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			if(tileMap.get(i).getLoc().getY() == tile.getLoc().getY() - UNIT_SIZE  && tile.getLoc().getY() - UNIT_SIZE  >= 0
				&& tileMap.get(i).getLoc().getX() == tile.getLoc().getX()) 
			{
				return tileMap.get(i);
			}
		}
		return null;
	}
	public Tile tileToTheDown(Tile tile)
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			if(tileMap.get(i).getLoc().getY() == tile.getLoc().getY() + UNIT_SIZE  && tile.getLoc().getY() + UNIT_SIZE  < 11*UNIT_SIZE 
				&& tileMap.get(i).getLoc().getX() == tile.getLoc().getX()) 
			{
				return tileMap.get(i);
			}
		}
		return null;
	}
	public void setCurrTileOfPlayer()
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			if(tileMap.get(i).getLoc().getX() == player.getLoc().getX() &&tileMap.get(i).getLoc().getY() == player.getLoc().getY())
			{
				player.setCurrentTile(tileMap.get(i));
				break;
			}
		}
	}
	public void setCurrTileOfEnemys()
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			if(tileMap.get(i).getLoc().getX() == enemy1.getLoc().getX() &&tileMap.get(i).getLoc().getY() == enemy1.getLoc().getY())
			{
				enemy1.setCurrentTile(tileMap.get(i));
				enemy1.setLoc(enemy1.getCurrentTile().getLoc());
				break;
			}
		}
		for(int j = 0; j < tileMap.size(); j++)
		{
			if(tileMap.get(j).getLoc().getX() == enemy2.getLoc().getX() &&tileMap.get(j).getLoc().getY() == enemy2.getLoc().getY())
			{
				enemy2.setCurrentTile(tileMap.get(j));
				enemy2.setLoc(enemy2.getCurrentTile().getLoc());
				break;
			}
		}
	}
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				
				
				case KeyEvent.VK_LEFT: 
					for(int i = 0; i < movementSpeed; i++)
					{
						if(player.getCurrentTile().getLeft() != null && player.getCurrentTile().getLeft().getType() == 1)
						{
						
						
							player.setCurrentTile(player.getCurrentTile().getLeft());
							player.setLoc(player.getCurrentTile().getLoc());
						
						
						}
					}
				break;
				case KeyEvent.VK_RIGHT: 
					for(int i = 0; i < movementSpeed; i++)
					{
						if(player.getCurrentTile().getRight() != null  && player.getCurrentTile().getRight().getType() == 1)
						{
							player.setCurrentTile(player.getCurrentTile().getRight());
							player.setLoc(player.getCurrentTile().getLoc());
						
						}
					}
				break;
				case KeyEvent.VK_UP: 
					for(int i = 0; i < movementSpeed; i++)
					{
						if(player.getCurrentTile().getUp() != null  &&player.getCurrentTile().getUp().getType() == 1)
						{
						
						
							player.setCurrentTile(player.getCurrentTile().getUp());
							player.setLoc(player.getCurrentTile().getLoc());
						
						
						}
					}
				break;
				case KeyEvent.VK_DOWN: 
					for(int i = 0; i < movementSpeed; i++)
					{
						if(player.getCurrentTile().getDown() != null && player.getCurrentTile().getDown().getType() == 1)
						{
						
						
							player.setCurrentTile(player.getCurrentTile().getDown());
							player.setLoc(player.getCurrentTile().getLoc());
						
						
						}
					}
				break;
			}
			
			checkCoinCollision();
			checkMushroomCollision();
			enemy1.shortestPath(player);
			enemy2.shortestPath(player);
			setCurrTileOfEnemys();
			repaint();
			
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == coinSpawnTimer)
		{
			do {
				for(int i = 0; i<Coin.numOfCoins; i++)
				{
					Coins.add(newCoin());
				}
				
				
			}while(!checkCoinOverlap());
			coinAppearanceTimer.start();
			coinSpawnTimer.stop();
			repaint();
		}
		else if(e.getSource() == coinAppearanceTimer)
		{
	    	coinAppearanceTimer.stop();
	    	coinSpawnTimer.start();
	    	int rnd = rand.nextInt(5000)+5000;
			coinSpawnTimer.setDelay(rnd);
			Coins.removeAll(Coins);
			repaint();
		}
		else if(e.getSource() == mushroomSpawnTimer)
		{
			do
			{
				
				   
					Mushrooms.add(newMushroom());
				
				
			}while(!checkMushroomOverlap());
			mushroomAppearanceTimer.start();
			mushroomSpawnTimer.stop();
			repaint();
		}
		else if(e.getSource() == mushroomAppearanceTimer)
		{
			    mushroomAppearanceTimer.stop();
			    mushroomSpawnTimer.start();
				Mushrooms.remove(0);
				
				int rnd = rand.nextInt(15000)+5000;
				mushroomSpawnTimer.setDelay(rnd);
				
				
			
			repaint();
			
		}
		
		
	}
	public boolean checkMushroomOverlap()
	{
		
		for(int i = 0; i < Mushrooms.size(); i++)
		{
			for(int j = i+1; j < Mushrooms.size(); j++)
			{
				if(Mushrooms.get(i).getLoc().getX() == Mushrooms.get(j).getLoc().getX() &&
				   Mushrooms.get(i).getLoc().getY() == Mushrooms.get(j).getLoc().getY())
				{
					Mushrooms.clear();
					return false;
				}
				
				
			}
			if(player.getLoc().getX() == Mushrooms.get(i).getLoc().getX() &&
			   player.getLoc().getY() == Mushrooms.get(i).getLoc().getY())
			{
				    Mushrooms.clear();
					return false;
			}
			
		}
		
		for(int i = 0; i < Mushrooms.size(); i++)
		{
			for(int j = 0; j < Coins.size(); j++)
			{
				if(Mushrooms.get(i).getLoc().getX() == Coins.get(j).getLoc().getX() &&
				   Mushrooms.get(i).getLoc().getY()== Coins.get(j).getLoc().getY())
				{
					Mushrooms.clear();
					return false;
				}
			}
		}
		return true;
	}
	public boolean checkCoinOverlap()
	{
		for(int i = 0; i < Coins.size(); i++)
		{
			for(int j = i+1; j < Coins.size(); j++)
			{
				if(Coins.get(i).getLoc().getX() == Coins.get(j).getLoc().getX() &&
				   Coins.get(i).getLoc().getY() == Coins.get(j).getLoc().getY())
				{
					Coins.clear();
					return false;
				}
			}
			
		}
		for(int i = 0; i < Coins.size(); i++)
		{
			for(int j = 0; j < Mushrooms.size(); j++)
			{
				if(Coins.get(i).getLoc().getX() == Mushrooms.get(j).getLoc().getX() &&
				   Coins.get(i).getLoc().getY()== Mushrooms.get(j).getLoc().getY())
				{
					Coins.clear();
					return false;
				}
			}
		}
		return true;
	}
	public Coin newCoin()
	{
		Random rand = new Random();
		int x,y;
		do
		{
			x = rand.nextInt(11);
			y = rand.nextInt(13);
		}while(!isAvailableTile(x*UNIT_SIZE,y*UNIT_SIZE));
		
		return new Coin(new Location(x,y));
	}
	
	public boolean isAvailableTile(int x, int y)
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			if(tileMap.get(i).getType() == 1 && x == tileMap.get(i).getLoc().getX() && y == tileMap.get(i).getLoc().getY())
			{
				return true;
			}
		}
		return false;
				
	}
	public void checkCoinCollision()
	{
		for(int i = 0; i < Coins.size(); i++)
		{
			if(player.getLoc().getX() == Coins.get(i).getLoc().getX() &&
			   player.getLoc().getY() == Coins.get(i).getLoc().getY())
			{
				points.coinPicked();
				Coins.remove(i);
			}
			
		}
	}
	public void checkMushroomCollision()
	{
		
		for(int i = 0; i < Mushrooms.size(); i++)
		{
			if(player.getLoc().getX() == Mushrooms.get(i).getLoc().getX() &&
			   player.getLoc().getY() == Mushrooms.get(i).getLoc().getY())
			{
				points.mushroomPicked();
				Mushrooms.remove(i);
			}
		}
		
	}
	public Mushroom newMushroom()
	{
		
		Random rand = new Random();
		int x,y;
		do
		{
			x = rand.nextInt(11);
			y = rand.nextInt(13);
		}while(!isAvailableTile(x*UNIT_SIZE,y*UNIT_SIZE));
		
		return new Mushroom(new Location(x,y));
	}
	
	
	public void findTheEdges()
	{
		for(int i = 0; i < tileMap.size(); i++)
		{
			if(tileMap.get(i).getType() !=0 )
			{
				if(tileMap.get(i).getRight() != null && tileMap.get(i).getRight().getType() != 0)
				{
					tileMap.get(i).getAdjacency().add(tileMap.get(i).getRight());
				}
				if(tileMap.get(i).getLeft() != null && tileMap.get(i).getLeft().getType() != 0)
				{
					tileMap.get(i).getAdjacency().add(tileMap.get(i).getLeft());
				}
				if(tileMap.get(i).getUp() != null && tileMap.get(i).getUp().getType() != 0)
				{
					tileMap.get(i).getAdjacency().add(tileMap.get(i).getUp());
				}
				if(tileMap.get(i).getDown() != null && tileMap.get(i).getDown().getType() != 0)
				{
					tileMap.get(i).getAdjacency().add(tileMap.get(i).getDown());
				}
			}
			
			
			
		}
	
	}
	public void createAdjacencyMatrix()
	{
		int numOfVertices = 0;
		for(int i = 0; i  < gameHeight; i++)
		{
			for(int j = 0; j < gameWidth; j++)
			{
				if(mapLocArray[i][j] == 1)
				{
					numOfVertices++;
				}
			}
				
		}
		
		adjacencyMatrix = new int[numOfVertices][numOfVertices];
		
		
		for(int k = 0; k < tileMap.size(); k++)
		{
			if(tileMap.get(k).getType() != 0)
			{
				adjacencyList.add(new AdjacencyTile(tileMap.get(k).getLoc().getY() / UNIT_SIZE, tileMap.get(k).getLoc().getX() / UNIT_SIZE,tileMap.get(k)));
			}
		}
		
		for(int i = 0; i < numOfVertices; i++)
		{
			adjacencyList.get(i).getTile().setID(i);
			for(int j = 0; j < numOfVertices; j++)
			{
				if(adjacencyList.get(i).getTile().getAdjacency().contains(adjacencyList.get(j).getTile()))
				{
					adjacencyMatrix[i][j] = 1;
					
				}
				else
				{
					adjacencyMatrix[i][j] = 0;
				}
				adjacencyList.get(i).setI(i);
				adjacencyList.get(i).setJ(j);
			}
		}
		
	}
	
	
	 
	 
	 public void adjWriteMethod()
	 {
		 for(int i = 0; i < adjacencyMatrix.length; i++)
		 {
			 for(int j = 0; j < adjacencyMatrix[0].length; j++)
			 {
				 adjPrinter.print(adjacencyMatrix[i][j]);
			 } 
			 adjPrinter.println();
		 }
		 adjPrinter.close();
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
	
	public boolean isGameOver()
	{
		// i == 7 && j == 12
		if(points.showPoints() <= 0 || (player.getLoc().getX() == 12*UNIT_SIZE && player.getLoc().getY() == 7*UNIT_SIZE))
			return true;
		
		return false;
	}

}
