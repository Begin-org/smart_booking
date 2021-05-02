package br.fatec.smartbooking.view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import br.fatec.smartbooking.utils.ViewUtil;

public class JRoundedTextField extends JPanel {

	protected int strokeSize = 1;
	protected Color shadowColor = Color.black;
	protected boolean shady = false;
	protected boolean highQuality = true;
	protected Dimension arcs;
	protected int shadowGap = 5;
	protected int shadowOffset = 4;
	protected int shadowAlpha = 150;

	private JTextField txtTextField;

	public JTextField getTxtTextField() {
		return txtTextField;
	}

	public void setTxtTextField(JTextField txtTextField) {
		this.txtTextField = txtTextField;
	}

	public JRoundedTextField(int radius, int width, int height, Color backgroundColor) {
		super();
		setOpaque(false);
		arcs = new Dimension(radius, radius);
		initializeTextField(width, height, backgroundColor);
		initializeComponents();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		int shadowGap = this.shadowGap;
		Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(),
				shadowAlpha);
		Graphics2D graphics = (Graphics2D) g;

		if (highQuality) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		if (shady) {
			graphics.setColor(shadowColorA);
			graphics.fillRoundRect(shadowOffset, shadowOffset, width - strokeSize - shadowOffset,
					height - strokeSize - shadowOffset, arcs.width, arcs.height);
		} else {
			shadowGap = 1;
		}

		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
		graphics.setColor(getForeground());
		graphics.setStroke(new BasicStroke(strokeSize));

		graphics.setStroke(new BasicStroke());
	}

	public void initializeTextField(int width, int height, Color backgroundColor) {

		setSize(width, height);
		setLayout(null);
		setBackground(backgroundColor);
	}

	public void initializeComponents() {

		txtTextField = new JTextField();
		txtTextField.setBorder(null);
		txtTextField.setBackground(getBackground());
		txtTextField.setSize(getWidth() / 2 + 180, getHeight() / 2);
		new ViewUtil().centralize(this, txtTextField);
		add(txtTextField);

	}

}
