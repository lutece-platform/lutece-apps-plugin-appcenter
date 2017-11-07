package fr.paris.lutece.plugins.appcenter.modules.identitystore.service;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreData;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDatas;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class IdentitystoreTask extends SimpleTask {

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle(Locale locale) {
        //TODO
        return "Identitystore Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale ) {

        IdentitystoreData identitystoreData = new IdentitystoreData();
        BeanUtil.populate ( identitystoreData, request );
        
        identitystoreData.setAttributeRights( IdentityStoreDemandService.getMapAttributeRights( request ) );

        //FIXME return real error message here        
        Set<ConstraintViolation<IdentitystoreData>> errors = BeanValidationUtil.validate( identitystoreData );
        if ( !errors.isEmpty() )
        {
            for ( ConstraintViolation<IdentitystoreData> constraint : errors )
            {	
                throw new RuntimeException( "Should not happen after validateTask :" + constraint.getMessage(  ) );
            }  
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource() );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication() );

        IdentitystoreDatas identitystoreDatas = ApplicationService.loadApplicationDataSubset ( application, IdentitystoreDatas.DATA_SUBSET_NAME, IdentitystoreDatas.class );
        if(identitystoreDatas == null)
        {
        	identitystoreDatas = new IdentitystoreDatas();
        }
        identitystoreDatas.addSetting( identitystoreData );
        ApplicationService.saveApplicationData ( application, identitystoreDatas );

        //TODO also save in history to be able to display in OpenamTaskComponent.getDisplayTaskInformation
    }

}
