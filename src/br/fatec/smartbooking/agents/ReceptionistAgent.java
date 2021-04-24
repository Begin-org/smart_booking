package br.fatec.smartbooking.agents;

import java.io.IOException;

import br.fatec.smartbooking.controller.BookingController;
import br.fatec.smartbooking.controller.DialogueController;
import br.fatec.smartbooking.controller.RoomController;
import br.fatec.smartbooking.model.Dialogue;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREResponder;
import br.fatec.smartbooking.utils.BehaviourConstants;;

public class ReceptionistAgent extends Agent {

	protected void setup() {
		MessageTemplate protocol = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		MessageTemplate perfomative = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		MessageTemplate pattern = MessageTemplate.and(protocol, perfomative);

		addBehaviour(new Conversation(this, pattern));
	}

	class Conversation extends AchieveREResponder {
		public Conversation(Agent agent, MessageTemplate messageTemplate) {
			super(agent, messageTemplate);
		}

		protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
			try {
				Dialogue dialogue = (Dialogue) request.getContentObject();
				BookingController bookingController = new BookingController();

				if (dialogue.getBehaviour() == BehaviourConstants.ANSWER_ONLY) {

					DialogueController dialogueController = new DialogueController();

					dialogue = dialogueController.findAnswer(dialogue);

					if (dialogue == null) {
						throw new RefuseException("O pedido veio vazio");
					} else {
						if (dialogue.getBehaviour() == BehaviourConstants.SHOW_AVAILABLE_ROOMS) {
							RoomController roomController = new RoomController();

							dialogue.setRoomsAvailable(roomController.listAvailableRooms());

						}

						if (dialogue.getBehaviour() == BehaviourConstants.ANSWER_ONLY
								|| dialogue.getBehaviour() == BehaviourConstants.SHOW_AVAILABLE_ROOMS) {

							ACLMessage inform = request.createReply();
							inform.setContentObject(dialogue);
							inform.setPerformative(ACLMessage.INFORM);
							return inform;

						} else if (dialogue.getBehaviour() == BehaviourConstants.CANCEL_BOOKING
								|| dialogue.getBehaviour() == BehaviourConstants.BOOK_ROOM
								|| dialogue.getBehaviour() == BehaviourConstants.SHOW_CUSTOMER_BOOKINGS) {

							ACLMessage agree = request.createReply();
							agree.setContentObject(dialogue);
							agree.setPerformative(ACLMessage.AGREE);
							return agree;

						} else {
							throw new NotUnderstoodException("Desculpe, eu não entendi");
						}
					}

				} else if (dialogue.getBehaviour() == BehaviourConstants.BOOK_ROOM) {
					dialogue.setAnswer(bookingController.bookRoom(dialogue.getCustomerBooking()));

				} else if (dialogue.getBehaviour() == BehaviourConstants.CANCEL_BOOKING) {
					dialogue.setAnswer(bookingController.cancelBooking(dialogue.getCustomerBooking()));

				} else if (dialogue.getBehaviour() == BehaviourConstants.SHOW_CUSTOMER_BOOKINGS) {
					dialogue.setCustomerBooking(bookingController.findBooking(dialogue.getCustomerBooking()));
				}

				ACLMessage inform = request.createReply();
				inform.setContentObject(dialogue);
				inform.setPerformative(ACLMessage.INFORM);
				return inform;

			} catch (UnreadableException | IOException e) {
				e.printStackTrace();
				throw new RefuseException("O objeto de diálogo não foi passado corretamente");
			}
		}

		protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response)
				throws FailureException {
			try {
				Dialogue dialogue = (Dialogue) request.getContentObject();

				if (dialogue.getBehaviour() == BehaviourConstants.SHOW_AVAILABLE_ROOMS
						&& dialogue.getRoomsAvailable().size() <= 0) {
					throw new FailureException("Não há quartos disponíveis para reserva!");
				} else if (dialogue.getBehaviour() == BehaviourConstants.SHOW_AVAILABLE_ROOMS
						&& dialogue.getRoomsAvailable().size() > 0) {
					String answer = "";

					for (int i = 0; i < dialogue.getRoomsAvailable().size(); i++) {
						answer += "Quarto: " + dialogue.getRoomsAvailable().get(i).getRoomType().getTitle() + "\n"
								+ dialogue.getRoomsAvailable().get(i).getRoomType().getDescription() + "\n"
								+ "Preço diário: " + dialogue.getRoomsAvailable().get(i).getRoomType().getPricePerDay();

						if (i != dialogue.getRoomsAvailable().size() - 1) {
							answer += "\n\n\n";
						}
					}

					dialogue.setAnswer(answer);

					response.setContentObject(dialogue);

				}
				return response;

			} catch (UnreadableException | IOException e) {
				e.printStackTrace();
				throw new FailureException("Erro ao preparar o resultado final da resposta");
			}
		}
	}

}
