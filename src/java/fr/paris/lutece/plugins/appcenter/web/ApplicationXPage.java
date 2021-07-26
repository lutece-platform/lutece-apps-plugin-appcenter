/*
 * Copyright (c) 2002-2019, Mairie de Paris
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

import static fr.paris.lutece.plugins.appcenter.web.Constants.JSP_PAGE_PORTAL;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_APPLICATION;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_APPLICATION_LIST;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_DEMANDS;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_DEMANDS_HISTORIES;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_DEMANDS_STATES;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USER;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USERS_LIST;
import static fr.paris.lutece.plugins.appcenter.web.Constants.PARAM_ACTION;
import static fr.paris.lutece.plugins.appcenter.web.Constants.PARAM_ID_APPLICATION;
import static fr.paris.lutece.plugins.appcenter.web.Constants.PARAM_PAGE;
import static fr.paris.lutece.plugins.appcenter.web.Constants.PARAM_USER_EMAIL;
import static fr.paris.lutece.plugins.appcenter.web.Constants.PARAM_USER_ROLE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDemandTypesEnable;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.CategoryDemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.DocumentationCategory;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.business.User;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRole;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRoleHome;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationHome;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManager;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManagerHome;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.AuthorizationService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.appcenter.service.EnvironmentService;
import fr.paris.lutece.plugins.appcenter.service.RoleService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.appcenter.util.AppCenterUtils;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;

/**
 * This class provides the user interface to manage Application xpages ( manage, createOrModify, modify, remove )
 */
@Controller( xpageName = "application", pageTitleI18nKey = "appcenter.xpage.application.pageTitle", pagePathI18nKey = "appcenter.xpage.application.pagePathLabel" )
public class ApplicationXPage extends AppCenterDemandXPage
{

    // Markers
    private static final String MARK_ORGANIZATIONS = "list_organizations";
    private static final String MARK_ORGANIZATION_MANAGERS = "list_organization_managers";
    private static final String MARK_ENVIRONMENTS = "environments";
    private static final String MARK_ACTIVE_ENVIRONMENT = "active_environment";
    private static final String MARK_CATEGORY_DEMAND_TYPE_LIST = "categorydemandtype_list";
    private static final String MARK_DEMAND_TYPE_LIST = "demandtype_list";
    private static final String MARK_DOCUMENTATION_CATEGORIES = "documentation_categories";
    private static final String MARK_CATEGORY_DEMAND_TYPES = "category_demand_types";
    private static final String MARK_DEMAND_TYPES = "demand_types";
    private static final String MARK_ACTIVE_DEMAND_TYPES = "active_demand_types";
    private static final String MARK_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION = "manage_multiple_deploy_configuration";
    

    // Templates
    private static final String TEMPLATE_MANAGE_APPLICATIONS = "/skin/plugins/appcenter/manage_applications.html";
    private static final String TEMPLATE_CREATE_APPLICATION = "/skin/plugins/appcenter/create_application.html";
    private static final String TEMPLATE_MODIFY_APPLICATION = "/skin/plugins/appcenter/modify_application.html";
    private static final String TEMPLATE_VIEW_DEMANDS = "/skin/plugins/appcenter/view_demands.html";

    // Message
    private static final String MESSAGE_CONFIRM_REMOVE_APPLICATION = "appcenter.message.confirmRemoveApplication";

    // Views
    private static final String VIEW_MANAGE_APPLICATIONS = "manageApplications";
    private static final String VIEW_CREATE_APPLICATION = "createApplication";
    private static final String VIEW_MODIFY_APPLICATION = "modifyApplication";
    private static final String VIEW_DEMANDS = "viewDemands";

    // Actions
    private static final String ACTION_CREATE_APPLICATION = "createApplication";
    private static final String ACTION_MODIFY_APPLICATION = "modifyApplication";
    private static final String ACTION_REMOVE_APPLICATION = "removeApplication";
    private static final String ACTION_CONFIRM_REMOVE_APPLICATION = "confirmRemoveApplication";
    private static final String ACTION_ADD_USER = "addUser";
    private static final String ACTION_REMOVE_USER = "removeUser";

    // Infos
    private static final String INFO_APPLICATION_CREATED = "appcenter.info.application.created";
    private static final String INFO_APPLICATION_UPDATED = "appcenter.info.application.updated";
    private static final String INFO_APPLICATION_REMOVED = "appcenter.info.application.removed";
    private static final String INFO_USER_ADDED = "appcenter.info.user.added";
    private static final String INFO_USER_REMOVED = "appcenter.info.user.removed";

