package br.fatec.smartbooking.model;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class RoomType {
	private int idRoomType;
	private String title;
	private String description;
	private BigDecimal pricePerDay;

	public RoomType(int idRoomType, String title, String description, BigDecimal pricePerDay) {
		super();
		this.idRoomType = idRoomType;
		this.title = title;
		this.description = description;
		this.pricePerDay = pricePerDay;
	}

	public RoomType() {
		super();
	}

	public int getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(int idRoomType) {
		this.idRoomType = idRoomType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPricePerDay() {
		return NumberFormat.getCurrencyInstance(br.fatec.smartbooking.utils.LanguageConstants.LOCALE)
				.format(this.pricePerDay);
	}

	public void setPricePerDay(BigDecimal pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

}
