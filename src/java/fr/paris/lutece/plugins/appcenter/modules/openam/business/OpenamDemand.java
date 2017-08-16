package fr.paris.lutece.plugins.appcenter.modules.openam.business;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.appcenter.business.Demand;

/**
 * 
 * OpenamDemand
 *
 */
public class OpenamDemand extends Demand{

 private String _strApplicationCode;
 @NotEmpty( message = "#i18n{appcenter.validation.openamagent.webapp.notEmpty}" )
 @Size( max = 50, message = "#i18n{appcenter.validation.openamagent.webapp.size}" )
 private String _strWebAppName;
 @NotEmpty( message = "#i18n{appcenter.validation.openamagent.publicurl.notEmpty}" )
 @Size( max = 50, message = "#i18n{appcenter.validation.openamagent.publicurl.size}" )
 private String _strPublicUrl;

 
 public String getApplicationCode() {
	return _strApplicationCode;
}
public void setApplicationCode(String _strApplicationCode) {
	this._strApplicationCode = _strApplicationCode;
}
public String getWebAppName() {
	return _strWebAppName;
}
public void setWebAppName(String _strWebAppName) {
	this._strWebAppName = _strWebAppName;
}
public String getPublicUrl() {
	return _strPublicUrl;
}
public void setPublicUrl(String _strPublicUrl) {
	this._strPublicUrl = _strPublicUrl;
}
 
 
}