    // Errors
    private static final String ERROR_USER_ROLE_NOT_AUTHORIZED = "appcenter.error.userRoleNotAuthorized";
    private static final String ERROR_INVALID_VALUE = "appcenter.error.invalidValue";
    private static final String ERROR_APPLICATION_CODE_ALREADY_USED = "appcenter.error.application.code.alreadyUsed";

    // Parameters
    private static final String PARAMETER_ACTIVE_ENVIRONMENT = "active_environment";
    private static final String PARAMETER_ID_ORGANIZATION_MANAGER = "id_organization_manager";

    // Session
    public static final String SESSION_ACTIVE_ENVIRONMENT = "active_environment";

    // Permissions
    private static final String PERMISSION_VIEW_APPLICATION = "PERMISSION_VIEW_APPLICATION";
    private static final String PERMISSION_MODIFY_APPLICATION = "PERMISSION_MODIFY_APPLICATION";
    private static final String PERMISSION_REMOVE_APPLICATION = "PERMISSION_REMOVE_APPLICATION";
    private static final String PERMISSION_VIEW_DEMANDS = "PERMISSION_VIEW_DEMANDS";
    private static final String PERMISSION_ADD_USERS = "PERMISSION_ADD_USERS";
    private static final String PERMISSION_REMOVE_USER = "PERMISSION_REMOVE_USER";
    private static final String PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION= "PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION";
    


    private static final String JSON_EMPTY = "{}";

    private Environment _activeEnvironment;

    @View( value = VIEW_MANAGE_APPLICATIONS, defaultView = true )
    public XPage getManageApplications( HttpServletRequest request ) throws UserNotSignedException
    {
        _application = null;
        Map<String, Object> model = getModel( );

        LuteceUser user = null;
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            user = SecurityService.getInstance( ).getRemoteUser( request );
            if ( user == null )
            {
                throw new UserNotSignedException( );
            }
            model.put( MARK_APPLICATION_LIST, ApplicationHome.getApplicationsByUser( UserService.getEmailUser( user ) ) );
            model.put(MARK_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION,isAuthorizedManageMultipleDeployConfiguration(request));
            return getXPage( TEMPLATE_MANAGE_APPLICATIONS, request.getLocale( ), model );
        }

        model.put( MARK_APPLICATION_LIST, ApplicationHome.getApplicationsList( ) );

