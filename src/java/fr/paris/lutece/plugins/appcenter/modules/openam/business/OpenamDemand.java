package fr.paris.lutece.plugins.appcenter.modules.openam.business;

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
public class OpenamDemand extends Demand
{

    public static final String ID_DEMAND_TYPE = "openam";
    public static final String DEMAND_TYPE = "openam";

    private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/openam/openam_agents_demand_infos.html";

    private String _strApplicationCode;
    @NotEmpty( message = "#i18n{module.appcenter.openam.validation.openamagent.webapp.notEmpty}" )
    @Size( max = 50, message = "#i18n{module.appcenter.openam.validation.openamagent.webapp.size}" )
    private String _strWebAppName;
    @NotEmpty( message = "#i18n{module.appcenter.openam.validation.openamagent.publicurl.notEmpty}" )
    @Size( max = 50, message = "#i18n{module.appcenter.openam.validation.openamagent.publicurl.size}" )
    private String _strPublicUrl;

    public String getApplicationCode( )
    {
        return _strApplicationCode;
    }

    public void setApplicationCode( String strApplicationCode )
    {
        _strApplicationCode = strApplicationCode;
    }

    public String getWebappName( )
    {
        return _strWebAppName;
    }

    public void setWebappName( String strWebAppName )
    {
        _strWebAppName = strWebAppName;
    }

    public String getPublicUrl( )
    {
        return _strPublicUrl;
    }

    public void setPublicUrl( String strPublicUrl )
    {
        _strPublicUrl = strPublicUrl;
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

    @JsonIgnore
    @Override
    public boolean isDependingOfEnvironment()
    {
        return true;
    }

}
