package br.com.begin.smartbooking.view.util;

import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JComponent;

public class ViewUtil {
	
	public final static int LIGHT_MODE = 1;
	public final static int DARK_MODE = 2;

	public Font registerNewFont(String path, int size){
		
		Font newFont = null;

		try {
			URL url = getClass().getResource(path);
			File file = new File(url.toURI());

			newFont = Font.createFont(Font.TRUETYPE_FONT, file);

			newFont  = newFont.deriveFont(Font.PLAIN, size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(newFont);

		} catch(IOException|FontFormatException e) {
			System.out.println("Erro ao adicionar uma nova fonte! \n" + e);
			e.printStackTrace();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return newFont;


	}
	
	public void centralize(JComponent parent,JComponent child) {

		child.setLocation((parent.getWidth() / 2) - (child.getWidth() / 2), (parent.getHeight() / 2 - child.getHeight() / 2) );
	}

	public void centralize(Container parent,JComponent child) {

		child.setLocation((parent.getWidth() / 2) - (child.getWidth() / 2), (parent.getHeight() / 2) - (child.getHeight() / 2 ));
		
	}
	public void centralizeYAxis(JComponent parent, JComponent child) {
		
		child.setLocation(child.getX(), parent.getHeight() / 2 - child.getHeight() / 2 );
	}

}
