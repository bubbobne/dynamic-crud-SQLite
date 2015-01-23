package utils.database.sqlite.db;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import utils.database.sqlite.api.IFieldData;
import utils.database.sqlite.api.ITables;
import utils.database.sqlite.data.ATables;
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
	/*
	 * Concurrency look.
	 */
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	/**
	 * Return the instance for the CRUD operation.
	 * 
	 * @param context
	 * @return the instance for the CRUD operation.
	 */
	public static synchronized DataSourceFactory getInstance(Context context,
	        int dbVersion, String dbName, Class<? extends ITables> tables) {
		if (instance == null) {
			instance = new DataSourceFactory(context, dbVersion, dbName, tables);
		}
		return instance;
	}

	public DbHelper getDbHelper() {

		return dbHelper;
	}

	private DataSourceFactory(Context context, int dbVersion, String dbName,
	        Class<? extends ITables> tables) {
		dbHelper = new DbHelper(context, dbVersion, dbName, tables);
	}

	public ArrayList<IFieldData> getAllRows(ITables table,
	        String whereCondition, String[] columns) {
		final ArrayList<IFieldData> data = new ArrayList<IFieldData>();
		;
		r.lock();
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		ATables tabs = dbHelper.tables;
		try {

			Cursor popSpin = database.query(table.getName(), columns,
			        whereCondition, null, null, null, null);
			popSpin.moveToFirst();
			while (popSpin.isAfterLast() == false) {
				data.add(tabs.getData(table, popSpin));
				popSpin.moveToNext();
			}
		} finally {
			closeDb(database);
			r.unlock();
		}
		return data;
	}

	public IFieldData getRow(ITables table, String whereCondition) {
		r.lock();
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		ATables tabs = dbHelper.tables;
		try {

			Cursor popSpin = database.query(table.getName(), null,
			        whereCondition, null, null, null, null);
			popSpin.moveToFirst();
			while (popSpin.isAfterLast() == false) {
				return tabs.getData(table, popSpin);
			}
		} finally {
			closeDb(database);
			r.unlock();
		}
		return null;

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
	public long addRowToTable(IFieldData data) {
		String table = data.getTable();
		ContentValues content = data.toContentValue();
		long y = addRowToTable(table, content);
		return y;
	}

	public long addRowToTable(String table, ContentValues cv) {
		w.lock();
		long y = -1;
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		database = dbHelper.getWritableDatabase();
		try {
			y = database.insert(table, null, cv);
		} finally {
			closeDb(database);
			w.unlock();
		}
		return y;
	}

	public long updateRowToTable(IFieldData data, String whereCl) {

		String table = data.getTable();
		ContentValues content = data.toContentValue();
		long y = updateRowToTable(table, content, whereCl);
		return y;

	}

	public long updateRowToTable(String table, ContentValues cv,
	        String whereClause) {
		w.lock();
		long y = -1;
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		try {
			y = database.update(table, cv, whereClause, null);
			database.close();
		} finally {
			closeDb(database);
			w.unlock();
		}
		return y;
	}

	public void removeAllTable() {
		w.lock();
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		try {
			String[] tableNames = dbHelper.getTables().getNames();
			int l = tableNames.length;
			for (int i = 0; i < l; i++) {
				database.delete(tableNames[i], null, null);

			}
		} finally {
			w.unlock();
			closeDb(database);
		}

	}

	/*
	 * Close the db.
	 */
	private void closeDb(SQLiteDatabase db) {
		if (db != null && db.isOpen()) {
			db.close();
		}

	}

	public void clearTable(ITables[] tables, IFieldData ifieldata) {

	}

}
