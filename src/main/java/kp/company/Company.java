package kp.company;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

/**
 * The high-level kp_spring01 business interface.
 * 
 * <p>
 * This is basically a data access object. kp_spring01 doesn't have a dedicated
 * business facade.
 */
public interface Company {

	/**
	 * Retrieve all <code>Title</code>s from the data store.
	 * 
	 * @return a <code>Collection</code> of <code>Title</code>s
	 */
	Collection<Title> getTitles() throws DataAccessException;

	/**
	 * Retrieve <code>Department</code>s from the data store by name, returning all
	 * departments whose name <i>starts</i> with the given name.
	 * 
	 * @param name the name to search for
	 * @return a <code>Collection</code> of matching <code>Department</code>s (or an
	 *         empty <code>Collection</code> if none found)
	 */
	Collection<Department> findDepartments(String name) throws DataAccessException;

	/**
	 * Retrieve an <code>Department</code> from the data store by id.
	 * 
	 * @param id the id to search for
	 * @return the <code>Department</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
	Department loadDepartment(int id) throws DataAccessException;

	/**
	 * Retrieve a <code>Employee</code> from the data store by id.
	 * 
	 * @param id the id to search for
	 * @return the <code>Employee</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */
	Employee loadEmployee(int id) throws DataAccessException;

	/**
	 * Save an <code>Department</code> to the data store, either inserting or
	 * updating it.
	 * 
	 * @param department the <code>Department</code> to save
	 * @see BaseEntity#isNew
	 */
	void storeDepartment(Department department) throws DataAccessException;

	/**
	 * Save a <code>Employee</code> to the data store, either inserting or updating
	 * it.
	 * 
	 * @param employee the <code>Employee</code> to save
	 * @see BaseEntity#isNew
	 */
	void storeEmployee(Employee employee) throws DataAccessException;

	/**
	 * Deletes a <code>Department</code> from the data store.
	 * 
	 * @param id department id
	 * @throws DataAccessException the exception
	 */
	void deleteDepartment(int id) throws DataAccessException;

	/**
	 * Deletes a <code>Employee</code> from the data store.
	 * 
	 * @param id employee id
	 * @throws DataAccessException the exception
	 */
	void deleteEmployee(int id) throws DataAccessException;
}