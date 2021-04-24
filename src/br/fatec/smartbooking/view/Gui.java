package br.fatec.smartbooking.view;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import br.fatec.smartbooking.agents.CustomerAgent;
import br.fatec.smartbooking.model.Booking;
import br.fatec.smartbooking.model.Dialogue;
import br.fatec.smartbooking.model.Room;
import br.fatec.smartbooking.model.RoomType;
import br.fatec.smartbooking.utils.BehaviourConstants;
import br.fatec.smartbooking.utils.DocumentValidator;
import br.fatec.smartbooking.utils.LanguageConstants;

public class Gui {

	private CustomerAgent customerAgent;

	public Gui(CustomerAgent customerAgent) {
		this.customerAgent = customerAgent;
	}

	public void getQuestionFromCustomer(Dialogue dialogue) {

		String cpf = "";
		Boolean isCpf = false;
		Booking booking = new Booking();
		dialogue.setQuestion("");

		Scanner input = new Scanner(System.in);

		if (dialogue.getBehaviour() == BehaviourConstants.ANSWER_ONLY
				|| dialogue.getBehaviour() == BehaviourConstants.NOT_UNDERSTOOD) {

			dialogue.setQuestion(input.nextLine());
			dialogue.setBehaviour(BehaviourConstants.ANSWER_ONLY);

		} else if (dialogue.getBehaviour() == BehaviourConstants.SHOW_CUSTOMER_BOOKINGS
				|| dialogue.getBehaviour() == BehaviourConstants.BOOK_ROOM
				|| dialogue.getBehaviour() == BehaviourConstants.CANCEL_BOOKING) {

			dialogue.setQuestion("Serviços de reserva");

			System.out.println("Por favor, informe seu CPF");

			do {
				cpf = input.nextLine();
				isCpf = DocumentValidator.isCPF(cpf);

				if (isCpf == false) {
					System.out.println("O CPF informado é inválido. Digite-o novamente");
				}
			} while (isCpf == false);

			booking.setCpfCustomer(DocumentValidator.removeCpfFormatting(cpf));

			if (dialogue.getBehaviour() == BehaviourConstants.BOOK_ROOM) {
				System.out.println("Digite a opção de quarto");

				Room room = new Room();
				RoomType roomType = new RoomType();
				roomType.setIdRoomType(input.nextInt());
				room.setRoomType(roomType);

				booking.setRoom(room);

				Calendar todayDate = Calendar.getInstance();
				Calendar tomorrowDate = Calendar.getInstance();
				tomorrowDate.add(Calendar.DATE, 1);

				String startDate = null;
				String endDate = null;

				do {

					System.out.println("Diga a data em que você chegará aqui no hotel no formato dia/mês/ano. Exemplo: "
							+ LanguageConstants.DATE_FORMAT.format(tomorrowDate.getTime())
							+ ". A data deve ser maior que a data de hoje");
					startDate = input.next();
					try {
						Date date = LanguageConstants.DATE_FORMAT.parse(startDate);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);

						if (todayDate.compareTo(calendar) >= 0) {
							System.out.println(
									"Atenção! Escolha uma data maior que hoje! Digite a data de entrada novamente.");
							startDate = null;
						} else {
							booking.setStartDate(calendar);
						}

					} catch (ParseException e) {
						System.out.println("Você digitou a data de maneira incorreta!");
						e.printStackTrace();
						startDate = null;
					}

				} while (startDate == null);

				do {

					System.out.println("Diga a data em que você sairá do hotel no formato dia/mês/ano. Exemplo: "
							+ LanguageConstants.DATE_FORMAT.format(tomorrowDate.getTime())
							+ ". A data deve ser maior que a data de entrada");
					endDate = input.next();
					try {
						Date date = LanguageConstants.DATE_FORMAT.parse(endDate);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);

						if (booking.getStartDate().compareTo(calendar) >= 0) {
							System.out.println(
									"Atenção! Escolha uma data maior que a data de entrada! Digite a data de saída novamente.");
							endDate = null;
						} else {
							booking.setEndDate(calendar);
						}

					} catch (ParseException e) {
						System.out.println("Você digitou a data de maneira incorreta!");
						e.printStackTrace();
						endDate = null;
					}

				} while (endDate == null);

			}

			dialogue.setCustomerBooking(booking);

		}

		if (dialogue.getQuestion() != "") {
			try {
				this.customerAgent.sendMessage(dialogue);
			} catch (IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				System.out.println("Estamos com problemas na aplicação! Tente novamente mais tarde.");
			}
		}

	}

	public void getAnswer(Dialogue dialogue) {
		System.out.println(dialogue.getAnswer());
		System.out.println("\n");
		getQuestionFromCustomer(dialogue);
	}

}