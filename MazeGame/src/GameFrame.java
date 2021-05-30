import javax.swing.*;
public class GameFrame extends JFrame
{
	
	GameFrame(int playerType)
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new GamePanel(playerType));
		this.setTitle("Þirinler");
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

}
