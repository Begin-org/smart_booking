package br.fatec.smartbooking.controller;

import br.fatec.smartbooking.dao.DialogueDAO;
import br.fatec.smartbooking.dao.IDialogue;
import br.fatec.smartbooking.model.Dialogue;

public class DialogueController implements IDialogue {
	DialogueDAO dialogueDAO = new DialogueDAO();

	@Override
	public Dialogue findAnswer(Dialogue dialogue) {
		if (dialogue == null || dialogue.getQuestion() == null || dialogue.getQuestion().trim() == "") {
			return null;
		} else {
			return this.dialogueDAO.findAnswer(dialogue);
		}
	}
}
