<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div class="title">
  <fmt:message key="welcome"/>
</div>
<div class="menu">
</div>

<div class="links" >
  <p><a href="<spring:url value="/departments/search" htmlEscape="true" />">
    Find Departments
  </a></p>
</div>
<div class="links" >
    <p><a href='<spring:url value="/departments/new" htmlEscape="true"/>'>
      Add Department
    </a></p>
</div>

<div style="clear:both;" ></div>

<div class="links" >
    <p><a href='<spring:url value="/departments/join" htmlEscape="true"/>'>
      Join Departments
    </a></p>
</div>
<div class="links" >
    <p><a href='<spring:url value="/static/images/uml_state_machine_diagram.jpg"/>'>
      State Machine Diagram
    </a></p>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>