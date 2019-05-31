package kp.company.service;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import kp.company.Company;
import kp.company.Department;
import kp.company.Employee;

/**
 * Bean with transactions. Example of programmatically triggered rollback.
 * 
 */
@Service
@Transactional
public class CompanyService {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private Company company;

	/**
	 * Joins two <code>Department</code>s. Moves <code>Employee</code>s from
	 * second <code>Department</code> to first <code>Department</code>.
	 * 
	 * <P>
	 * Simulates workflow for joining departments process.
	 * <P>
	 * Workflow outcome depends on the number of departments before join:
	 * <UL>
	 * <LI>success for more than 2 departments - join departments and commit
	 * this transaction
	 * <LI>failure for exactly 2 departments - join departments and rollback
	 * this transaction
	 * <LI>failure for less than 2 departments - no join
	 * </UL>
	 */
	public void joinDepartments() {

		Collection<Department> departments = company.findDepartments("");
		int numberOfDepartments = departments.size();
		if (numberOfDepartments < 2) {
			// only one single department in the company
			logger.info("joinDepartments(): ONE DEPARTMENT ONLY");
			return;
		}

		Iterator<Department> iterator = departments.iterator();
		Department targetDepartment = iterator.next();
		Department sourceDepartment = iterator.next();
		for (Employee sourceEmployee : sourceDepartment.getEmployees()) {
			Employee targetEmployee = new Employee();
			targetEmployee.setFirstName(sourceEmployee.getFirstName());
			targetEmployee.setLastName(sourceEmployee.getLastName());
			targetEmployee.setTitle(sourceEmployee.getTitle());

			sourceDepartment.removeEmployee(sourceEmployee);
			company.deleteEmployee(sourceEmployee.getId());

			targetDepartment.addEmployee(targetEmployee);
			company.storeEmployee(targetEmployee);
		}
		company.deleteDepartment(sourceDepartment.getId());
		company.storeDepartment(targetDepartment);

		if (numberOfDepartments == 2) {
			// trigger rollback programmatically
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			logger.info("joinDepartments(): ROLLBACK");
		} else {
			logger.info("joinDepartments(): WORKFLOW OK");
		}
	}
}
