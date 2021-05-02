package br.fatec.smartbooking.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import br.fatec.smartbooking.utils.LanguageConstants;
import jade.util.leap.Serializable;

public class Booking implements Serializable{
	private int idBooking;
	private Room room;
	private String cpfCustomer;
	private Calendar startDate;
	private Calendar endDate;
	private int periodInDays;
	private BigDecimal fullPrice;
	private Calendar bookingDate;
	private Calendar cancellationDate;

	public Booking(int idBooking, Room room, String cpfCustomer, Calendar startDate, Calendar endDate, int periodInDays,
			BigDecimal fullPrice, Calendar bookingDate, Calendar cancellationDate) {
		super();
		this.idBooking = idBooking;
		this.room = room;
		this.cpfCustomer = cpfCustomer;
		this.startDate = startDate;
		this.endDate = endDate;
		this.periodInDays = periodInDays;
		this.fullPrice = fullPrice;
		this.bookingDate = bookingDate;
		this.cancellationDate = cancellationDate;
	}

	public Booking() {
		super();
	}

	public int getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getCpfCustomer() {
		return cpfCustomer;
	}

	public void setCpfCustomer(String cpfCustomer) {
		this.cpfCustomer = cpfCustomer;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public int getPeriodInDays() {
		return periodInDays;
	}

	public void setPeriodInDays(int periodInDays) {
		this.periodInDays = periodInDays;
	}

	public BigDecimal getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(BigDecimal fullPrice) {
		this.fullPrice = fullPrice;
	}

	public Calendar getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Calendar bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Calendar getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Calendar cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	@Override
	public String toString() {
		if (this.idBooking != 0) {
			return "Você tem uma reserva ativa! " + "Quarto: " + this.getRoom().getRoomType().getTitle() + "<br> "
					+ this.getRoom().getRoomType().getDescription() + " " + "Preço diário: "
					+ this.getRoom().getRoomType().getPricePerDay() + ". " + "Número do quarto: "
					+ this.getRoom().getRoomNumber() + ". " + "Dia de entrada: "
					+ LanguageConstants.DATE_FORMAT.format(this.getStartDate().getTime()) + ". " + "Dia de saída: "
					+ LanguageConstants.DATE_FORMAT.format(this.getEndDate().getTime()) + ". " + "Período de estadia: "
					+ this.getPeriodInDays() + " dias. " + "Preço total da estadia: "
					+ NumberFormat.getCurrencyInstance(br.fatec.smartbooking.utils.LanguageConstants.LOCALE)
							.format(this.getFullPrice())
					+ ". " + "Data da reserva: "
					+ LanguageConstants.DATETIME_FORMAT.format(this.getBookingDate().getTime());
		} else {
			return "Você não tem reservas ativas";
		}
	}

}
