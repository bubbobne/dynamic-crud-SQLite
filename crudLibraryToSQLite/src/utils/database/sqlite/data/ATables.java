package utils.database.sqlite.data;

import java.util.ArrayList;

import utils.database.sqlite.api.IColumns;
import utils.database.sqlite.api.IFieldData;
import utils.database.sqlite.api.IGroup;
import utils.database.sqlite.api.ITables;
import android.database.Cursor;
import android.util.Log;

public class ATables {
	private static final String CREATE = "CREATE TABLE ";

	private static final String COMMA = ",";

	private Class<? extends ITables> tabelle;

	public ATables(Class<? extends ITables> tabelle) {
		this.tabelle = tabelle;
	}

	public String[] getCreatequery() {
		ITables[] tables = tabelle.getEnumConstants();
		int l = tables.length;
		String[] queries = new String[l];
		for (int i = 0; i < l; i++) {
			ITables tab = tables[i];
			StringBuilder sb = new StringBuilder(CREATE + tab.getName() + " (");
			String primary = " primary key(";
			ArrayList<String> primaryName = new ArrayList<String>();

			IColumns[] col = tab.getColumns();
			int l2 = col.length;
			for (int j = 0; j < l2 - 1; j++) {
				IColumns c = col[j];
				sb.append(c.getName() + " " + c.getType() + COMMA);
				if (c.isPrimary()) {
					primaryName.add(c.getName());
				}
			}
			Log.d("create tab", primaryName.toString());
			IColumns c = col[l2 - 1];
			if (c.isPrimary()) {
				primaryName.add(c.getName());
			}
			sb.append(c.getName() + " " + c.getType());

			if (primaryName.size() > 0) {
				sb.append(COMMA);
				sb.append(primary);
				sb.append(primaryName.get(0));
				for (int k = 1; k < primaryName.size(); k++) {
					sb.append(COMMA + primaryName.get(k));
				}

				sb.append(")");
			}
			sb.append(")");
			queries[i] = sb.toString();
			Log.d("create tab", queries[i]);
		}
		return queries;
	}

	public String[] getNames() {
		ITables[] tabs = tabelle.getEnumConstants();
		String[] names = new String[tabs.length];
		for (int i = 0; i < tabs.length; i++) {
			names[i] = tabs[i].getName();
		}
		return names;
	}

	public String[] getNames(IGroup group) {
		ITables[] tabs = tabelle.getEnumConstants();
		ArrayList<String> array = new ArrayList<String>();
		for (int i = 0; i < tabs.length; i++) {

			if (tabs[i].getIGroup() != null
			        && tabs[i].getIGroup().isInstance(group)) {
				array.add(tabs[i].getName());
			}
		}

		return array.toArray(new String[array.size()]);
	}

	public ArrayList<ITables> getTables(IGroup group) {
		ITables[] tabs = tabelle.getEnumConstants();
		ArrayList<ITables> array = new ArrayList<ITables>();
		for (int i = 0; i < tabs.length; i++) {

			if (tabs[i].getIGroup() != null
			        && tabs[i].getIGroup().isInstance(group)) {
				array.add(tabs[i]);
			}
		}

		return array;
	}

	public IFieldData getData(ITables table, Cursor popSpin, IGroup group) {
		return table.createData(table, popSpin);
	}

	public ITables getTable(String name) {
		ITables[] tables = tabelle.getEnumConstants();
		for (ITables t : tables) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	public ITables[] getTables(ITables[] exclude) {
		ITables[] tabs = tabelle.getEnumConstants();
		ArrayList<ITables> array = new ArrayList<ITables>();
		for (int i = 0; i < tabs.length; i++) {
			boolean isOk = true;
			for (int k = 0; k < exclude.length; k++) {
				if (tabs[i].getName().equals(exclude[k].getName())) {
					isOk = false;
				}
				if (isOk) {
					array.add(tabs[i]);
				}
			}
		}
		return array.toArray(new ITables[array.size()]);
	}
}
