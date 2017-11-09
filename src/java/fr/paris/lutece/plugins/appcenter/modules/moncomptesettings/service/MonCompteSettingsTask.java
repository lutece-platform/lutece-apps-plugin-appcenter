package fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingData;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingsData;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamAgentData;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class MonCompteSettingsTask extends SimpleTask {

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle(Locale locale) {
        //TODO
        return "MonCompte Settings Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale ) {

        MonCompteSettingData monCompteSettingData = new MonCompteSettingData();
        BeanUtil.populate ( monCompteSettingData, request );

        //FIXME return real error message here        
        Set<ConstraintViolation<MonCompteSettingData>> errors = BeanValidationUtil.validate( monCompteSettingData );
        if ( !errors.isEmpty() )
        {
            for ( ConstraintViolation<MonCompteSettingData> constraint : errors )
            {	
                throw new RuntimeException( "Should not happen after validateTask :" + constraint.getMessage(  ) );
            }  
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource() );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication() );

        MonCompteSettingsData monCompteSettingsData = ApplicationService.loadApplicationDataSubset ( application, MonCompteSettingsData.DATA_SUBSET_NAME, MonCompteSettingsData.class );
        if(monCompteSettingsData == null)
        {
        	monCompteSettingsData = new MonCompteSettingsData();
        }
        monCompteSettingsData.addData( monCompteSettingData );
        ApplicationService.saveApplicationData ( application, monCompteSettingsData );

        //TODO also save in history to be able to display in OpenamTaskComponent.getDisplayTaskInformation
    }

}
