package utils.database.sqlite.db;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.database.sqlite.ConstantsDB;
import utils.database.sqlite.api.IColumns;
import utils.database.sqlite.api.IFieldData;
import utils.database.sqlite.api.IGroup;
import utils.database.sqlite.api.ITables;
import utils.database.sqlite.data.ATables;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Constants;

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
	private final Lock w = rwl.writeLock();
	private static SQLiteDatabase database;

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

	/**
	 * 
	 * @return get the DbHelper of this database.
	 */
	private DbHelper getDbHelper() {
		return dbHelper;
	}

	private DataSourceFactory(Context context, int dbVersion, String dbName,
			Class<? extends ITables> tables) {
		dbHelper = new DbHelper(context, dbVersion, dbName, tables);
		database = dbHelper.getWritableDatabase();

	}

	/**
	 * Get Values, stored in a object collections.
	 * 
	 * @param table
	 *            where to extract the value.
	 * @param whereCondition
	 * @param columns
	 *            to extract.
	 * @param group
	 *            that belong.
	 * @return
	 */
	public ArrayList<IFieldData> getAllRows(ITables table,
			String whereCondition, String[] columns, IGroup group) {
		final ArrayList<IFieldData> data = new ArrayList<IFieldData>();

		w.lock();

		if (group != null) {
			whereCondition = whereCondition + " AND " + group.getWhereClause();
		}

		ATables tabs = dbHelper.tables;
		try {

			Cursor popSpin = database.query(table.getName(), columns,
					whereCondition, null, null, null, null);
			popSpin.moveToFirst();
			while (popSpin.isAfterLast() == false) {
				data.add(tabs.getData(table, popSpin, group));
				popSpin.moveToNext();
			}
		} finally {
			w.unlock();
		}
		return data;
	}

	/**
	 * Get an object that store a single row data.
	 * 
	 * @param table
	 * @param whereCondition
	 * @param group
	 * @param order
	 * @return
	 */
	public IFieldData getRow(ITables table, String whereCondition,
			IGroup group, String order) {
		w.lock();
		ATables tabs = dbHelper.tables;
		try {

			Cursor popSpin = database.query(table.getName(), null,
					whereCondition, null, null, null, order, "1");
			popSpin.moveToFirst();
			while (popSpin.isAfterLast() == false) {
				return tabs.getData(table, popSpin, group);
			}
		} finally {
			w.unlock();
		}
		return null;

	}

	/**
	 * * Get an object that store in a numbers of row data.
	 * 
	 * @param table
	 * @param whereCondition
	 * @param group
	 * @param order
	 * @param limit
	 * @return
	 */
	public IFieldData[] getRows(ITables table, String whereCondition,
			IGroup group, String order, int limit) {
		w.lock();
		IFieldData[] data = new IFieldData[limit];
		ATables tabs = dbHelper.tables;
		try {

			Cursor popSpin = database.query(table.getName(), null,
					whereCondition, null, null, null, order,
					String.valueOf(limit));
			popSpin.moveToFirst();
			int i = 0;
			while (popSpin.isAfterLast() == false) {
				data[i] = tabs.getData(table, popSpin, group);
				popSpin.moveToNext();

				i++;

			}
			return data;
		} finally {
			w.unlock();
		}

	}

	/**
	 * Extract an int value to row.
	 * 
	 * @param table
	 * @param whereCondition
	 * @param columns
	 * @return
	 */
	public int getIntValue(ITables table, String whereCondition,
			String[] columns) {
		if (columns != null && columns.length > 0) {
			w.lock();
			ATables tabs = dbHelper.tables;
			try {

				Cursor popSpin = database.query(table.getName(), columns,
						whereCondition, null, null, null, null);
				popSpin.moveToFirst();
				while (popSpin.isAfterLast() == false) {
					return popSpin.getInt(popSpin.getColumnIndex(columns[0]));
				}
			} finally {
				w.unlock();
			}
		}
		return 0;

	}

	/**
	 * Add a row to the table in the database.
	 * 
	 * @param table
	 *            the table where to insert the row.
	 */
	public long addRowToTable(IFieldData data) {
		String table = data.getTable();
		ContentValues content = data.toContentValue();
		long y = addRowToTable(table, content);
		return y;
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
	public long addRowToTable(String table, ContentValues cv) {
		w.lock();
		long y = -1;
		database = dbHelper.getWritableDatabase();
		try {
			y = database.replace(table, null, cv);
		} finally {
			w.unlock();
		}
		return y;
	}

	/**
	 * Update row.
	 * 
	 * @param data
	 * @param whereCl
	 * @return
	 */
	public long updateRowToTable(IFieldData data, String whereCl) {
		String table = data.getTable();
		ContentValues content = data.toContentValue();
		long y = updateRowToTable(table, content, whereCl);
		return y;

	}

	/**
	 * Update row.
	 * 
	 * @param table
	 * @param cv
	 * @param whereClause
	 * @return
	 */
	public long updateRowToTable(String table, ContentValues cv,
			String whereClause) {
		w.lock();
		long y = -1;
		try {
			y = database.update(table, cv, whereClause, null);
		} finally {
			w.unlock();
		}
		return y;
	}

	/**
	 * Remove all table, used when the database is updated.
	 */
	public void removeAllTable() {
		w.lock();
		try {
			String[] tableNames = dbHelper.getTables().getNames();
			int l = tableNames.length;
			for (int i = 0; i < l; i++) {
				database.delete(tableNames[i], null, null);
			}
		} finally {
			w.unlock();
		}
	}

	/**
	 * Remove
	 * 
	 * @param table
	 */
	public void removeTable(String table) {

		w.lock();
		try {
			if (table != null) {
				database.delete(table, null, null);
			}
		} finally {
			w.unlock();
		}
	}

	/**
	 * Delete a rows.
	 * 
	 * @param table
	 * @param ifieldata
	 */
	public void clearValueToTable(ITables table, IFieldData ifieldata) {
		w.lock();
		try {
			if (table != null) {
				database.delete(table.getName(), ifieldata.getWhereToUpdate(),
						null);
			}
		} finally {
			w.unlock();
		}

	}

	/**
	 * 
	 * @return the db path.
	 */
	public String getPath() {
		String value;

		value = database.getPath();

		return value;

	}

	/**
	 * 
	 * @return the db path.
	 */
	public void updateRows(JSONArray jsonA, String tableName, ITables table,
			SharedPreferences mPref, String dataKey) {
		w.lock();
		database.beginTransaction();
		try {
			for (int j = 0; j < jsonA.length(); j++) {
				JSONObject jsonObject = jsonA.getJSONObject(j);
				int stato = jsonObject.getInt("STATO");
				if (stato == ConstantsDB.STATO_CANCEL) {
					int y = database.delete(tableName,
							table.getWhere(jsonObject), null);

				} else if (stato == ConstantsDB.STATO_OK) {
					ContentValues initialValues = jsonToContentValue(
							jsonObject, table);
					long y = database.replace(tableName, null, initialValues);
					if (y == -1) {
						database.insert(tableName, null, initialValues);
					}

					if (jsonObject.has(ConstantsDB.DATA_MODIFICA)) {
						Editor editor = mPref.edit();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd", Locale.US);
						try {
							Date data1;

							data1 = format.parse(jsonObject
									.getString(ConstantsDB.DATA_MODIFICA));
							SimpleDateFormat format2 = new SimpleDateFormat(
									ConstantsDB.DATE, Locale.ITALIAN);
							editor.putString(dataKey, format2.format(data1));
							editor.commit();

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			database.setTransactionSuccessful();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (database != null) {
				database.endTransaction();
			}
			w.unlock();

		}

	}
	
	public static ContentValues jsonToContentValue(JSONObject jsonObject,
			ITables tab) {
		ContentValues contentValues = new ContentValues();

		@SuppressWarnings("unchecked")
		IColumns[] keys = tab.getColumns();
		for (int i = 0; i < keys.length; i++) {
			IColumns key = keys[i];
			if (jsonObject.has(key.getName())) {
				String type = key.getType();
				String name = key.getName();
				try {
					if (type.equals(ConstantsDB.INTEGER)) {

						int valueToPut = -9999;
						if (jsonObject.getString(name) != "null") {
							valueToPut = jsonObject.getInt(name);
						} else {
						}
						contentValues.put(name, valueToPut);
					} else if (type.equals(ConstantsDB.REAL)) {
						double valueToPut = jsonObject.getDouble(name);
						contentValues.put(name, valueToPut);
					} else if (type.equals(ConstantsDB.TEXT)) {
						String valueToPut = jsonObject.getString(name);
						if (!valueToPut.equals("")) {
							contentValues.put(name, valueToPut);
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return contentValues;
	}

}
