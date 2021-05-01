package br.com.begin.smartbooking.view.windows;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.begin.smartbooking.view.components.JImage;
import br.com.begin.smartbooking.view.components.JMessage;
import br.com.begin.smartbooking.view.components.JRoundedTextField;
import br.com.begin.smartbooking.view.components.JToggle;
import br.com.begin.smartbooking.view.components.ScrollBar;
import br.com.begin.smartbooking.view.util.ViewUtil;

public class ChatView extends Window implements MouseListener{


	Color backgroundColor; 
	Color headerColor;

	JPanel pnlHeader;
	JPanel pnlMessages;

	JImage imgLogo;
	JImage imgSent;

	JRoundedTextField txtMessage;

	JScrollPane jspScrollBar;

	List<JMessage> historyMessages;

	JToggle pnlToggle;

	private int heightOfPnlMessages;
	private int colorTheme;

	public ChatView() {
		
		colorTheme = ViewUtil.LIGHT_MODE;
	}

	@Override
	public void initializeWindow() {
		super.initializeWindow();	
		getContentPane().setBackground(backgroundColor);

	}
	@Override
	public void initializeComponents() {

		backgroundColor = new Color(249,249,249);
		headerColor = Color.white;
		heightOfPnlMessages = 0;

		pnlHeader = new JPanel();
		pnlHeader.setLayout(null);
		pnlHeader.setBackground(headerColor);
		pnlHeader.setSize(getWidth(), 160);
		pnlHeader.setLocation(0,0);
		add(pnlHeader);

		imgLogo = new JImage("../assets/images/logo-text-grande.png", 250, 120);
		new ViewUtil().centralize(pnlHeader, imgLogo);
		imgLogo.setLocation(imgLogo.getX(), imgLogo.getY() + 5);
		pnlHeader.add(imgLogo);

		pnlToggle = new JToggle(50, 96, 48, this);
		pnlToggle.setLocation(pnlHeader.getWidth() - pnlToggle.getWidth() - 40, 20);
		pnlToggle.addMouseListener(this);
		pnlHeader.add(pnlToggle);

		pnlMessages = new JPanel();
		pnlMessages.setLayout(null);
		pnlMessages.setBackground(backgroundColor);
		pnlMessages.setLocation(0,0);
		pnlMessages.setPreferredSize(new Dimension(350,390));
		pnlMessages.setSize(350, 350);


		jspScrollBar = new JScrollPane();
		jspScrollBar.setSize(getWidth()-50,400);
		jspScrollBar.setViewportView(pnlMessages);
		jspScrollBar.setVerticalScrollBarPolicy
		(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspScrollBar.getVerticalScrollBar().setUnitIncrement(15);
		jspScrollBar.getVerticalScrollBar().setUI(new ScrollBar());
		jspScrollBar.getVerticalScrollBar().setOpaque(false);
		jspScrollBar.setBackground(backgroundColor);
		jspScrollBar.setBorder(null);

		add(jspScrollBar);


		new ViewUtil().centralize(this, jspScrollBar);
		jspScrollBar.setLocation(jspScrollBar.getX() - 8, pnlHeader.getHeight() + 20 );

		txtMessage = new JRoundedTextField(15, jspScrollBar.getWidth(), 50, new Color(232, 232, 232));
		txtMessage.setLocation(jspScrollBar.getX(), jspScrollBar.getY() + jspScrollBar.getHeight() + 50);
		txtMessage.getTxtTextField().setFont(new ViewUtil().registerNewFont("../assets/fonts/LexendDeca-Regular.ttf", 14));

		add(txtMessage);

		imgSent = new JImage("../assets/images/sent-light.png", 25, 25);
		imgSent.setLocation(txtMessage.getWidth() - imgSent.getWidth() - 20, 0);
		new ViewUtil().centralizeYAxis(txtMessage, imgSent);
		imgSent.addMouseListener(this);
		imgSent.setCursor(new Cursor(Cursor.HAND_CURSOR));

		txtMessage.add(imgSent);

		historyMessages = new ArrayList<JMessage>();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if ( e.getSource().equals(imgSent) ) {

			if ( !txtMessage.getTxtTextField().getText().isEmpty() || 
					!txtMessage.getTxtTextField().getText().isBlank() ) {

				JMessage pnlMessage;

				if (historyMessages.size() % 2 == 0) {

					pnlMessage = new JMessage(15, txtMessage.getTxtTextField().getText(), 
							JMessage.MESSAGE_SENT,colorTheme);


				} else {

					pnlMessage = new JMessage(15, txtMessage.getTxtTextField().getText(), 
							JMessage.MESSAGE_RECEIVED, colorTheme);
				}


				pnlMessage.setLocation(defineMessageLocation(pnlMessage));
				pnlMessages.add(pnlMessage);

				refreshScrollPaneHeight(pnlMessage.getHeight(), 20);
				historyMessages.add(pnlMessage);
				
				txtMessage.getTxtTextField().setText("");


			} else {

				JOptionPane.showMessageDialog(null, "Mensagem vazia, por favor insira algum texto para enviar a mensagem!");
			}

		} else if (e.getSource().equals(pnlToggle)) {

			Thread newThread = new Thread(pnlToggle);
			newThread.start();

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {


	}

	@Override
	public void mouseReleased(MouseEvent e) {


	}

	@Override
	public void mouseEntered(MouseEvent e) {


	}

	@Override
	public void mouseExited(MouseEvent e) {


	}

	private Point defineMessageLocation(JMessage message) {

		Point point = new Point();

		int marginTop = 20;
		int marginLeft = 10;
		int lastMessagePositionY = (historyMessages.size() > 0) ? 
				historyMessages.get( historyMessages.size() - 1 ).getY() 
				+ historyMessages.get( historyMessages.size() - 1 ).getHeight() : 0;


		if (message.getMessageType() == JMessage.MESSAGE_SENT) {

			int positionX = pnlMessages.getWidth() - message.getWidth() - (marginLeft * 2); 
			point.setLocation(positionX, lastMessagePositionY + marginTop);

		} else {


			point.setLocation(marginLeft*2, lastMessagePositionY + marginTop);

		}

		return point;
	}

	private void refreshScrollPaneHeight(int additionHeight, int additionMargin) {



		if ( heightOfPnlMessages
				+ additionHeight + additionMargin >= jspScrollBar.getHeight() ) {

			pnlMessages.setPreferredSize(
					new Dimension(
							(int) pnlMessages.getPreferredSize().getWidth(),
							heightOfPnlMessages 
							+ additionHeight + additionMargin));

		}
		heightOfPnlMessages += additionHeight + additionMargin;
		repaint();
		revalidate();
		jspScrollBar.getVerticalScrollBar().setValue(jspScrollBar.getVerticalScrollBar().getMaximum());

	}

	public void changeToLightMode() {

		backgroundColor = new Color (249, 249, 249);
		colorTheme = ViewUtil.LIGHT_MODE;
		headerColor = Color.white;

		getContentPane().setBackground(backgroundColor);
		pnlMessages.setBackground(backgroundColor);
		jspScrollBar.setBackground(backgroundColor);
		pnlHeader.setBackground(headerColor);

		for( JMessage message : historyMessages) {

			if (message.getMessageType() == JMessage.MESSAGE_RECEIVED) {
				message.changeToLightMode();
			}
		}

		txtMessage.setBackground(new Color(232, 232, 232));
		txtMessage.getTxtTextField().setBackground(txtMessage.getBackground());
		txtMessage.getTxtTextField().setForeground(Color.black);

		
	}

	public void changeToDarkMode() {

		backgroundColor = new Color (28, 28, 33);
		headerColor =  backgroundColor;
		colorTheme = ViewUtil.DARK_MODE;
		
		getContentPane().setBackground(backgroundColor);
		pnlMessages.setBackground(backgroundColor);
		jspScrollBar.setBackground(backgroundColor);
		pnlHeader.setBackground(headerColor);

		for( JMessage message : historyMessages) {

			if (message.getMessageType() == JMessage.MESSAGE_RECEIVED) {
				message.changeToDarkMode();
			}
		}

		txtMessage.setBackground(new Color(23, 23, 29));
		txtMessage.getTxtTextField().setBackground(txtMessage.getBackground());
		txtMessage.getTxtTextField().setForeground(Color.white);


	}
}
