package utils.database.sqlite.api;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * An Interface for a table of the SQLite.
 * 
 * @author <a href="mailto:daniele.andreis@gmail.com"> Daniele Andreis </a>.
 * @version 2.0 26/lug/2014
 */
public interface ITables {
	/**
	 * Get the form used to fill the {@link IFieldData}.
	 * 
	 * @return an activity class with a form.
	 */
	Class<? extends Activity> getActivity();

	/**
	 * 
	 * @return tables name.
	 */
	String getName();

	/**
	 * Get all the {@link IColumns} related to tis tble.
	 * 
	 * @return
	 */
	IColumns[] getColumns();

	/**
	 * Create an object {@link IFieldData}.
	 * 
	 * @param tab
	 *            this table.
	 * @param cursor
	 * @return the object related to this table.
	 */
	IFieldData createData(ITables tab, Cursor cursor);

	/**
	 * 
	 * @return the {@link IGroup} which table belongs.
	 */
	Class<? extends IGroup> getIGroup();

	/**
	 * A where clause to update value.
	 * 
	 * @param jO
	 *            which contains the value to use in where.
	 * @return
	 */
	String getWhere(JSONObject jO);

	/**
	 * Where clause for unregistered value (whith remote db).
	 * 
	 * @return
	 */
	String getWhereUnregister();

	/**
	 * @return true if this table have a child tables.
	 */
	boolean isParent();

	/**
	 * 
	 * @return the collection of child table.
	 */
	ITables[] getChilds();

	/**
	 * Create the cv to use in update.
	 * 
	 * @param valueToUpdate, is the value to update.
	 * @param d
	 * @return
	 */
	ContentValues colToUpdate(String valueToUpdate, IFieldData d);
}
