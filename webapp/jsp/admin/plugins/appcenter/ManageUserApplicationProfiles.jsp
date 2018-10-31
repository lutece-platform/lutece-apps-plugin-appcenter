<jsp:useBean id="manageuserapplicationprofileUserApplicationProfile" scope="session" class="fr.paris.lutece.plugins.appcenter.web.UserApplicationProfileJspBean" />
<% String strContent = manageuserapplicationprofileUserApplicationProfile.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
