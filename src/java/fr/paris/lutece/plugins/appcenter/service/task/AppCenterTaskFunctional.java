package fr.paris.lutece.plugins.appcenter.service.task;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDatas;
import fr.paris.lutece.plugins.appcenter.business.Demand;

@FunctionalInterface
public interface AppCenterTaskFunctional<AD extends ApplicationData, ADS extends ApplicationDatas<AD>, D extends Demand>
{

    void treatment( HttpServletRequest request, Locale locale, AD applicationData, ADS applicationDatas, D demand );

}
