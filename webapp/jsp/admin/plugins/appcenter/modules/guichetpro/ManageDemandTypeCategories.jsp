<jsp:useBean id="manageDemandTypeCategories" scope="session" class="fr.paris.lutece.plugins.appcenter.modules.guichetpro.web.DemandTypeCategoryJspBean" />
<% String strContent = manageDemandTypeCategories.processController ( request , response ); %>

<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>
