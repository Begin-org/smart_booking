package br.fatec.smartbooking.view;

import br.fatec.smartbooking.controller.DialogueController;
import br.fatec.smartbooking.model.Dialogue;

public class Main {
	public static void main(String[] args) {	
		
		DialogueController cd = new DialogueController();
		
		Dialogue d = new Dialogue();
		d.setQuestion("Comigo est�. E com voc�?");
		
		System.out.println(cd.findAnswer(d));

	}
}
