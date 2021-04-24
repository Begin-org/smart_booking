package br.fatec.smartbooking.model;

import java.util.ArrayList;

import jade.util.leap.Serializable;

public class Dialogue implements Serializable {
	private String question;
	private String answer;
	private int behaviour;
	private ArrayList<Room> roomsAvailable;
	private Booking customerBooking;

	public Dialogue(String answer) {
		super();
		this.answer = answer;
		this.behaviour = 1;
	}

	public Dialogue() {
		super();
		this.behaviour = 1;
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

	public ArrayList<Room> getRoomsAvailable() {
		return roomsAvailable;
	}

	public void setRoomsAvailable(ArrayList<Room> roomsAvailable) {
		this.roomsAvailable = roomsAvailable;
	}

	public Booking getCustomerBooking() {
		return customerBooking;
	}

	public void setCustomerBooking(Booking customerBooking) {
		this.customerBooking = customerBooking;
	}

	@Override
	public String toString() {
		return this.getAnswer();
	}

}
