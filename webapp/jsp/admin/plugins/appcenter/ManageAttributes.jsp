<jsp:useBean id="manageattributesAttribute" scope="session" class="fr.paris.lutece.plugins.appcenter.modules.identitystore.web.AttributeJspBean" />
<% String strContent = manageattributesAttribute.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
