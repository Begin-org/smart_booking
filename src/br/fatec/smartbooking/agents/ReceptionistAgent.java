package br.fatec.smartbooking.agents;

import java.io.IOException;
import br.fatec.smartbooking.controller.BookingController;
import br.fatec.smartbooking.controller.DialogueController;
import br.fatec.smartbooking.controller.RoomController;
import br.fatec.smartbooking.model.Dialogue;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREResponder;
import br.fatec.smartbooking.utils.BehaviourConstants;;

public class ReceptionistAgent extends Agent {

	protected void setup() {
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType("Atendimento ao cliente");
		serviceDescription.setName("Reserva de quartos");
		this.registerService(serviceDescription);

		System.out.println("Olá, em que posso ajudar?");

		MessageTemplate protocol = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		MessageTemplate perfomative = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		MessageTemplate pattern = MessageTemplate.and(protocol, perfomative);

		addBehaviour(new Conversation(this, pattern));
	}

	protected void registerService(ServiceDescription serviceDescription) {
		DFAgentDescription dfAgentDescription = new DFAgentDescription();
		dfAgentDescription.setName(getAID());
		dfAgentDescription.addServices(serviceDescription);

		try {
			DFService.register(this, dfAgentDescription);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	class Conversation extends AchieveREResponder {
		public Conversation(Agent agent, MessageTemplate messageTemplate) {
			super(agent, messageTemplate);
		}

		protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
			try {
				Dialogue dialogue = (Dialogue) request.getContentObject();
				BookingController bookingController = new BookingController();
				int initialBehavior = dialogue.getBehaviour();

				if (initialBehavior == BehaviourConstants.ANSWER_ONLY) {

					DialogueController dialogueController = new DialogueController();

					dialogue = dialogueController.findAnswer(dialogue);

					if (dialogue == null) {
						throw new RefuseException("Digite o que deseja fazer");
					} else {
						if (dialogue.getBehaviour() == BehaviourConstants.SHOW_AVAILABLE_ROOMS) {
							RoomController roomController = new RoomController();

							dialogue.setRoomsAvailable(roomController.listAvailableRooms());

							if (dialogue.getRoomsAvailable().size() <= 0) {
								throw new RefuseException("Não há quartos disponíveis para reserva!");

							} else if (dialogue.getRoomsAvailable().size() > 0) {

								String answer = "";

								for (int i = 0; i < dialogue.getRoomsAvailable().size(); i++) {
									answer += "Opção "
											+ dialogue.getRoomsAvailable().get(i).getRoomType().getIdRoomType() + ": "
											+ dialogue.getRoomsAvailable().get(i).getRoomType().getTitle() + "\n"
											+ dialogue.getRoomsAvailable().get(i).getRoomType().getDescription() + "\n"
											+ "Preço diário: "
											+ dialogue.getRoomsAvailable().get(i).getRoomType().getPricePerDay();

									if (i != dialogue.getRoomsAvailable().size() - 1) {
										answer += "\n\n\n";
									}
								}

								dialogue.setAnswer(dialogue.getAnswer() + "\n\n" + answer);
							}

						}

						if (dialogue.getBehaviour() == BehaviourConstants.ANSWER_ONLY
								|| dialogue.getBehaviour() == BehaviourConstants.SHOW_AVAILABLE_ROOMS) {

							ACLMessage agree = request.createReply();
							agree.setPerformative(ACLMessage.AGREE);
							agree.setContentObject(dialogue);
							return agree;

						} else if (dialogue.getBehaviour() == BehaviourConstants.CANCEL_BOOKING
								|| dialogue.getBehaviour() == BehaviourConstants.BOOK_ROOM
								|| dialogue.getBehaviour() == BehaviourConstants.SHOW_CUSTOMER_BOOKINGS) {


							ACLMessage inform = request.createReply();
							inform.setPerformative(ACLMessage.INFORM);
							inform.setContentObject(dialogue);
							return inform;

						} else {
							throw new NotUnderstoodException("Desculpe, eu não entendi");
						}
					}

				} else if (initialBehavior == BehaviourConstants.BOOK_ROOM) {
					dialogue.setAnswer(bookingController.bookRoom(dialogue.getCustomerBooking()));

				} else if (initialBehavior == BehaviourConstants.CANCEL_BOOKING) {
					dialogue.setAnswer(bookingController.cancelBooking(dialogue.getCustomerBooking()));

				} else if (initialBehavior == BehaviourConstants.SHOW_CUSTOMER_BOOKINGS) {
					dialogue.setCustomerBooking(bookingController.findBooking(dialogue.getCustomerBooking()));
					dialogue.setAnswer(dialogue.getCustomerBooking().toString());
				}

				ACLMessage agree = request.createReply();
				agree.setPerformative(ACLMessage.AGREE);
				agree.setContentObject(dialogue);
				return agree;

			} catch (UnreadableException | IOException e) {
				e.printStackTrace();
				throw new RefuseException("Estamos com problemas na aplicação! Tente novamente mais tarde.");
			}
		}

		protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response)
				throws FailureException {
			return response;
		}

	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

}
