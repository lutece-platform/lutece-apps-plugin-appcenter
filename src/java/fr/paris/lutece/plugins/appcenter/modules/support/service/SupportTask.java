package fr.paris.lutece.plugins.appcenter.modules.support.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.modules.support.business.SupportData;
import fr.paris.lutece.plugins.appcenter.modules.support.business.SupportDemand;
import fr.paris.lutece.plugins.appcenter.modules.support.business.SupportsData;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;

public class SupportTask extends AppcenterTask
{

    
    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "Support Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
    	   super.processTask(nIdResourceHistory, request, locale, SupportData.class, SupportsData.class, SupportDemand.class);
    }
    
}
