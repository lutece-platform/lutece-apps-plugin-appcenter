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

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDatas;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDemandTypesEnable;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.CategoryDemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.DocumentationCategory;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.business.RoleHome;
import fr.paris.lutece.plugins.appcenter.business.User;
import fr.paris.lutece.plugins.appcenter.service.ActionService;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.AuthorizationService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.appcenter.service.ResourceTypeConfig;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.util.mvc.utils.MVCUtils;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import java.util.List;
import java.util.Map;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import fr.paris.lutece.util.ReferenceList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;

/**
 *
 * Parent
 *
 */
public abstract class AppCenterXPage extends MVCApplication
{
    private static final String ERROR_APP_NOT_FOUND = "appcenter.error.applicationNotFound";
    private static final String ERROR_USER_NOT_AUTHORIZED = "appcenter.error.userNotAuthorized";
    private static final String ERROR_INVALID_APP_ID = "appcenter.error.invalidAppId";

    private static final String MARK_ENVIRONMENTS = "environments";
    private static final String MARK_ACTIVE_ENVIRONMENT = "active_environment";
    private static final String MARK_APPLICATION = "application";
    private static final String MARK_CATEGORY_DEMAND_TYPE_LIST = "categorydemandtype_list";
    private static final String MARK_DEMAND_TYPE_LIST = "demandtype_list";
    private static final String MARK_DOCUMENTATION_CATEGORIES = "documentation_categories";
    private static final String MARK_ACTIVE_DEMAND_TYPES = "active_demand_types";
    private static final String MARK_ACTIVE_DEMAND_TYPE = "active_demand_type";

    private static final long serialVersionUID = -490960650523760757L;

    protected Application _application;

