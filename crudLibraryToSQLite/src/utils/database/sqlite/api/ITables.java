package utils.database.sqlite.api;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */
import android.app.Activity;
import android.database.Cursor;

/**
 * 
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

}
