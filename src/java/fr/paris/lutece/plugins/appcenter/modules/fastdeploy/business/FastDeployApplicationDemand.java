package fr.paris.lutece.plugins.appcenter.modules.fastdeploy.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * OpenamDemand
 *
 */
public class FastDeployApplicationDemand extends Demand
{

    public static final String ID_DEMAND_TYPE = "fastdeploy_application";
    public static final String DEMAND_TYPE = "fastdeploy_application";

    private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/fastdeploy/application_demand_infos.html";
    @NotEmpty( message = "#i18n{module.appcenter.fastdeploy.validation.applicationCode.notEmpty}" )
    private String _strCode;
    @NotEmpty( message = "#i18n{module.appcenter.fastdeploy.validation.applicationName.notEmpty}" )
    private String _strName;
    @NotEmpty( message = "#i18n{module.appcenter.fastdeploy.validation.applicationWebApp.notEmpty}" )
    private String _strWebApp;
    @NotEmpty( message = "#i18n{module.appcenter.fastdeploy.validation.applicationSiteUrl.notEmpty}" )
    private String _strUrlSite;
    @NotEmpty( message = "#i18n{module.appcenter.fastdeploy.validation.applicationService.notEmpty}" )
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
    
    
    public String getCode( )
    {
        return _strCode;
    }

    public void setCode( String _strCode )
    {
        this._strCode = _strCode;
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

}
