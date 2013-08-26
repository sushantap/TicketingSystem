package com.talentica.semanticweb.app;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {
	public static Date getRandomDate(){
		Calendar cal = Calendar.getInstance();
		int month = new Random().nextInt(12);
		int date = new Random().nextInt(28);
		cal.set(2013, month, date);
		return cal.getTime();
	}
}
