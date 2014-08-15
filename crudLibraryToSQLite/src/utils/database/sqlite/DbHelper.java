package utils.database.sqlite;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 * */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The helper to use SQLite.
 * 
 * 
 * @author Daniele Andreis 27 Jun 2014
 * @param <T>
 */
public class DbHelper extends SQLiteOpenHelper {
	private final static String DROP = "DROP TABLE IF EXISTS ";
	private static final CursorFactory DB_FACTORY = null;
	ITables tables;

	public ITables getTables() {
		return tables;
	}

	public DbHelper(Context appContext, int dbVersion, String dbName,
			ITables tables) {
		super(appContext, dbName, DB_FACTORY, dbVersion);
		this.tables = tables;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create al the table at same time.
		String[] tableNames = tables.getNames();
		int l = tableNames.length;
		for (int i = 0; i < l; i++) {
			if (tables.getColumns(i) != null) {
				db.execSQL(Utils.getInitialQuery(tables, i));
			}
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String[] tableNames = tables.getNames();
		int l = tableNames.length;
		for (int i = 0; i < l; i++) {
			db.execSQL(DROP + tableNames[i]);
		}
		onCreate(db);
	}
}
