<jsp:useBean id="manageOrganizationManager" scope="session" class="fr.paris.lutece.plugins.appcenter.web.organization.OrganizationManagerJspBean" />
<% String strContent = manageOrganizationManager.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
