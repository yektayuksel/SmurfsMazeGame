import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameoverFrame extends JFrame implements MouseListener
{
	static final int UNIT_SIZE = 70;
	static int gameWidth = 13;
	static int gameHeight = 11;
	static final int GAME_WIDTH= gameWidth*UNIT_SIZE;
	static final int GAME_HEIGHT = gameHeight*UNIT_SIZE;
	
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	JButton buton1;
	JButton buton2;
	GameoverFrame(int a)
	{
		JLabel label = new JLabel();
		String headLine;
		buton1 = new JButton("Tekrar Dene");
		buton2 = new JButton("Çýk");
		if(a == 0)
		{
			label.setForeground(Color.green);
			headLine = "KAZANDINIZ";
			buton1.setForeground(Color.green);
			buton2.setForeground(Color.green);
		}
		else
		{
			label.setForeground(Color.red);
			headLine = "KAYBETTÝNÝZ";
			buton1.setForeground(Color.red);
			buton2.setForeground(Color.red);
		}
		
		label.setText(headLine);
		label.setFont(new Font("Consolas", Font.BOLD, 100));
		
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		
		buton1.setBackground(Color.black);
		buton2.setBackground(Color.black);
		
		buton1.setFont(new Font("Consolas", Font.BOLD, 30));
		buton2.setFont(new Font("Consolas", Font.BOLD, 30));
		/*buton1.setVerticalAlignment(JButton.BOTTOM);
		buton1.setHorizontalAlignment(JButton.RIGHT);
		buton2.setVerticalAlignment(JButton.BOTTOM);
		buton2.setHorizontalAlignment(JButton.LEFT);*/
		buton1.setBounds(50,500, 250,100 );
		buton2.setBounds(600,500, 250,100 );
		buton1.setFocusable(false);
		buton2.setFocusable(false);
		buton1.addMouseListener(this);
		buton2.addMouseListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Þirinler");
		this.setSize(SCREEN_SIZE);
		this.add(buton1);
		this.add(buton2);
		this.add(label);
		
		this.getContentPane().setBackground(Color.black);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == buton1)
		{
			this.dispose();
			new CharSelectFrame();
		}
		else if(e.getSource() == buton2)
		{
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
