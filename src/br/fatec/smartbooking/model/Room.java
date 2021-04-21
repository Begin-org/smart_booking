package br.fatec.smartbooking.model;

public class Room {
	private int idRoom;
	private String roomNumber;
	private RoomType roomType;

	public Room(int idRoom, String roomNumber, RoomType roomType) {
		super();
		this.idRoom = idRoom;
		this.roomNumber = roomNumber;
		this.roomType = roomType;
	}

	public Room() {
		super();
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	@Override
	public String toString() {
		return "Quarto: " + this.roomType.getTitle() + "\n" + this.roomType.getDescription() + "\n" + "Preço diário: "
				+ this.roomType.getPricePerDay() + "\n";
	}

}
