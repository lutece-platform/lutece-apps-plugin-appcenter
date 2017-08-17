package fr.paris.lutece.plugins.appcenter.modules.openam.business;

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
public class OpenamDemand extends Demand{

 private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/openam/openam_agents_demand_infos.html";
    
 private String _strApplicationCode;
 @NotEmpty( message = "#i18n{appcenter.validation.openamagent.webapp.notEmpty}" )
 @Size( max = 50, message = "#i18n{appcenter.validation.openamagent.webapp.size}" )
 private String _strWebAppName;
 @NotEmpty( message = "#i18n{appcenter.validation.openamagent.publicurl.notEmpty}" )
 @Size( max = 50, message = "#i18n{appcenter.validation.openamagent.publicurl.size}" )
 private String _strPublicUrl;
 private String _strEnvironment;
 
 private String _strApplicationCodeLabelKey;
 private String _strWebappNameLabelKey;
 private String _strPublicUrlLabelKey;
 private String _strEnvironmentLabelKey;
 

 
 public String getApplicationCode() {
	return _strApplicationCode;
}
 
public void setApplicationCode(String strApplicationCode) {
	_strApplicationCode = strApplicationCode;
}

public String getWebappName() {
	return _strWebAppName;
}

public void setWebappName(String strWebAppName) {
	_strWebAppName = strWebAppName;
}
public String getPublicUrl() {
	return _strPublicUrl;
}
public void setPublicUrl(String strPublicUrl) {
	_strPublicUrl = strPublicUrl;
}

public String getApplicationCodeLabelKey()
{
    return _strApplicationCodeLabelKey;
}

public void setApplicationCodeLabelKey( String strApplicationCodeLabelKey )
{
    _strApplicationCodeLabelKey = strApplicationCodeLabelKey;
}

public String getWebappNameLabelKey()
{
    return _strWebappNameLabelKey;
}

public void setWebappNameLabelKey( String strWebappNameLabelKey )
{
    _strWebappNameLabelKey = strWebappNameLabelKey;
}

public String getPublicUrlLabelKey()
{
    return _strPublicUrlLabelKey;
}

public void setPublicUrlLabelKey( String strPublicUrlLabelKey )
{
    _strPublicUrlLabelKey = strPublicUrlLabelKey;
}


public String getEnvironment()
{
    return _strEnvironment;
}

public void setEnvironment( String strEnvironment )
{
    _strEnvironment = strEnvironment;
}

public String getEnvironmentLabelKey()
{
    return _strEnvironmentLabelKey;
}

public void setEnvironmentLabelKey( String strEnvironmentLabelKey )
{
    _strEnvironmentLabelKey = strEnvironmentLabelKey;
}
 
@Override
public String getComplementaryInfos ( )
{
    Map<String,Object> model = new HashMap<String,Object>();
    model.put( MARK_DEMAND, this );
    return AppTemplateService.getTemplate( TEMPLATE_SOURCES_DEMAND_INFOS, Locale.FRENCH , model ).getHtml();
}
 
}
