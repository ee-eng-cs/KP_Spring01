package kp.company.jpa;

/**
 * <p>
 * Tests for the DAO variant based on the shared EntityManager approach, using
 * Hibernate EntityManager for testing instead of the reference implementation.
 * </p>
 * <p>
 * Specifically tests usage of an <code>orm.xml</code> file, loaded by the
 * persistence provider through the Spring-provided persistence unit root URL.
 * </p>
 */
public class HibernateEntityManagerCompanyTests extends EntityManagerCompanyTests {

	@Override
	protected String[] getConfigPaths() {
		return new String[] {
			"applicationContext-jpaCommon.xml",
			"applicationContext-hibernateAdapter.xml",
			"applicationContext-entityManager.xml"
		};
	}

}
