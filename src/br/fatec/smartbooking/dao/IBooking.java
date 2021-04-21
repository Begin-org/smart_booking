package br.fatec.smartbooking.dao;

import br.fatec.smartbooking.model.Booking;

public interface IBooking {
	public Booking findBooking(Booking booking);
	public String cancelBooking(Booking booking);
	public String bookRoom(Booking booking);
}
