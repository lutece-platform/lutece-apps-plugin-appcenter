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
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

/**
 * SourcesXPage
 */
@Controller( xpageName = "openam", pageTitleI18nKey = "appcenter.xpage.openam.pageTitle", pagePathI18nKey = "appcenter.xpage.openam.pagePathLabel" )
public class OpenamAgentsXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_AGENTS = "/skin/plugins/appcenter/modules/openam/manage_openam_agents.html";

    //Markers
    private static final String MARK_ENVIRONMENT_REC = "envi_rec";
    private static final String MARK_ENVIRONMENT_PROD = "envi_prod";
    
    //VIEW
    private static final String VIEW_MANAGE_AGENTS = "manageagents";
    
   //ACTION
    private static final String ACTION_ADD_AGENT= "addAgent";
    
    //PARAMETERS
    private static final String PARAMETER_APPLICATION_CODE = "application_code";
    private static final String PARAMETER_WEBAPP_NAME = "webapp_name";
    private static final String PARAMETER_PUBLIC_URL = "public_url";
    private static final String PARAMETER_ENVIRONMENT = "environment";
    
    //MESSAGE KEYS
    private static final String MESSAGE_KEY_APPLICATION_CODE = "appcenter.manage_openam_agents.applicationCode.label";
    private static final String MESSAGE_KEY_WEBAPP_NAME = "appcenter.manage_openam_agents.webappName.label";
    private static final String MESSAGE_KEY_PUBLIC_URL = "appcenter.manage_openam_agents.publicUrl.label";
    private static final String MESSAGE_KEY_ENVIRONMENT = "appcenter.manage_openam_agents.environment.label";
    
    private static final String DEMAND_TYPE="openam";
    

    @View( value = VIEW_MANAGE_AGENTS, defaultView = true )
    public XPage getManageAgents( HttpServletRequest request )  throws UserNotSignedException, SiteMessageException
    {
       
    	Application application = getApplication(request);
        OpenamAgentsData dataSubset = ApplicationService.loadApplicationDataSubset( application, OpenamAgentsData.DATA_OPENAM_AGENTS_NAME, OpenamAgentsData.class );

        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION, application );
        model.put( Constants.MARK_DATA, dataSubset );
        model.put( MARK_ENVIRONMENT_REC, Environment.getEnvironment( "rec" ) );
        model.put( MARK_ENVIRONMENT_PROD, Environment.getEnvironment( "prod" ) );
        addListDemand( request, application, model, DEMAND_TYPE );

        return getXPage( TEMPLATE_MANAGE_AGENTS, request.getLocale( ), model );
    }

    @Action( ACTION_ADD_AGENT )
    public XPage doAddAgent( HttpServletRequest request )  throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter( Constants.PARAMETER_ID_APPLICATION ) );
        Application application = getApplication(request);
        String strApplicationCode = request.getParameter( PARAMETER_APPLICATION_CODE );
        String strWebappName = request.getParameter( PARAMETER_WEBAPP_NAME );
        String strPublicUrl = request.getParameter( PARAMETER_PUBLIC_URL );
        String strEnvironment = request.getParameter( PARAMETER_ENVIRONMENT );
        
        OpenamDemand demand = new OpenamDemand();
        demand.setIdDemandType( DEMAND_TYPE );
        demand.setDemandType( DEMAND_TYPE );
        demand.setIdApplication( application.getId( ) );
        
        demand.setApplicationCodeLabelKey( MESSAGE_KEY_APPLICATION_CODE );
        demand.setWebappNameLabelKey( MESSAGE_KEY_WEBAPP_NAME );
        demand.setPublicUrlLabelKey( MESSAGE_KEY_PUBLIC_URL );
        demand.setEnvironmentLabelKey(MESSAGE_KEY_ENVIRONMENT );
        
        demand.setEnvironment( strEnvironment );
        demand.setApplicationCode( strApplicationCode );
        demand.setWebappName( strWebappName );
        demand.setPublicUrl( strPublicUrl );
        
        DemandService.saveDemand( demand, application );
        
        return redirect( request, VIEW_MANAGE_AGENTS, Constants.PARAMETER_ID_APPLICATION, nId );
    }

}
