<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<c:choose>
	<c:when test="${department.id == null}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<div class="title">
  <c:if test="${department.id == null}">New </c:if>Department:
</div>
<div class="menu">
    &nbsp;<a href="/kp_spring01/">Home</a>
	&nbsp;/&nbsp;
	<a href="<spring:url value="/departments/search" htmlEscape="true" />">Find Departments</a>
	&nbsp;/&nbsp;
    <spring:url value="/departments" var="departmentUrl">
      <spring:param name="name" value="${department.name}"/>
    </spring:url>
	<a href="${fn:escapeXml(departmentUrl)}">Department Information</a>
</div>

<form:form modelAttribute="department" method="${method}">
  <div class="fields_left">
    <p><form:errors path="name" cssClass="errors"/> Name:</p>
  </div>
  <div class="fields_right">
    <p><form:input path="name" size="25" maxlength="30"/></p>
  </div>

  <div style="clear:both;" ></div>

  <div class="buttons" style="" >
    <c:choose>
      <c:when test="${department.id == null}">
        <p><input type="submit" value="Add Department"/></p>
      </c:when>
      <c:otherwise>
        <p><input type="submit" value="Update Department"/></p>
      </c:otherwise>
    </c:choose>
  </div>
</form:form>

<c:if test="${department.id != null}">
  <form:form method="delete">
    <div class="buttons" >
      <p><input type="submit" value="Delete Department" style="background-color: yellow;"/></p>
    </div>
  </form:form>
</c:if>    

<%@ include file="/WEB-INF/jsp/footer.jsp" %>