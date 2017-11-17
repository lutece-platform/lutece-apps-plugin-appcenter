package fr.paris.lutece.plugins.appcenter.service.task;

import java.util.ListIterator;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.mchange.lang.IntegerUtils;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDatas;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public abstract class AppcenterTask extends SimpleTask
{

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

   

    public <AD extends ApplicationData,ADS extends ApplicationDatas<AD>,D extends Demand >void  processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale,Class<AD>  applicationDataClass,Class<ADS> applicationDatasClass,String strDataSubsetName, Class<D> demandClass,AppCenterTaskFunctional funct  )
    {
    	 AD  applicationData=null;
    	 try {
    		 applicationData = applicationDataClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				AppLogService.error(e);
			}
          BeanUtil.populate( applicationData, request );
          // FIXME return real error message here
          if ( !BeanValidationUtil.validate( applicationData ).isEmpty( ) )
          {
              throw new RuntimeException( "Should not happen after validateTask" );
          }
          
          

          ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
          D demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) ,demandClass);

          Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

          ADS datas = ApplicationService.loadApplicationDataSubset( application, strDataSubsetName,
        		  applicationDatasClass );
          if ( datas == null )
          {
        	  try {
				datas = applicationDatasClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				AppLogService.error(e);
			}
          }
          
          
          if(funct!=null)
          {
        	  funct.treatment(request, locale, applicationData, datas, demand);
          }
          applicationData.addDemandAssociated(demand.getId());
          
          if(demand.getIdApplicationData()==null || demand.getIdApplicationData()==0 )
          {
        	  //Add new data
        	  datas.addData( applicationData );
          }
          else
          {
        	//Modify data
        	ListIterator<AD> itr = datas.getListData().listIterator();
			while(itr.hasNext()) {
			  AD it = itr.next();
			  if(it.getIdApplicationData()==demand.getIdApplicationData())
				    itr.set(applicationData);
					break;
			}
	          
          }
          
          ApplicationService.saveApplicationData( application, datas );
    }
    
	
	




}
