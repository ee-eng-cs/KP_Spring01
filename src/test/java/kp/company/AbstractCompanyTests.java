package kp.company;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import kp.company.util.EntityUtils;

/**
 * <p>
 * Base class for {@link Company} integration tests.
 * </p>
 * <p>
 * &quot;AbstractCompanyTests-context.xml&quot; declares a common
 * {@link javax.sql.DataSource DataSource}. Subclasses should specify additional
 * context locations which declare a
 * {@link org.springframework.transaction.PlatformTransactionManager
 * PlatformTransactionManager} and a concrete implementation of {@link Company}.
 * </p>
 * <p>
 * This class extends {@link AbstractTransactionalJUnit4SpringContextTests}, one
 * of the valuable testing support classes provided by the
 * <em>Spring TestContext Framework</em> found in the
 * <code>org.springframework.test.context</code> package. The annotation-driven
 * configuration used here represents best practice for integration tests with
 * Spring. Note, however, that AbstractTransactionalJUnit4SpringContextTests
 * serves only as a convenience for extension. For example, if you do not wish
 * for your test classes to be tied to a Spring-specific class hierarchy, you
 * may configure your tests with annotations such as
 * {@link ContextConfiguration @ContextConfiguration},
 * {@link org.springframework.test.context.TestExecutionListeners
 * @TestExecutionListeners},
 * {@link org.springframework.transaction.annotation.Transactional
 * @Transactional}, etc.
 * </p>
 * <p>
 * AbstractCompanyTests and its subclasses benefit from the following services
 * provided by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary
 * set up time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning
 * that we don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>company</code> instance variable,
 * which uses autowiring <em>by type</em>. As an alternative, we could annotate
 * <code>company</code> with {@link javax.annotation.Resource @Resource} to
 * achieve dependency injection <em>by name</em>.
 * <em>(see: {@link ContextConfiguration @ContextConfiguration},
 * {@link org.springframework.test.context.support.DependencyInjectionTestExecutionListener DependencyInjectionTestExecutionListener})</em>
 * </li>
 * <li><strong>Transaction management</strong>, meaning each test method is
 * executed in its own transaction, which is automatically rolled back by
 * default. Thus, even if tests insert or otherwise change database state, there
 * is no need for a teardown or cleanup script.
 * 
 * <em>(see: {@link org.springframework.test.context.transaction.TransactionConfiguration @TransactionConfiguration},
 * {@link org.springframework.transaction.annotation.Transactional @Transactional},
 * {@link org.springframework.test.context.transaction.TransactionalTestExecutionListener TransactionalTestExecutionListener})</em>
 * </li>
 * <li><strong>Useful inherited protected fields</strong>, such as a
 * {@link org.springframework.jdbc.core.simple.SimpleJdbcTemplate
 * SimpleJdbcTemplate} that can be used to verify database state after test
 * operations or to verify the results of queries performed by application code.
 * An {@link org.springframework.context.ApplicationContext ApplicationContext}
 * is also inherited and can be used for explicit bean lookup if necessary.
 * 
 * <em>(see: {@link org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests AbstractJUnit4SpringContextTests},
 * {@link AbstractTransactionalJUnit4SpringContextTests})</em></li>
 * </ul>
 * <p>
 * The Spring TestContext Framework and related unit and integration testing
 * support classes are shipped in <code>spring-test.jar</code>.
 * </p>
 */
@ContextConfiguration
public abstract class AbstractCompanyTests extends
		AbstractTransactionalJUnit4SpringContextTests {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	protected Company company;

	/**
	 * Tests getting titles.
	 */
	@Test
	public void getTitles() {

		Collection<Title> titles = this.company.getTitles();
		assertEquals("JDBC query must show the same number of employee types",
				super.countRowsInTable("TITLES"), titles.size());

		Title title = EntityUtils.getById(titles, Title.class, 1);
		assertEquals("Manager", title.getName());

		title = EntityUtils.getById(titles, Title.class, 3);
		assertEquals("Developer", title.getName());
		logger.info("getTitles():");
	}

	/**
	 * Tests finding departments.
	 */
	@Test
	public void findDepartments() {

		Collection<Department> departments = this.company
				.findDepartments("D-e-p-t");
		assertEquals(3, departments.size());

		departments = this.company.findDepartments("D-e-p-t-99");
		assertEquals(0, departments.size());

		logger.info("findDepartments():");
	}

	/**
	 * Tests loading departments.
	 */
	@Test
	public void loadDepartment() {

		Department department = this.company.loadDepartment(1);
		assertEquals("D-e-p-t-1", department.getName());

		department = this.company.loadDepartment(3);
		assertEquals("D-e-p-t-3", department.getName());

		// XXX: Add programmatic support for ending transactions with the
		// TestContext Framework.

		// Check lazy loading, by ending the transaction:
		// endTransaction();

		// Now Departments are "disconnected" from the data store.
		// We might need to touch this collection if we switched to lazy loading
		// in mapping files, but this test would pick this up.
		department.getEmployees();

		logger.info("loadDepartment():");
	}

	/**
	 * Tests inserting departments.
	 */
	@Test
	public void insertDepartment() {

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

		logger.info("insertDepartment():");
	}

	/**
	 * Tests updating departments.
	 */
	@Test
	public void updateDepartment() throws Exception {

		Department department = this.company.loadDepartment(1);
		String oldName = department.getName();
		department.setName(oldName + "X");
		this.company.storeDepartment(department);

		department = this.company.loadDepartment(1);
		assertEquals(oldName + "X", department.getName());

		logger.info("updateDepartment():");
	}

	/**
	 * Tests loading employee.
	 */
	@Test
	public void loadEmployee() {

		Employee employee = this.company.loadEmployee(1);
		assertEquals("L-N-a-m-e-11", employee.getLastName());
		assertEquals("D-e-p-t-1", employee.getDepartment().getName());

		employee = this.company.loadEmployee(9);
		assertEquals("L-N-a-m-e-33", employee.getLastName());
		assertEquals("D-e-p-t-3", employee.getDepartment().getName());

		logger.info("loadEmployee():");
	}

	/**
	 * Tests inserting employee.
	 */
	@Test
	public void insertEmployee() {

		Department department = this.company.loadDepartment(1);
		int found = department.getEmployees().size();
		Employee employee = new Employee();
		employee.setFirstName("F-N-a-m-e-99");
		employee.setLastName("L-N-a-m-e-99");
		Collection<Title> titles = this.company.getTitles();
		employee.setTitle(EntityUtils.getById(titles, Title.class, 1));
		department.addEmployee(employee);
		assertEquals(found + 1, department.getEmployees().size());

		// both storeEmployee and storeDepartment are necessary to cover all ORM
		// tools
		this.company.storeEmployee(employee);
		this.company.storeDepartment(department);
		// assertTrue(!employee.isNew()); -- NOT TRUE FOR TOPLINK (before
		// commit)
		department = this.company.loadDepartment(1);
		assertEquals(found + 1, department.getEmployees().size());

		logger.info("insertEmployee():");
	}

	/**
	 * Tests updating employee.
	 */
	@Test
	public void updateEmployee() throws Exception {

		Employee employee = this.company.loadEmployee(1);
		String oldName = employee.getLastName();
		employee.setLastName(oldName + "X");
		this.company.storeEmployee(employee);

		employee = this.company.loadEmployee(1);
		assertEquals(oldName + "X", employee.getLastName());

		logger.info("updateEmployee():");
	}

}
