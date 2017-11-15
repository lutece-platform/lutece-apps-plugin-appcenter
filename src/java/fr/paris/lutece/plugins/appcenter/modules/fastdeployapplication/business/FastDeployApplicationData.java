
package fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business;

import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This is the business class for the object OpenamAgentData
 */ 
public class FastDeployApplicationData extends ApplicationData
{
	
	
	@NotEmpty( message = "#i18n{module.appcenter.fastdeployapplication.validation.applicationCode.notEmpty}" )
	private String _strCode;
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

    public void setService( String _strService )
    {
        this._strService= _strService;
    }
	

}

