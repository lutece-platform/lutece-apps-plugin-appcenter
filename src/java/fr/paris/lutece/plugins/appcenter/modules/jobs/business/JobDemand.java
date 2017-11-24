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
 * JobDemand
 *
 */
public class JobDemand extends Demand
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1360289488188596187L;
	public static final String ID_DEMAND_TYPE = "jobs";
    public static final String DEMAND_TYPE = "jobs";

    private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/jobs/job_demand_infos.html";

 
    @NotEmpty( message = "#i18n{module.appcenter.jobs.validation.jobs.repositoryUrl.notEmpty}" )
    private String _strRepositoyUrl;
    
    

    /**
     * get Plugin Url
     * @return PluginUrl
     */
    public String getRepositoryUrl( )
    {
        return _strRepositoyUrl ;
    }

    /**
     * set Plugin Url
     * 
     * @param strPluginUrl 
     */
    public void setRepositoryUrl( String strPluginUrl )
    {
    	_strRepositoyUrl = strPluginUrl;
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

    @JsonIgnore
    @Override
    public boolean isDependingOfEnvironment()
    {
        return false;
    }

}
