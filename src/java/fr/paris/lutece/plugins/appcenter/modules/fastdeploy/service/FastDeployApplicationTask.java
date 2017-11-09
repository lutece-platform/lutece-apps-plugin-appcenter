package fr.paris.lutece.plugins.appcenter.modules.fastdeploy.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.fastdeploy.business.FastDeployApplicationData;
import fr.paris.lutece.plugins.appcenter.modules.fastdeploy.business.FastDeployApplicationsData;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamAgentData;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamAgentsData;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class FastDeployApplicationTask extends SimpleTask
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

        FastDeployApplicationData fastDeployApplicationData = new FastDeployApplicationData( );
        BeanUtil.populate( fastDeployApplicationData, request );

        // FIXME return real error message here
        if ( !BeanValidationUtil.validate( fastDeployApplicationData ).isEmpty( ) )
        {
            throw new RuntimeException( "Should not happen after validateTask" );
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        FastDeployApplicationsData fastDeployApplicationsData = ApplicationService.loadApplicationDataSubset( application, FastDeployApplicationsData.DATA_FASTDEPLOY_APPLICATIONS_NAME,
                FastDeployApplicationsData.class );
        if ( fastDeployApplicationsData == null )
        {
            fastDeployApplicationsData = new FastDeployApplicationsData( );
        }
        fastDeployApplicationsData.addData( fastDeployApplicationData );
        ApplicationService.saveApplicationData( application, fastDeployApplicationsData );

        // TODO also save in history to be able to display in OpenamTaskComponent.getDisplayTaskInformation
    }

}
