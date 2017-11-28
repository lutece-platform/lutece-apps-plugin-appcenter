package fr.paris.lutece.plugins.appcenter.modules.identitystore.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreData;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDatas;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDemand;
import fr.paris.lutece.plugins.appcenter.service.task.AppCenterTaskFunctional;
import fr.paris.lutece.plugins.appcenter.service.task.AppcenterTask;

public class IdentitystoreTask extends  AppcenterTask{

     @Override
    public String getTitle(Locale locale) {
        //TODO
        return "Identitystore Task";
    }

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale ) {

       
        
        
        AppCenterTaskFunctional<IdentitystoreData, IdentitystoreDatas, IdentitystoreDemand> funct=( requestParam,  localeParam, applicationDataParam, applicationDatasParam,demandParam)-> addAttributeRights(requestParam,applicationDataParam);
        super.processTask(nIdResourceHistory, request, locale, IdentitystoreData.class, IdentitystoreDatas.class,IdentitystoreDemand.class,funct);

        
    }
    
    
    
    public void  addAttributeRights(HttpServletRequest request, IdentitystoreData identitystoreData)
    {
        
        identitystoreData.setAttributeRights( IdentityStoreDemandService.getMapAttributeRights( request ) );

    }
      

}
