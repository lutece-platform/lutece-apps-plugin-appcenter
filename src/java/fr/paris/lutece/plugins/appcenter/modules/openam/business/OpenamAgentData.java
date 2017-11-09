
package fr.paris.lutece.plugins.appcenter.modules.openam.business;

import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.appcenter.business.Environment;

/**
 * This is the business class for the object OpenamAgentData
 */ 
public class OpenamAgentData extends ApplicationData {
	
	
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.agentName.notEmpty}" )
	private String _strName;
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.agentPassword.notEmpty}" )
	private String _strPassword;
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.serverUrl.notEmpty}" )
	private String _strServerUrl;
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.agentUrl.notEmpty}" )
	private String _strAgentUrl;
        private boolean _bG98SettingNeeded;

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
	public String getPassword() {
		return _strPassword;
	}
	/**
	 * 
	 * @param _strPassword
	 */
	public void setPassword(String _strPassword) {
		this._strPassword = _strPassword;
	}
	/**
	 * 
	 * @return
	 */
	public String getServerUrl() {
		return _strServerUrl;
	}
	/**
	 * 
	 * @param _strServerUrl
	 */
	public void setServerUrl(String _strServerUrl) {
		this._strServerUrl = _strServerUrl;
	}
	/**
	 * 
	 * @return
	 */
	public String getAgentUrl() {
		return _strAgentUrl;
	}
	/**
	 * 
	 * @param _strAgentUrl
	 */
	public void setAgentUrl(String _strAgentUrl) {
		this._strAgentUrl = _strAgentUrl;
	}

    /**
     * Returns a boolean describing whether a G98 setting is needed or not
     *
     * @return True if a G98 setting is needed
     */
    public boolean getG98SettingNeeded( )
    {
        return _bG98SettingNeeded;
    }

    /**
     * Sets a boolean describing whether a G98 setting is needed or not
     *
     * @param bG98SettingNeeded
     *            True if a G98 setting is needed
     */
    public void setG98SettingNeeded( boolean bG98SettingNeeded )
    {
        _bG98SettingNeeded = bG98SettingNeeded;
    }
}
