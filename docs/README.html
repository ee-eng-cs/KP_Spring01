<!DOCTYPE html>
<HTML>
<HEAD>
	<META charset="UTF-8">
</HEAD>
<BODY>
<H2>KP_Spring01 README</H2>
<HR/>
<H2>Use Cases</H2>
<UL>
<LI/><B>Find departments</B>: view a list of company's departments and its employees
<LI/><B>Edit department</B>: view the information pertaining to a department
<LI/><B>Update department</B>: update the information pertaining to a department
<LI/><B>Add department</B>: add a new department to the company
<LI/><B>Delete department</B>: delete existing department (with its employees) from the company
<LI/><B>Edit employee</B>: view the information pertaining to an employee
<LI/><B>Update employee</B>: update the information pertaining to an employee
<LI/><B>Add employee</B>: add a new employee to the department
<LI/><B>Delete employee</B>: delete employee from the department
<LI/><B>Join departments</B>: join two departments moving employees from the second department to the first department
</UL>
<H2>Use Case Diagram</H2>
<IMG src="images/uml_use_case_diagram.jpg"/><BR/>
<HR/>
<H2>Four-tier Layered Architecture</H2>
<UL>
<LI/>Presentation Layer
<LI/>Business Logic Layer
<LI/>Persistence Layer
<LI/>Database Layer
</UL>
<HR/>

<H2>Application Design & Implementation</H2>
<H3>Server Technology</H3>
<P>Application should be usable with any Java EE web application container that is compatible with the Servlet 2.4 and JSP 2.0 specifications.
It was tested with <B>Apache Tomcat 7</B>.
The view technologies that are to be used for rendering the application are Java Server Pages (JSP) along with the Java Standard Tag Library (JSTL). 

<H3>Database Technology</H3>
<P>Application uses a relational database for data storage. It was tested with database <B>MySQL 5.1</B>.

<H3>Logging</H3>
<P>Application uses Apache Log4J loggers.


<H3>Business Layer</H3>
<P>The Business Layer consists of a number of basic JavaBean classes representing the application domain objects (classes in package <B>kp.company</B>)
and associated validation objects that are used by the Presentation Layer (classes in package <B>kp.company.validation</B>).
The validation objects used are all implementations of the <B>org.springframework.validation.Validator</B> interface. 

<H3>Business / Persistence Layer</H3>
<P>There is no separation of the primary Business and Persistence Layer API's.
The Persistence Layer is configured to use MySQL database with any one of the following
data access technologies aided by infrastructure provided by Spring: 
<UL>
	<LI/>JDBC 
	<LI/>Hibernate 3 
	<LI/>Java Persistence API 
</UL>
<P>The high-level business/persistence API is the <B>kp.company.Company</B> interface.
Each persistence strategy in KP_Spring01 is a different implementation of the Company interface:
<UL>
	<LI/>The JDBC implementation is <B>kp.company.jdbc.SimpleJdbcCompany</B>
	<LI/>The Hibernate 3 implementation is <B>kp.company.hibernate.HibernateCompany</B>.
	The Hibernate configuration is provided by the file <B>kp_spring01.hbm.xml</B>.
	<LI/>The JPA implementation is <B>kp.company.jpa.EntityManagerCompany</B>
	The JPA configuration is provided by <B>orm.xml</B> and <B>persistence.xml</B>. 
</UL>
<P>All Company methods are run in a transactional context. 

<H3>ApplicationContext</H3>
<P>A Spring <B>org.springframework.context.ApplicationContext</B> object provides a map of user-defined JavaBeans that
specify either a singleton object or the initial construction of prototype instances.</BR>
These beans constitute the Business/Persistence Layer of KP_Spring01.</BR>
<P>The following beans are defined in all 3 versions (1 per access strategy) of the KP_Spring01 war/WEB-INF/applicationContext-*.xml file: 
<UL>
	<LI/>PropertyPlaceholderConfigurer (for JDBC-related settings from jdbc.properties file). 
	<LI/>dataSource
	<LI/>transactionManager
	<LI/>company (defines the implementation of the Company interface)
</UL>

<H3>Presentation Layer</H3>
The web application is configured via the following files: 
<UL>
	<LI/>WEB-INF/web.xml
	<LI/>WEB-INF/kp_spring01-servlet.xml (configures the dispatcher servlet and the other controllers and forms that it uses)
	<LI/>WEB-INF/classes/messages*.properties
</UL>
<P>The beans defined in <B>kp_spring01-servlet.xml</B> file reference the Business/Persistence Layer beans defined in <B>applicationContext-*.xml</B>. 

