package fr.paris.lutece.plugins.appcenter.web;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;

/**
 * 
 * Parent
 *
 */
public class AppCenterXPage extends MVCApplication {

	
	private static final long serialVersionUID = -490960650523760757L;

	public Application getApplication(HttpServletRequest request) throws UserNotSignedException {

		Application application = null;
		LuteceUser user = null;
		if (SecurityService.isAuthenticationEnable()) {

			user = SecurityService.getInstance().getRemoteUser(request);
			if (user == null) {
				throw new UserNotSignedException();
			}
			
		}
			
			int nId = Integer.parseInt(request.getParameter(Constants.PARAMETER_ID_APPLICATION));
			application = ApplicationHome.findByPrimaryKey(nId);

		
		return application;

	}

}
