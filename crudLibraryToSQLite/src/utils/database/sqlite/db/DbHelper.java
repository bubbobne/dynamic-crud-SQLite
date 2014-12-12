package utils.database.sqlite.db;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 * */
import utils.database.sqlite.api.ITables;
import utils.database.sqlite.data.ATables;
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
	public final static String TABLE_KEY = "tableKey";

	ATables tables;

	public ATables getTables() {
		return tables;
	}

	public DbHelper(Context appContext, int dbVersion, String dbName,
	        Class<? extends ITables> tables) {
		super(appContext, dbName, DB_FACTORY, dbVersion);
		this.tables = new ATables(tables);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create al the table at same time.
		String[] query = tables.getCreatequery();
		for (String str : query) {
			db.execSQL(str);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String[] tabs = tables.getNames();
		for (String s : tabs) {
			db.execSQL(DROP + s);
		}
		onCreate(db);
	}
}
