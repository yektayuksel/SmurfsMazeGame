import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class CharSelectFrame extends JFrame implements MouseListener
{
	static final int UNIT_SIZE = 70;
	static int gameWidth = 13;
	static int gameHeight = 11;
	static final int GAME_WIDTH= gameWidth*UNIT_SIZE;
	static final int GAME_HEIGHT = gameHeight*UNIT_SIZE;
	JLabel gozluklu;
	JLabel guclu;
	JLabel chooseChar;
	JPanel panel;
	
	CharSelectFrame()
	{
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
		panel.setBackground(Color.white);
		gozluklu = new JLabel();
		gozluklu.setIcon(new ImageIcon(new ImageIcon("Gozluklu.png").getImage().getScaledInstance(681/2, 888/2, Image.SCALE_SMOOTH))); 
		guclu = new JLabel();
		guclu.setIcon(new ImageIcon(new ImageIcon("Guclu.png").getImage().getScaledInstance(681/2, 888/2, Image.SCALE_SMOOTH)));
		chooseChar = new JLabel();
		chooseChar.setText("Karakterinizi seçiniz");
		chooseChar.setFont(new Font("Consolas", Font.BOLD, 75));
		panel.add(chooseChar);
		panel.add(gozluklu);
		panel.add(guclu);
		gozluklu.addMouseListener(this);
		guclu.addMouseListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
		this.add(panel);
		this.setTitle("Þirinler");
		this.setLocationRelativeTo(null);
		this.addMouseListener(this);
		this.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == gozluklu)
		{
			new GameFrame(0);
			this.dispose();
		}
		else if(e.getSource() == guclu)
		{
			new GameFrame(1);
			this.dispose();
		}
		
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
