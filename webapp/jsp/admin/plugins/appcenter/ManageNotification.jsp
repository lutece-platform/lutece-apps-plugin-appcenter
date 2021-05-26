<jsp:useBean id="manageNotification" scope="session" class="fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.web.ManageNotificationJspBean" />
<% String strContent = manageNotification.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
