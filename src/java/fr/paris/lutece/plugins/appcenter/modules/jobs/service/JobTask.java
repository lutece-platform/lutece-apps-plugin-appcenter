package fr.paris.lutece.plugins.appcenter.modules.jobs.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobData;
import fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobsData;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class JobTask extends SimpleTask
{

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "job Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {

        JobData jobData = new JobData( );
        BeanUtil.populate( jobData, request );

        // FIXME return real error message here
        if ( !BeanValidationUtil.validate( jobData ).isEmpty( ) )
        {
            throw new RuntimeException( "Should not happen after validateTask" );
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        JobsData jobsData = ApplicationService.loadApplicationDataSubset( application, JobsData.DATA_JOBS_NAME,
                JobsData.class );
        
        if ( jobsData == null ) jobsData = new JobsData( );
        
        jobsData.addJob( jobData );
        ApplicationService.saveApplicationData( application, jobsData );

        // TODO also save in history to be able to display in OpenamTaskComponent.getDisplayTaskInformation
    }

}
