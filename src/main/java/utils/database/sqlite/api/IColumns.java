package utils.database.sqlite.api;

import utils.database.sqlite.ConstantsDB;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */
/**
 * An interfaces that contains columns in a table ({@link ITables}.
 * 
 * @author <a href="mailto:daniele.andreis@gmail.com"> Daniele Andreis </a>,
 * 
 * @version 2.0 26/lug/2014
 */
public interface IColumns {

	/**
	 * Get the type associated to a column.
	 * 
	 * @return a value that indicates the type ({@link ConstantsDB#REAL},
	 *         {@link ConstantsDB#INTEGER},{@link ConstantsDB#TEXT}).
	 */
	String getType();

	/**
	 * Get if a columns is a primary key.
	 * 
	 * @return true if the columns is a primary key.
	 */
	boolean isPrimary();

	/**
	 * Get the column's name.
	 * 
	 * @return the name.
	 */
	String getName();

	/**
	 * Get all name of columns in a table.
	 * 
	 * @return the name.
	 */
	String[] getNames();
}
