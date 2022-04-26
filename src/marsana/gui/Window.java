package marsana.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Window {
	private String title;
	private JFrame window;
	
	public Window(String title) {
		this.title = title;
	}

	public void createWindow() {
		window = new JFrame(title);
		
	    window.setSize(getScreenSize().width, getScreenSize().height);
	    window.setVisible(true);
	    window.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    window.setResizable(true);
	    
	    //get the absolute path so this works on every windows machine
	    String path = System.getProperty("user.dir") + "\\files\\icon\\icon.jpg";
	    window.setIconImage(new ImageIcon(path).getImage());
	    
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Get the user screen size and set the window to maximized
	public Dimension getScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize;
	}
	
	public JFrame getWindow() {
		return window;
	}
	
	public String getTitle() {
		return title;
	}	
}
