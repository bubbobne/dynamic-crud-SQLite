package utils.database.sqlite.api;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.ContentValues;

public interface IFieldData {

	String getTable();

	ContentValues toContentValue();

	void cleanValue();

	String getDescription();

	int getId();

	int getPlaceId();

	int getColor();

	List<BasicNameValuePair> toList();

	ContentValues getContentValueToUpdate(String value);

	String getWhereToUpdate();

	String getData();

	boolean isRegistered();

	void setRegistered(boolean reg);

}
