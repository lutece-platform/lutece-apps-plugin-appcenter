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

import fr.paris.lutece.plugins.appcenter.business.Role;
import fr.paris.lutece.plugins.appcenter.business.RoleHome;
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
 * This class provides the user interface to manage Role features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageRoles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_ROLE_MANAGEMENT" )
public class RoleJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_ROLES = "/admin/plugins/appcenter/manage_roles.html";
    private static final String TEMPLATE_CREATE_ROLE = "/admin/plugins/appcenter/create_role.html";
    private static final String TEMPLATE_MODIFY_ROLE = "/admin/plugins/appcenter/modify_role.html";

    // Parameters
    private static final String PARAMETER_ID_ROLE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ROLES = "appcenter.manage_roles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_ROLE = "appcenter.modify_role.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ROLE = "appcenter.create_role.pageTitle";

    // Markers
    private static final String MARK_ROLE_LIST = "role_list";
    private static final String MARK_ROLE = "role";

    private static final String JSP_MANAGE_ROLES = "jsp/admin/plugins/appcenter/ManageRoles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ROLE = "appcenter.message.confirmRemoveRole";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.role.attribute.";

    // Views
    private static final String VIEW_MANAGE_ROLES = "manageRoles";
    private static final String VIEW_CREATE_ROLE = "createRole";
    private static final String VIEW_MODIFY_ROLE = "modifyRole";

    // Actions
    private static final String ACTION_CREATE_ROLE = "createRole";
    private static final String ACTION_MODIFY_ROLE = "modifyRole";
    private static final String ACTION_REMOVE_ROLE = "removeRole";
    private static final String ACTION_CONFIRM_REMOVE_ROLE = "confirmRemoveRole";

    // Infos
    private static final String INFO_ROLE_CREATED = "appcenter.info.role.created";
    private static final String INFO_ROLE_UPDATED = "appcenter.info.role.updated";
    private static final String INFO_ROLE_REMOVED = "appcenter.info.role.removed";
    
    // Session variable to store working values
    private Role _role;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ROLES, defaultView = true )
    public String getManageRoles( HttpServletRequest request )
    {
        _role = null;
        List<Role> listRoles = RoleHome.getRolesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_ROLE_LIST, listRoles, JSP_MANAGE_ROLES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ROLES, TEMPLATE_MANAGE_ROLES, model );
    }

    /**
     * Returns the form to create a role
     *
     * @param request The Http request
     * @return the html code of the role form
     */
    @View( VIEW_CREATE_ROLE )
    public String getCreateRole( HttpServletRequest request )
    {
        _role = ( _role != null ) ? _role : new Role(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_ROLE, _role );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ROLE, TEMPLATE_CREATE_ROLE, model );
    }

    /**
     * Process the data capture form of a new role
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_ROLE )
    public String doCreateRole( HttpServletRequest request )
    {
        populate( _role, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _role, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ROLE );
        }

        RoleHome.create( _role );
        addInfo( INFO_ROLE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ROLES );
    }

    /**
     * Manages the removal form of a role whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ROLE )
    public String getConfirmRemoveRole( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ROLE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ROLE ) );
        url.addParameter( PARAMETER_ID_ROLE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ROLE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a role
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage roles
     */
    @Action( ACTION_REMOVE_ROLE )
    public String doRemoveRole( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ROLE ) );
        RoleHome.remove( nId );
        addInfo( INFO_ROLE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ROLES );
    }

    /**
     * Returns the form to update info about a role
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_ROLE )
    public String getModifyRole( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ROLE ) );

        if ( _role == null || ( _role.getId(  ) != nId ) )
        {
            _role = RoleHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_ROLE, _role );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_ROLE, TEMPLATE_MODIFY_ROLE, model );
    }

    /**
     * Process the change form of a role
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_ROLE )
    public String doModifyRole( HttpServletRequest request )
    {
        populate( _role, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _role, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_ROLE, PARAMETER_ID_ROLE, _role.getId( ) );
        }

        RoleHome.update( _role );
        addInfo( INFO_ROLE_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ROLES );
    }
}