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
	IColumns getColumns(int i);

	IColumns getColumns(String tableName);

	Class<? extends Activity> getActivity();

	String getName();

	Enum getEnum();

	IColumns[] getColumns();

	IFieldData createData(ITables tab, Cursor cursor);

	Class<? extends IGroup> getIGroup();

	ITables[] getListNames(IGroup group);

	String getWhere(JSONObject jO);

	String getWhereUnregister();

	boolean isParent();

	ITables[] getChilds();

	ContentValues colToUpdate(String value, IFieldData d);
}
