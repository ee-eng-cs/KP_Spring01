package kp.company.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kp.company.Company;
import kp.company.Department;
import kp.company.Employee;
import kp.company.Title;
import kp.company.util.EntityUtils;

/**
 * A simple JDBC-based implementation of the {@link Company} interface.
 * 
 * <p>
 * This class uses Java 5 language features and the {@link SimpleJdbcTemplate}
 * plus {@link SimpleJdbcInsert}. It also takes advantage of classes like
 * {@link BeanPropertySqlParameterSource} and {@link RowMapper} which provide
 * automatic mapping between JavaBean properties and JDBC parameters or query
 * results.
 * 
 * <p>
 * SimpleJdbcCompany is a rewrite of the AbstractJdbcCompany which was the base
 * class for JDBC implementations of the Company interface for Spring 2.0.
 */
@Service
public class SimpleJdbcCompany implements Company {

	private final Log logger = LogFactory.getLog(getClass());

	private SimpleJdbcTemplate simpleJdbcTemplate;
	private SimpleJdbcInsert insertDepartment;
	private SimpleJdbcInsert insertEmployee;

	/**
	 * Initializes data source.
	 * Autowired by Spring's dependency injection facilities.
	 * 
	 * @param dataSource
	 *            data source
	 */
	@Autowired
	public void init(DataSource dataSource) {

		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);

		this.insertDepartment = new SimpleJdbcInsert(dataSource).withTableName(
				"departments").usingGeneratedKeyColumns("id");

		this.insertEmployee = new SimpleJdbcInsert(dataSource).withTableName(
				"employees").usingGeneratedKeyColumns("id");

