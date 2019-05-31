package kp.company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * JUnit test for the {@link Department} class.
 */
public class DepartmentTests {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Tests department with employees.
	 */
	@Test
	public void testHasEmployee() {

		Department department = new Department();
		Employee employee = new Employee();
		employee.setFirstName("F-N-a-m-e-99");
		employee.setLastName("L-N-a-m-e-99");

		assertNull(department.getEmployee("f-n-a-m-e-99", "l-n-a-m-e-99"));
		assertNull(department.getEmployee("F-N-a-m-e-99", "L-N-a-m-e-99"));

		department.addEmployee(employee);
		assertEquals(employee, department.getEmployee("f-n-a-m-e-99",
				"l-n-a-m-e-99"));
		assertEquals(employee, department.getEmployee("F-N-a-m-e-99",
				"L-N-a-m-e-99"));

		logger.info("testHasEmployee()");
	}
}