<P>Controllers
<UL>
	<LI/>annotation-driven, POJO MultiActionController controllers
	<UL>
		<LI/><B>CompanyController</B>
	</UL>
	<LI/>annotation-driven, POJO Form controllers
	<UL>
		<LI/><B>FindDepartmentsForm</B>
		<LI/><B>AddDepartmentForm</B>
		<LI/><B>EditDepartmentForm</B>
		<LI/><B>AddEmployeeForm</B>
		<LI/><B>EditEmployeeForm</B>
	</UL>
</UL>

<P>Model Attributes
<UL>
	<LI/>departments
	<LI/>department
	<LI/>employee
	<LI/>titles
</UL>

<P>Logical Views
<UL>
	<LI/><B>welcome.jsp</B>  is the "home" screen
	<LI/><B>departments/search.jsp</B> is used to find <B>departments</B> by name
	<LI/><B>departments/list.jsp</B> is used to display a list of found <B>departments</B>
	<LI/><B>departments/show.jsp</B> is used to display <B>department</B> details
	<LI/><B>departments/form.jsp</B> is used to create, update or delete <B>department</B>
	<LI/><B>employees/form.jsp</B> is used to create, update or delete <B>employee</B>
</UL>
<P>Mapped URL paths:
<UL>
	<LI/>path <B>[/]</B> mapped onto handler <B>CompanyController</B>
	<LI/>path <B>[/departments/join]</B> mapped onto handler <B>CompanyController</B>
	<LI/>path <B>[/departments/new]</B> mapped onto handler <B>AddDepartmentForm</B>
	<LI/>path <B>[/departments]</B> mapped onto handler <B>FindDepartmentsForm</B>
	<LI/>path <B>[/departments/search]</B> mapped onto handler <B>FindDepartmentsForm</B>
	<LI/>path <B>[/departments/{departmentId}]</B> mapped onto handler <B>CompanyController</B>
	<LI/>path <B>[/departments/{departmentId}/edit]</B> mapped onto handler <B>EditDepartmentForm</B>
	<LI/>path <B>[/departments/{departmentId}/employees/new]</B> mapped onto handler <B>AddEmployeeForm</B>
	<LI/>path <B>[/departments/*/employees/{employeeId}/edit]</B> mapped onto handler <B>EditEmployeeForm</B>
</UL>

<HR/>

<H2>Model-View-Controller Diagram</H2>
<IMG src="images/Spring_Web_MVC.jpg"/><BR/>
<HR/>

<H2>Class Diagram</H2>
<IMG src="images/uml_class_diagram.jpg"/><BR/>
<HR/>


<H2>Database Model Diagram</H2>
<IMG src="images/database_model.jpg"/><BR/>
<HR/>

<H2>State Machine Diagram</H2>
<IMG src="images/uml_state_machine_diagram.jpg"/><BR/>
<HR/>

<H2>Testing</H2>
<H3>Canoo Webtest</H3>
<P><A HREF="..\webtest\results\index.html">Webtest Results</A>

<H3>JUnit 4 based Test Cases</H3>
<OL>
	<LI/><B>DepartmentTests</B> (these tests do not use database connection)
	<LI/><B>AbstractCompanyTests</B> for testing:
	<UL>
		<LI/>Simple JDBC implementation of the <B>Company</B> interface: <B>SimpleJdbcCompanyTests</B>
		<LI/>Hibernate   implementation of the <B>Company</B> interface: <B>HibernateCompanyTests</B>
	</UL>
	<LI/><B>AbstractJpaCompanyTests</B> for testing<BR>
	<UL>
		<LI/>JPA implementation of the <B>Company</B> interface:
		<UL>
			<LI/>with <B>TopLink</B>   implementation of the EntityManager: <B>EntityManagerCompanyTests</B>
			<LI/>with <B>Hibernate</B> implementation of the EntityManager: <B>HibernateEntityManagerCompanyTests</B>
			<LI/>with <B>OpenJpa</B>   implementation of the EntityManager: <B>OpenJpaEntityManagerCompanyTests</B>
		</UL>
	</UL>
</OL>
<P>
Tests from items 1 and 2 above use Spring TestContext Framework.</BR>
Tests from item 3 use deprecated package with the legacy JUnit 3.8 classes.</BR>
<P>
<IMG src="images/test_jpa.jpg"/><BR/>

<HR/>
<A href="apidocs/index.html?overview-summary.html" >API Specifications</A>
(API was not commited to <B>GitHub</B>; this link should be active after local build with <I>'mvn javadoc'</I>)
<HR/>
</BODY>
</HTML>