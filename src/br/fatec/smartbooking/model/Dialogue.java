package br.fatec.smartbooking.model;

public class Dialogue {
	private String question;
	private String answer;
	private int behaviour;
	
	public Dialogue(String question, String answer, int behaviour) {
		super();
		this.question = question;
		this.answer = answer;
		this.behaviour = behaviour;
	}
	
	public Dialogue() {
		super();
	}

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getBehaviour() {
		return behaviour;
	}
	public void setBehaviour(int behaviour) {
		this.behaviour = behaviour;
	}

	@Override
	public String toString() {
		return this.getAnswer();
	}
	
}
