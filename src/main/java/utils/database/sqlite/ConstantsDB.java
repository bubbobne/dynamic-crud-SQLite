package utils.database.sqlite;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */
/**
 * 
 * @author Daniele Andreis.
 * 
 */
public class ConstantsDB {
	/**
	 * The string to set as an integer the value of a columns.
	 */
	public final static String INTEGER = "INTEGER";
	/**
	 * The string to set as an text the value of a columns.
	 */
	public final static String TEXT = "TEXT";
	/**
	 * The string to set a real the value of a columns.
	 */
	public final static String REAL = "REAL";
	/**
	 * Status of a new variable.
	 */
	public static final int STATO_NEW = -9999;
	/**
	 * Status of a new variable.
	 */
	public static final int NO_DATO = -9999;
	/**
	 * Data format.
	 */
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATA_MODIFICA = "DATA_MODIFICA";
	public static final int STATO_CANCEL = 2;
	public static final int STATO_OK = 0;
}
