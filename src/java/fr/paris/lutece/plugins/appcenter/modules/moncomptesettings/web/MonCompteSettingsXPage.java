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

package fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.web;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingDemand;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingsData;
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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * SourcesXPage
 */
@Controller( xpageName = "moncomptesettings", pageTitleI18nKey = "module.appcenter.moncomptesettings.xpage.moncomptesettings.pageTitle", pagePathI18nKey = "module.appcenter.moncomptesettings.xpage.moncomptesettings.pagePathLabel" )
public class MonCompteSettingsXPage extends AppCenterXPage
{

    private static final String MARK_ENVIRONMENT = "environment";

    // Templates
    private static final String TEMPLATE_MANAGE_MONCOMPTESETTINGS_DEMAND = "/skin/plugins/appcenter/modules/moncomptesettings/manage_moncomptesettings_demand.html";

    private static final String VIEW_MANAGE_MONCOMPTESETTINGS_DEMAND = "manageMoncompteSettingsDemand";

    private static final String ACTION_CREATE_MONCOMPTESETTINGS_DEMAND = "doCreateMonCompteSettingsDemand";

    /**
     * Returns the form to manage the MonCompteSettingsDemands
     *
     * @param request
     *            The Http request
     * @return the html form to manage MonCompteSettingsDemands
     * @throws fr.paris.lutece.portal.service.security.UserNotSignedException
     */
    @View( value = VIEW_MANAGE_MONCOMPTESETTINGS_DEMAND, defaultView = true ) 
    public XPage getManageMoncompteSettingsDemands( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );

        return getXPage( TEMPLATE_MANAGE_MONCOMPTESETTINGS_DEMAND, request.getLocale( ), model );
    }

    @Action( ACTION_CREATE_MONCOMPTESETTINGS_DEMAND )
    public XPage doCreateMonCompteSettingsDemand( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = ApplicationHome.findByPrimaryKey( nId );

        MonCompteSettingDemand demand = new MonCompteSettingDemand( );
        demand.setIdApplication( application.getId( ) );
        
        populate( demand, request );
        populateCommonsDemand( demand, request);

        // Check constraints
        if ( !validateBean( demand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_MONCOMPTESETTINGS_DEMAND );
        }

        DemandService.saveDemand( demand, application );

        return redirect(request, VIEW_MANAGE_MONCOMPTESETTINGS_DEMAND, Constants.PARAM_ID_APPLICATION, nId );
    }

    @Override
    protected String getDemandType()
    {
        return MonCompteSettingDemand.DEMAND_TYPE;
    }

    @Override
    protected Class getDemandClass()
    {
        return MonCompteSettingDemand.class;
    }

    @Override
    protected String getDatasName()
    {
        return MonCompteSettingsData.DATA_SUBSET_NAME;
    }

    @Override
    protected Class getDatasClass()
    {
        return MonCompteSettingsData.class;
    }
}
