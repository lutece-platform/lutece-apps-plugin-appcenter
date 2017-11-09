
package fr.paris.lutece.plugins.appcenter.modules.jobs.business;

import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * This is the business class for the object JobData
 */ 
public class JobData extends ApplicationData
{

    // Constants
    public static final String CONSTANT_BUILD_JOB_TYPE = "build_job_type" ;
    public static final String CONSTANT_DOC_JOB_TYPE = "doc_job_type" ;

    // attributes
    @NotEmpty( message = "#i18n{module.appcenter.jobs.validation.DeployJobName.notEmpty}" )
    private String _strDeployJobName;
    @NotEmpty( message = "#i18n{module.appcenter.jobs.validation.DocAndQAJobName.notEmpty}" )
    private String _strDocQAJobName;
    @NotEmpty( message = "#i18n{module.appcenter.jobs.validation.pluginUrl.notEmpty}" )
    private String _strPluginUrl;

    /**
     * get the job name
     * 
     * @return the name
     */
    public String getDeployJobName( ) 
    {
            return _strDeployJobName;
    }

    /**
     * set the name 
     * @param _strDeployJobName
     */
    public void setDeployJobName(String _strDeployJobName) 
    {
            this._strDeployJobName = _strDeployJobName;
    }

    /**
     * get Doc And QA Job name
     * @return DocAndQAJobName
     */
    public String getDocQAJobName( ) 
    {
        return _strDocQAJobName;
    }

    /**
     * set DocAndQAJob Name
     * 
     * @param _strDocAndQAJobName 
     */
    public void setDocQAJobName(String _strDocAndQAJobName) 
    {
        this._strDocQAJobName = _strDocAndQAJobName;
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
	
}

