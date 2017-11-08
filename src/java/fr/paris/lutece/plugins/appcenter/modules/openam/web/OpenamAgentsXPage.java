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

package fr.paris.lutece.plugins.appcenter.modules.openam.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamAgentsData;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamDemand;
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

/**
 * SourcesXPage
 */
@Controller( xpageName = "openam", pageTitleI18nKey = "module.appcenter.openam.xpage.openam.pageTitle", pagePathI18nKey = "module.appcenter.openam.xpage.openam.pagePathLabel" )
public class OpenamAgentsXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_AGENTS = "/skin/plugins/appcenter/modules/openam/manage_openam_agents.html";

    // Markers
    private static final String MARK_ENVIRONMENT_REC = "envi_rec";
    private static final String MARK_ENVIRONMENT_PROD = "envi_prod";

    // VIEW
    private static final String VIEW_MANAGE_AGENTS = "manageagents";

    // ACTION
    private static final String ACTION_ADD_AGENT = "addAgent";

    @View( value = VIEW_MANAGE_AGENTS, defaultView = true )
    public XPage getManageAgents( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {

        Application application = getApplication( request );
        OpenamAgentsData dataSubset = ApplicationService.loadApplicationDataSubset( application, OpenamAgentsData.DATA_OPENAM_AGENTS_NAME,
                OpenamAgentsData.class );

        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION, application );
        model.put( Constants.MARK_DATA, dataSubset );
        model.put( MARK_ENVIRONMENT_REC, Environment.getEnvironment( "rec" ) );
        model.put( MARK_ENVIRONMENT_PROD, Environment.getEnvironment( "prod" ) );
        model.put( MARK_USER, UserService.getCurrentUser( request, application.getId( ) ));
        addListDemand( request, application, model, OpenamDemand.ID_DEMAND_TYPE, OpenamDemand.class );

        return getXPage( TEMPLATE_MANAGE_AGENTS, request.getLocale( ), model );
    }

    @Action( ACTION_ADD_AGENT )
    public XPage doAddAgent( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = getApplication( request );

        OpenamDemand agentDemand = new OpenamDemand( );
        agentDemand.setIdApplication( application.getId( ) );

        populate( agentDemand, request );

        // Check constraints
        if ( !validateBean( agentDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_AGENTS );
        }

        DemandService.saveDemand( agentDemand, application );

        return redirect(request, VIEW_MANAGE_AGENTS, Constants.PARAM_ID_APPLICATION, nId );
    }

}