        return getXPage( TEMPLATE_MANAGE_APPLICATIONS, request.getLocale( ), model );
    }

    /**
     * Returns the form to createOrModify a application
     *
     * @param request
     *            The Http request
     * @return the html code of the application form
     */
    @View( VIEW_CREATE_APPLICATION )
    public XPage getCreateApplication( HttpServletRequest request )
    {
        _application = ( _application != null ) ? _application : new Application( );

        ReferenceList organizationsList = OrganizationHome.getOrganizationsReferenceList( );
        AppCenterUtils.addEmptyItem( organizationsList, getLocale( request ) );

        Map<String, Object> model = getModel( );
        model.put( MARK_CATEGORY_DEMAND_TYPES, CategoryDemandTypeHome.getCategoryDemandTypesList( ) );
        model.put( MARK_DEMAND_TYPES, DemandTypeHome.getDemandTypesList( ) );
        model.put( MARK_APPLICATION, _application );
        model.put( MARK_ORGANIZATIONS, organizationsList );
        model.put( MARK_ORGANIZATION_MANAGERS, OrganizationManagerHome.getOrganizationManagersList( ) );
        model.put( MARK_ENVIRONMENTS, ReferenceList.convert( Arrays.asList( Environment.values( ) ), "prefix", "labelKey", false ) );

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
    public XPage doCreateApplication( HttpServletRequest request ) throws UserNotSignedException
    {
        populate( _application, request );

        if( StringUtils.isNotEmpty( _application.getCode( ) ) && ApplicationHome.findByCode( _application.getCode( ) ) != null )
        {
            addError( ERROR_APPLICATION_CODE_ALREADY_USED, getLocale( request ) );
            return redirectView( request, VIEW_CREATE_APPLICATION );
        }

        _application.setListEnvironment( EnvironmentService.getEnvironmentList( request ) );
        _application.setApplicationData( JSON_EMPTY );

        String strIdOrganizationManager = request.getParameter( PARAMETER_ID_ORGANIZATION_MANAGER );
        if ( strIdOrganizationManager!=null && !strIdOrganizationManager.isEmpty( ) )
        {
            int nIdOrganizationManager = Integer.parseInt( strIdOrganizationManager );
            OrganizationManager organizationManager = OrganizationManagerHome.findByPrimaryKey( nIdOrganizationManager );
            _application.setOrganizationManager( organizationManager );
        }

        // Check constraints
        if ( !validateBean( _application, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_CREATE_APPLICATION );
        }

        ApplicationHome.create( _application );

        ApplicationDemandTypesEnable appDemandTypeIdEnabled = new ApplicationDemandTypesEnable( );
        for ( Map.Entry<String, String [ ]> entry : request.getParameterMap( ).entrySet( ) )
        {
            if ( entry.getKey( ).startsWith( "demandtype_" ) )
            {
                String strDemandType = entry.getKey( ).split( "demandtype_" ) [1];
                appDemandTypeIdEnabled.addDemandTypeEnabled( strDemandType );
            }
        }

        ApplicationService.saveApplicationData( _application, appDemandTypeIdEnabled );

        User user = UserService.getCurrentUserInAppContext( request, _application.getId( ) );
        UserApplicationRole authorization = new UserApplicationRole( );
        authorization.setIdApplication( _application.getId( ) );
        authorization.setIdUser( user.getId( ) );
        authorization.setIdRole( RoleService.getAppOwnerRole( ).getKey( ) );
        UserApplicationRoleHome.create( authorization );

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
    public XPage getConfirmRemoveApplication( HttpServletRequest request ) throws SiteMessageException, AccessDeniedException, UserNotSignedException
    {
        try
        {
            _application = getApplication( request );
            checkPermission( request, PERMISSION_REMOVE_APPLICATION, null );
        }
        catch( AccessDeniedException e )
        {
            getUnauthorizedAccessMessage( request );
        }

        UrlItem url = new UrlItem( JSP_PAGE_PORTAL );
        url.addParameter( PARAM_PAGE, MARK_APPLICATION );
        url.addParameter( PARAM_ACTION, ACTION_REMOVE_APPLICATION );
        url.addParameter( PARAM_ID_APPLICATION, _application.getId( ) );

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
    public XPage doRemoveApplication( HttpServletRequest request ) throws SiteMessageException, AccessDeniedException, UserNotSignedException
    {
        try
        {
            _application = getApplication( request );
            checkPermission( request, PERMISSION_REMOVE_APPLICATION, null );
        }
        catch( AccessDeniedException e )
        {
            getUnauthorizedAccessMessage( request );
        }

        ApplicationService.remove( _application.getId( ) );
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
    public XPage getModifyApplication( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException, UserNotSignedException
    {
        try
        {
            _application = getApplication( request );
            checkPermission( request, PERMISSION_VIEW_APPLICATION, null );
        }
        catch( AccessDeniedException e )
        {
            getUnauthorizedAccessMessage( request );
        }

        // Set the active environment
        String strActiveEnvi = request.getParameter( PARAMETER_ACTIVE_ENVIRONMENT );
        if ( strActiveEnvi != null )
        {
            if ( strActiveEnvi != "no_active_environment" )
            {
                _activeEnvironment = Environment.getEnvironment( strActiveEnvi );
                // Put this active environment in session
                HttpSession session = request.getSession( true );
                session.setAttribute( SESSION_ACTIVE_ENVIRONMENT, _activeEnvironment );
            }
            else
            {
                HttpSession session = request.getSession( true );
                session.removeAttribute( SESSION_ACTIVE_ENVIRONMENT );
            }
        }

        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );
        model.put( MARK_APPLICATION, _application );
        model.put( MARK_ACTIVE_DEMAND_TYPES,
                ApplicationService.loadApplicationDataSubset( _application, ApplicationDemandTypesEnable.DATA_SUBSET_NAME, ApplicationDemandTypesEnable.class ) );
        model.put( MARK_CATEGORY_DEMAND_TYPE_LIST, CategoryDemandTypeHome.getCategoryDemandTypesList( ) );
        model.put( MARK_DEMAND_TYPE_LIST, DemandTypeHome.getDemandTypesList( ) );
        model.put( MARK_DOCUMENTATION_CATEGORIES,
                Arrays.asList( DocumentationCategory.values( ) ).stream( ).collect( Collectors.toMap( DocumentationCategory::getPrefix, docCat -> docCat ) ) );
        model.put( MARK_ENVIRONMENTS, ReferenceList.convert( Arrays.asList( Environment.values( ) ), "prefix", "labelKey", false ) );
        List<? extends Demand> listFullDemands = DemandHome.getListFullDemandsByIdApplication( _application.getId( ) );
        model.put( MARK_DEMANDS, listFullDemands );
        model.put( MARK_USERS_LIST, UserApplicationRoleHome.getUserApplicationRolesListByIdApplication( _application.getId( ) ) );
        if ( _activeEnvironment != null )
        {
            model.put( MARK_ACTIVE_ENVIRONMENT, _activeEnvironment );
        }
        Map<String, Object> mapStates = new HashMap<>( );
        Map<String, Object> mapHistories = new HashMap<>( );
        for ( Demand demand : listFullDemands )
        {
            String strWorkflowResourceType = DemandTypeService.getWorkflowResourceType( demand.getIdDemandType( ) );
            int nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getIdDemandType( ) );
            State state = WorkflowService.getInstance( ).getState( demand.getId( ), strWorkflowResourceType, nIdWorkflow, -1 );
            mapStates.put( Integer.toString( demand.getId( ) ), state );

            String strHistoryHtml = WorkflowService.getInstance( ).getDisplayDocumentHistory( demand.getId( ), strWorkflowResourceType, nIdWorkflow, request,
                    request.getLocale( ) );
            mapHistories.put( Integer.toString( demand.getId( ) ), strHistoryHtml );
        }

        model.put( MARK_DEMANDS_STATES, mapStates );
        model.put( MARK_DEMANDS_HISTORIES, mapHistories );
        model.put( MARK_USER, UserService.getCurrentUserInAppContext( request, _application.getId( ) ) );

        return getXPage( TEMPLATE_MODIFY_APPLICATION, request.getLocale( ), model );
    }

    @View( VIEW_DEMANDS )
    public XPage getViewDemands( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException, UserNotSignedException,
            AccessDeniedException
    {
        try
        {
            _application = getApplication( request );
            checkPermission( request, PERMISSION_VIEW_DEMANDS, null );
        }
        catch( AccessDeniedException e )
        {
            getUnauthorizedAccessMessage( request );
        }

        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );
        model.put( MARK_APPLICATION, _application );
        model.put( MARK_ACTIVE_DEMAND_TYPES,
                ApplicationService.loadApplicationDataSubset( _application, ApplicationDemandTypesEnable.DATA_SUBSET_NAME, ApplicationDemandTypesEnable.class ) );
        model.put( MARK_CATEGORY_DEMAND_TYPE_LIST, CategoryDemandTypeHome.getCategoryDemandTypesList( ) );
        model.put( MARK_DEMAND_TYPE_LIST, DemandTypeHome.getDemandTypesList( ) );
        model.put( MARK_ENVIRONMENTS, ReferenceList.convert( Arrays.asList( Environment.values( ) ), "prefix", "labelKey", false ) );
        List<? extends Demand> listFullDemands = DemandHome.getListFullDemandsByIdApplication( _application.getId( ) );
        model.put( MARK_DEMANDS, listFullDemands );
        model.put( MARK_USERS_LIST, UserApplicationRoleHome.getUserApplicationRolesListByIdApplication( _application.getId( ) ) );
        Map<String, Object> mapStates = new HashMap<>( );
        Map<String, Object> mapHistories = new HashMap<>( );
        for ( Demand demand : listFullDemands )
        {
            String strWorkflowResourceType = DemandTypeService.getWorkflowResourceType( demand.getIdDemandType( ) );
            int nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getIdDemandType( ) );
            State state = WorkflowService.getInstance( ).getState( demand.getId( ), strWorkflowResourceType, nIdWorkflow, -1 );
            mapStates.put( Integer.toString( demand.getId( ) ), state );

            String strHistoryHtml = WorkflowService.getInstance( ).getDisplayDocumentHistory( demand.getId( ), strWorkflowResourceType, nIdWorkflow, request,
                    request.getLocale( ) );
            mapHistories.put( Integer.toString( demand.getId( ) ), strHistoryHtml );
        }

        model.put( MARK_DEMANDS_STATES, mapStates );
        model.put( MARK_DEMANDS_HISTORIES, mapHistories );
        model.put( MARK_USER, UserService.getCurrentUserInAppContext( request, _application.getId( ) ) );

        return getXPage( TEMPLATE_VIEW_DEMANDS, request.getLocale( ), model );
    }

    /**
     * Process the change form of a application
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_APPLICATION )
    public XPage doModifyApplication( HttpServletRequest request ) throws SiteMessageException, UserNotSignedException, AccessDeniedException
    {

        try
        {
            checkPermission( request, PERMISSION_MODIFY_APPLICATION, null );
        }
        catch( AccessDeniedException e )
        {
            getUnauthorizedAccessMessage( request );
        }

        populate( _application, request );

        if( StringUtils.isNotEmpty( _application.getCode( ) )
                && !ApplicationHome.findByPrimaryKey( _application.getId( ) ).getCode( ).equals( _application.getCode( ) )
                && ApplicationHome.findByCode( _application.getCode( ) ) != null )
        {
            addError( ERROR_APPLICATION_CODE_ALREADY_USED, getLocale( request ) );
            return redirect( request, VIEW_MODIFY_APPLICATION, PARAM_ID_APPLICATION, _application.getId( ) );
        }

        _application.setListEnvironment( EnvironmentService.getEnvironmentList( request ) );

        String strIdOrganizationManager = request.getParameter( PARAMETER_ID_ORGANIZATION_MANAGER );
        if ( strIdOrganizationManager!=null && !strIdOrganizationManager.isEmpty( ) )
        {
            int nIdOrganizationManager = Integer.parseInt( strIdOrganizationManager );
            OrganizationManager organizationManager = OrganizationManagerHome.findByPrimaryKey( nIdOrganizationManager );
            _application.setOrganizationManager( organizationManager );
        }

        // Check constraints
        if ( !validateBean( _application, getLocale( request ) ) )
        {
            return redirect( request, VIEW_MODIFY_APPLICATION, PARAM_ID_APPLICATION, _application.getId( ) );
        }

        ApplicationHome.update( _application );

        ApplicationDemandTypesEnable appDemandTypeIdEnabled = new ApplicationDemandTypesEnable( );
        for ( Map.Entry<String, String [ ]> entry : request.getParameterMap( ).entrySet( ) )
        {
            if ( entry.getKey( ).startsWith( "demandtype_" ) )
            {
                String strDemandType = entry.getKey( ).split( "demandtype_" ) [1];
                appDemandTypeIdEnabled.addDemandTypeEnabled( strDemandType );
            }
        }

        ApplicationService.saveApplicationData( _application, appDemandTypeIdEnabled );

        addInfo( INFO_APPLICATION_UPDATED, getLocale( request ) );

        return redirect( request, VIEW_MODIFY_APPLICATION, PARAM_ID_APPLICATION, _application.getId( ) );
    }

    /**
     * Add a User
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws fr.paris.lutece.portal.service.security.UserNotSignedException
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException
     */
    @Action( ACTION_ADD_USER )
    public XPage doAddUser( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException, UserNotSignedException
    {
        try
        {
            _application = getApplication( request );
            checkPermission( request, PERMISSION_ADD_USERS, null );
        }
        catch( AccessDeniedException e )
        {
            getUnauthorizedAccessMessage( request );
        }

        UserApplicationRole authorization = new UserApplicationRole( );

        populate( authorization, request );
        authorization.setIdApplication( _application.getId( ) );

        // Check constraints
        if ( !validateBean( authorization ) || request.getParameter( PARAM_USER_ROLE ).isEmpty( ) )
        {
            addError( ERROR_INVALID_VALUE, getLocale( request ) );
            return redirect( request, VIEW_MODIFY_APPLICATION, PARAM_ID_APPLICATION, _application.getId( ) );
        }

        UserApplicationRoleHome.create( authorization );

        addInfo( INFO_USER_ADDED, getLocale( request ) );

        return redirect( request, VIEW_MODIFY_APPLICATION, PARAM_ID_APPLICATION, _application.getId( ) );
    }

    /**
     * Remove a User
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws fr.paris.lutece.portal.service.security.UserNotSignedException
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException
     */
    @Action( ACTION_REMOVE_USER )
    public XPage doRemoveUser( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException, UserNotSignedException
    {
        try
        {
            Application application = getApplication( request );
            checkPermission( request, PERMISSION_REMOVE_USER, null );
        }
        catch( AccessDeniedException e )
        {
            getUnauthorizedAccessMessage( request );
        }

        String strUserEmail = request.getParameter( PARAM_USER_EMAIL );
        UserApplicationRoleHome.removeByApplicationIdAndUserId( _application.getId( ), strUserEmail );

        addInfo( INFO_USER_REMOVED, getLocale( request ) );

        return redirect( request, VIEW_MODIFY_APPLICATION, PARAM_ID_APPLICATION, _application.getId( ) );
    }

    @Override
    protected String getDemandType( )
    {
        return null;
    }

    @Override
    protected Class getDemandClass( )
    {
        return null;
    }

    @Override
    protected String getDatasName( )
    {
        return null;
    }

    @Override
    protected Class getDatasClass( )
    {
        return null;
    }
    
    private boolean isAuthorizedManageMultipleDeployConfiguration(HttpServletRequest request)
    {
    	
    	String strUserId = UserService.getCurrentUserId(request);	
		return AuthorizationService.isAuthorized( strUserId, 0,PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION, "*" );	
    	
    }
    

}
