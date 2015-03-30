package utils.database.sqlite.api;

public interface IGroup {

	String getName();

	int getId();

	int getId(String name);

	String getWhereClause();

	float getIcon();

	boolean isSame(Class<? extends IGroup> iGroup);
}
