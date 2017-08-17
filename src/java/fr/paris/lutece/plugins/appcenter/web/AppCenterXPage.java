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


package fr.paris.lutece.plugins.appcenter.web;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDemand;
import static fr.paris.lutece.plugins.appcenter.modules.sources.web.SourcesXPage.DEMAND_TYPE;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Parent
 *
 */
public class AppCenterXPage extends MVCApplication
{

    private static final long serialVersionUID = -490960650523760757L;

    public Application getApplication(HttpServletRequest request) throws UserNotSignedException
    {

        Application application;
        LuteceUser user = null;
        if (SecurityService.isAuthenticationEnable())
        {

            user = SecurityService.getInstance().getRemoteUser(request);
            if (user == null)
            {
                throw new UserNotSignedException();
            }

        }

        try
        {
            int nId = Integer.parseInt(request.getParameter(Constants.PARAMETER_ID_APPLICATION));
            application = ApplicationHome.findByPrimaryKey(nId);
            if( application == null )
            {
                addError( "Application not found" );
                return null;
            }
            if( user != null && !ApplicationHome.isAuthorized( nId, user.getEmail()) )
            {
                addError( "User not authorized" );
                return null;
            }
        }
        catch( NumberFormatException e )
        {
            addError( "Invalid application ID" );
            return null;
        }

        return application;

    }
    
    
    protected <T extends Demand> void addListDemand ( HttpServletRequest request, Application application,  Map<String, Object> model, Class<T> demandClass )
    {
        List<T> listDemand = DemandService.getDemandsListByApplicationAndType( application, DEMAND_TYPE, demandClass);
        model.put( Constants.MARK_DEMANDS, listDemand );
        int nIdWorkflow = DemandTypeService.getIdWorkflow( DEMAND_TYPE );
        Map<String, Object> mapStates = new HashMap<>();
        Map<String, Object> mapHistories = new HashMap<>();
        for (T demand: listDemand) {
            State state = WorkflowService.getInstance( ).getState( demand.getId( ), Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow, -1 );
            mapStates.put( Integer.toString( demand.getId() ), state );

            String strHistoryHtml = WorkflowService.getInstance( ).getDisplayDocumentHistory(
                    demand.getId( ), Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow, request, request.getLocale( )
            );
            mapHistories.put( Integer.toString( demand.getId( ) ), strHistoryHtml );
        }
        model.put( Constants.MARK_DEMANDS_STATES, mapStates );
        model.put( Constants.MARK_DEMANDS_HISTORIES, mapHistories );
    }
            

}
