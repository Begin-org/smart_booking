package br.com.begin.smartbooking.view.windows;


import javax.swing.JFrame;

import br.com.begin.smartbooking.view.components.ColorMode;

public abstract class Window extends JFrame implements ColorMode{

	public Window() {
		initializeWindow();
	}
	public void initializeWindow() {
		setTitle("Smart Booking");
		setSize(650, 725);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initializeComponents();	
		setVisible(true);

	}

	public abstract void initializeComponents();

	
}
