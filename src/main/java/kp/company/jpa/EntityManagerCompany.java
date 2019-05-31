package kp.company.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kp.company.Company;
import kp.company.Department;
import kp.company.Employee;
import kp.company.Title;

/**
 * JPA implementation of the Company interface using EntityManager.
 * 
 * <p>
 * The mappings are defined in "orm.xml" located in the META-INF directory.
 */
@Repository
@Transactional
public class EntityManagerCompany implements Company {

	private final Log logger = LogFactory.getLog(getClass());

	@PersistenceContext
	private EntityManager em;

	/**
	 * Gets titles.
	 * 
	 * @return resultList list of titles
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Title> getTitles() {
		
		String sql = "SELECT title FROM Title title ORDER BY title.name";
		Query query = this.em.createQuery(sql);
		Collection<Title> resultList = query.getResultList();

		logger.debug("getTitles():");
		return resultList;
	}
	/**
	 * Finds departments.
	 * 
	 * @param name
	 *            name
	 * @return resultList list of departments
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Department> findDepartments(String name) {

		String sql = "SELECT department FROM Department department WHERE department.name LIKE :name";
		Query query = this.em.createQuery(sql);
		query.setParameter("name", name + "%");
		Collection<Department> resultList = query.getResultList();

		logger.debug("findDepartments(): name[" + name + "]");
		return resultList;
	}

	/**
	 * Loads department.
	 * 
	 * @param id
	 *            id
	 * @return department department
	 */
	@Transactional(readOnly = true)
	public Department loadDepartment(int id) {

		Department department = this.em.find(Department.class, id);

		logger.debug("loadDepartment(): id[" + id + "]");
		return department;
	}

	/**
	 * Loads employee.
	 * 
	 * @param id
	 *            id
	 * @return employee employee
	 */
	@Transactional(readOnly = true)
	public Employee loadEmployee(int id) {

		Employee employee = this.em.find(Employee.class, id);

		logger.debug("loadEmployee(): id[" + id + "]");
		return employee;
	}

	/**
	 * Stores department.
	 * 
	 * @param department
	 *            department
	 */
	public void storeDepartment(Department department) {

		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
		Department merged = this.em.merge(department);
		this.em.flush();
		Integer id = merged.getId();
		department.setId(id);

		logger.debug("storeDepartment(): id[" + id + "]");
	}

	/**
	 * Stores employee.
	 * 
	 * @param employee
	 *            employee
	 */
	public void storeEmployee(Employee employee) {

		// Consider returning the persistent object here, for exposing
		// a newly assigned id using any persistence provider...
		Employee merged = this.em.merge(employee);
		this.em.flush();
		Integer id = merged.getId();
		employee.setId(id);

		logger.debug("storeEmployee(): id[" + id + "]");
	}

	/**
	 * Deletes department.
	 * 
	 * @param id
	 *            id
	 */
	public void deleteDepartment(int id) throws DataAccessException {

		Department department = this.em.find(Department.class, id);
		this.em.remove(department);

		logger.debug("deleteDepartment(): id[" + id + "]");
	}

	/**
	 * Deletes employee.
	 * 
	 * @param id
	 *            id
	 */
	public void deleteEmployee(int id) throws DataAccessException {

		Employee employee = this.em.find(Employee.class, id);
		Department department = employee.getDepartment();
		department.removeEmployee(employee);
		this.em.remove(employee);

		logger.debug("deleteEmployee(): id[" + id + "]");
	}

}
