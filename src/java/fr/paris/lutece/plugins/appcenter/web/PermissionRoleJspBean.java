/*
 * Copyright (c) 2002-2018, Mairie de Paris
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

import fr.paris.lutece.plugins.appcenter.business.PermissionRole;
import fr.paris.lutece.plugins.appcenter.business.PermissionRoleHome;
import fr.paris.lutece.plugins.appcenter.business.Role;
import fr.paris.lutece.plugins.appcenter.business.RoleHome;
import fr.paris.lutece.plugins.appcenter.service.AppCenterService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage PermissionRole features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManagePermissionRoles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class PermissionRoleJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_PERMISSIONROLES = "/admin/plugins/appcenter/manage_permissionroles.html";
    private static final String TEMPLATE_CREATE_PERMISSIONROLE = "/admin/plugins/appcenter/create_permissionrole.html";

    // Parameters
    private static final String PARAMETER_CODE_PERMISSION = "code_permission";
    private static final String PARAMETER_CODE_RESOURCE = "code_resource";
    private static final String PARAMETER_ID_ROLE = "id_role";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_PERMISSIONROLES = "appcenter.manage_permissionroles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_PERMISSIONROLE = "appcenter.create_permissionrole.pageTitle";

    // Markers
    private static final String MARK_PERMISSIONROLE_LIST = "permissionrole_list";
    private static final String MARK_PERMISSIONROLE = "permissionrole";
    private static final String MARK_PERMISSION_LIST = "permission_list";

    private static final String JSP_MANAGE_PERMISSIONROLES = "jsp/admin/plugins/appcenter/ManagePermissionRoles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_PERMISSIONROLE = "appcenter.message.confirmRemovePermissionRole";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.permissionrole.attribute.";

    // Views
    private static final String VIEW_MANAGE_PERMISSIONROLES = "managePermissionRoles";
    private static final String VIEW_CREATE_PERMISSIONROLE = "createPermissionRole";

    // Actions
    private static final String ACTION_CREATE_PERMISSIONROLE = "createPermissionRole";
    private static final String ACTION_REMOVE_PERMISSIONROLE = "removePermissionRole";
    private static final String ACTION_CONFIRM_REMOVE_PERMISSIONROLE = "confirmRemovePermissionRole";

    // Infos
    private static final String INFO_PERMISSIONROLE_CREATED = "appcenter.info.permissionrole.created";
    private static final String INFO_PERMISSIONROLE_REMOVED = "appcenter.info.permissionrole.removed";
    
    // Session variable to store working values
    private PermissionRole _permissionrole;
    private Role _role;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_PERMISSIONROLES, defaultView = true )
    public String getManagePermissionRoles( HttpServletRequest request )
    {
        _permissionrole = null;
        
        String strIdRole = request.getParameter( PARAMETER_ID_ROLE );
        
        if ( strIdRole != null )
        {
            _role = RoleHome.findByPrimaryKey( Integer.parseInt( strIdRole ) );
        }
        if ( _role != null )
        {
            List<PermissionRole> listPermissionRoles = listPermissionRoles = PermissionRoleHome.getPermissionRolesListByIdRole( _role.getId() );
            Map<String, Object> model = getPaginatedListModel( request, MARK_PERMISSIONROLE_LIST, listPermissionRoles, JSP_MANAGE_PERMISSIONROLES );
            return getPage( PROPERTY_PAGE_TITLE_MANAGE_PERMISSIONROLES, TEMPLATE_MANAGE_PERMISSIONROLES, model );
        }
        return redirect(request, "/jsp/admin/plugins/appcenter/ManageRoles.jsp");
    }

    /**
     * Returns the form to create a permissionrole
     *
     * @param request The Http request
     * @return the html code of the permissionrole form
     */
    @View( VIEW_CREATE_PERMISSIONROLE )
    public String getCreatePermissionRole( HttpServletRequest request )
    {
        _permissionrole = ( _permissionrole != null ) ? _permissionrole : new PermissionRole(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_PERMISSION_LIST, AppCenterService.getPermissionList( ) );
        
        model.put( MARK_PERMISSIONROLE, _permissionrole );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_PERMISSIONROLE, TEMPLATE_CREATE_PERMISSIONROLE, model );
    }

    /**
     * Process the data capture form of a new permissionrole
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_PERMISSIONROLE )
    public String doCreatePermissionRole( HttpServletRequest request )
    {
        populate( _permissionrole, request, request.getLocale( ) );
        if ( _role != null )
        {
            _permissionrole.setIdRole( _role.getId( ) );
        }
        // Check constraints
        if ( !validateBean( _permissionrole, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_PERMISSIONROLE );
        }

        PermissionRoleHome.create( _permissionrole );
        addInfo( INFO_PERMISSIONROLE_CREATED, getLocale(  ) );
        
        return redirectView( request, VIEW_MANAGE_PERMISSIONROLES );
    }

    /**
     * Manages the removal form of a permissionrole whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_PERMISSIONROLE )
    public String getConfirmRemovePermissionRole( HttpServletRequest request )
    {
        String strPermissionCode = request.getParameter( PARAMETER_CODE_PERMISSION );
        String strIdRole = request.getParameter( PARAMETER_ID_ROLE );
        String strResourceCode = request.getParameter( PARAMETER_CODE_RESOURCE );
        
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_PERMISSIONROLE ) );
        
        url.addParameter( PARAMETER_CODE_PERMISSION, strPermissionCode );
        url.addParameter( PARAMETER_ID_ROLE, strIdRole );
        url.addParameter( PARAMETER_CODE_RESOURCE, strResourceCode );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_PERMISSIONROLE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a permissionrole
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage permissionroles
     */
    @Action( ACTION_REMOVE_PERMISSIONROLE )
    public String doRemovePermissionRole( HttpServletRequest request )
    {
        String strPermissionCode = request.getParameter( PARAMETER_CODE_PERMISSION );
        String strIdRole = request.getParameter( PARAMETER_ID_ROLE );
        String strResourceCode = request.getParameter( PARAMETER_CODE_RESOURCE );
        
        
        PermissionRoleHome.remove( strPermissionCode, Integer.parseInt( strIdRole ), strResourceCode );
        addInfo( INFO_PERMISSIONROLE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_PERMISSIONROLES );
    }


}