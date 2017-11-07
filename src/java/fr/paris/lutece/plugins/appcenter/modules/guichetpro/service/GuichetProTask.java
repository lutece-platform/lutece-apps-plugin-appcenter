package fr.paris.lutece.plugins.appcenter.modules.guichetpro.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProData;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProDatas;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class GuichetProTask extends SimpleTask
{

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "GuichetPro";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        GuichetProData guichetproData = new GuichetProData( );
        BeanUtil.populate( guichetproData, request );

        // FIXME return real error message here
        if ( !BeanValidationUtil.validate( guichetproData ).isEmpty( ) )
        {
            throw new RuntimeException( "Should not happen after validateTask" );
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        GuichetProDatas guichetproDatas = ApplicationService.loadApplicationDataSubset( application, GuichetProDatas.DATA_GUICHET_PRO_NAME,
                GuichetProDatas.class );
        if ( guichetproDatas == null )
        {
            guichetproDatas = new GuichetProDatas( );
        }
        guichetproDatas.addGuichetProData( guichetproData );
        ApplicationService.saveApplicationData( application, guichetproDatas );
    }

}
