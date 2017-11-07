package fr.paris.lutece.plugins.appcenter.modules.jobs.business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * OpenamDemand
 *
 */
public class JobDemand extends Demand
{

    public static final String ID_DEMAND_TYPE = "job";
    public static final String DEMAND_TYPE = "job";

    private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/jobs/job_demand_infos.html";

    private String _strApplicationCode;
    @NotEmpty( message = "#i18n{appcenter.validation.jobs.pluginName.notEmpty}" )
    private String _strName;
    @NotEmpty( message = "#i18n{appcenter.validation.jobs.name.notEmpty}" )
    private String _strPluginName;
    @NotEmpty( message = "#i18n{appcenter.validation.jobs.pluginUrl.notEmpty}" )
    private String _strPluginUrl;

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

    /**
     * 
     * @return 
     */
    public String getApplicationCode( )
    {
        return _strApplicationCode;
    }

    /**
     * 
     * @param strApplicationCode 
     */
    public void setApplicationCode( String strApplicationCode )
    {
        _strApplicationCode = strApplicationCode;
    }

    /**
     * get Plugin Name
     * @return PluginName
     */
    public String getPluginName( )
    {
        return _strPluginName ;
    }

    /**
     * set Plugin Name 
     * @param strPluginName 
     */
    public void setPluginName( String strPluginName )
    {
        _strPluginName = strPluginName;
    }

    /**
     * get Plugin Url
     * @return PluginUrl
     */
    public String getPluginUrl( )
    {
        return _strPluginUrl ;
    }

    /**
     * set Plugin Url
     * 
     * @param strPluginUrl 
     */
    public void setPluginUrl( String strPluginUrl )
    {
        _strPluginUrl = strPluginUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getComplementaryInfos( )
    {
        Map<String, Object> model = new HashMap<>( );
        model.put( MARK_DEMAND, this );
        return AppTemplateService.getTemplate( TEMPLATE_SOURCES_DEMAND_INFOS, I18nService.getDefaultLocale( ), model ).getHtml( );
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
