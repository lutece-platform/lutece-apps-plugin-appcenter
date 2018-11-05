<jsp:useBean id="manageuserapplicationroleUserApplicationRole" scope="session" class="fr.paris.lutece.plugins.appcenter.web.UserApplicationRoleJspBean" />
<% String strContent = manageuserapplicationroleUserApplicationRole.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
