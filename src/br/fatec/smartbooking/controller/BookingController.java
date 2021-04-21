package br.fatec.smartbooking.controller;

import java.util.Calendar;

import br.fatec.smartbooking.dao.BookingDAO;
import br.fatec.smartbooking.dao.IBooking;
import br.fatec.smartbooking.model.Booking;

public class BookingController implements IBooking {
	BookingDAO bookingDAO = new BookingDAO();

	@Override
	public Booking findBooking(Booking booking) {

		if (booking == null || booking.getCpfCustomer() == null || booking.getCpfCustomer().trim() == "") {
			return null;
		} else {
			return this.bookingDAO.findBooking(booking);
		}
	}

	@Override
	public String cancelBooking(Booking booking) {
		if (booking == null || booking.getCpfCustomer() == null || booking.getCpfCustomer().trim() == "") {
			return "Dados inválidos";
		} else {
			return this.bookingDAO.cancelBooking(booking);
		}
	}

	@Override
	public String bookRoom(Booking booking) {
		Calendar todayDate = Calendar.getInstance();

		if (booking == null || booking.getCpfCustomer() == null || booking.getCpfCustomer().trim() == ""
				|| booking.getRoom().getRoomType().getIdRoomType() == 0 || booking.getStartDate() == null
				|| booking.getEndDate() == null || booking.getStartDate().compareTo(booking.getEndDate()) >= 0
				|| todayDate.compareTo(booking.getStartDate()) >= 0 || todayDate.compareTo(booking.getEndDate()) >= 0) {
			return "Dados inválidos";
		} else {
			return this.bookingDAO.bookRoom(booking);
		}
	}
}
