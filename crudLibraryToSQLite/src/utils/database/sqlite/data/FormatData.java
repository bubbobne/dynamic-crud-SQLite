package utils.database.sqlite.data;

/**
 * The data format.
 * 
 * 
 * @author Daniele Andreis. 24 Mar 2015
 */
public enum FormatData {
	USA("'%Y-%m-%d'"), ITALY("'%d-%m-%Y'");

	private final String value;

	private FormatData(String value) {
		this.value = value;
	}

	public String getDataFormatString() {
		// TODO Auto-generated method stub
		return value;
	}
}
