package kp.company.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.jpa.AbstractJpaTests;

import kp.company.Company;
import kp.company.Department;
import kp.company.Employee;
import kp.company.Title;
import kp.company.util.EntityUtils;

/**
 * <p>
 * This class extends {@link AbstractJpaTests}, one of the valuable test
 * superclasses provided in the <code>org.springframework.test</code> package.
 * This represents best practice for integration tests with Spring for JPA based
 * tests which require <em>shadow class loading</em>. For all other types of
 * integration testing, the <em>Spring TestContext Framework</em> is preferred.
 * </p>
 * <p>
 * AbstractJpaTests and its superclasses provide the following services:
 * <ul>
 * <li>Injects test dependencies, meaning that we don't need to perform
 * application context lookups. See the setCompany() method. Injection uses
 * autowiring by type.</li>
 * <li>Executes each test method in its own transaction, which is automatically
 * rolled back by default. This means that even if tests insert or otherwise
 * change database state, there is no need for a teardown or cleanup script.</li>
 * <li>Provides useful inherited protected fields, such as a
 * {@link SimpleJdbcTemplate} that can be used to verify database state after
 * test operations, or verify the results of queries performed by application
 * code. Alternatively, you can use protected convenience methods such as
 * {@link #countRowsInTable(String)}, {@link #deleteFromTables(String[])}, etc.
 * An ApplicationContext is also inherited, and can be used for explicit lookup
 * if necessary.</li>
 * </ul>
 * <p>
 * {@link AbstractJpaTests} and related classes are shipped in
 * <code>spring-test.jar</code>.
 * </p>
 */
@SuppressWarnings("deprecation")
public abstract class AbstractJpaCompanyTests extends AbstractJpaTests {

	private final Log logger = LogFactory.getLog(getClass());

	protected Company company;

	/**
	 * This method is provided to set the Company instance being tested by the
	 * Dependency Injection injection behavior of the superclass from the
	 * <code>org.springframework.test</code> package.
	 * 
	 * @param company
	 *            company to test
	 */
	public void setCompany(Company company) {

		this.company = company;
	}

	@ExpectedException(IllegalArgumentException.class)
	public void testBogusJpql() {

		this.sharedEntityManager
				.createQuery("SELECT RUBBISH FROM RUBBISH HEAP")
				.executeUpdate();

		logger.info("testBogusJpql():");
	}

	/**
	 * Tests application managed.
	 */
	public void testApplicationManaged() {

		EntityManager appManaged = this.entityManagerFactory
				.createEntityManager();
		appManaged.joinTransaction();

		logger.info("testApplicationManaged():");
	}

	/**
	 * Tests getting titles.
	 */
	public void testGetTitles() {

		Collection<Title> titles = this.company.getTitles();
		assertEquals("JDBC query must show the same number of employee types",
				super.countRowsInTable("TITLES"), titles.size());

		Title title = EntityUtils.getById(titles, Title.class, 1);
		assertEquals("Manager", title.getName());

		title = EntityUtils.getById(titles, Title.class, 3);
		assertEquals("Developer", title.getName());

		logger.info("testGetTitles():");
	}

	/**
	 * Tests finding departments.
	 */
	public void testFindDepartments() {

		Collection<Department> departments = this.company
				.findDepartments("D-e-p-t");
		assertEquals(3, departments.size());

		departments = this.company.findDepartments("D-e-p-t-99");
		assertEquals(0, departments.size());

		logger.info("testFindDepartments():");
	}

	/**
	 * Tests loading departments.
	 */
	public void testLoadDepartment() {

		Department department = this.company.loadDepartment(1);
		assertEquals("D-e-p-t-1", department.getName());

		department = this.company.loadDepartment(3);
		assertEquals("D-e-p-t-3", department.getName());

		// Check lazy loading, by ending the transaction
		endTransaction();

		// Now Departments are "disconnected" from the data store.
		// We might need to touch this collection if we switched to lazy loading
		// in mapping files, but this test would pick this up.
		department.getEmployees();

		logger.info("testLoadDepartment():");
	}

	/**
	 * Tests inserting departments.
	 */
	public void testInsertDepartment() {

		Collection<Department> departments = this.company
				.findDepartments("D-e-p-t");
		int found = departments.size();

		Department department = new Department();
		department.setName("D-e-p-t-99");
		this.company.storeDepartment(department);
		// assertTrue(!department.isNew()); -- NOT TRUE FOR TOPLINK (before
		// commit)

		departments = this.company.findDepartments("D-e-p-t");
		assertEquals(
				"Verifying number of departments after inserting a new one.",
				found + 1, departments.size());

		logger.info("testInsertDepartment():");
	}

	/**
	 * Tests updating departments.
	 */
	public void testUpdateDepartment() throws Exception {

		Department department = this.company.loadDepartment(1);
		String oldName = department.getName();
		department.setName(oldName + "X");
		this.company.storeDepartment(department);

		department = this.company.loadDepartment(1);
		assertEquals(oldName + "X", department.getName());

		logger.info("testUpdateDepartment():");
	}

	/**
	 * Tests loading employee.
	 */
	public void testLoadEmployee() {

		Employee employee = this.company.loadEmployee(1);
		assertEquals("L-N-a-m-e-11", employee.getLastName());
		assertEquals("D-e-p-t-1", employee.getDepartment().getName());

		employee = this.company.loadEmployee(9);
		assertEquals("L-N-a-m-e-33", employee.getLastName());
		assertEquals("D-e-p-t-3", employee.getDepartment().getName());

		logger.info("testLoadEmployee():");
	}

	/**
	 * Tests inserting employee.
	 */
	public void testInsertEmployee() {

		Department department = this.company.loadDepartment(1);
		int found = department.getEmployees().size();
		Employee employee = new Employee();
		employee.setFirstName("F-N-a-m-e-99");
		employee.setLastName("L-N-a-m-e-99");
		Collection<Title> titles = this.company.getTitles();
		employee.setTitle(EntityUtils.getById(titles, Title.class, 1));
		department.addEmployee(employee);
		assertEquals(found + 1, department.getEmployees().size());

		this.company.storeDepartment(department);
		// assertTrue(!employee.isNew()); -- NOT TRUE FOR TOPLINK (before
		// commit)
		department = this.company.loadDepartment(1);
		assertEquals(found + 1, department.getEmployees().size());

		logger.info("testInsertEmployee():");
	}

	/**
	 * Tests updating employee.
	 */
	public void testUpdateEmployee() throws Exception {

		Employee employee = this.company.loadEmployee(1);
		String oldName = employee.getLastName();
		employee.setLastName(oldName + "X");
		this.company.storeEmployee(employee);

		employee = this.company.loadEmployee(1);
		assertEquals(oldName + "X", employee.getLastName());

		logger.info("testUpdateEmployee():");
	}

}