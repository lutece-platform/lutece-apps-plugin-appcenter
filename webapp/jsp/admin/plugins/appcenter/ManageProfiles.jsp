<jsp:useBean id="manageprofileProfile" scope="session" class="fr.paris.lutece.plugins.appcenter.web.ProfileJspBean" />
<% String strContent = manageprofileProfile.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
