package kp.company.jpa;

/**
 * <p>
 * Tests for the DAO variant based on the shared EntityManager approach. Uses
 * TopLink Essentials (the reference implementation) for testing.
 * </p>
 * <p>
 * Specifically tests usage of an <code>orm.xml</code> file, loaded by the
 * persistence provider through the Spring-provided persistence unit root URL.
 * </p>
 */
public class EntityManagerCompanyTests extends AbstractJpaCompanyTests {

	@Override
	protected String[] getConfigPaths() {
		return new String[] {
			"applicationContext-jpaCommon.xml",
			"applicationContext-toplinkAdapter.xml",
			"applicationContext-entityManager.xml"
		};
	}

}
