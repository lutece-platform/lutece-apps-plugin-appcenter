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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Role;
import fr.paris.lutece.plugins.appcenter.business.RoleHome;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRole;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRoleFilter;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRoleHome;
import fr.paris.lutece.plugins.appcenter.util.AppCenterUtils;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sort.AttributeComparator;
import fr.paris.lutece.util.url.UrlItem;

/**
 * This class provides the user interface to manage UserApplicationRole features ( manage, createOrModify, modify, remove )
 */
@Controller( controllerJsp = "ManageUserApplicationRoles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class UserApplicationRoleJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_USER_APPLICATION_ROLES = "/admin/plugins/appcenter/manage_userapplicationroles.html";
    private static final String TEMPLATE_CREATE_USER_APPLICATION_ROLE = "/admin/plugins/appcenter/create_userapplicationrole.html";
    private static final String TEMPLATE_MODIFY_USER_APPLICATION_ROLE = "/admin/plugins/appcenter/modify_userapplicationrole.html";

    // Parameters
    private static final String PARAMETER_ID_ROLE_OLD = "id_role_old";
    private static final String PARAMETER_ID_ROLE = "id_role";
    private static final String PARAMETER_ID_APPLICATION = "id_application";
    private static final String PARAMETER_ID_USER = "id_user";
    private static final String PARAMETER_APPLICATION_CODE_OR_NAME = "application_code_or_name";
    private static final String PARAMETER_ROLE_LABEL = "role_label";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_USER_APPLICATION_ROLES = "appcenter.manage_userApplicationRoles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_USER_APPLICATION_ROLE = "appcenter.modify_userApplicationRole.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_USER_APPLICATION_ROLE = "appcenter.create_userApplicationRole.pageTitle";

    // Markers
    private static final String MARK_USER_APPLICATION_ROLE_LIST = "user_application_role_list";
    private static final String MARK_USER_APPLICATION_ROLE = "user_application_role";
    private static final String MARK_APPLICATION_LIST = "applications_list";
    private static final String MARK_ROLES_LIST = "roles_list";
    private static final String MARK_APPLICATION_MAP = "applications_map";
    private static final String MARK_ROLES_MAP = "roles_map";
    private static final String MARK_USER_APPLICATION_ROLE_FILTER = "user_application_role_filter";
    private static final String MARK_USER_REF_LIST = "user_ref_list";

    private static final String JSP_MANAGE_USER_APPLICATION_ROLES = "jsp/admin/plugins/appcenter/ManageUserApplicationRoles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_USER_APPLICATION_ROLE = "appcenter.message.confirmRemoveUserApplicationRole";
    private static final String MESSAGE_CONFIRM_REMOVE_USER_APPLICATION_ROLE_BY_USER = "appcenter.message.confirmRemoveUserApplicationRoleByUser";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.userApplicationRole.attribute.";

    // Views
    private static final String VIEW_MANAGE_USER_APPLICATION_ROLES = "manageUserApplicationRoles";
    private static final String VIEW_CREATE_USER_APPLICATION_ROLE = "createUserApplicationRole";
    private static final String VIEW_MODIFY_USER_APPLICATION_ROLE = "modifyUserApplicationRole";

    // Actions
    private static final String ACTION_CREATE_USER_APPLICATION_ROLE = "createUserApplicationRole";
    private static final String ACTION_MODIFY_USER_APPLICATION_ROLE = "modifyUserApplicationRole";
    private static final String ACTION_REMOVE_USER_APPLICATION_ROLE = "removeUserApplicationRole";
    private static final String ACTION_REMOVE_USER_APPLICATION_ROLE_BY_USER = "removeUserApplicationRoles";
    private static final String ACTION_CONFIRM_REMOVE_USER_APPLICATION_ROLE = "confirmRemoveUserApplicationRole";
    private static final String ACTION_CONFIRM_REMOVE_USER_APPLICATION_ROLE_BY_USER = "confirmRemoveUserApplicationRoleByUser";
    private static final String ACTION_FILTER_USER_APPLICATION_ROLE = "filterUserApplicationRoles";

    // Constant
    private static final String CONSTANT_CODE_APPLICATION = "codeApplication";
    private static final String CONSTANT_NAME_APPLICATION = "nameApplication";
    private static final String CONSTANT_ID_USER = "idUser";
    private static final String CONSTANT_LABEL_ROLE = "labelRole";

    // Constants - set role for all applications
    private static final String CONSTANT_ID_ALL_APPLICATIONS = "0";
    private static final String CONSTANT_LABEL_ALL_APPLICATIONS = "appcenter.manage_userApplicationRole.AllApplication.label";

    // Infos
    private static final String INFO_USER_APPLICATION_ROLE_CREATED = "appcenter.info.userApplicationRole.created";
    private static final String INFO_USER_APPLICATION_ROLE_UPDATED = "appcenter.info.userApplicationRole.updated";
    private static final String INFO_USER_APPLICATION_ROLE_REMOVED = "appcenter.info.userApplicationRole.removed";

    // Error
    private static final String ERROR_ROLE_ALREADY_EXISTS = "appcenter.error.roleAlreadyExists";

    // Session variable to store working values
    private UserApplicationRole _userApplicationRole;
    private UserApplicationRoleFilter _filter;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_USER_APPLICATION_ROLES, defaultView = true )
    public String getManageUserApplicationRoles( HttpServletRequest request )
    {
        // Initialize the demand filter
        if ( _filter == null )
        {
            _filter = new UserApplicationRoleFilter( );
        }

        _userApplicationRole = null;
        List<UserApplicationRole> listUserApplicationRoles = UserApplicationRoleHome.getUserApplicationRolesListByFilter( _filter );
        Map<String, Application> mapApplications = ApplicationHome.getApplicationsMap( );
        Map<String, Role> mapRoles = RoleHome.getRolesMap( );
        ReferenceList refListIdUser = UserApplicationRoleHome.getIdUserReferenceList( );

        // SORT
        String strSortedAttributeName = request.getParameter( Parameters.SORTED_ATTRIBUTE_NAME );
        String strAscSort = null;

        if ( strSortedAttributeName != null )
        {
            strAscSort = request.getParameter( Parameters.SORTED_ASC );

            boolean bIsAscSort = Boolean.parseBoolean( strAscSort );

            if ( strSortedAttributeName.equals( CONSTANT_ID_USER ) )
            {
                Collections.sort( listUserApplicationRoles, new AttributeComparator( strSortedAttributeName, bIsAscSort ) );
            }
            else
            {
                Comparator<UserApplicationRole> c = null;

                if ( strSortedAttributeName.equals( CONSTANT_CODE_APPLICATION ) )
                {
                    c = Comparator.comparing( ( UserApplicationRole x ) -> mapApplications.get( Integer.toString( x.getIdApplication( ) ) ).getCode( ) );
                }
                if ( strSortedAttributeName.equals( CONSTANT_NAME_APPLICATION ) )
                {
                    c = Comparator.comparing( ( UserApplicationRole x ) -> mapApplications.get( Integer.toString( x.getIdApplication( ) ) ).getName( ) );
                }
                else
                    if ( strSortedAttributeName.equals( CONSTANT_LABEL_ROLE ) )
                    {
                        c = Comparator.comparing( ( UserApplicationRole x ) -> mapRoles.get( Integer.toString( x.getIdRole( ) ) ).getLabel( ) );
                    }

                if ( c != null )
                {
                    if ( bIsAscSort )
                    {
                        Collections.sort( listUserApplicationRoles, Collections.reverseOrder( c ) );
                    }
                    else
                    {
                        Collections.sort( listUserApplicationRoles, c );
                    }
                }
            }
        }

        UrlItem url = new UrlItem( JSP_MANAGE_USER_APPLICATION_ROLES );

        if ( strSortedAttributeName != null )
        {
            url.addParameter( Parameters.SORTED_ATTRIBUTE_NAME, strSortedAttributeName );
        }

        if ( strAscSort != null )
        {
            url.addParameter( Parameters.SORTED_ASC, strAscSort );
        }

        Map<String, Object> model = getPaginatedListModel( request, MARK_USER_APPLICATION_ROLE_LIST, listUserApplicationRoles, url.getUrl( ) );
        model.put( MARK_APPLICATION_MAP, mapApplications );
        model.put( MARK_ROLES_MAP, mapRoles );
        model.put( MARK_USER_APPLICATION_ROLE_FILTER, _filter );
        model.put( MARK_USER_REF_LIST, refListIdUser );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_USER_APPLICATION_ROLES, TEMPLATE_MANAGE_USER_APPLICATION_ROLES, model );
    }

    /**
     * Process the action of filtering applications; set the filter
     * 
     * @param request
     * @return The manage applications view
     */
    @Action( ACTION_FILTER_USER_APPLICATION_ROLE )
    public String doFilterUserApplicationRole( HttpServletRequest request )
    {
        String strApplicationCodeOrName = request.getParameter( PARAMETER_APPLICATION_CODE_OR_NAME );
        String strIdUser = request.getParameter( PARAMETER_ID_USER );
        String strRoleLabel = request.getParameter( PARAMETER_ROLE_LABEL );

        _filter = new UserApplicationRoleFilter( );

        if ( strApplicationCodeOrName != null && !strApplicationCodeOrName.isEmpty( ) )
        {
            _filter.setApplicationCodeOrName( strApplicationCodeOrName );
            _filter.setHasApplicationCodeOrName( true );
        }
        if ( strIdUser != null && !strIdUser.isEmpty( ) )
        {
            _filter.setIdUser( strIdUser );
            _filter.setHasIdUser( true );
        }
        if ( strRoleLabel != null && !strRoleLabel.isEmpty( ) )
        {
            _filter.setRoleLabel( strRoleLabel );
            _filter.setHasRoleLabel( true );
        }

        return redirectView( request, VIEW_MANAGE_USER_APPLICATION_ROLES );
    }

    /**
     * Returns the form to createOrModify a userApplicationRole
     *
     * @param request
     *            The Http request
     * @return the html code of the userApplicationRole form
     */
    @View( VIEW_CREATE_USER_APPLICATION_ROLE )
    public String getCreateUserApplicationRole( HttpServletRequest request )
    {
        _userApplicationRole = ( _userApplicationRole != null ) ? _userApplicationRole : new UserApplicationRole( );

        Map<String, Object> model = getModel( );

        model.put( MARK_USER_APPLICATION_ROLE, _userApplicationRole );

        // Application references list
        ReferenceList applicationsReferencesList = ApplicationHome.getApplicationsReferenceList( );
        ReferenceItem applicationReference = new ReferenceItem( );
        applicationReference.setCode( CONSTANT_ID_ALL_APPLICATIONS );
        applicationReference.setName( I18nService.getLocalizedString( CONSTANT_LABEL_ALL_APPLICATIONS, getLocale( ) ) );
        applicationsReferencesList.add( 0, applicationReference );
        model.put( MARK_APPLICATION_LIST, applicationsReferencesList );

        // Roles list
        ReferenceList rolesList = RoleHome.getRolesReferenceList( );
        AppCenterUtils.addEmptyItem( rolesList, getLocale( ) );
        model.put( MARK_ROLES_LIST, rolesList );

        model.put( "error", request.getAttribute( "error" ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_USER_APPLICATION_ROLE, TEMPLATE_CREATE_USER_APPLICATION_ROLE, model );
    }

    /**
     * Process the data capture form of a new userApplicationRole
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_USER_APPLICATION_ROLE )
    public String doCreateUserApplicationRole( HttpServletRequest request )
    {
        populate( _userApplicationRole, request );

        // Check constraints
        if ( !validateBean( _userApplicationRole, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_USER_APPLICATION_ROLE );
        }

        // Is this user role exist ?
        UserApplicationRole uar = UserApplicationRoleHome.findByPrimaryKey( _userApplicationRole.getIdRole( ), _userApplicationRole.getIdApplication( ),
                _userApplicationRole.getIdUser( ) );
        if ( uar != null )
        {
            addError( I18nService.getLocalizedString( ERROR_ROLE_ALREADY_EXISTS, getLocale( ) ) );
            return redirectView( request, VIEW_CREATE_USER_APPLICATION_ROLE );
        }

        UserApplicationRoleHome.create( _userApplicationRole );
        addInfo( INFO_USER_APPLICATION_ROLE_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_USER_APPLICATION_ROLES );
    }

    /**
     * Manages the removal form of a userApplicationRole whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_USER_APPLICATION_ROLE )
    public String getConfirmRemoveUserApplicationRole( HttpServletRequest request )
    {
        int nIdRole = Integer.parseInt( request.getParameter( PARAMETER_ID_ROLE ) );
        int nIdApplication = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATION ) );
        String strIdUser = request.getParameter( PARAMETER_ID_USER );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_USER_APPLICATION_ROLE ) );
        url.addParameter( PARAMETER_ID_ROLE, nIdRole );
        url.addParameter( PARAMETER_ID_APPLICATION, nIdApplication );
        url.addParameter( PARAMETER_ID_USER, strIdUser );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_USER_APPLICATION_ROLE, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Manages the removal form of all userApplicationRole of a user
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_USER_APPLICATION_ROLE_BY_USER )
    public String getConfirmRemoveUserApplicationRoleByIdUser( HttpServletRequest request )
    {
        String strIdUser = request.getParameter( PARAMETER_ID_USER );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_USER_APPLICATION_ROLE_BY_USER ) );
        url.addParameter( PARAMETER_ID_USER, strIdUser );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_USER_APPLICATION_ROLE_BY_USER, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a userApplicationRole
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage userApplicationRoles
     */
    @Action( ACTION_REMOVE_USER_APPLICATION_ROLE )
    public String doRemoveUserApplicationRole( HttpServletRequest request )
    {
        int nIdRole = Integer.parseInt( request.getParameter( PARAMETER_ID_ROLE ) );
        int nIdApplication = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATION ) );
        String strIdUser = request.getParameter( PARAMETER_ID_USER );
        UserApplicationRoleHome.remove( nIdRole, nIdApplication, strIdUser );
        addInfo( INFO_USER_APPLICATION_ROLE_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_USER_APPLICATION_ROLES );
    }

    /**
     * Handles the removal form of all userApplicationRole of a user
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage userApplicationRoles
     */
    @Action( ACTION_REMOVE_USER_APPLICATION_ROLE_BY_USER )
    public String doRemoveUserApplicationRoleByIdUser( HttpServletRequest request )
    {
        String strIdUser = request.getParameter( PARAMETER_ID_USER );
        UserApplicationRoleHome.removeByIdUser( strIdUser );
        addInfo( INFO_USER_APPLICATION_ROLE_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_USER_APPLICATION_ROLES );
    }

    /**
     * Returns the form to update info about a userApplicationRole
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_USER_APPLICATION_ROLE )
    public String getModifyUserApplicationRole( HttpServletRequest request )
    {
        int nIdRole = Integer.parseInt( request.getParameter( PARAMETER_ID_ROLE ) );
        int nIdApplication = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATION ) );
        String strIdUser = request.getParameter( PARAMETER_ID_USER );

        if ( _userApplicationRole == null || ( _userApplicationRole.getIdRole( ) != nIdRole ) || ( _userApplicationRole.getIdApplication( ) != nIdApplication )
                || ( _userApplicationRole.getIdUser( ) != strIdUser ) )
        {
            _userApplicationRole = UserApplicationRoleHome.findByPrimaryKey( nIdRole, nIdApplication, strIdUser );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_USER_APPLICATION_ROLE, _userApplicationRole );
        Map<String, Application> mapApplications = ApplicationHome.getApplicationsMap( );
        model.put( MARK_APPLICATION_MAP, mapApplications );
        ReferenceList rolesList = RoleHome.getRolesReferenceList( );
        AppCenterUtils.addEmptyItem( rolesList, getLocale( ) );
        model.put( MARK_ROLES_LIST, rolesList );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_USER_APPLICATION_ROLE, TEMPLATE_MODIFY_USER_APPLICATION_ROLE, model );
    }

    /**
     * Process the change form of a userApplicationRole
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_USER_APPLICATION_ROLE )
    public String doModifyUserApplicationRole( HttpServletRequest request )
    {
        UserApplicationRole userApplicationRole = new UserApplicationRole( );
        int nIdRoleOld = Integer.parseInt( request.getParameter( PARAMETER_ID_ROLE_OLD ) );

        populate( userApplicationRole, request );
        populate( _userApplicationRole, request );

        _userApplicationRole.setIdRole( nIdRoleOld );

        // Check constraints
        // Also check user role parameter because populate method init the field to 0 in _userApplicationRole
        if ( !validateBean( userApplicationRole, VALIDATION_ATTRIBUTES_PREFIX ) || request.getParameter( PARAMETER_ID_ROLE ).isEmpty( ) )
        {
            Map<String, String> mapParameters = new HashMap<String, String>( );
            mapParameters.put( PARAMETER_ID_ROLE, Integer.toString( _userApplicationRole.getIdRole( ) ) );
            mapParameters.put( PARAMETER_ID_APPLICATION, Integer.toString( _userApplicationRole.getIdApplication( ) ) );
            mapParameters.put( PARAMETER_ID_USER, _userApplicationRole.getIdUser( ) );
            return redirect( request, VIEW_MODIFY_USER_APPLICATION_ROLE, mapParameters );
        }

        UserApplicationRoleHome.update( _userApplicationRole, userApplicationRole );
        _userApplicationRole = userApplicationRole;
        addInfo( INFO_USER_APPLICATION_ROLE_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_USER_APPLICATION_ROLES );
    }
}
