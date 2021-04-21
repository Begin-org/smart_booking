package br.fatec.smartbooking.dao;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.fatec.smartbooking.model.Room;
import br.fatec.smartbooking.model.RoomType;

public class RoomDAO implements IRoom {

	@Override
	public ArrayList<Room> listAvailableRooms() {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Room> rooms = new ArrayList<Room>();

		try {

			preparedStatement = connection.prepareStatement("SELECT room_type.id_room_type, room_type.title, "
					+ "room_type.description, room_type.price_per_day, COUNT(rooms.id_room) as quantity_available "
					+ "FROM rooms INNER JOIN room_type ON rooms.id_room_type = room_type.id_room_type "
					+ "LEFT JOIN booking on rooms.id_room = booking.id_room AND booking.status = 'Agendada' "
					+ "WHERE booking.id_booking IS NULL GROUP BY room_type.id_room_type HAVING quantity_available > 0 ");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				RoomType roomType = new RoomType();
				roomType.setTitle(resultSet.getString("title"));
				roomType.setDescription(resultSet.getString("description"));
				roomType.setPricePerDay(resultSet.getBigDecimal("price_per_day"));
				roomType.setIdRoomType(resultSet.getInt("id_room_type"));

				Room room = new Room();
				room.setRoomType(roomType);

				rooms.add(room);
			}

			connection.close();

			return rooms;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			databaseConnection.closeConnection(connection);
		}

	}

}
