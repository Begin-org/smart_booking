package br.fatec.smartbooking.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LanguageConstants {
	private LanguageConstants() {}
	
	public static final Locale LOCALE = new Locale("pt", "BR");
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy 'às' HH'h'mm");
}
