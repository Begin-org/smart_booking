package br.fatec.smartbooking.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import br.fatec.smartbooking.model.Booking;
import br.fatec.smartbooking.model.Room;
import br.fatec.smartbooking.model.RoomType;

public class BookingDAO implements IBooking {

	@Override
	public Booking findBooking(Booking booking) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection
					.prepareStatement("SELECT *,booking.end_date - booking.start_date AS period_in_days "
							+ "FROM booking INNER JOIN rooms ON booking.id_room = rooms.id_room "
							+ "INNER JOIN room_type ON rooms.id_room_type = room_type.id_room_type "
							+ "WHERE booking.cpf_customer LIKE ? AND status LIKE 'Agendada'");
			preparedStatement.setString(1, booking.getCpfCustomer());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				RoomType roomType = new RoomType();
				roomType.setTitle(resultSet.getString("title"));
				roomType.setDescription(resultSet.getString("description"));
				roomType.setPricePerDay(resultSet.getBigDecimal("price_per_day"));

				Room room = new Room();
				room.setRoomType(roomType);
				room.setRoomNumber(resultSet.getString("room_number"));
				room.setIdRoom(resultSet.getInt("id_room"));
				roomType.setIdRoomType(resultSet.getInt("id_room_type"));

				Calendar startDate = Calendar.getInstance();
				startDate.setTime(resultSet.getDate("start_date"));
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(resultSet.getDate("end_date"));
				Calendar bookingDate = Calendar.getInstance();
				bookingDate.setTime(resultSet.getTimestamp("booking_date"));
				Calendar cancellationDate = Calendar.getInstance();
				if (resultSet.getDate("cancellation_date") != null) {
					cancellationDate.setTime(resultSet.getDate("cancellation_date"));
				}

				booking.setRoom(room);
				booking.setStartDate(startDate);
				booking.setEndDate(endDate);
				booking.setBookingDate(bookingDate);
				booking.setCancellationDate(cancellationDate);
				booking.setCpfCustomer(resultSet.getString("cpf_customer"));
				booking.setIdBooking(resultSet.getInt("id_booking"));
				booking.setFullPrice(resultSet.getBigDecimal("full_price"));
				booking.setPeriodInDays(resultSet.getInt("period_in_days"));
			}

			return booking;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			databaseConnection.closeConnection(connection);
		}

	}

	@Override
	public String cancelBooking(Booking booking) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("call cancel_booking(?);");
			preparedStatement.setString(1, booking.getCpfCustomer());
			resultSet = preparedStatement.executeQuery();

			resultSet.next();

			return resultSet.getString(1);

		} catch (Exception e) {
			e.printStackTrace();
			return "Houve um erro na aplicação";
		} finally {
			databaseConnection.closeConnection(connection);
		}

	}

	@Override
	public String bookRoom(Booking booking) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("call book_room(?, ?, ?, ?);");
			preparedStatement.setInt(1, booking.getRoom().getRoomType().getIdRoomType());
			preparedStatement.setString(2, booking.getCpfCustomer());
			preparedStatement.setDate(3, new Date(booking.getStartDate().getTimeInMillis()));
			preparedStatement.setDate(4, new Date(booking.getEndDate().getTimeInMillis()));
			resultSet = preparedStatement.executeQuery();

			resultSet.next();

			return resultSet.getString(1);

		} catch (Exception e) {
			e.printStackTrace();
			return "Houve um erro na aplicação";
		} finally {
			databaseConnection.closeConnection(connection);
		}

	}

}
