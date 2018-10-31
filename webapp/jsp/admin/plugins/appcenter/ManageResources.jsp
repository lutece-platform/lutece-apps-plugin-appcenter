<jsp:useBean id="manageresourceResource" scope="session" class="fr.paris.lutece.plugins.appcenter.web.ResourceJspBean" />
<% String strContent = manageresourceResource.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
