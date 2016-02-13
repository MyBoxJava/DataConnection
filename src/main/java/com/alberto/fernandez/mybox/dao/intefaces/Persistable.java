package com.alberto.fernandez.mybox.dao.intefaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This interfaces gives the hability of persitability to the objects that
 * implement it. Defines the basic CRUD operations
 * 
 * @param <P>
 *            Generic class that will be changed for our own Class, for example
 *            <code>User</code>
 * 
 * @author ur00
 *
 */
public interface Persistable<P> {

	/**
	 * List all the objectos from the Data Base, ordered by descendant
	 * <code>id</code>
	 * 
	 * @return {@code List
	 * <P>} if exists data, else an empty {@code List}
	 * @throws SQLException
	 */
	List<P> getAll();

	/**
	 * Returns a P object from the Data Base by the <code>id</code>
	 * 
	 * @param id
	 *            <code>int</code> the id of a user in the database
	 * @return <code>P</code> If the id exists the <code>P</code> is returned,
	 *         else <code>null</code>
	 */
	P getById(int id);

	/**
	 * Deletes the object from the DataBase
	 * 
	 * @param id
	 *            {@code int} the object identifier
	 * @return {@code Boolean}
	 *         <ul>
	 *         <li><strong>true</strong> if deleted</li>
	 *         <li><strong>false</strong> if not deleted</li>
	 *         </ul>
	 */
	boolean delete(int id);

	/**
	 * Modifies the given object in the DataBase
	 * 
	 * @param persistable
	 *            {@code P} The object with the values to modify
	 * @return {@code Boolean}
	 *         <ul>
	 *         <li><strong>true</strong> if modified</li>
	 *         <li><strong>false</strong> if not modified</li>
	 *         </ul>
	 */
	boolean update(P persistable);

	/**
	 * Inserts a new object
	 * 
	 * @param persistable
	 *            {@code P} The object to insert
	 * @return {@code int} Object identifier, else -1
	 */
	int insert(P persistable);
	
	/**
	 * Builds a {@code P} object from the data retrieved from the Data Base
	 * @param rs {@code ResultSer} the retrieved data from the Data Base
	 * @return {@code P} the object 
	 * @throws SQLException
	 */
	P mapping(ResultSet rs) throws SQLException;
}
