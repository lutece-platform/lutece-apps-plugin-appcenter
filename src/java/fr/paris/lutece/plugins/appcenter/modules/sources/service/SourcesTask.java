package fr.paris.lutece.plugins.appcenter.modules.sources.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesData;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDatas;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class SourcesTask extends SimpleTask
{

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "Sources";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        SourcesData sourcesData = new SourcesData( );
        BeanUtil.populate( sourcesData, request );

        // FIXME return real error message here
        if ( !BeanValidationUtil.validate( sourcesData ).isEmpty( ) )
        {
            throw new RuntimeException( "Should not happen after validateTask" );
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        SourcesDatas sourcesDatas = ApplicationService.loadApplicationDataSubset( application, SourcesDatas.DATA_SOURCES_NAME,
                SourcesDatas.class );
        if ( sourcesDatas == null )
        {
            sourcesDatas = new SourcesDatas( );
        }
        sourcesDatas.addSourceData( sourcesData );
        ApplicationService.saveApplicationData( application, sourcesDatas );
    }

}
