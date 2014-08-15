package utils.database.sqlite;

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

	Class<? extends Activity> getActivity(Enum<?> e);

	String[] getNames();

	String getInitialQuery(Enum tb);

	Enum getEnum();

	AFieldData getData(Cursor popSpin);

}
