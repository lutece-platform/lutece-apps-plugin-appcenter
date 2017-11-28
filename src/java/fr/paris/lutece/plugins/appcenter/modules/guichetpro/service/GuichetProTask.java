package fr.paris.lutece.plugins.appcenter.modules.guichetpro.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProData;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProDatas;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProDemand;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;

public class GuichetProTask extends AppcenterTask
{

    
    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "Guichet Pro Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
           super.processTask(nIdResourceHistory, request, locale, GuichetProData.class, GuichetProDatas.class,GuichetProDemand.class);

    }
    
}
