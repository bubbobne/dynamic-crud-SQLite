package utils.database.sqlite.api;

import android.content.ContentValues;

public interface IFieldData {

	String getTable();

	ContentValues toContentValue();

	void cleanValue();

}
