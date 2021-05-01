package br.com.begin.smartbooking.view.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
public class JImage extends JLabel{
	
	private String path;
	
	public JImage(String path,int width, int height) {
		this.path = path;
		setSize(width, height);
		drawImage(path);
	}
	
	public JImage() {
		
	}
	
	public void drawImage(String path){
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(path));
		Image image = imageIcon.getImage().getScaledInstance(
				getWidth(), getHeight(), Image.SCALE_SMOOTH);
		
		setIcon(new ImageIcon(image));
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
