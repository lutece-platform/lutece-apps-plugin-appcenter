package fr.paris.lutece.plugins.appcenter.modules.support.business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 
 * SupportDemand
 *
 */
public class SupportDemand extends Demand
{

    /**
    * 
    */
    private static final long serialVersionUID = -1360289488188596187L;
    public static final String ID_DEMAND_TYPE = "support";
    public static final String DEMAND_TYPE = "support";

    private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/support/support_demand_infos.html";

 
    @NotEmpty( message = "#i18n{module.appcenter.support.validation.questionSupport.notEmpty}" )
    private String _strQuestionSupport;
    private List<UploadFile> _listFilesSupport;
    


    /**
     * Get the question for lutece support
     * @return the question for lutece support
     */
    public String getQuestionSupport( ) 
    {
            return _strQuestionSupport;
    }

    /**
     * Set the question for lutece support
     * @param strQuestionSupport
     */
    public void setQuestionSupport(String _strQuestionSupport) 
    {
            this._strQuestionSupport = _strQuestionSupport;
    }

    /**
     * get the list of files linked to the question for lutece support
     * @return the list of files linked to the question for lutece support
     */
    public List<UploadFile> getListFilesSupport( ) 
    {
            return _listFilesSupport;
    }

    /**
     * set the list of files linked to the question for lutece support
     * @param _listFilesSupport
     */
    public void setListFilesSupport(List<UploadFile> _listFilesSupport) 
    {
            this._listFilesSupport = _listFilesSupport;
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
