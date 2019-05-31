<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="title">
  Departments:
</div>
<div class="menu">
    &nbsp;<a href="/kp_spring01/">Home</a>
	&nbsp;/&nbsp;
	<a href="<spring:url value="/departments/search" htmlEscape="true" />">Find Departments</a>
</div>

<div class="box_header_left">
  Name
</div>
<div class="box_header_right">
  Employees
</div>

<div style="clear:both;" ></div>

<c:forEach var="department" items="${departments}">
  <spring:url value="departments/{departmentId}" var="departmentUrl">
    <spring:param name="departmentId" value="${department.id}"/>
  </spring:url>
  <c:set var="htmlId" value="${department.name}"/>
  
  <div class="box_left">
    <a href="${fn:escapeXml(departmentUrl)}" id="${fn:escapeXml(htmlId)}">
      ${department.name}
    </a>
  </div>
  <div class="box_right">
    <c:forEach var="employee" items="${department.employees}">
      ${employee.lastName}<br/>
    </c:forEach>
  </div>

  <div style="clear:both;" ></div>
</c:forEach>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>