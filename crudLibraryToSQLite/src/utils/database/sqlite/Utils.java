package utils.database.sqlite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
 *This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */

/**
 * 
 * 
 * 
 * A collection of utilities.
 * 
 * @author <a href="mailto:daniele.andreis@gmail.com"> Daniele Andreis </a>,
 * 
 * @version 2.0 26/lug/2014
 */
public class Utils {

	/**
	 * Get an array of names.
	 * 
	 * @param ec
	 * @return the names of the enum.
	 */
	public static <T extends Enum<T>> String[] names(Class<T> ec) {
		T[] values = ec.getEnumConstants();
		String[] names = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			names[i] = values[i].name();
		}

		return names;
	}

	/**
	 * Get the current date.
	 * 
	 * @return now!
	 */
	public static String getData() {
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantsDB.DATE,
		        Locale.ITALIAN);
		return sdf.format(new Date());
	}
}
