package br.fatec.smartbooking.view.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

import br.fatec.smartbooking.utils.ViewUtil;
import br.fatec.smartbooking.view.windows.Window;

public class JToggle extends JRoundedPanel implements Runnable, ColorMode {

	private Color backgroudColor;
	private Color labelColor;
	private JRoundedLabel lblCircle;
	private JImage imgIcon;
	private int optionSelected;
	private Window parent;

	public JToggle(int radius, int width, int height, Window parent) {
		super(radius);
		optionSelected = ViewUtil.LIGHT_MODE;
		this.parent = parent;
		initializeToogle(width, height);
		initializeComponents();
	}

	public void initializeToogle(int width, int height) {
		backgroudColor = new Color(92, 56, 145);
		setBackground(backgroudColor);
		setLayout(null);
		setSize(width, height);
		setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

	public void initializeComponents() {

		labelColor = new Color(28, 28, 33);

		lblCircle = new JRoundedLabel(80);
		lblCircle.setBackground(labelColor);
		lblCircle.setSize(30, 30);
		lblCircle.setLocation(getWidth() - lblCircle.getWidth() - 10, 10);
		add(lblCircle);

		imgIcon = new JImage("/moon.png", 30, 30);
		imgIcon.setLocation(10, 10);
		add(imgIcon);
	}

	@Override
	public void run() {

		if (optionSelected == ViewUtil.LIGHT_MODE) {

			changeToDarkMode();

		} else {

			changeToLightMode();

		}

	}

	public void changeToLightMode() {

		imgIcon.drawImage("/moon.png");
		lblCircle.setBackground(labelColor);

		int finalPositionImgIcon = 10;
		int finalPositionLblCircle = getWidth() - 10;

		for (int xImgIcon = imgIcon.getX(), xLblCircle = lblCircle.getX(); xImgIcon > finalPositionImgIcon
				&& xLblCircle < finalPositionLblCircle; xImgIcon--, xLblCircle++) {

			try {

				Thread.sleep(7);
				imgIcon.setLocation(xImgIcon, imgIcon.getY());
				lblCircle.setLocation(xLblCircle, lblCircle.getY());

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		optionSelected = ViewUtil.LIGHT_MODE;
		parent.changeToLightMode();
	}

	public void changeToDarkMode() {

		imgIcon.drawImage("/sun.png");
		lblCircle.setBackground(Color.white);

		int finalPositionImgIcon = getWidth() - 10;
		int finalPositionLblCircle = 10;

		for (int xImgIcon = imgIcon.getX(), xLblCircle = lblCircle.getX(); xImgIcon < finalPositionImgIcon
				&& xLblCircle > finalPositionLblCircle; xImgIcon++, xLblCircle--) {

			try {

				Thread.sleep(7);
				imgIcon.setLocation(xImgIcon, imgIcon.getY());
				lblCircle.setLocation(xLblCircle, lblCircle.getY());

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		optionSelected = ViewUtil.DARK_MODE;
		parent.changeToDarkMode();

	}

}
