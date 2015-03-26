package utils.database.sqlite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import utils.database.sqlite.data.FormatData;

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
public class UtilsDB {

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
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantsDB.DATE, Locale.US);
		return sdf.format(new Date());
	}

	/**
	 * Get a order string.
	 * 
	 * @return a string to use as order data.
	 */

	public static String getDataOrder(FormatData fd, String columnsData,
	        boolean isDesc) {
		StringBuilder sb = new StringBuilder("strftime(");
		sb.append(fd.getDataFormatString());
		sb.append(",");
		sb.append(columnsData);
		sb.append(")");
		if (isDesc) {
			sb.append(" DESC");
		}
		return sb.toString();
	}

}
