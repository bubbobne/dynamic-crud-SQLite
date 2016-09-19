package utils.database.sqlite.api;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.ContentValues;

/**
 * An interface associated to a table.
 * 
 * @author Daniele ANdreis.
 */
public interface IFieldData {

	/**
	 * Get the name of table which is used to create this class.
	 * 
	 * @return the table name.
	 */
	String getTable();

	/**
	 * Create the ContentValue of this object.
	 * 
	 * @return
	 */
	ContentValues toContentValue();

	/**
	 * Reset the value stored in the object.
	 */
	void cleanValue();

	/**
	 * Create a short description of the data stored.
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * Get the row's ID or object ID.
	 * 
	 * @return an ID related to the object.
	 */
	int getId();

	/**
	 * Get a list to send.
	 * 
	 * @return a list of value to send via http.
	 */
	List<BasicNameValuePair> toList();

	/**
	 * Get a ContenentValue.
	 * 
	 * @return a ContentValue whiy only value to update.
	 */
	ContentValues getContentValueToUpdate(String value);

	/**
	 * Get a where condition related to the object.
	 * 
	 * @return a string to use as where clause.
	 */
	String getWhereToUpdate();

	/**
	 * 
	 * 
	 * @return the data.
	 */
	String getData();

	/**
	 * 
	 * 
	 * @return true if the current object is already saved in db.
	 */
	boolean isRegistered();

	/**
	 * Set the value of registered object in SQLite.
	 * 
	 * @param reg
	 */
	void setRegistered(boolean reg);

}
