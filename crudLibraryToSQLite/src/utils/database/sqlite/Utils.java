package utils.database.sqlite;

/*
 *This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.util.Log;

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

	private static <T extends Enum<T>> ContentValues jsonToContentValue(
			JSONObject jsonObject, String tablesName, ITables tables) {
		ContentValues contentValues = new ContentValues();
		IColumns c = tables.getColumns(tablesName);
		String[] keys = c.getNames();
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			Log.d("test", key);
			if (jsonObject.has(key)) {
				String type = c.getType(key);
				try {
					if (type.equals(Constants.INTEGER)) {
						int valueToPut = jsonObject.getInt(key);
						contentValues.put(key, valueToPut);
						Log.d("test", key + " " + valueToPut);

					} else if (type.equals(Constants.REAL)) {
						double valueToPut = jsonObject.getDouble(key);
						contentValues.put(key, valueToPut);
						Log.d("test", key + " " + valueToPut);
					} else if (type.equals(Constants.TEXT)) {
						String valueToPut = jsonObject.getString(key);
						contentValues.put(key, valueToPut);
						Log.d("test", key + " " + valueToPut);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return contentValues;
	}

	public static String getInitialQuery(ITables tables, int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
