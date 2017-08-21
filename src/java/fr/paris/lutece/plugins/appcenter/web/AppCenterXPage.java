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
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.paris.lutece.portal.web.l10n.LocaleService;

/**
 *
 * Parent
 *
 */
public class AppCenterXPage extends MVCApplication
{
    private static final String ERROR_APP_NOT_FOUND = "appcenter.error.applicationNotFound";
    private static final String ERROR_USER_NOT_AUTHORIZED = "appcenter.error.userNotAuthorized";
    private static final String ERROR_INVALID_APP_ID = "appcenter.error.invalidAppId";

    private static final long serialVersionUID = -490960650523760757L;

    /**
     * Get the current application
     * 
     * @param request
     *            The HTTP request
     * @return The application
     * @throws UserNotSignedException
     *             If the user is not signed
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException
     *             if an error occurs
     */
    protected Application getApplication( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {

        Application application = null;
        LuteceUser user = null;
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            user = SecurityService.getInstance( ).getRemoteUser( request );
            if ( user == null )
            {
                throw new UserNotSignedException( );
            }
        }

        try
        {
            int nId = Integer.parseInt( request.getParameter( Constants.PARAMETER_ID_APPLICATION ) );
            application = ApplicationHome.findByPrimaryKey( nId );
            if ( application == null )
            {
                SiteMessageService.setMessage( request, ERROR_APP_NOT_FOUND, SiteMessage.TYPE_ERROR );
            }
            if ( user != null && !ApplicationHome.isAuthorized( nId, user.getEmail( ) ) )
            {
                SiteMessageService.setMessage( request, ERROR_USER_NOT_AUTHORIZED, SiteMessage.TYPE_ERROR );
            }
        }
        catch( NumberFormatException e )
        {
            SiteMessageService.setMessage( request, ERROR_INVALID_APP_ID, SiteMessage.TYPE_ERROR );
        }

        return application;

    }

    /**
     * Add a demand
     * 
     * @param <T>
     *            The object template
     * @param request
     *            The HTTP request
     * @param application
     *            The aapplication
     * @param model
     *            The model
     * @param strDemandType
     *            the demand type
     * @param demandClass
     *            The demand class
     */
    protected <T extends Demand> void addListDemand( HttpServletRequest request, Application application, Map<String, Object> model, String strDemandType,
            Class<T> demandClass )
    {
        List<T> listDemand = DemandService.getDemandsListByApplicationAndType( application, strDemandType, demandClass );
        model.put( Constants.MARK_DEMANDS, listDemand );
        int nIdWorkflow = DemandTypeService.getIdWorkflow( strDemandType );
        Map<String, Object> mapStates = new HashMap<>( );
        Map<String, Object> mapHistories = new HashMap<>( );
        for ( T demand : listDemand )
        {
            State state = WorkflowService.getInstance( ).getState( demand.getId( ), Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow, -1 );
            mapStates.put( Integer.toString( demand.getId( ) ), state );

            String strHistoryHtml = WorkflowService.getInstance( ).getDisplayDocumentHistory( demand.getId( ), Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow,
                    request, request.getLocale( ) );
            mapHistories.put( Integer.toString( demand.getId( ) ), strHistoryHtml );
        }
        model.put( Constants.MARK_DEMANDS_STATES, mapStates );
        model.put( Constants.MARK_DEMANDS_HISTORIES, mapHistories );
    }

    /**
     * Get a message from message bundle files
     * 
     * @param strMessageKey
     *            The message key
     * @return The message
     */
    protected String getMessage( String strMessageKey )
    {
        return I18nService.getLocalizedString( strMessageKey, LocaleService.getDefault( ) );
    }

}
