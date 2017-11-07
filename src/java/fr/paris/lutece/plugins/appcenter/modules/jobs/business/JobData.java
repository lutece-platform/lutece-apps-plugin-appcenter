
package fr.paris.lutece.plugins.appcenter.modules.jobs.business;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * This is the business class for the object JobData
 */ 
public class JobData 
{

    // Constants
    public static final String CONSTANT_BUILD_JOB_TYPE = "build_job_type" ;
    public static final String CONSTANT_DOC_JOB_TYPE = "doc_job_type" ;

    // attributes
    @NotEmpty( message = "#i18n{module.appcenter.jobs.validation.name.notEmpty}" )
    private String _strName;
    @NotEmpty( message = "#i18n{module.appcenter.jobs.validation.pluginName.notEmpty}" )
    private String _strPluginName;
    @NotEmpty( message = "#i18n{module.appcenter.jobs.validation.pluginUrl.notEmpty}" )
    private String _strPluginUrl;
    private String _strJobType;

    /**
     * get the job name
     * 
     * @return the name
     */
    public String getName( ) 
    {
            return _strName;
    }

    /**
     * set the name 
     * @param _strName
     */
    public void setName(String _strName) 
    {
            this._strName = _strName;
    }

    /**
     * get Plugin Name
     * @return PluginName
     */
    public String getPluginName( ) 
    {
        return _strPluginName;
    }

    /**
     * set Plugin Name
     * 
     * @param _strPluginName 
     */
    public void setPluginName(String _strPluginName) 
    {
        this._strPluginName = _strPluginName;
    }

    /**
     * get Plugin Url
     * @return PluginUrl
     */
    public String getPluginUrl( ) 
    {
        return _strPluginUrl;
    }

    /**
     * set Plugin Url
     * 
     * @param _strPluginUrl 
     */
    public void setPluginUrl(String _strPluginUrl) 
    {
        this._strPluginUrl = _strPluginUrl;
    }

    /**
     * get Job Type
     * 
     * @return JobType
     */
    public String getJobType( ) 
    {
        return _strJobType;
    }

    /**
     * set Job Type
     * 
     * @param _strJobType 
     */
    public void setJobType(String _strJobType) 
    {
        this._strJobType = _strJobType;
    }
	
}

