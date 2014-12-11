package utils.database.sqlite.api;

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
	String getType();

	boolean isPrimary();

	String getName();

	String[] getNames();
}
