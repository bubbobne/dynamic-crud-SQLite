package utils.database.sqlite.api;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */

/**
 * An interface to collect several tables.
 * 
 * @author Daniele Andreis.
 *
 */
public interface IGroup {

	/**
	 * 
	 * 
	 * @return the name of the group.
	 */
	String getName();

	/**
	 * 
	 * @return the where clause to extract all value belong to a group.
	 */
	String getWhereClause();

	/**
	 * 
	 * @return a value associated to a picture of the group.
	 */
	float getIcon();

	/**
	 * Return if this object is the same of param.
	 * 
	 * @param iGroup
	 *            the object to compare.
	 * @return true if this and param are instance of the same class.
	 */
	boolean isSame(Class<? extends IGroup> iGroup);
}
