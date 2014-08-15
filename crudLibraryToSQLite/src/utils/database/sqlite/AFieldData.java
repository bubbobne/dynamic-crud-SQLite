package utils.database.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * The data to store in db.
 * 
 * 
 * @author Daniele Andreis 27 Jun 2014
 */
public abstract class AFieldData {
	public AFieldData(Cursor cursor) {
	}

	public abstract String getTable();

	public abstract ContentValues toContentValue();

}
