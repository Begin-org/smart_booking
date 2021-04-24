package br.fatec.smartbooking.agents;

import java.io.IOException;
import br.fatec.smartbooking.model.Dialogue;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREInitiator;

public class CustomerAgent extends Agent {

	private Dialogue dialogue;

	protected void setup() {

	}

	public void sendMessage(Dialogue customerMessage, Agent receptionist) throws IOException {
		ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);

		aclMessage.addReceiver(receptionist.getAID());
		aclMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		aclMessage.setContentObject(customerMessage);

		addBehaviour(new Intiator(this, aclMessage));
	}

	public Dialogue getAnswer() {
		return this.dialogue;
	}

	class Intiator extends AchieveREInitiator {

		CustomerAgent customer;

		public Intiator(CustomerAgent agent, ACLMessage message) {
			super(agent, message);
			this.customer = agent;
		}

		protected void handleAgree(ACLMessage agree) {
			try {
				this.customer.dialogue = (Dialogue) agree.getContentObject();
			} catch (UnreadableException e) {
				e.printStackTrace();
				this.customer.dialogue = new Dialogue("Ocorreu um erro na aplicação");
			}
		}

		protected void handleRefuse(ACLMessage refuse) {
			this.customer.dialogue = new Dialogue(refuse.getContent());
		}

		protected void handleNotUnderstood(ACLMessage notUnderstood) {

			this.customer.dialogue = new Dialogue(notUnderstood.getContent());
		}

		protected void handleFailure(ACLMessage failure) {

			if (failure.getSender().equals(getAMS())) {
				System.err.println("Falha nas páginas brancas");
			}

			this.customer.dialogue = new Dialogue(failure.getContent());
		}

		protected void handleInform(ACLMessage inform) {
			try {
				this.customer.dialogue = (Dialogue) inform.getContentObject();
			} catch (UnreadableException e) {
				e.printStackTrace();
				this.customer.dialogue = new Dialogue("Ocorreu um erro na aplicação");
			}
		}

	}

}
