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
 * This class provides the user interface to manage PermissionRole features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManagePermissionRoles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_PERMISSIONS_ROLE_MANAGEMENT" )
public class PermissionRoleJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_ACTIONROLEROLES = "/admin/plugins/appcenter/manage_permissionroles.html";
    private static final String TEMPLATE_CREATE_ACTIONROLEROLE = "/admin/plugins/appcenter/create_permissionrole.html";

    // Parameters
    private static final String PARAMETER_CODE_ACTIONROLE = "codePermission";
    private static final String PARAMETER_CODE_ROLE = "codeRole";
    private static final String PARAMETER_CODE_RESOURCE = "codeResource";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ACTIONROLEROLES = "appcenter.manage_permissionroles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ACTIONROLEROLE = "appcenter.create_permissionrole.pageTitle";

    // Markers
    private static final String MARK_ACTIONROLEROLE_LIST = "permissionrole_list";
    private static final String MARK_ACTIONROLEROLE = "permissionrole";

    private static final String JSP_MANAGE_ACTIONROLEROLES = "jsp/admin/plugins/appcenter/ManagePermissionRoles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ACTIONROLEROLE = "appcenter.message.confirmRemovePermissionRole";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.permissionrole.attribute.";

    // Views
    private static final String VIEW_MANAGE_ACTIONROLEROLES = "managePermissionRoles";
    private static final String VIEW_CREATE_ACTIONROLEROLE = "createPermissionRole";

    // Actions
    private static final String ACTION_CREATE_ACTIONROLEROLE = "createPermissionRole";
    private static final String ACTION_REMOVE_ACTIONROLEROLE = "removePermissionRole";
    private static final String ACTION_CONFIRM_REMOVE_ACTIONROLEROLE = "confirmRemovePermissionRole";

    // Infos
    private static final String INFO_ACTIONROLEROLE_CREATED = "appcenter.info.permissionrole.created";
    private static final String INFO_ACTIONROLEROLE_REMOVED = "appcenter.info.permissionrole.removed";
    
    // Session variable to store working values
    private PermissionRole _permissionrole;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ACTIONROLEROLES, defaultView = true )
    public String getManagePermissionRoles( HttpServletRequest request )
    {
        _permissionrole = null;
        List<PermissionRole> listPermissionRoles = PermissionRoleHome.getPermissionRolesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_ACTIONROLEROLE_LIST, listPermissionRoles, JSP_MANAGE_ACTIONROLEROLES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ACTIONROLEROLES, TEMPLATE_MANAGE_ACTIONROLEROLES, model );
    }

    /**
     * Returns the form to create a permissionrole
     *
     * @param request The Http request
     * @return the html code of the permissionrole form
     */
    @View( VIEW_CREATE_ACTIONROLEROLE )
    public String getCreatePermissionRole( HttpServletRequest request )
    {
        _permissionrole = ( _permissionrole != null ) ? _permissionrole : new PermissionRole(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_ACTIONROLEROLE, _permissionrole );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ACTIONROLEROLE, TEMPLATE_CREATE_ACTIONROLEROLE, model );
    }

    /**
     * Process the data capture form of a new permissionrole
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_ACTIONROLEROLE )
    public String doCreatePermissionRole( HttpServletRequest request )
    {
        populate( _permissionrole, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _permissionrole, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ACTIONROLEROLE );
        }

        PermissionRoleHome.create( _permissionrole );
        addInfo( INFO_ACTIONROLEROLE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLEROLES );
    }

    /**
     * Manages the removal form of a permissionrole whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ACTIONROLEROLE )
    public String getConfirmRemovePermissionRole( HttpServletRequest request )
    {
        String strPermissionCode = request.getParameter( PARAMETER_CODE_ACTIONROLE );
        String strRoleCode = request.getParameter( PARAMETER_CODE_ROLE );
        String strResourceCode = request.getParameter( PARAMETER_CODE_RESOURCE );
        
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ACTIONROLEROLE ) );
        
        url.addParameter( PARAMETER_CODE_ACTIONROLE, strPermissionCode );
        url.addParameter( PARAMETER_CODE_ROLE, strRoleCode );
        url.addParameter( PARAMETER_CODE_RESOURCE, strResourceCode );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ACTIONROLEROLE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a permissionrole
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage permissionroles
     */
    @Action( ACTION_REMOVE_ACTIONROLEROLE )
    public String doRemovePermissionRole( HttpServletRequest request )
    {
        String strPermissionCode = request.getParameter( PARAMETER_CODE_ACTIONROLE );
        String strRoleCode = request.getParameter( PARAMETER_CODE_ROLE );
        String strResourceCode = request.getParameter( PARAMETER_CODE_RESOURCE );
        
        
        PermissionRoleHome.remove( strPermissionCode, strRoleCode, strResourceCode );
        addInfo( INFO_ACTIONROLEROLE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLEROLES );
    }


}