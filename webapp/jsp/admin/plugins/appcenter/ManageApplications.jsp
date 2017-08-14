<jsp:useBean id="manageappcenterApplication" scope="session" class="fr.paris.lutece.plugins.appcenter.web.ApplicationJspBean" />
<% String strContent = manageappcenterApplication.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
