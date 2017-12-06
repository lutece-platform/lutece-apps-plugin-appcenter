package fr.paris.lutece.plugins.appcenter.modules.appcode.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeData;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeDatas;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeDemand;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreData;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDatas;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDemand;
import fr.paris.lutece.plugins.appcenter.service.task.AppCenterTaskFunctional;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
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
           
        AppCenterTaskFunctional<AppCodeData, AppCodeDatas, AppCodeDemand> funct=( requestParam,  localeParam, applicationDataParam, applicationDatasParam,demandParam)-> updateAppCode( applicationDataParam, nIdResourceHistory );
         super.processTask(nIdResourceHistory, request, locale, AppCodeData.class, AppCodeDatas.class, AppCodeDemand.class,funct);

    }
    
    
    private void updateAppCode(AppCodeData appCodeData,int nIdResourceHistory)
    {
        ResourceHistory resourceHistory = super._resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );
        application.setCode( appCodeData.getApplicationCode( ) );
        ApplicationHome.update( application );
        
    }
        
    
    
}