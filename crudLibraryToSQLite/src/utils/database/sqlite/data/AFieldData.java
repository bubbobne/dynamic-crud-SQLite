package utils.database.sqlite.data;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Set;

import utils.database.sqlite.ConstantsDB;
import utils.database.sqlite.UtilsDB;
import utils.database.sqlite.api.IColumns;
import utils.database.sqlite.api.IFieldData;
import utils.database.sqlite.api.ITables;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * The data to store in db.
 * 
 * 
 * @author Daniele Andreis 27 Jun 2014
 */
public abstract class AFieldData implements IFieldData {

	protected ITables tab = null;
	protected HashMap<IColumns, Integer> integerValue = new HashMap<IColumns, Integer>();
	protected HashMap<IColumns, Double> doubleValue = new HashMap<IColumns, Double>();
	protected HashMap<IColumns, String> stringValue = new HashMap<IColumns, String>();
	protected static DecimalFormat decimalFormat = new DecimalFormat("##.##");

	public AFieldData(ITables t) {
		this.tab = t;
		init(t);

	}

	public AFieldData(Class<? extends IColumns> c, ITables t) {
		this.tab = t;
		IColumns[] value = c.getEnumConstants();
		init(value);

	}

	private void init(ITables t) {
		IColumns[] values = t.getColumns();
		init(values);
	}

	private void init(IColumns[] values) {
		for (IColumns c : values) {
			String name = ((Enum) c).name();
			if (c.getType().equals(ConstantsDB.INTEGER)) {
				if (!name.equals("STATO")) {
					integerValue.put(c, 0);
				} else {
					integerValue.put(c, ConstantsDB.STATO_NEW);
				}
			} else if (c.getType().equals(ConstantsDB.TEXT)) {
				stringValue.put(c, null);
			}

			else if (c.getType().equals(ConstantsDB.REAL)) {
				doubleValue.put(c, 0.0);
			}
		}
	}

	public AFieldData(ITables t, Cursor cursor) {
		this.tab = t;
		IColumns[] values = t.getColumns();
		if (cursor != null) {
			for (IColumns c : values) {
				String name = ((Enum) c).name();
				if (c.getType().equals(ConstantsDB.INTEGER)) {

					integerValue.put(c,
					        cursor.getInt(cursor.getColumnIndex(name)));

				} else if (c.getType().equals(ConstantsDB.TEXT)) {
					stringValue.put(c,
					        cursor.getString(cursor.getColumnIndex(name)));
				}

				else if (c.getType().equals(ConstantsDB.REAL)) {
					doubleValue.put(c,
					        cursor.getDouble(cursor.getColumnIndex(name)));
				}
			}
		}
	}

	public int getIntValue(IColumns key) {
		if (integerValue.containsKey(key)) {
			return integerValue.get(key);
		} else {
			return ConstantsDB.STATO_NEW;
		}

	}

	public double getDoubleValue(IColumns key) {
		if (doubleValue.containsKey(key)) {
			return doubleValue.get(key);
		} else {
			return ConstantsDB.STATO_NEW;
		}

	}

	public String getStringValue(IColumns key) {
		if (stringValue.containsKey(key)) {
			return stringValue.get(key);
		} else {
			return null;
		}
	}

	public void setValue(IColumns key, int value) {
		if (integerValue.containsKey(key)) {
			integerValue.put(key, value);
		}
	}

	public void setValue(IColumns key, double value) {
		if (doubleValue.containsKey(key)) {
			doubleValue.put(key, value);
		}
	}

	public void setValue(IColumns key, String value) {
		if (stringValue.containsKey(key)) {
			stringValue.put(key, value);
		}
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return tab.getName();
	}

	@Override
	public ContentValues toContentValue() {
		ContentValues cv = new ContentValues();
		Set<IColumns> name = integerValue.keySet();
		for (IColumns n : name) {
			cv.put(n.toString(), integerValue.get(n));
		}
		name = stringValue.keySet();
		for (IColumns n : name) {

			String t = stringValue.get(n);
			if (stringValue.containsKey(n)) {
				String nome = ((Enum) n).name();
				if (nome.equals("DATA_RILIEVO")) {
					cv.put(n.toString(), UtilsDB.getData());
				} else if (t != null) {
					cv.put(n.toString(), t);
				}
			}
		}
		name = doubleValue.keySet();
		for (IColumns n : name) {

			cv.put(n.toString(), doubleValue.get(n));
		}
		setCvToNew(cv);
		return cv;

	}

	protected abstract void setCvToNew(ContentValues cv);

	// TODO Auto-generated method stub
	public int addIntValue(IColumns key) {
		int j = ConstantsDB.NO_DATO;
		if (integerValue.containsKey(key)) {
			j = integerValue.get(key) + 1;
			integerValue.put(key, j);
		}
		return j;

	}

	public int minusIntValue(IColumns key) {
		int j = ConstantsDB.NO_DATO;
		if (integerValue.containsKey(key)) {
			j = integerValue.get(key) - 1;
			integerValue.put(key, j);
		}
		return j;

	}

	@Override
	public void cleanValue() {
		Set<IColumns> name = integerValue.keySet();
		for (IColumns n : name) {
			integerValue.put(n, 0);
		}

		name = doubleValue.keySet();
		for (IColumns n : name) {

			doubleValue.put(n, 0.0);
		}

	}

	public boolean containKeys(IColumns key) {
		if (doubleValue.containsKey(key) || integerValue.containsKey(key)
		        || stringValue.containsKey(key)) {
			return true;
		}

		return false;

	}

	public double sumValue(IColumns key, double valueToAdd) {
		if (doubleValue.containsKey(key)) {
			double v = doubleValue.get(key) + valueToAdd;
			doubleValue.put(key, v);
		}
		return doubleValue.get(key);
	}

}
