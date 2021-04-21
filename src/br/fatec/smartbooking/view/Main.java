package br.fatec.smartbooking.view;

import br.fatec.smartbooking.controller.DialogueController;
import br.fatec.smartbooking.model.Dialogue;

public class Main {
	public static void main(String[] args) {	
		
		DialogueController cd = new DialogueController();
		
		Dialogue d = new Dialogue();
		d.setQuestion("Comigo está. E com você?");
		
		System.out.println(cd.findAnswer(d));

	}
}
