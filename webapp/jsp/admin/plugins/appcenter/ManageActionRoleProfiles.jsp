<jsp:useBean id="manageactionrolesprofileActionRoleProfile" scope="session" class="fr.paris.lutece.plugins.appcenter.web.ActionRoleProfileJspBean" />
<% String strContent = manageactionrolesprofileActionRoleProfile.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
