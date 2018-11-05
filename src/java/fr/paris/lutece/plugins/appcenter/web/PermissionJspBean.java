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

import fr.paris.lutece.plugins.appcenter.business.Permission;
import fr.paris.lutece.plugins.appcenter.business.PermissionHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage Permission features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManagePermissions.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_PERMISSIONS_MANAGEMENT" )
public class PermissionJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_ACTIONROLES = "/admin/plugins/appcenter/manage_permissions.html";
    private static final String TEMPLATE_CREATE_ACTIONROLE = "/admin/plugins/appcenter/create_permission.html";
    private static final String TEMPLATE_MODIFY_ACTIONROLE = "/admin/plugins/appcenter/modify_permission.html";

    // Parameters
    private static final String PARAMETER_ID_ACTIONROLE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ACTIONROLES = "appcenter.manage_permissions.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_ACTIONROLE = "appcenter.modify_permission.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ACTIONROLE = "appcenter.create_permission.pageTitle";

    // Markers
    private static final String MARK_ACTIONROLE_LIST = "permission_list";
    private static final String MARK_ACTIONROLE = "permission";

    private static final String JSP_MANAGE_ACTIONROLES = "jsp/admin/plugins/appcenter/ManagePermissions.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ACTIONROLE = "appcenter.message.confirmRemovePermission";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.permission.attribute.";

    // Views
    private static final String VIEW_MANAGE_ACTIONROLES = "managePermissions";
    private static final String VIEW_CREATE_ACTIONROLE = "createPermission";
    private static final String VIEW_MODIFY_ACTIONROLE = "modifyPermission";

    // Actions
    private static final String ACTION_CREATE_ACTIONROLE = "createPermission";
    private static final String ACTION_MODIFY_ACTIONROLE = "modifyPermission";
    private static final String ACTION_REMOVE_ACTIONROLE = "removePermission";
    private static final String ACTION_CONFIRM_REMOVE_ACTIONROLE = "confirmRemovePermission";

    // Infos
    private static final String INFO_ACTIONROLE_CREATED = "appcenter.info.permission.created";
    private static final String INFO_ACTIONROLE_UPDATED = "appcenter.info.permission.updated";
    private static final String INFO_ACTIONROLE_REMOVED = "appcenter.info.permission.removed";
    
    // Session variable to store working values
    private Permission _permission;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ACTIONROLES, defaultView = true )
    public String getManagePermissions( HttpServletRequest request )
    {
        _permission = null;
        List<Permission> listPermissions = PermissionHome.getPermissionsList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_ACTIONROLE_LIST, listPermissions, JSP_MANAGE_ACTIONROLES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ACTIONROLES, TEMPLATE_MANAGE_ACTIONROLES, model );
    }

    /**
     * Returns the form to create a permission
     *
     * @param request The Http request
     * @return the html code of the permission form
     */
    @View( VIEW_CREATE_ACTIONROLE )
    public String getCreatePermission( HttpServletRequest request )
    {
        _permission = ( _permission != null ) ? _permission : new Permission(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_ACTIONROLE, _permission );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ACTIONROLE, TEMPLATE_CREATE_ACTIONROLE, model );
    }

    /**
     * Process the data capture form of a new permission
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_ACTIONROLE )
    public String doCreatePermission( HttpServletRequest request )
    {
        populate( _permission, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _permission, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ACTIONROLE );
        }

        PermissionHome.create( _permission );
        addInfo( INFO_ACTIONROLE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLES );
    }

    /**
     * Manages the removal form of a permission whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ACTIONROLE )
    public String getConfirmRemovePermission( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ACTIONROLE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ACTIONROLE ) );
        url.addParameter( PARAMETER_ID_ACTIONROLE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ACTIONROLE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a permission
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage permissions
     */
    @Action( ACTION_REMOVE_ACTIONROLE )
    public String doRemovePermission( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ACTIONROLE ) );
        PermissionHome.remove( nId );
        addInfo( INFO_ACTIONROLE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLES );
    }

    /**
     * Returns the form to update info about a permission
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_ACTIONROLE )
    public String getModifyPermission( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ACTIONROLE ) );

        if ( _permission == null || ( _permission.getId(  ) != nId ) )
        {
            _permission = PermissionHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_ACTIONROLE, _permission );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_ACTIONROLE, TEMPLATE_MODIFY_ACTIONROLE, model );
    }

    /**
     * Process the change form of a permission
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_ACTIONROLE )
    public String doModifyPermission( HttpServletRequest request )
    {
        populate( _permission, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _permission, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_ACTIONROLE, PARAMETER_ID_ACTIONROLE, _permission.getId( ) );
        }

        PermissionHome.update( _permission );
        addInfo( INFO_ACTIONROLE_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLES );
    }
}