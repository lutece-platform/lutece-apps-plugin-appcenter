package fr.paris.lutece.plugins.appcenter.modules.notificationgru.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.notificationgru.business.NotificationGruData;
import fr.paris.lutece.plugins.appcenter.modules.notificationgru.business.NotificationGruDatas;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

public class NotificationGruTask extends SimpleTask
{

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "NotificationGru";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        NotificationGruData notificationgruData = new NotificationGruData( );
        BeanUtil.populate( notificationgruData, request );

        // FIXME return real error message here
        if ( !BeanValidationUtil.validate( notificationgruData ).isEmpty( ) )
        {
            throw new RuntimeException( "Should not happen after validateTask" );
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        NotificationGruDatas notificationgruDatas = ApplicationService.loadApplicationDataSubset( application, NotificationGruDatas.DATA_NOTIFICATION_Gru_NAME,
                NotificationGruDatas.class );
        if ( notificationgruDatas == null )
        {
            notificationgruDatas = new NotificationGruDatas( );
        }
        notificationgruDatas.addData( notificationgruData );
        ApplicationService.saveApplicationData( application, notificationgruDatas );
    }

}
