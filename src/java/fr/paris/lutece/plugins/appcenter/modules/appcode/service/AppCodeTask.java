package fr.paris.lutece.plugins.appcenter.modules.appcode.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeData;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeDatas;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeDemand;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;
/**
 * 
 *  AppCodeTask
 *
 */
public class AppCodeTask extends AppcenterTask
{

   

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "AppCode";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
           super.processTask(nIdResourceHistory, request, locale, AppCodeData.class, AppCodeDatas.class, AppCodeDemand.class);

    }
}