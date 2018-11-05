<jsp:useBean id="managepermissionsPermission" scope="session" class="fr.paris.lutece.plugins.appcenter.web.PermissionJspBean" />
<% String strContent = managepermissionsPermission.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