    /**
     * Get the current application
     * 
     * @param request
     *            The HTTP request
     * @return The application
     */
    protected Application getApplication( HttpServletRequest request )
    {
        try
        {
            int nId = Integer.parseInt( request.getParameter( Constants.PARAM_ID_APPLICATION ) );
            _application = ApplicationHome.findByPrimaryKey( nId );
        }
        catch( NumberFormatException e )
        {

        }

        if ( _application == null )
        {
            redirect( request, AppPathService.getBaseUrl( request ) + AppPathService.getPortalUrl( ) + "?" + MVCUtils.PARAMETER_PAGE + "="
                    + Constants.XPAGE_APPLICATION );
        }

        return _application;
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
     */
    protected <T extends Demand> void addListDemand( HttpServletRequest request, Application application, Map<String, Object> model )
    {
        List<T> listDemand = DemandHome.getListFullDemandsByIdApplication( application.getId( ) );

        model.put( MARK_ACTIVE_DEMAND_TYPE, getDemandType( ) );
        model.put( Constants.MARK_DEMANDS, listDemand );

        Map<String, Object> mapStates = new HashMap<>( );
        Map<String, Object> mapHistories = new HashMap<>( );
        for ( T demand : listDemand )
        {
            int nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getDemandType( ) );

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
     * return the current environment choose
     */
    protected Environment getActiveEnvironment( HttpServletRequest request )
    {
        HttpSession session = request.getSession( true );
        Environment environment = (Environment) session.getAttribute( ApplicationXPage.SESSION_ACTIVE_ENVIRONMENT );
        return environment;

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

    @Override
    protected void fillCommons( Map<String, Object> model )
    {
        super.fillCommons( model );
        model.put( MARK_ENVIRONMENTS, ReferenceList.convert( Arrays.asList( Environment.values( ) ), "prefix", "labelKey", false ) );
        model.put( MARK_CATEGORY_DEMAND_TYPE_LIST, CategoryDemandTypeHome.getCategoryDemandTypesList( ) );
        model.put( MARK_DEMAND_TYPE_LIST, DemandTypeHome.getDemandTypesList( ) );
        model.put( MARK_DOCUMENTATION_CATEGORIES,
                Arrays.asList( DocumentationCategory.values( ) ).stream( ).collect( Collectors.toMap( DocumentationCategory::getPrefix, docCat -> docCat ) ) );
        model.put( Constants.MARK_ROLES_LIST, RoleHome.getRolesReferenceList( ) );

    }

    protected void fillAppCenterCommons( Map<String, Object> model, HttpServletRequest request ) throws SiteMessageException, UserNotSignedException
    {
        // Fill the active environment if it is stored in session
        HttpSession session = request.getSession( true );
        Environment environment = getActiveEnvironment( request );
        if ( environment != null )
        {
            model.put( MARK_ACTIVE_ENVIRONMENT, environment );
        }

        // Fill with application
        _application = getApplication( request );

        model.put( MARK_APPLICATION, _application );

        // Add the user
        User user = UserService.getCurrentUserInAppContext( request, _application.getId( ) );
        model.put( Constants.MARK_USER, user );

        // Add the category action list
        // Build the ResourceType config
        ResourceTypeConfig resourceTypeConfig = new ResourceTypeConfig( );
        resourceTypeConfig.addResourceTypeConfig( "APP", "APP" );
        if ( getActiveEnvironment( request ) != null )
        {
            resourceTypeConfig.addResourceTypeConfig( "ENV", getActiveEnvironment( request ).getPrefix( ) );
        }
        model.put( Constants.MARK_LIST_CATEGORY_ACTIONS,
                ActionService.getCategoryActionsListOfUserForApplication( request, _application.getId( ), resourceTypeConfig ) );

        // Add the demands
        addListDemand( request, _application, model );

        // Fill the active demand types
        model.put( MARK_ACTIVE_DEMAND_TYPES,
                ApplicationService.loadApplicationDataSubset( _application, ApplicationDemandTypesEnable.DATA_SUBSET_NAME, ApplicationDemandTypesEnable.class ) );
    }

    protected <T extends ApplicationData> void addDatas( HttpServletRequest request, Application application, Map<String, Object> model, String strDatasName,
            Class datasClass )
    {
        ApplicationDatas applicationDatas = (ApplicationDatas) ApplicationService.loadApplicationDataSubset( application, strDatasName, datasClass );
        List<ApplicationData> listFilteredApplicationData = new ArrayList<>( );
        HttpSession session = request.getSession( true );
        Environment environment = getActiveEnvironment( request );
        if ( environment != null && applicationDatas != null )
        {
            for ( T appData : (List<T>) applicationDatas.getListData( ) )
            {
                if ( appData.getEnvironment( ) != null && appData.getEnvironment( ).equals( environment.getPrefix( ) ) )
                {
                    listFilteredApplicationData.add( appData );
                }
            }
            model.put( Constants.MARK_DATA, listFilteredApplicationData );
        }
        else
            if ( applicationDatas != null )
            {
                model.put( Constants.MARK_DATA, (List<T>) applicationDatas.getListData( ) );
            }
        model.put( Constants.MARK_DATAS, applicationDatas );

    }

    /**
     * Check a permission for a user, on given resource code.Ex: PERMISSION_DEPLY_APPLI for user john.doe@paris.fr on resource REC (environement)
     * 
     * @param request
     * @param strPermissionCode
     * @param strResourceCode
     * @return
     * @throws SiteMessageException
     * @throws UserNotSignedException
     * @throws AccessDeniedException
     */
    protected boolean checkPermission( HttpServletRequest request, String strPermissionCode, String strResourceCode ) throws SiteMessageException,
            UserNotSignedException, AccessDeniedException
    {
        _application = getApplication( request );

        if ( _application == null )
        {
            throw new AccessDeniedException( ERROR_USER_NOT_AUTHORIZED );
        }

        User user = UserService.getCurrentUserInAppContext( request, _application.getId( ) );
        if ( !AuthorizationService.isAuthorized( user.getId( ), _application.getId( ), strPermissionCode, strResourceCode ) )
        {
            throw new AccessDeniedException( ERROR_USER_NOT_AUTHORIZED );
        }
        return true;
    }

    /**
     * Get an authorized site message
     * 
     * @param request
     * @throws SiteMessageException
     */
    protected void getUnauthorizedAccessMessage( HttpServletRequest request ) throws SiteMessageException
    {
        SiteMessageService.setMessage( request, ERROR_USER_NOT_AUTHORIZED, SiteMessage.TYPE_ERROR );
    }

    /**
     * fill the model with the permissions for displaying buttons in the XPages
     * 
     * @param model
     * @param request
     * @param strPermission
     * @param strResourceCode
     */
    protected void fillDisplayPermission( Map<String, Object> model, HttpServletRequest request, String strPermission, String strResourceCode )
    {
        try
        {
            if ( checkPermission( request, strPermission, strResourceCode ) )
            {
                model.put( strPermission, true );
            }
        }
        catch( Exception e )
        {

        }
    }

    protected String getDemandType( )
    {
        return "";
    }
}
