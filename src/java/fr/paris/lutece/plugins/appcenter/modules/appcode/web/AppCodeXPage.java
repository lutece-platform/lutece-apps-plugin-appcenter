/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.appcenter.modules.appcode.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeData;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeDatas;
import fr.paris.lutece.plugins.appcenter.modules.appcode.business.AppCodeDemand;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USER;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceList;
import java.util.Arrays;

/**
 * AppCodeXPage
 */
@Controller( xpageName = "appcode", pageTitleI18nKey = "module.appcenter.appcode.xpage.appcode.pageTitle", pagePathI18nKey = "module.appcenter.appcode.xpage.appcode.pagePathLabel" )
public class AppCodeXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_APP_CODE = "/skin/plugins/appcenter/modules/appcode/manage_appcode.html";

    private static final String VIEW_MANAGE_APP_CODE_DEMANDS = "manageAppCodeDemands";
    private static final String ACTION_CREATE_APP_CODE_DEMAND = "doCreateAppCodeDemand";
    

    /**
     * Manage appcode view
     * @param request The HttpServletRequest
     * @return the view for managing appcode
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @View( value = VIEW_MANAGE_APP_CODE_DEMANDS, defaultView = true )
    public XPage getManageAppCodeDemands( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );

        return getXPage( TEMPLATE_MANAGE_APP_CODE, request.getLocale( ), model );
    }

    /**
     * Action : add access demand
     * @param request the HttpServletRequest
     * @return the manage view, after processing action
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @Action( ACTION_CREATE_APP_CODE_DEMAND )
    public XPage doCreateAppCodeDemand( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = getApplication( request );
        AppCodeDemand appCodeDemand = new AppCodeDemand( );
        appCodeDemand.setIdApplication( application.getId( ) );

        populate( appCodeDemand, request );
        populateCommonsDemand( appCodeDemand, request);
        
        // Check constraints
        if ( !validateBean( appCodeDemand, getLocale( request ) ) )
        {
            return redirect( request, VIEW_MANAGE_APP_CODE_DEMANDS, Constants.PARAM_ID_APPLICATION, nId );
        }

        DemandService.saveDemand( appCodeDemand, application );

        return redirect( request, VIEW_MANAGE_APP_CODE_DEMANDS, Constants.PARAM_ID_APPLICATION, nId );
    }

    @Override
    protected String getDemandType()
    {
        return AppCodeDemand.DEMAND_TYPE;
    }

    @Override
    protected Class getDemandClass()
    {
        return AppCodeDemand.class;
    }

    @Override
    protected String getDatasName()
    {
        return AppCodeDatas.DATA_APP_CODE_NAME;
    }

    @Override
    protected Class getDatasClass()
    {
        return AppCodeDatas.class;

    }
}
