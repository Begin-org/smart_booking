package br.fatec.smartbooking.agents;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.fatec.smartbooking.model.Dialogue;
import br.fatec.smartbooking.utils.BehaviourConstants;
import br.fatec.smartbooking.view.Gui;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREInitiator;

public class CustomerAgent extends Agent {

	private Dialogue dialogue;
	private Class<Gui> gui;
	private Gui instanceGui;
	private Method getQuestionFromCustomer;
	private Method getAnswer;

	protected void setup() {
		Object[] arguments = getArguments();

		if (arguments != null && arguments.length > 0) {

			try {

				this.gui = (Class<Gui>) arguments[0];

				Class<Dialogue> partypesDialogue[] = new Class[1];
				partypesDialogue[0] = Dialogue.class;

				this.getQuestionFromCustomer = this.gui.getMethod("getQuestionFromCustomer", partypesDialogue);
				this.getAnswer = this.gui.getMethod("getAnswer", partypesDialogue);

				this.dialogue = new Dialogue();

				Constructor<Gui> constructor = this.gui.getDeclaredConstructor(CustomerAgent.class);
				constructor.setAccessible(true);
				this.instanceGui = constructor.newInstance(this);
			
				this.getQuestionFromCustomer.invoke(this.instanceGui, dialogue);

			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | InstantiationException e) {
				System.out.println("Estamos com problemas na aplicação! Tente novamente mais tarde.");
				e.printStackTrace();
			}

		}
	}

	public void sendMessage(Dialogue customerMessage)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType("Atendimento ao cliente");
		serviceDescription.setName("Reserva de quartos");
		template.addServices(serviceDescription);

		DFAgentDescription[] result = {};

		try {
			result = DFService.search(this, template);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		if (result.length > 0) {

			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);

			aclMessage.addReceiver(result[0].getName());
			aclMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			aclMessage.setContentObject(customerMessage);

			addBehaviour(new Intiator(this, aclMessage));

		} else {
			dialogue.setAnswer("Não há nenhum agente recepcionista para atender os clientes!");
			dialogue.setBehaviour(BehaviourConstants.ANSWER_ONLY);
			this.getAnswer.invoke(this.instanceGui, dialogue);
		}
	}

	class Intiator extends AchieveREInitiator {

		public Intiator(CustomerAgent agent, ACLMessage message) {
			super(agent, message);
		}

		private void sendAnswer() {
			try {
				getAnswer.invoke(instanceGui, dialogue);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.out.println("Estamos com problemas na aplicação! Tente novamente mais tarde.");
				e.printStackTrace();
			}
		}

		protected void handleAgree(ACLMessage agree) {
			try {
				dialogue = (Dialogue) agree.getContentObject();
				dialogue.setBehaviour(BehaviourConstants.ANSWER_ONLY);
			} catch (UnreadableException e) {
				e.printStackTrace();
				dialogue = new Dialogue("Estamos com problemas na aplicação! Tente novamente mais tarde.");
			} finally {
				this.sendAnswer();
			}
		}

		protected void handleRefuse(ACLMessage refuse) {
			dialogue = new Dialogue(refuse.getContent());
			dialogue.setBehaviour(BehaviourConstants.ANSWER_ONLY);
			this.sendAnswer();
		}

		protected void handleNotUnderstood(ACLMessage notUnderstood) {
			dialogue = new Dialogue(notUnderstood.getContent());
			dialogue.setBehaviour(BehaviourConstants.NOT_UNDERSTOOD);
			this.sendAnswer();
		}

		protected void handleFailure(ACLMessage failure) {

			if (failure.getSender().equals(getAMS())) {
				System.err.println("Falha nas páginas brancas");
			}

			dialogue = new Dialogue(failure.getContent());
			dialogue.setBehaviour(BehaviourConstants.ANSWER_ONLY);
			this.sendAnswer();
		}

		protected void handleInform(ACLMessage inform) {
			try {
				dialogue = (Dialogue) inform.getContentObject();
			} catch (UnreadableException e) {
				e.printStackTrace();
				dialogue = new Dialogue("Estamos com problemas na aplicação! Tente novamente mais tarde.");
			} finally {
				this.sendAnswer();
			}
		}

	}

}
