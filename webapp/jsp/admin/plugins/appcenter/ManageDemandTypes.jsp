<jsp:useBean id="managedemandtypeDemandType" scope="session" class="fr.paris.lutece.plugins.appcenter.web.DemandTypeJspBean" />
<% String strContent = managedemandtypeDemandType.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
