package fr.paris.lutece.plugins.appcenter.modules.jobs.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobData;
import fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobDemand;
import fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobsData;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;

public class JobTask extends AppcenterTask
{

    
    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "Job Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
    	   super.processTask(nIdResourceHistory, request, locale, JobData.class, JobsData.class,JobDemand.class);

    }
    
}
