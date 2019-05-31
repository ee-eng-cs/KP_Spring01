<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="title">
  Department Information:
</div>
<div class="menu">
    &nbsp;<a href="/kp_spring01/">Home</a>
	&nbsp;/&nbsp;
	<a href="<spring:url value="/departments/search" htmlEscape="true" />">Find Departments</a>
</div>

<div class="fields_left">
  <p>Name:</p>
</div>
<div class="fields_right">
  <p>${department.name}</p>
</div>

<div style="clear:both;" /></div>

<spring:url value="{departmentId}/edit" var="editUrl">
  <spring:param name="departmentId" value="${department.id}" />
</spring:url>
<spring:url value="{departmentId}/employees/new" var="addUrl">
  <spring:param name="departmentId" value="${department.id}" />
</spring:url>
<c:set var="htmlId" value="${department.name}"/>

<div class="links" >
  <p><a href="${fn:escapeXml(editUrl)}" id="${fn:escapeXml(htmlId)}">
    Edit Department
  </a></p>
</div>
<div class="links" >
  <p><a href="${fn:escapeXml(addUrl)}" id="${fn:escapeXml(htmlId)}">
    Add Employee
  </a></p>
</div>

<div style="clear:both;" /></div>

<div class="subtitle">
  Employees
</div>

<c:forEach var="employee" items="${department.employees}">
 <spring:url value="{departmentId}/employees/{employeeId}/edit" var="employeeUrl">
  <spring:param name="departmentId" value="${department.id}"/>
  <spring:param name="employeeId" value="${employee.id}"/>
 </spring:url>
 <c:set var="htmlId" value="${employee.firstName} ${employee.lastName}"/>
 
 <div class="fields_left">
    <p>First Name:</p>
    <p>Last Name:</p>
    <p>Title:</p>
    <p style="text-align: center;">
      <a href="${fn:escapeXml(employeeUrl)}" id="${fn:escapeXml(htmlId)}">
      Edit Employee
    </a></p>
  </div>
  <div class="fields_right">
    <p>${employee.firstName}</td>
    <p>${employee.lastName}</td>
    <p>${employee.title.name}</td>
  </div>

  <div style="clear:both;" /></div>
</c:forEach>
  
<%@ include file="/WEB-INF/jsp/footer.jsp" %>