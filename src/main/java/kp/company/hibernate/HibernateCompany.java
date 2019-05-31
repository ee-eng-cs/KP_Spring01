package kp.company.hibernate;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kp.company.Company;
import kp.company.Department;
import kp.company.Employee;
import kp.company.Title;

/**
 * Hibernate implementation of the Company interface.
 * 
 * <p>
 * The mappings are defined in "kp_spring01.hbm.xml", located in the root of the
 * class path.
 * 
 * <p>
 * Note that transactions are declared with annotations and that some methods
 * contain "readOnly = true" which is an optimization that is particularly
 * valuable when using Hibernate (to suppress unnecessary flush attempts for
 * read-only operations).
 */
@Repository
@Transactional
public class HibernateCompany implements Company {

	private final Log logger = LogFactory.getLog(getClass());

	private SessionFactory sessionFactory;

	/**
	 * Constructor (autowired by Spring's dependency injection facilities).
	 * 
	 * @param sessionFactory the session factory
	 */
	@Autowired
	public HibernateCompany(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;

		logger.debug("HibernateCompany():");
	}

	/**
	 * Gets titles.
	 * 
	 * @return resultList list of titles
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Title> getTitles() {

		String hql = "from Title title order by title.name";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Collection<Title> resultList = query.list();

		logger.debug("getTitles():");
		return resultList;
	}

	/**
	 * Finds departments.
	 * 
	 * @param name name
	 * @return resultList list of departments
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<Department> findDepartments(String name) {

		String hql = "from Department department where department.name like :name";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("name", name + "%");
		Collection<Department> resultList = query.list();

		logger.debug("findDepartments(): name[" + name + "]");
		return resultList;
	}

	/**
	 * Loads department.
	 * 
	 * @param id id
	 * @return department department
	 */
	@Transactional(readOnly = true)
	public Department loadDepartment(int id) {

		Department department = (Department) sessionFactory.getCurrentSession().load(Department.class, id);

		logger.debug("loadDepartment(): id[" + id + "]");
		return department;
	}

	/**
	 * Loads employee.
	 * 
	 * @param id id
	 * @return employee employee
	 */
	@Transactional(readOnly = true)
	public Employee loadEmployee(int id) {

		Employee employee = (Employee) sessionFactory.getCurrentSession().load(Employee.class, id);

		logger.debug("loadEmployee(): id[" + id + "]");
		return employee;
	}

	/**
	 * Stores department.
	 * 
	 * @param department department
	 */
	public void storeDepartment(Department department) {
		/*
		 * Note: Hibernate3's merge operation does not reassociate the object with the
		 * current Hibernate Session. Instead, it will always copy the state over to a
		 * registered representation of the entity. In case of a new entity, it will
		 * register a copy as well, but will not update the id of the passed-in object.
		 * To still update the ids of the original objects too, we need to register
		 * Spring's IdTransferringMergeEventListener on our SessionFactory.
		 */
		sessionFactory.getCurrentSession().merge(department);

		logger.debug("storeDepartment(): id[" + department.getId() + "]");
	}

	/**
	 * Stores employee.
	 * 
	 * @param employee employee
	 */
	public void storeEmployee(Employee employee) {

		sessionFactory.getCurrentSession().merge(employee);

		logger.debug("storeEmployee(): id[" + employee.getId() + "]");
	}

	/**
	 * Deletes department.
	 * 
	 * @param id id
	 */
	public void deleteDepartment(int id) throws DataAccessException {

		Session session = sessionFactory.getCurrentSession();
		Department department = (Department) session.load(Department.class, id);
		session.delete(department);

		logger.debug("deleteDepartment(): id[" + id + "]");
	}

	/**
	 * Deletes employee.
	 * 
	 * @param id id
	 */
	public void deleteEmployee(int id) throws DataAccessException {

		Session session = sessionFactory.getCurrentSession();
		Employee employee = (Employee) session.load(Employee.class, id);
		Department department = employee.getDepartment();
		department.removeEmployee(employee);
		session.delete(employee);

		logger.debug("deleteEmployee(): id[" + id + "]");
	}

}
