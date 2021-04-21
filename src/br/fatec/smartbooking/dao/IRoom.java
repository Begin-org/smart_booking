package br.fatec.smartbooking.dao;

import java.util.ArrayList;
import br.fatec.smartbooking.model.Room;

public interface IRoom {
	public ArrayList<Room> listAvailableRooms();
}
