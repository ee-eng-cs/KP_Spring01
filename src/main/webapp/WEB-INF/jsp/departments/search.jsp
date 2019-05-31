<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="title">
  Find Departments:
</div>
<div class="menu">
    &nbsp;<a href="/kp_spring01/">Home</a>
</div>

<spring:url value="/departments" var="formUrl"/>
<form:form modelAttribute="department" action="${fn:escapeXml(formUrl)}" method="get">
  <div class="fields_left">
    <p><form:errors path="*" cssClass="errors"/> Name:</p>
  </div>
  <div class="fields_right">
    <p><form:input path="name" size="25" maxlength="30" /><p>
  </div>

  <div style="clear:both;" ></div>

  <div class="buttons" >
    <p><input type="submit" value="Find Departments"/></p>
  </div>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>