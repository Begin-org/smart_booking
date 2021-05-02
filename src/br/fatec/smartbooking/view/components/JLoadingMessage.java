package br.fatec.smartbooking.view.components;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

import br.fatec.smartbooking.utils.ViewUtil;

public class JLoadingMessage extends JRoundedPanel {

	private Color backgroundColor;
	private Color fontColor;
	private int colorTheme;
	JRoundedLabel[] lblCircle = new JRoundedLabel[3];

	public JLoadingMessage(int radius) {
		super(radius);

		this.colorTheme = colorTheme;

		lblCircle[0] = new JRoundedLabel(80);
		lblCircle[0].setBackground(new Color(170, 99, 248));
		lblCircle[0].setSize(15, 15);
		lblCircle[0].setLocation(10, 0);
		add(lblCircle[0]);
		lblCircle[1] = new JRoundedLabel(80);
		lblCircle[1] = new JRoundedLabel(80);
		lblCircle[1].setLocation((lblCircle[0].getWidth() + lblCircle[0].getX()) + 5, 0);
		lblCircle[1].setBackground(new Color(170, 99, 248));
		lblCircle[1].setSize(15, 15);
		add(lblCircle[1]);
		lblCircle[2] = new JRoundedLabel(80);
		lblCircle[2] = new JRoundedLabel(80);
		lblCircle[2].setLocation((lblCircle[1].getWidth() + lblCircle[1].getX()) + 5, 0);
		lblCircle[2].setBackground(new Color(170, 99, 248));
		lblCircle[2].setSize(15, 15);
		add(lblCircle[2]);

		lblCircle[0].setVisible(false);
		lblCircle[1].setVisible(false);
		lblCircle[2].setVisible(false);

		initializeMessage();
	}

	public void initializeMessage() {
		if (colorTheme == ViewUtil.LIGHT_MODE) {
			backgroundColor = new Color(232, 232, 232);
			setBackground(backgroundColor);
		} else {
			backgroundColor = new Color(23, 23, 29);
			setBackground(backgroundColor);
		}

		setLayout(null);
		setSize(100, 60);
	}

	public void initializeLoading() {
		try {

			lblCircle[0].setVisible(true);
			lblCircle[1].setVisible(true);
			lblCircle[2].setVisible(true);
			lblCircle[0].setLocation(lblCircle[0].getX(), 0);
			lblCircle[1].setLocation(lblCircle[1].getX(), 0);
			lblCircle[2].setLocation(lblCircle[2].getX(), 0);

			int j = 0;

			int lowerY = 40;

			for (int i = 0; i < lowerY; i++) {
				if (j > 2) {
					Thread.sleep(100);
					lblCircle[0].setVisible(false);
					lblCircle[1].setVisible(false);
					lblCircle[2].setVisible(false);

					break;
				}

				lblCircle[j].setLocation(lblCircle[j].getX(), i);

				Thread.sleep(6);

				if (i == (lowerY - 1)) {
					i = 0;
					j += 1;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
