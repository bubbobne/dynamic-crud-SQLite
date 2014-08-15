package utils.database.sqlite;
/*
* This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
* Version 3.
*/
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class is a .... which allow to read,delete, write a specific table.
 * 
 * <p>
 * Is a skeleton of the db, in order to have only one object of this class
 * </p>
 * 
 * @author Daniele Andreis 27 Jun 2014
 */
public class DataSourceFactory {
	/*
	 * The CRUD object.
	 */
	private static DataSourceFactory instance = null;
	/*
	 * The class that define the SQLite database.
	 */
	private final DbHelper dbHelper;

	/**
	 * Return the instance for the CRUD operation.
	 * 
	 * @param context
	 * @return the instance for the CRUD operation.
	 */
	public static synchronized DataSourceFactory getInstance(Context context,int dbVersion, String dbName,
			ITables tables) {
		if (instance == null) {
			instance = new DataSourceFactory(context, dbVersion, dbName, tables);
		}
		return instance;
	}

	private DataSourceFactory(Context context, int dbVersion, String dbName,
			ITables tables) {
		dbHelper = new DbHelper(context, dbVersion, dbName, tables);
	}

	public ArrayList<AFieldData> getAllRows(String table,
			String whereCondition, String[] columns) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		final ArrayList<AFieldData> data = new ArrayList<AFieldData>();

		Cursor popSpin = database.query(table, columns, whereCondition, null,
				null, null, null);
		popSpin.moveToFirst();
		while (popSpin.isAfterLast() == false) {

			data.add(dbHelper.getTables().getData(popSpin));
			popSpin.moveToNext();
		}
		database.close();
		return data;
	}

	/**
	 * Add a row to the table in the database.
	 * 
	 * @param table
	 *            the table where to insert the row.
	 * @param content
	 *            the value to insert.
	 * @return the number of new row or -1 if it doesn't work correctly.
	 */
	public long addRowToTable(AFieldData data) {
		String table = data.getTable();
		ContentValues content = data.toContentValue();
		long y = addRowToTable(table, content);
		return y;
	}

	public long addRowToTable(String table, ContentValues cv) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		database = dbHelper.getWritableDatabase();
		long y = database.insert(table, null, cv);
		database.close();
		return y;
	}

	public long updateRowToTable(String table, ContentValues cv,
			String whereClause) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		database = dbHelper.getWritableDatabase();
		long y = database.update(table, cv, whereClause, null);
		database.close();
		return y;
	}

	public SQLiteDatabase getWritableDatabase() {
		return dbHelper.getReadableDatabase();
	}

	public void removeAllColumnsAllTable() {
		SQLiteDatabase database = dbHelper.getWritableDatabase();	
		String[] tableNames =dbHelper.getTables().getNames();
		int l = tableNames.length;
		for (int i=0;i<l;i++) {
			if (dbHelper.getTables().getColumns(i) != null) {
				database.delete(tableNames[i], null, null);
			}
		}
		database.close();

	}

}
