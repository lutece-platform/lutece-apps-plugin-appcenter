<%@ page import="fr.paris.lutece.portal.service.security.UserNotSignedException,
                 fr.paris.lutece.portal.service.security.SecurityService,
                 fr.paris.lutece.portal.service.message.SiteMessageException,
                 fr.paris.lutece.portal.service.util.AppPathService,
                 fr.paris.lutece.portal.web.PortalJspBean" %>
<jsp:useBean id="doDownloadFile" scope="session" class="fr.paris.lutece.plugins.appcenter.modules.support.web.DoDownloadFile" />
<%
    try
    {
        doDownloadFile.doDownloadFile(request,response);
    }
    catch( SiteMessageException lme )
    {
        response.sendRedirect( AppPathService.getSiteMessageUrl( request ) );
    }
    catch( UserNotSignedException e )
    {
        // This exception means that a content service needs the
        // user to be authenticated (Added in v1.1)
        // The user should be redirected to the Lutece login page

        if( SecurityService.getInstance(  ).isExternalAuthentication(  ) && 
                !SecurityService.getInstance(  ).isMultiAuthenticationSupported(  ) )
        {
%>
            <center>
            <br />
            <br />
            <h3>
            Error : This page requires an authenticated user identified by an external service
            but no user is available.
            </h3>
            </center>
<%
        }
        else
        {
            response.sendRedirect( PortalJspBean.redirectLogin( request ));
        }
    }
%>
%>