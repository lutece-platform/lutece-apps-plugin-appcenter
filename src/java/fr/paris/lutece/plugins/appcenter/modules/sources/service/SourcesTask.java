package fr.paris.lutece.plugins.appcenter.modules.sources.service;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.rometools.rome.feed.rss.Source;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourceUserData;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourceUserDemand;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesData;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDatas;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDemand;
import fr.paris.lutece.plugins.appcenter.modules.sources.web.SourcesTaskComponent;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.task.AppCenterTaskFunctional;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class SourcesTask extends AppcenterTask
{

   @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "Sources";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
    	 AppCenterTaskFunctional<SourcesData, SourcesDatas, SourcesDemand> funct=( requestParam,  localeParam, applicationDataParam, applicationDatasParam,demandParam)-> addUsers(requestParam,applicationDataParam,demandParam);
         super.processTask(nIdResourceHistory, request, locale, SourcesData.class, SourcesDatas.class, SourcesDemand.class,funct);

    }
    
    
    
    public void  addUsers(HttpServletRequest request, SourcesData sourcesData,SourcesDemand demand )
    {
    	
    	 sourcesData.setListUserData( new ArrayList<>( ) );
         SourceUserData userData=null;
         for(SourceUserDemand user: demand.getListSourceUserDemand( ))
         {
             userData=new SourceUserData( );
             if(request.getParameter( user.getUserName( ) )!=null)
             {
                 userData.setUserName( user.getUserName( ) );
                 userData.setEmail( user.getEmail( ) );
                 sourcesData.getListUserData( ).add( userData );
             }
          
          }
    }
	
    
    
    
    

}
