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

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.UserApplication;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationHome;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.appcenter.service.RoleService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage Application xpages ( manage, create, modify, remove )
 */
@Controller( xpageName = "application", pageTitleI18nKey = "appcenter.xpage.application.pageTitle", pagePathI18nKey = "appcenter.xpage.application.pagePathLabel" )
public class ApplicationXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_APPLICATIONS = "/skin/plugins/appcenter/manage_applications.html";
    private static final String TEMPLATE_CREATE_APPLICATION = "/skin/plugins/appcenter/create_application.html";
    private static final String TEMPLATE_MODIFY_APPLICATION = "/skin/plugins/appcenter/modify_application.html";

    // Message
    private static final String MESSAGE_CONFIRM_REMOVE_APPLICATION = "appcenter.message.confirmRemoveApplication";

    // Views
    private static final String VIEW_MANAGE_APPLICATIONS = "manageApplications";
    private static final String VIEW_CREATE_APPLICATION = "createApplication";
    private static final String VIEW_MODIFY_APPLICATION = "modifyApplication";

    // Actions
    private static final String ACTION_CREATE_APPLICATION = "createApplication";
    private static final String ACTION_MODIFY_APPLICATION = "modifyApplication";
    private static final String ACTION_REMOVE_APPLICATION = "removeApplication";
    private static final String ACTION_CONFIRM_REMOVE_APPLICATION = "confirmRemoveApplication";

    // Infos
    private static final String INFO_APPLICATION_CREATED = "appcenter.info.application.created";
    private static final String INFO_APPLICATION_UPDATED = "appcenter.info.application.updated";
    private static final String INFO_APPLICATION_REMOVED = "appcenter.info.application.removed";

    private static final String MESSAGE_INVALID_ROLE_LEVEL = "appcenter.error.invalidRoleLevel";

    // Session variable to store working values
    private Application _application;

    @View( value = VIEW_MANAGE_APPLICATIONS, defaultView = true )
    public XPage getManageApplications( HttpServletRequest request )
    {
        _application = null;
        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION_LIST, ApplicationHome.getApplicationsList( ) );

        return getXPage( TEMPLATE_MANAGE_APPLICATIONS, request.getLocale( ), model );
    }

    /**
     * Returns the form to create a application
     *
     * @param request
     *            The Http request
     * @return the html code of the application form
     */
    @View( VIEW_CREATE_APPLICATION )
    public XPage getCreateApplication( HttpServletRequest request )
    {
        _application = ( _application != null ) ? _application : new Application( );

        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION, _application );

        return getXPage( TEMPLATE_CREATE_APPLICATION, request.getLocale( ), model );
    }

    /**
     * Process the data capture form of a new application
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_APPLICATION )
    public XPage doCreateApplication( HttpServletRequest request )
    {
        populate( _application, request );

        // Check constraints
        if ( !validateBean( _application, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_CREATE_APPLICATION );
        }

        ApplicationHome.create( _application );
        UserApplication authorization = new UserApplication( );
        authorization.setId( _application.getId( ) );
        authorization.setUserId( UserService.getCurrentUserId( request ) );
        authorization.setUserRole( RoleService.ROLE_OWNER );
        UserApplicationHome.create( authorization );

        addInfo( INFO_APPLICATION_CREATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * Manages the removal form of a application whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException
     */
    @Action( ACTION_CONFIRM_REMOVE_APPLICATION )
    public XPage getConfirmRemoveApplication( HttpServletRequest request ) throws SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter( Constants.PARAMETER_ID_APPLICATION ) );
        String strUserId = UserService.getCurrentUserId( request );
        int nRole = ApplicationHome.getUserRole( nId, strUserId );
        if ( nRole != RoleService.ROLE_ADMIN && nRole != RoleService.ROLE_OWNER )
        {
            addError( getMessage( MESSAGE_INVALID_ROLE_LEVEL ) );
            return redirect( request, VIEW_MODIFY_APPLICATION, Constants.PARAMETER_ID_APPLICATION, nId );
        }

        UrlItem url = new UrlItem( Constants.JSP_PAGE_PORTAL );
        url.addParameter( Constants.PARAM_PAGE, Constants.MARK_APPLICATION );
        url.addParameter( Constants.PARAM_ACTION, ACTION_REMOVE_APPLICATION );
        url.addParameter( Constants.PARAMETER_ID_APPLICATION, nId );

        SiteMessageService.setMessage( request, MESSAGE_CONFIRM_REMOVE_APPLICATION, SiteMessage.TYPE_CONFIRMATION, url.getUrl( ) );
        return null;
    }

    /**
     * Handles the removal form of a application
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage applications
     */
    @Action( ACTION_REMOVE_APPLICATION )
    public XPage doRemoveApplication( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( Constants.PARAMETER_ID_APPLICATION ) );
        ApplicationHome.remove( nId );
        addInfo( INFO_APPLICATION_REMOVED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * Returns the form to update info about a application
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     * @throws UserNotSignedException
     *             If user not connected
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException
     */
    @View( VIEW_MODIFY_APPLICATION )
    public XPage getModifyApplication( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        _application = getApplication( request );

        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION, _application );

        List<Demand> listDemand = DemandHome.getDemandsListByApplication( _application.getId( ) ); // TODO filter on active state, not all demands ?
        model.put( Constants.MARK_DEMANDS, listDemand );

        Map<String, Object> mapStates = new HashMap<>( );
        Map<String, Object> mapHistories = new HashMap<>( );
        for ( Demand demand : listDemand )
        {
            String strWorkflowResourceType = DemandTypeService.getWorkflowResourceType( demand.getIdDemandType( ) );
            int nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getIdDemandType( ) );
            State state = WorkflowService.getInstance( ).getState( demand.getId( ), strWorkflowResourceType, nIdWorkflow, -1 );
            mapStates.put( Integer.toString( demand.getId( ) ), state );

            String strHistoryHtml = WorkflowService.getInstance( ).getDisplayDocumentHistory( demand.getId( ), strWorkflowResourceType, nIdWorkflow, request,
                    request.getLocale( ) );
            mapHistories.put( Integer.toString( demand.getId( ) ), strHistoryHtml );
        }

        String strUserId = UserService.getCurrentUserId( request );
        int nRole = ApplicationHome.getUserRole( _application.getId( ), strUserId );
        boolean bAdminRole = ( nRole == RoleService.ROLE_ADMIN ) || ( nRole == RoleService.ROLE_OWNER );
        model.put( Constants.MARK_DEMANDS_STATES, mapStates );
        model.put( Constants.MARK_DEMANDS_HISTORIES, mapHistories );
        model.put( Constants.MARK_ADMIN_ROLE, bAdminRole );

        return getXPage( TEMPLATE_MODIFY_APPLICATION, request.getLocale( ), model );
    }

    /**
     * Process the change form of a application
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_APPLICATION )
    public XPage doModifyApplication( HttpServletRequest request )
    {
        populate( _application, request );

        // Check constraints
        if ( !validateBean( _application, getLocale( request ) ) )
        {
            return redirect( request, VIEW_MODIFY_APPLICATION, Constants.PARAMETER_ID_APPLICATION, _application.getId( ) );
        }

        ApplicationHome.update( _application );
        addInfo( INFO_APPLICATION_UPDATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }
}
