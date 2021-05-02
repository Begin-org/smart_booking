package br.fatec.smartbooking.main;

import br.fatec.smartbooking.view.windows.ChatView;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main {

	public static void main(String[] args) {

		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		ContainerController containerController = runtime.createMainContainer(profile);

		AgentController receptionistAgentController;
		AgentController customerAgentController;

		try {

			customerAgentController = containerController.createNewAgent("Customer",
					"br.fatec.smartbooking.agents.CustomerAgent", new Object[] { ChatView.class });
			receptionistAgentController = containerController.createNewAgent("Receptionist",
					"br.fatec.smartbooking.agents.ReceptionistAgent", null);

			receptionistAgentController.start();
			customerAgentController.start();

		} catch (StaleProxyException e) {
			e.printStackTrace();
			System.out.println("Estamos com problemas na aplicação! Tente novamente mais tarde.");
		}
		
	}

}
