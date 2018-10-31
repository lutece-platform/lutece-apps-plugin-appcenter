<jsp:useBean id="manageactionrolesActionRole" scope="session" class="fr.paris.lutece.plugins.appcenter.web.ActionRoleJspBean" />
<% String strContent = manageactionrolesActionRole.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