		logger.debug("init():");
	}

	// START of Company implementation section *******************************
	/**
	 * Gets titles.
	 * 
	 * @return resultList list of titles
	 */
	@Transactional(readOnly = true)
	public Collection<Title> getTitles() throws DataAccessException {

		String sql = "SELECT id, name FROM titles ORDER BY name";
		Collection<Title> resultList = this.simpleJdbcTemplate.query(sql,
				new TitleRowMapper());

		logger.debug("getTitles():");
		return resultList;
	}

	/**
	 * Loads {@link Department departments} from the data store by name,
	 * returning all departments whose last name <i>starts</i> with the given
	 * name; also loads the {@link Employee employees} for the corresponding
	 * departments, if not already loaded.
	 * 
	 * @param name
	 *            name
	 * @return resultList list of departments
	 */
	@Transactional(readOnly = true)
	public Collection<Department> findDepartments(String name)
			throws DataAccessException {

		String sql = "SELECT id, name FROM departments WHERE name like ?";
		Collection<Department> resultList = this.simpleJdbcTemplate.query(sql,
				new DepartmentRowMapper(), name + "%");
		loadEmployeesForDepartmentsList(resultList);

		logger.debug("findDepartments(): name[" + name + "]");
		return resultList;
	}

	/**
	 * Loads the {@link Department department} with the supplied <code>id</code>
	 * ; also loads the {@link Employee employees} for the corresponding
	 * department, if not already loaded.
	 * 
	 * @param id
	 *            department id
	 * @return department
	 */
	@Transactional(readOnly = true)
	public Department loadDepartment(int id) throws DataAccessException {

		String sql = "SELECT id, name FROM departments WHERE id=?";
		Department department;
		try {
			department = this.simpleJdbcTemplate.queryForObject(sql,
					new DepartmentRowMapper(), id);
		} catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Department.class,
					new Integer(id));
		}
		Collection<Title> titles = getTitles();
		loadEmployeesForDepartment(department, titles);

		logger.debug("loadDepartment(): id[" + id + "]");
		return department;
	}

	/**
	 * Loads the {@link Employee employee}.
	 * 
	 * @param id
	 *            employee id
	 * @return employee
	 */
	@Transactional(readOnly = true)
	public Employee loadEmployee(int id) throws DataAccessException {

		String sql = "SELECT id, first_name, last_name, department_id, title_id "
				+ "FROM employees WHERE id=?";
		JdbcEmployee jdbcEmployee;
		try {
			jdbcEmployee = this.simpleJdbcTemplate.queryForObject(sql,
					new JdbcEmployeeRowMapper(), id);
		} catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Employee.class,
					new Integer(id));
		}
		Department department = loadDepartment(jdbcEmployee.getDepartmentId());
		Employee employee = department.getEmployee(jdbcEmployee.getFirstName(),
				jdbcEmployee.getLastName());
		department.addEmployee(jdbcEmployee);
		jdbcEmployee.setTitle(employee.getTitle());

		logger.debug("loadEmployee(): id[" + id + "]");
		return jdbcEmployee;
	}

	/**
	 * Stores the {@link Department department}.
	 * 
	 * @param department
	 *            department
	 */
	@Transactional
	public void storeDepartment(Department department)
			throws DataAccessException {

		if (department.isNew()) {
			Number newKey = this.insertDepartment
					.executeAndReturnKey(new BeanPropertySqlParameterSource(
							department));
			department.setId(newKey.intValue());
		} else {
			String sql = "UPDATE departments SET name=:name WHERE id=:id";
			this.simpleJdbcTemplate.update(sql,
					new BeanPropertySqlParameterSource(department));
		}

		logger.debug("storeDepartment(): id[" + department.getId() + "]");
	}

	/**
	 * Stores employee.
	 * 
	 * @param employee
	 *            employee
	 */
	@Transactional
	public void storeEmployee(Employee employee) throws DataAccessException {

		if (employee.isNew()) {
			Number newKey = this.insertEmployee
					.executeAndReturnKey(createEmployeeParameterSource(employee));
			employee.setId(newKey.intValue());
		} else {
			String sql = "UPDATE employees "
					+ "SET first_name=:first_name, last_name=:last_name, "
					+ "department_id=:department_id, title_id=:title_id "
					+ "WHERE id=:id";

			this.simpleJdbcTemplate.update(sql,
					createEmployeeParameterSource(employee));
		}

		logger.debug("storeEmployee(): id[" + employee.getId() + "]");
	}

	/**
	 * Deletes department and its employees.
	 * 
	 * @param id
	 *            department id
	 */
	public void deleteDepartment(int id) throws DataAccessException {

		String sql = "DELETE FROM employees WHERE department_id=?";
		this.simpleJdbcTemplate.update(sql, id);
		sql = "DELETE FROM departments WHERE id=?";
		this.simpleJdbcTemplate.update(sql, id);

		logger.debug("deleteDepartment(): id[" + id + "]");
	}

	/**
	 * Deletes employee.
	 * 
	 * @param id
	 *            employee id
	 */
	public void deleteEmployee(int id) throws DataAccessException {

		String sql = "DELETE FROM employees WHERE id=?";
		this.simpleJdbcTemplate.update(sql, id);

		logger.debug("deleteEmployee(): id[" + id + "]");
	}

	// END of Company implementation section
	// ************************************

	/**
	 * Creates a {@link MapSqlParameterSource} based on data values from the
	 * supplied {@link Employee} instance.
	 * 
	 * @param employee
	 *            employee
	 * @return mapSql map SQL
	 */
	private MapSqlParameterSource createEmployeeParameterSource(
			Employee employee) {

		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("id", employee.getId());
		mapSql.addValue("first_name", employee.getFirstName());
		mapSql.addValue("last_name", employee.getLastName());
		mapSql.addValue("department_id", employee.getDepartment().getId());
		mapSql.addValue("title_id", employee.getTitle().getId());

		return mapSql;

	}

	/**
	 * Loads the {@link Employee} data for the supplied {@link List} of
	 * {@link Department Departments}.
	 * 
	 * @param departments
	 *            the list of departments for whom the employee data should be
	 *            loaded
	 * @see #loadEmployeesForDepartment(Department, Collection<Title>)
	 */
	private void loadEmployeesForDepartmentsList(
			Collection<Department> departments) {

		Collection<Title> titles = getTitles();
		for (Department department : departments) {
			loadEmployeesForDepartment(department, titles);
		}
	}

	/**
	 * Loads the {@link Employee} data for the supplied {@link Department}.
	 * 
	 * @param department
	 *            department
	 */
	private void loadEmployeesForDepartment(final Department department,
			Collection<Title> titles) {

		String sql = "SELECT id, first_name, last_name, department_id, title_id "
				+ "FROM employees WHERE department_id=?";
		final List<JdbcEmployee> employees = this.simpleJdbcTemplate.query(sql,
				new JdbcEmployeeRowMapper(), department.getId().intValue());

		for (JdbcEmployee employee : employees) {
			department.addEmployee(employee);
			int titleId = employee.getTitleId();
			Title title = EntityUtils.getById(titles, Title.class, titleId);
			employee.setTitle(title);
		}
	}

	/**
	 * TitleRowMapper class.
	 * <p>
	 * {@link RowMapper} implementation mapping data from a {@link ResultSet} to
	 * the corresponding properties of the {@link Title} class.
	 */
	private static final class TitleRowMapper implements RowMapper<Title> {
		/**
		 * Maps row.
		 * 
		 * @param rs
		 *            result set
		 * @param rownum
		 *            row number
		 * @return title
		 */
		public Title mapRow(ResultSet rs, int rowNum) throws SQLException {

			Title title = new Title();
			title.setId(rs.getInt("id"));
			title.setName(rs.getString("name"));
			return title;
		}
	};

	/**
	 * DepartmentRowMapper class.
	 * <p>
	 * {@link RowMapper} implementation mapping data from a {@link ResultSet} to
	 * the corresponding properties of the {@link Department} class.
	 */
	private static final class DepartmentRowMapper implements
			RowMapper<Department> {
		/**
		 * Maps row.
		 * 
		 * @param rs
		 *            result set
		 * @param rownum
		 *            row number
		 * @return department
		 */
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {

			Department department = new Department();
			department.setId(rs.getInt("id"));
			department.setName(rs.getString("name"));
			return department;
		}
	};

	/**
	 * JdbcEmployeeRowMapper class.
	 * <p>
	 * {@link RowMapper} implementation mapping data from a {@link ResultSet} to
	 * the corresponding properties of the {@link JdbcEmployee} class.
	 */
	private static final class JdbcEmployeeRowMapper implements
			RowMapper<JdbcEmployee> {

		/**
		 * Maps row.
		 * 
		 * @param rs
		 *            result set
		 * @param rownum
		 *            row number
		 * @return employee
		 */
		public JdbcEmployee mapRow(ResultSet rs, int rownum)
				throws SQLException {

			JdbcEmployee employee = new JdbcEmployee();
			employee.setId(rs.getInt("id"));
			employee.setFirstName(rs.getString("first_name"));
			employee.setLastName(rs.getString("last_name"));
			employee.setDepartmentId(rs.getInt("department_id"));
			employee.setTitleId(rs.getInt("title_id"));
			return employee;
		}
	}
}