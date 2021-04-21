package br.fatec.smartbooking.controller;

import java.util.ArrayList;

import br.fatec.smartbooking.dao.IRoom;
import br.fatec.smartbooking.dao.RoomDAO;
import br.fatec.smartbooking.model.Room;

public class RoomController implements IRoom {
	RoomDAO roomDAO = new RoomDAO();

	public ArrayList<Room> listAvailableRooms() {
		return this.roomDAO.listAvailableRooms();
	}
}
