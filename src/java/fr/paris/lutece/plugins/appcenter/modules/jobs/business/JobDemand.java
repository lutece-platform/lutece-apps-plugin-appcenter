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
    @NotEmpty( message = "#i18n{appcenter.validation.jobs.pluginUrl.notEmpty}" )
    private String _strPluginUrl;
    private String _strPluginName;

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
     * get plugin name
     * @return 
     */
    public String getPluginName() {
        return _strPluginName;
    }

    /**
     * set plugin name
     * @param _strPluginName 
     */
    public void setPluginName(String _strPluginName) {
        this._strPluginName = _strPluginName;
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
