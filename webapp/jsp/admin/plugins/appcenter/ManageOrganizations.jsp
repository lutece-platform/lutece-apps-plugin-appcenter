<jsp:useBean id="manageOrganization" scope="session" class="fr.paris.lutece.plugins.appcenter.web.organization.OrganizationJspBean" />
<% String strContent = manageOrganization.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
