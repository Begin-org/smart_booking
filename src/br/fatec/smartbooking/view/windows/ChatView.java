package br.fatec.smartbooking.view.windows;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import br.fatec.smartbooking.view.components.JImage;
import br.fatec.smartbooking.view.components.JMessage;
import br.fatec.smartbooking.view.components.JRoundedLabel;
import br.fatec.smartbooking.view.components.JRoundedTextField;
import br.fatec.smartbooking.view.components.JToggle;
import br.fatec.smartbooking.view.components.ScrollBar;
import br.fatec.smartbooking.agents.CustomerAgent;
import br.fatec.smartbooking.model.Booking;
import br.fatec.smartbooking.model.Dialogue;
import br.fatec.smartbooking.model.Room;
import br.fatec.smartbooking.model.RoomType;
import br.fatec.smartbooking.utils.BehaviourConstants;
import br.fatec.smartbooking.utils.DocumentValidator;
import br.fatec.smartbooking.utils.LanguageConstants;
import br.fatec.smartbooking.utils.ViewUtil;

public class ChatView extends Window implements MouseListener {

	private CustomerAgent customerAgent;
	private String message = "";
	private Runnable runnable;
	private boolean cancelCurrentOperation = false;

	Color backgroundColor;
	Color headerColor;

	JPanel pnlHeader;
	JPanel pnlMessages;

	JImage imgLogo;
	JImage imgSent;

	JRoundedTextField txtMessage;

	JRoundedLabel cancelAction;
	JRoundedLabel showRoomsAction;
	JRoundedLabel bookAction;

	JScrollPane jspScrollBar;

	List<JMessage> historyMessages;

	JToggle pnlToggle;

	private int heightOfPnlMessages;
	private int colorTheme;

	public ChatView(CustomerAgent customerAgent) {

		colorTheme = ViewUtil.LIGHT_MODE;
		this.customerAgent = customerAgent;

	}

	@Override
	public void initializeWindow() {
		super.initializeWindow();
		getContentPane().setBackground(backgroundColor);

	}

