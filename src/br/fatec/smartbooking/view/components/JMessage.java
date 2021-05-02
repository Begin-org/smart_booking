package br.fatec.smartbooking.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JLabel;
import br.fatec.smartbooking.utils.ViewUtil;

public class JMessage extends JRoundedPanel implements ColorMode {

	private Color backgroundColor;
	private Color fontColor;

	private ArrayList<JLabel> lblMessages;

	private int quantityOfCaractersInLine;
	private int paddingTop;
	private int paddingBottom;
	private int paddingX;
	private int messageType;
	private int colorTheme;
	public final static int MESSAGE_RECEIVED = 1;
	public final static int MESSAGE_SENT = 0;

	public JMessage(int radius, String text, int messageType, int colorTheme) {
		super(radius);
		quantityOfCaractersInLine = 30;
		paddingTop = 5;
		paddingBottom = 5;
		paddingX = 10;
		lblMessages = new ArrayList<JLabel>();
		this.messageType = messageType;
		this.colorTheme = colorTheme;
		initializeMessage(text);

	}

	public void initializeMessage(String text) {

		if (colorTheme == ViewUtil.LIGHT_MODE) {

			if (messageType == MESSAGE_SENT) {
				backgroundColor = new Color(170, 99, 248);
				setBackground(backgroundColor);

			} else {
				backgroundColor = new Color(232, 232, 232);
				setBackground(backgroundColor);
			}

		} else {

			if (messageType == MESSAGE_RECEIVED) {

				backgroundColor = new Color(23, 23, 29);
				setBackground(backgroundColor);
			} else {
				backgroundColor = new Color(170, 99, 248);
				setBackground(backgroundColor);
			}
		}

		setLayout(null);
		initializeComponents(text);
		setSize(definingPanelSize());

	}

	public void initializeComponents(String text) {

		if (colorTheme == ViewUtil.LIGHT_MODE) {

			if (messageType == MESSAGE_SENT) {

				fontColor = Color.white;

			} else {

				fontColor = Color.black;
			}
		} else {

			fontColor = Color.white;
		}

		String formattedText = "<html>";

		String[] words = text.split(" ");

		String line = "";

		String biggestWidthLine = "";

		int countBR = text.split("<br>").length;

		int labelHeight = 20 + (countBR * 20);

		for (int i = 0; i < words.length; i++) {

			if ((line.length() + words[i].length()) <= quantityOfCaractersInLine) {
				line += words[i] + " ";

				if (line.length() > biggestWidthLine.length()) {
					biggestWidthLine = line;
				}
			} else {
				formattedText += line + "<br>";
				line = words[i] + " ";

				labelHeight += 20;
			}

			if (i == words.length - 1) {
				formattedText += line;
			}

		}

		formattedText += "</html>";

		addLabel(formattedText, labelHeight, biggestWidthLine);

	}

	public Dimension definingPanelSize() {

		int heightOfThePanel = 0;

		for (JLabel label : lblMessages) {

			heightOfThePanel += (int) label.getHeight();
		}

		return new Dimension((int) lblMessages.get(0).getWidth() + paddingX,
				heightOfThePanel + paddingTop + paddingBottom);

	}

	public void addLabel(String text, int height, String biggestLine) {

		JLabel lblMessage = new JLabel();

		lblMessage.setFont(new ViewUtil().registerNewFont("/LexendDeca-Regular.ttf", 16));

		lblMessage.setText(text);
		lblMessage.setSize(getFontMetrics(lblMessage.getFont()).stringWidth(biggestLine) + 10, height);

		int marginTop = (lblMessages.size() == 0) ? paddingTop : 0;
		int positionY = (lblMessages.size() > 0)
				? lblMessages.get(lblMessages.size() - 1).getY() + lblMessages.get(lblMessages.size() - 1).getHeight()
				: 0;

		lblMessage.setLocation(paddingX, positionY + marginTop);
		lblMessage.setForeground(fontColor);

		lblMessages.add(lblMessage);
		add(lblMessage);
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public ArrayList<JLabel> getLblMessages() {
		return lblMessages;
	}

	public void setLblMessages(ArrayList<JLabel> lblMessages) {
		this.lblMessages = lblMessages;
	}

	@Override
	public void changeToLightMode() {

		backgroundColor = new Color(232, 232, 232);
		fontColor = Color.black;

		setBackground(backgroundColor);

		for (JLabel lblMessage : lblMessages) {

			lblMessage.setForeground(fontColor);

		}

		colorTheme = ViewUtil.LIGHT_MODE;
	}

	@Override
	public void changeToDarkMode() {

		backgroundColor = new Color(23, 23, 29);
		fontColor = Color.white;

		setBackground(backgroundColor);

		for (JLabel lblMessage : lblMessages) {

			if (messageType == MESSAGE_RECEIVED) {
				lblMessage.setForeground(fontColor);
			}

		}

		colorTheme = ViewUtil.DARK_MODE;

	}

}
