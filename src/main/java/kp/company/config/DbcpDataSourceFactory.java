package kp.company.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * A factory that creates a data source fit for use in a system environment.
 * Creates a DBCP (Database Connection Pool ) simple data source from the
 * provided connection properties.
 * 
 * Is a FactoryBean, for exposing the fully-initialized DataSource as a Spring
 * bean. See {@link #getObject()}.
 */
public class DbcpDataSourceFactory implements FactoryBean<DataSource>,
		DisposableBean {

	private final Log logger = LogFactory.getLog(getClass());

	/*
	 * Configurable properties.
	 */
	private String driverClassName;
	private String url;
	private String username;
	private String password;

	/*
	 * The object created by this factory.
	 */
	private BasicDataSource dataSource;

	/**
	 * Sets driver class name.
	 * 
	 * @param driverClassName
	 *            driver class name
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * Sets the data source connection URL
	 * 
	 * @param url
	 *            the data source connection URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Sets username.
	 * 
	 * @param username
	 *            the data source username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets password
	 * 
	 * @param password
	 *            the data source password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	// implementing FactoryBean

	/**
	 * This method is called by Spring to expose the DataSource as a bean.
	 * 
	 * @return data source
	 */
	public DataSource getObject() throws Exception {

		if (dataSource == null) {
			this.dataSource = createDataSource();
			checkDatabaseConnection();
		}
		return dataSource;
	}

	/**
	 * Gets object type.
	 * 
	 * @return object type
	 */
	public Class<DataSource> getObjectType() {
		return DataSource.class;
	}

	/**
	 * Checks if it is singleton.
	 * 
	 * @return check result
	 */
	public boolean isSingleton() {
		return true;
	}

	// implementing DisposableBean

	/**
	 * Destroys data source.
	 * 
	 */
	public void destroy() throws Exception {
		dataSource.close();
	}

	// internal helper methods
	/**
	 * Creates data source.
	 * 
	 * @return dataSource data source
	 */
	private BasicDataSource createDataSource() {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(this.driverClassName);
		dataSource.setUrl(this.url);
		dataSource.setUsername(this.username);
		dataSource.setPassword(this.password);

		logger.debug("createDataSource(): url[" + url + "] username["
				+ username + "]");
		return dataSource;
	}

	/**
	 * Checks database connection.
	 */
	private void checkDatabaseConnection() throws Exception {

		Connection connection = null;
		String errMsg = "Database connection problem, url[" + url
				+ "] username[" + username + "]";
		try {
			connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute("SELECT 1 FROM DUAL");
		} catch (SQLException ex) {
			throw new Exception(errMsg, ex);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ex) {
					throw new Exception(errMsg, ex);
				}
			}
		}
	}

}
