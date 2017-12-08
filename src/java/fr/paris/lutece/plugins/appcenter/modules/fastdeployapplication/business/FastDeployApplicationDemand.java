package fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.web.FastDeployApplicationsXPage;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.ReferenceList;

/**
 * 
 * OpenamDemand
 *
 */
public class FastDeployApplicationDemand extends Demand
{

    public static final String ID_DEMAND_TYPE = "fastdeployapplication";
    public static final String DEMAND_TYPE = "fastdeployapplication";

    private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/fastdeploy/application_demand_infos.html";
   
    @NotEmpty( message = "#i18n{module.appcenter.fastdeployapplication.validation.applicationName.notEmpty}" )
    private String _strName;
    @NotEmpty( message = "#i18n{module.appcenter.fastdeployapplication.validation.applicationWebApp.notEmpty}" )
    private String _strWebApp;
    @NotEmpty( message = "#i18n{module.appcenter.fastdeployapplication.validation.applicationSiteUrl.notEmpty}" )
    private String _strUrlSite;
    @NotEmpty( message = "#i18n{module.appcenter.fastdeployapplication.validation.applicationService.notEmpty}" )
    private String _strService;
    
    /**
     * 
     * @return
     */
    public String getName() {
        return _strName;
    }
    
    /**
     * 
     * @param _strName
     */
    public void setName(String _strName) {
        this._strName = _strName;
    }
    
    
   public String getWebApp( )
    {
        return _strWebApp;
    }

    public void setWebApp( String _strWebApp )
    {
        this._strWebApp = _strWebApp;
    }

    public String getUrlSite( )
    {
        return _strUrlSite;
    }

    public void setUrlSite( String _strUrlSite )
    {
        this._strUrlSite = _strUrlSite;
    }

    public String getService( )
    {
        return _strService;
    }

    public void setService( String strWorkgroup )
    {
        this._strService = strWorkgroup;
    }
    

    @Override
    public String getComplementaryInfos( )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_DEMAND, this );
        ReferenceList refServices=DatastoreService.getDataByPrefix( FastDeployApplicationsXPage.DATA_PREFIX_FAST_DEPLOY_SERVICES );
        model.put( FastDeployApplicationsXPage.MARK_MAP_SERVICES, refServices.toMap( ) );
        
        
        return AppTemplateService.getTemplate( TEMPLATE_SOURCES_DEMAND_INFOS, Locale.FRENCH, model ).getHtml( );
    }

    /**
     * Get the demand type id
     * 
     * @return the demand type id
     */
    @JsonIgnore
    @Override
    public String getIdDemandType( )
    {
        return ID_DEMAND_TYPE;
    }

    /**
     * Get the demand type id
     * 
     * @return the demand type id
     */
    @JsonIgnore
    @Override
    public String getDemandType( )
    {
        return DEMAND_TYPE;
    }

    @JsonIgnore
    @Override
    public boolean isDependingOfEnvironment()
    {
        return false;
    }

}
