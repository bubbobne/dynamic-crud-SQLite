package utils.database.sqlite;

/*
 * This software is released under the terms of the GNU GENERAL PUBLIC LICENSE
 * Version 3.
 */
/**
 * 
 * 
 * @author <a href="mailto:daniele.andreis@gmail.com"> Daniele Andreis </a>,
 * 
 * @version 2.0 26/lug/2014
 */
public interface IColumns {
	String getCreateQuery();

	String getType(String name);

	String[] getNames();

}
