package pl.egalit.vocab.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringDateConversion {
	static Date toDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	static String toString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		return df.format(date);
	}
}
