<%@page import="fr.paris.lutece.plugins.appcenter.modules.support.web.DoAdminDownloadFile"%>
<% 
    String strResult =  DoAdminDownloadFile.doDownloadFile(request,response);
    if (!response.isCommitted())
    {
              response.sendRedirect(strResult);
    }
%>