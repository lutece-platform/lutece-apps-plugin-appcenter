<jsp:useBean id="manageroleRole" scope="session" class="fr.paris.lutece.plugins.appcenter.web.RoleJspBean" />
<% String strContent = manageroleRole.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
