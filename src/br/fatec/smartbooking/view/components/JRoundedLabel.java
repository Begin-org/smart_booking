package br.fatec.smartbooking.view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

import br.fatec.smartbooking.utils.ViewUtil;

public class JRoundedLabel extends JLabel {

	protected int strokeSize = 1;
	protected Color shadowColor = Color.black;
	protected boolean shady = false;
	protected boolean highQuality = true;
	protected Dimension arcs;
	protected int shadowGap = 5;
	protected int shadowOffset = 4;
	protected int shadowAlpha = 150;
	protected boolean border = false;
	protected JLabel insideLabel;

	public JRoundedLabel(int radius) {
		super();
		setOpaque(false);
		arcs = new Dimension(radius, radius);
	}

	public JRoundedLabel(int radius, boolean border, String text) {
		super();
		setOpaque(false);
		arcs = new Dimension(radius, radius);
		this.border = border;
		
		insideLabel = new JLabel();
		insideLabel.setText(text);
		insideLabel.setFont(new ViewUtil().registerNewFont("/LexendDeca-Regular.ttf", 16));
		insideLabel.setSize(getFontMetrics(insideLabel.getFont()).stringWidth(text) + 10, 25);
		insideLabel.setLocation(15, 3);
		insideLabel.setForeground(new Color(170, 99, 248));
		add(insideLabel);
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

		if (this.border) {
			graphics.setColor(new Color(170, 99, 248));
			graphics.drawRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
		}

		graphics.setStroke(new BasicStroke());

	}
}
