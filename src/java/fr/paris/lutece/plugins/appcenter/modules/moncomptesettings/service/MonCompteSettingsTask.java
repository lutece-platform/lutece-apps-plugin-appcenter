package fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingData;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingDemand;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingsData;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;

public class MonCompteSettingsTask extends AppcenterTask
{

    
    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "MonCompte Settings Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
           super.processTask(nIdResourceHistory, request, locale, MonCompteSettingData.class, MonCompteSettingsData.class,MonCompteSettingDemand.class);

    }
    
}