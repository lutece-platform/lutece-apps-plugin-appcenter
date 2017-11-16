package fr.paris.lutece.plugins.appcenter.modules.appcode.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeData;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeDatas;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class AppCodeTask extends SimpleTask
{

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "AppCode";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        AppCodeData appCodeData = new AppCodeData( );
        BeanUtil.populate( appCodeData, request );

        // FIXME return real error message here
        if ( !BeanValidationUtil.validate( appCodeData ).isEmpty( ) )
        {
            throw new RuntimeException( "Should not happen after validateTask" );
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        AppCodeDatas appCodeDatas = ApplicationService.loadApplicationDataSubset( application, AppCodeDatas.DATA_APP_CODE_NAME,
                AppCodeDatas.class );
        if ( appCodeDatas == null )
        {
            appCodeDatas = new AppCodeDatas( );
        }
        appCodeDatas.addData( appCodeData );
        ApplicationService.saveApplicationData( application, appCodeDatas );
        
        application.setCode( appCodeData.getApplicationCode( ) );
        ApplicationHome.update( application );
    }

}
