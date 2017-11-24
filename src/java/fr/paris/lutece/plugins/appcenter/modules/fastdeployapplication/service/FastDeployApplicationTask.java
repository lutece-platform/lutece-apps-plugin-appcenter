package fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationData;
import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationDemand;
import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationsData;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;

public class FastDeployApplicationTask extends AppcenterTask
{

   

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "FastDeploy Application Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
    	   super.processTask(nIdResourceHistory, request, locale, FastDeployApplicationData.class, FastDeployApplicationsData.class,FastDeployApplicationsData.DATA_FASTDEPLOY_APPLICATIONS_NAME, FastDeployApplicationDemand.class);

    }
}
