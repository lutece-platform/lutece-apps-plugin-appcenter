<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="manageappcenter" scope="session" class="fr.paris.lutece.plugins.appcenter.web.ManageAppCenterJspBean" />

<% manageappcenter.init( request, manageappcenter.RIGHT_MANAGEAPPCENTER ); %>
<%= manageappcenter.getManageAppCenterHome ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
