package fr.paris.lutece.plugins.appcenter.modules.openam.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamAgentData;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamAgentsData;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class OpenamTask extends SimpleTask
{

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "Openam Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {

        OpenamAgentData openamAgentData = new OpenamAgentData( );
        BeanUtil.populate( openamAgentData, request );

        // FIXME return real error message here
        if ( !BeanValidationUtil.validate( openamAgentData ).isEmpty( ) )
        {
            throw new RuntimeException( "Should not happen after validateTask" );
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        OpenamAgentsData openamAgentsData = ApplicationService.loadApplicationDataSubset( application, OpenamAgentsData.DATA_OPENAM_AGENTS_NAME,
                OpenamAgentsData.class );
        if ( openamAgentsData == null )
        {
            openamAgentsData = new OpenamAgentsData( );
        }
        openamAgentsData.addData( openamAgentData );
        ApplicationService.saveApplicationData( application, openamAgentsData );

        // TODO also save in history to be able to display in OpenamTaskComponent.getDisplayTaskInformation
    }

}