	@Override
	public void initializeComponents() {

		backgroundColor = new Color(249, 249, 249);
		headerColor = Color.white;
		heightOfPnlMessages = 0;

		pnlHeader = new JPanel();
		pnlHeader.setLayout(null);
		pnlHeader.setBackground(headerColor);
		pnlHeader.setSize(getWidth(), 160);
		pnlHeader.setLocation(0, 0);
		add(pnlHeader);

		imgLogo = new JImage("/logo-text-grande.png", 250, 120);
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
		pnlMessages.setLocation(0, 0);
		pnlMessages.setPreferredSize(new Dimension(350, 390));
		pnlMessages.setSize(350, 350);

		jspScrollBar = new JScrollPane();
		jspScrollBar.setSize(getWidth() - 50, 400);
		jspScrollBar.setViewportView(pnlMessages);
		jspScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspScrollBar.getVerticalScrollBar().setUnitIncrement(15);
		jspScrollBar.getVerticalScrollBar().setUI(new ScrollBar());
		jspScrollBar.getVerticalScrollBar().setOpaque(false);
		jspScrollBar.setBackground(backgroundColor);
		jspScrollBar.setBorder(null);

		add(jspScrollBar);

		new ViewUtil().centralize(this, jspScrollBar);
		jspScrollBar.setLocation(jspScrollBar.getX() - 8, pnlHeader.getHeight() + 20);

		txtMessage = new JRoundedTextField(15, jspScrollBar.getWidth(), 50, new Color(232, 232, 232));
		txtMessage.setLocation(jspScrollBar.getX(), jspScrollBar.getY() + jspScrollBar.getHeight() + 50);
		txtMessage.getTxtTextField().setFont(new ViewUtil().registerNewFont("/LexendDeca-Regular.ttf", 14));

		txtMessage.getTxtTextField().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enterMessage();
				}
			}

		});

		add(txtMessage);

		imgSent = new JImage("/sent-light.png", 25, 25);
		imgSent.setLocation(txtMessage.getWidth() - imgSent.getWidth() - 20, 0);
		new ViewUtil().centralizeYAxis(txtMessage, imgSent);
		imgSent.addMouseListener(this);
		imgSent.setCursor(new Cursor(Cursor.HAND_CURSOR));

		txtMessage.add(imgSent);

		cancelAction = new JRoundedLabel(35, true, "Cancelar operação");
		cancelAction.setSize((int) (txtMessage.getWidth() * 0.3), 30);
		cancelAction.setLocation((int) (txtMessage.getWidth() * 0.72),
				txtMessage.getY() - cancelAction.getHeight() - 10);
		cancelAction.setBackground(headerColor);
		cancelAction.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cancelAction.addMouseListener(this);
		add(cancelAction);
		cancelAction.setVisible(false);

		showRoomsAction = new JRoundedLabel(35, true, "Mostrar quartos");
		showRoomsAction.setSize((int) (txtMessage.getWidth() * 0.26), 30);
		showRoomsAction.setLocation((int) (txtMessage.getX()), txtMessage.getY() - cancelAction.getHeight() - 10);
		showRoomsAction.setBackground(headerColor);
		showRoomsAction.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showRoomsAction.addMouseListener(this);
		add(showRoomsAction);

		bookAction = new JRoundedLabel(35, true, "Reservar");
		bookAction.setSize((int) (txtMessage.getWidth() * 0.18), 30);
		bookAction.setLocation((int) ((showRoomsAction.getWidth() + showRoomsAction.getX()) + 20),
				txtMessage.getY() - cancelAction.getHeight() - 10);
		bookAction.setBackground(headerColor);
		bookAction.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bookAction.addMouseListener(this);
		add(bookAction);

		historyMessages = new ArrayList<JMessage>();

	}

	public void showMessage(String message, int type) {
		JMessage pnlMessage;

		pnlMessage = new JMessage(15, message, type, colorTheme);

		pnlMessage.setLocation(defineMessageLocation(pnlMessage));
		pnlMessages.add(pnlMessage);

		refreshScrollPaneHeight(pnlMessage.getHeight(), 20);
		historyMessages.add(pnlMessage);

	}

	public void enterMessage() {
		if (!txtMessage.getTxtTextField().getText().isEmpty()
				|| !txtMessage.getTxtTextField().getText().trim().isEmpty()) {

			showMessage(txtMessage.getTxtTextField().getText(), JMessage.MESSAGE_SENT);
			message = txtMessage.getTxtTextField().getText();
			txtMessage.getTxtTextField().setText("");
			synchronized (runnable) {
				runnable.notify();
			}
		} else {

			JOptionPane.showMessageDialog(null, "Mensagem vazia, por favor insira algum texto para enviar a mensagem!");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(imgSent)) {
			enterMessage();

		} else if (e.getSource().equals(pnlToggle)) {

			Thread newThread = new Thread(pnlToggle);
			newThread.start();

		} else if (e.getSource().equals(cancelAction)) {
			cancelCurrentOperation = true;
			synchronized (runnable) {
				runnable.notify();
			}
		} else if (e.getSource().equals(bookAction)) {
			message = "agendar";
			synchronized (runnable) {
				runnable.notify();
			}
		} else if (e.getSource().equals(showRoomsAction)) {
			message = "mostrar quartos";
			synchronized (runnable) {
				runnable.notify();
			}
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
		int lastMessagePositionY = (historyMessages.size() > 0) ? historyMessages.get(historyMessages.size() - 1).getY()
				+ historyMessages.get(historyMessages.size() - 1).getHeight() : 0;

		if (message.getMessageType() == JMessage.MESSAGE_SENT) {

			int positionX = pnlMessages.getWidth() - message.getWidth() - (marginLeft * 2);
			point.setLocation(positionX, lastMessagePositionY + marginTop);

		} else {

			point.setLocation(marginLeft * 2, lastMessagePositionY + marginTop);

		}

		return point;
	}

	private void refreshScrollPaneHeight(int additionHeight, int additionMargin) {

		if (heightOfPnlMessages + additionHeight + additionMargin >= jspScrollBar.getHeight()) {

			pnlMessages.setPreferredSize(new Dimension((int) pnlMessages.getPreferredSize().getWidth(),
					heightOfPnlMessages + additionHeight + additionMargin));

		}
		heightOfPnlMessages += additionHeight + additionMargin;
		repaint();
		revalidate();
		jspScrollBar.getVerticalScrollBar().setValue(jspScrollBar.getVerticalScrollBar().getMaximum());

	}

	public void changeToLightMode() {

		backgroundColor = new Color(249, 249, 249);
		colorTheme = ViewUtil.LIGHT_MODE;
		headerColor = Color.white;

		getContentPane().setBackground(backgroundColor);
		pnlMessages.setBackground(backgroundColor);
		jspScrollBar.setBackground(backgroundColor);
		pnlHeader.setBackground(headerColor);
		showRoomsAction.setBackground(headerColor);
		bookAction.setBackground(headerColor);
		cancelAction.setBackground(headerColor);

		for (JMessage message : historyMessages) {

			if (message.getMessageType() == JMessage.MESSAGE_RECEIVED) {
				message.changeToLightMode();
			}
		}

		txtMessage.setBackground(new Color(232, 232, 232));
		txtMessage.getTxtTextField().setBackground(txtMessage.getBackground());
		txtMessage.getTxtTextField().setForeground(Color.black);

	}

	public void changeToDarkMode() {

		backgroundColor = new Color(28, 28, 33);
		headerColor = backgroundColor;
		colorTheme = ViewUtil.DARK_MODE;

		getContentPane().setBackground(backgroundColor);
		pnlMessages.setBackground(backgroundColor);
		jspScrollBar.setBackground(backgroundColor);
		pnlHeader.setBackground(headerColor);
		cancelAction.setBackground(headerColor);
		showRoomsAction.setBackground(headerColor);
		bookAction.setBackground(headerColor);

		for (JMessage message : historyMessages) {

			if (message.getMessageType() == JMessage.MESSAGE_RECEIVED) {
				message.changeToDarkMode();
			}
		}

		txtMessage.setBackground(new Color(23, 23, 29));
		txtMessage.getTxtTextField().setBackground(txtMessage.getBackground());
		txtMessage.getTxtTextField().setForeground(Color.white);

	}

	public void getQuestionFromCustomer(Dialogue dialogue) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (this) {

					runnable = this;

					try {

						String cpf = "";
						Boolean isCpf = false;
						Booking booking = new Booking();
						dialogue.setQuestion("");

						if (dialogue.getBehaviour() == BehaviourConstants.ANSWER_ONLY
								|| dialogue.getBehaviour() == BehaviourConstants.NOT_UNDERSTOOD) {

							this.wait();
							dialogue.setQuestion(message);
							dialogue.setBehaviour(BehaviourConstants.ANSWER_ONLY);

						} else if (dialogue.getBehaviour() == BehaviourConstants.SHOW_CUSTOMER_BOOKINGS
								|| dialogue.getBehaviour() == BehaviourConstants.BOOK_ROOM
								|| dialogue.getBehaviour() == BehaviourConstants.CANCEL_BOOKING) {

							cancelAction.setVisible(true);
							showRoomsAction.setVisible(false);
							bookAction.setVisible(false);

							dialogue.setQuestion("Serviços de reserva");

							showMessage("Por favor, informe seu CPF", JMessage.MESSAGE_RECEIVED);

							do {
								wait();

								if (cancelCurrentOperation) {
									dialogue.setQuestion("");
									break;
								}

								cpf = message;
								isCpf = DocumentValidator.isCPF(cpf);

								if (isCpf == false) {
									showMessage("O CPF informado é inválido. Digite-o novamente",
											JMessage.MESSAGE_RECEIVED);
								}
							} while (isCpf == false);

							booking.setCpfCustomer(DocumentValidator.removeCpfFormatting(cpf));

							if (dialogue.getBehaviour() == BehaviourConstants.BOOK_ROOM && !cancelCurrentOperation) {

								showMessage("Digite a opção de quarto", JMessage.MESSAGE_RECEIVED);

								Room room = new Room();
								RoomType roomType = new RoomType();
								wait();

								if (!cancelCurrentOperation) {

									roomType.setIdRoomType(Integer.parseInt(message));
									room.setRoomType(roomType);

									booking.setRoom(room);

									Calendar todayDate = Calendar.getInstance();
									Calendar tomorrowDate = Calendar.getInstance();
									tomorrowDate.add(Calendar.DATE, 1);

									String startDate = null;
									String endDate = null;

									do {

										showMessage(
												"Diga a data em que você chegará aqui no hotel no formato dia/mês/ano. Exemplo: "
														+ LanguageConstants.DATE_FORMAT.format(tomorrowDate.getTime())
														+ ". A data deve ser maior que a data de hoje",
												JMessage.MESSAGE_RECEIVED);

										wait();

										if (cancelCurrentOperation) {
											dialogue.setQuestion("");
											break;
										} else {

											startDate = message;
											try {
												Date date = LanguageConstants.DATE_FORMAT.parse(startDate);
												Calendar calendar = Calendar.getInstance();
												calendar.setTime(date);

												if (todayDate.compareTo(calendar) >= 0) {
													showMessage(
															"Atenção! Escolha uma data maior que hoje! Digite a data de entrada novamente.",
															JMessage.MESSAGE_RECEIVED);

													startDate = null;
												} else {
													booking.setStartDate(calendar);
												}

											} catch (ParseException e) {
												showMessage("Você digitou a data de maneira incorreta!",
														JMessage.MESSAGE_RECEIVED);

												e.printStackTrace();
												startDate = null;
											}

										}

									} while (startDate == null);

									do {

										showMessage(
												"Diga a data em que você sairá do hotel no formato dia/mês/ano. Exemplo: "
														+ LanguageConstants.DATE_FORMAT.format(tomorrowDate.getTime())
														+ ". A data deve ser maior que a data de entrada",
												JMessage.MESSAGE_RECEIVED);

										wait();

										if (cancelCurrentOperation) {
											dialogue.setQuestion("");
											break;
										} else {

											endDate = message;
											try {
												Date date = LanguageConstants.DATE_FORMAT.parse(endDate);
												Calendar calendar = Calendar.getInstance();
												calendar.setTime(date);

												if (booking.getStartDate().compareTo(calendar) >= 0) {
													showMessage(
															"Atenção! Escolha uma data maior que a data de entrada! Digite a data de saída novamente.",
															JMessage.MESSAGE_RECEIVED);

													endDate = null;
												} else {
													booking.setEndDate(calendar);
												}

											} catch (ParseException e) {
												showMessage("Você digitou a data de maneira incorreta!",
														JMessage.MESSAGE_RECEIVED);

												e.printStackTrace();
												endDate = null;
											}

										}

									} while (endDate == null);

								}else {
									dialogue.setQuestion("");
								}

							}

							if (!cancelCurrentOperation) {

								dialogue.setCustomerBooking(booking);
							}

						}

						if (dialogue.getQuestion() != "") {
							try {
								customerAgent.sendMessage(dialogue);
							} catch (IOException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException e) {
								e.printStackTrace();
								showMessage("Estamos com problemas na aplicação! Tente novamente mais tarde.",
										JMessage.MESSAGE_RECEIVED);
							}
						} else {
							dialogue.setBehaviour(1);
							showMessage("Operação cancelada", JMessage.MESSAGE_RECEIVED);
							cancelAction.setVisible(false);
							showRoomsAction.setVisible(true);
							bookAction.setVisible(true);
							cancelCurrentOperation = false;
							getQuestionFromCustomer(dialogue);
						}
					} catch (Exception e) {
						e.printStackTrace();
						showMessage("Estamos com problemas na aplicação! Tente novamente mais tarde.",
								JMessage.MESSAGE_RECEIVED);
					}
				}
			}
		}).start();

	}

	public void getAnswer(Dialogue dialogue) {
		showMessage(dialogue.getAnswer(), JMessage.MESSAGE_RECEIVED);
		cancelAction.setVisible(false);
		showRoomsAction.setVisible(true);
		bookAction.setVisible(true);
		cancelCurrentOperation = false;
		getQuestionFromCustomer(dialogue);
	}
}
