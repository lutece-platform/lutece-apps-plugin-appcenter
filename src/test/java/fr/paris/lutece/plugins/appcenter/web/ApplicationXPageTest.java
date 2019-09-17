package fr.paris.lutece.plugins.appcenter.web;

import org.springframework.mock.web.MockHttpServletRequest;

import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.test.LuteceTestCase;

/**
 * ApplicationXPage Test Class
 */
public class ApplicationXPageTest extends LuteceTestCase
{
    public void testGetPage( ) throws SiteMessageException, UserNotSignedException
    {
        System.out.println( "getPage" );

        MockHttpServletRequest request = new MockHttpServletRequest( );

        int nMode = 0;
        Plugin plugin = null;
        ApplicationXPage instance = new ApplicationXPage( );

        XPage result = instance.getPage( request, nMode, plugin );
    }

    public void testGetCreateApplication( ) throws SiteMessageException, UserNotSignedException
    {
        System.out.println( "getPage" );

        MockHttpServletRequest request = new MockHttpServletRequest( );
        request.addParameter( "view", "createApplication" );

        int nMode = 0;
        Plugin plugin = null;
        ApplicationXPage instance = new ApplicationXPage( );

        XPage result = instance.getPage( request, nMode, plugin );
    }

    public void testGetModifyApplication( ) throws SiteMessageException, UserNotSignedException
    {
        System.out.println( "getPage" );

        MockHttpServletRequest request = new MockHttpServletRequest( );
        request.addParameter( "view", "modifyApplication" );
        request.addParameter( "id", "1" );

        int nMode = 0;
        Plugin plugin = null;
        ApplicationXPage instance = new ApplicationXPage( );

        XPage result = instance.getPage( request, nMode, plugin );
    }
}
