package fr.paris.lutece.plugins.appcenter.modules.notificationgru.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingData;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingDemand;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingsData;
import fr.paris.lutece.plugins.appcenter.modules.notificationgru.business.NotificationGruData;
import fr.paris.lutece.plugins.appcenter.modules.notificationgru.business.NotificationGruDatas;
import fr.paris.lutece.plugins.appcenter.modules.notificationgru.business.NotificationGruDemand;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class NotificationGruTask extends AppcenterTask
{

    
    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "Notification Gru Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
           super.processTask(nIdResourceHistory, request, locale, NotificationGruData.class, NotificationGruDatas.class,NotificationGruDemand.class);

    }
    
}
