
package fr.paris.lutece.plugins.appcenter.modules.openam.business;

import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.appcenter.business.Environment;

/**
 * This is the business class for the object OpenamAgentData
 */ 
public class OpenamAgentData {
	
	
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.agentName.notEmpty}" )
	private String _strName;
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.agentPassword.notEmpty}" )
	private String _strPassword;
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.serverUrl.notEmpty}" )
	private String _strServerUrl;
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.agentUrl.notEmpty}" )
	private String _strAgentUrl;
	 @NotEmpty( message = "#i18n{module.appcenter.openam.validation.environmentCode.notEmpty}" )
	private String _environmentCode;

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

	public String getEnvironment() {
		return _environmentCode;
	}

	public void setEnvironment(String _environment) {
		this._environmentCode = _environment;
	}
	
	

}

