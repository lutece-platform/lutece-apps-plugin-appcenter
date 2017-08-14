<jsp:useBean id="manageappcenterUserApplication" scope="session" class="fr.paris.lutece.plugins.appcenter.web.UserApplicationJspBean" />
<% String strContent = manageappcenterUserApplication.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
