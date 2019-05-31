package kp.company;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import kp.company.hibernate.HibernateCompanyTests;
import kp.company.jdbc.SimpleJdbcCompanyTests;
import kp.company.jpa.EntityManagerCompanyTests;
import kp.company.jpa.HibernateEntityManagerCompanyTests;
import kp.company.jpa.OpenJpaEntityManagerCompanyTests;

/**
 * JUnit 4 based test suite for all kp_spring01 tests.
 */
@RunWith(Suite.class)
@SuiteClasses({
		DepartmentTests.class,
		SimpleJdbcCompanyTests.class,
		HibernateCompanyTests.class,
		EntityManagerCompanyTests.class,
		HibernateEntityManagerCompanyTests.class,
		OpenJpaEntityManagerCompanyTests.class
})
public class CompanySuiteTests {

}
