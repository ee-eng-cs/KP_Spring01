<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<c:choose>
	<c:when test="${employee.id == null}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<div class="title">
  <c:if test="${employee.id == null}">New </c:if>Employee:
</div>
<div class="menu">
    &nbsp;<a href="/kp_spring01/">Home</a>
	&nbsp;/&nbsp;
	<a href="<spring:url value="/departments/search" htmlEscape="true" />">Find Departments</a>
	&nbsp;/&nbsp;
    <spring:url value="/departments" var="departmentUrl">
      <spring:param name="name" value="${employee.department.name}"/>
    </spring:url>
    <a href="${fn:escapeXml(departmentUrl)}">Department Information</a>
</div>

<form:form modelAttribute="employee" method="${method}">
  <div class="fields_left">
    <p>Department:</p>
    <p><form:errors path="firstName" cssClass="errors"/> First Name:</p>
    <p><form:errors path="lastName" cssClass="errors"/> Last Name:</p>
    <p>Title:</p>
  </div>
  <div class="fields_right">
    <p>${employee.department.name}</p>
    <p><form:input path="firstName" size="25" maxlength="30"/></p>
    <p><form:input path="lastName" size="25" maxlength="30"/></p>
    <p><form:select path="title" items="${titles}"/></p>
  </div>

  <div style="clear:both;" /></div>

  <div class="buttons" >
    <c:choose>
      <c:when test="${employee.id == null}">
        <p class="submit"><input type="submit" value="Add Employee"/></p>
      </c:when>
      <c:otherwise>
        <p class="submit"><input type="submit" value="Update Employee"/></p>
      </c:otherwise>
    </c:choose>
  </div>
</form:form>

<c:if test="${employee.id != null}">
  <form:form method="delete">
    <div class="buttons" >
      <p><input type="submit" value="Delete Employee" style="background-color: yellow;"/></p>
    </div>
  </form:form>
</c:if>    

<%@ include file="/WEB-INF/jsp/footer.jsp" %>