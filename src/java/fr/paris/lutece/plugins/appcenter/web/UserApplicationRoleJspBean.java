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

import fr.paris.lutece.plugins.appcenter.business.UserApplicationRole;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRoleHome;
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
 * This class provides the user interface to manage UserApplicationRole features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageUserApplicationRoles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_USER_APPLICATION_ROLE_MANAGEMENT" )
public class UserApplicationRoleJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_USERAPPLICATIONROLES = "/admin/plugins/appcenter/manage_userapplicationroles.html";
    private static final String TEMPLATE_CREATE_USERAPPLICATIONROLE = "/admin/plugins/appcenter/create_userapplicationrole.html";

    // Parameters
    private static final String PARAMETER_ID_USERAPPLICATIONROLE = "id";
    private static final String PARAMETER_ID_ROLE = "idRole" ;
    private static final String PARAMETER_ID_APPLICATION = "idApplication" ;
    private static final String PARAMETER_ID_USER = "idUser" ;
               

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_USERAPPLICATIONROLES = "appcenter.manage_userapplicationroles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_USERAPPLICATIONROLE = "appcenter.create_userapplicationrole.pageTitle";

    // Markers
    private static final String MARK_USERAPPLICATIONROLE_LIST = "userapplicationrole_list";
    private static final String MARK_USERAPPLICATIONROLE = "userapplicationrole";

    private static final String JSP_MANAGE_USERAPPLICATIONROLES = "jsp/admin/plugins/appcenter/ManageUserApplicationRoles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_USERAPPLICATIONROLE = "appcenter.message.confirmRemoveUserApplicationRole";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.userapplicationrole.attribute.";

    // Views
    private static final String VIEW_MANAGE_USERAPPLICATIONROLES = "manageUserApplicationRoles";
    private static final String VIEW_CREATE_USERAPPLICATIONROLE = "createUserApplicationRole";

    // Actions
    private static final String ACTION_CREATE_USERAPPLICATIONROLE = "createUserApplicationRole";
    private static final String ACTION_REMOVE_USERAPPLICATIONROLE = "removeUserApplicationRole";
    private static final String ACTION_CONFIRM_REMOVE_USERAPPLICATIONROLE = "confirmRemoveUserApplicationRole";

    // Infos
    private static final String INFO_USERAPPLICATIONROLE_CREATED = "appcenter.info.userapplicationrole.created";
    private static final String INFO_USERAPPLICATIONROLE_REMOVED = "appcenter.info.userapplicationrole.removed";
    
    // Session variable to store working values
    private UserApplicationRole _userapplicationrole;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_USERAPPLICATIONROLES, defaultView = true )
    public String getManageUserApplicationRoles( HttpServletRequest request )
    {
        _userapplicationrole = null;
        List<UserApplicationRole> listUserApplicationRoles = UserApplicationRoleHome.getUserApplicationRolesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_USERAPPLICATIONROLE_LIST, listUserApplicationRoles, JSP_MANAGE_USERAPPLICATIONROLES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_USERAPPLICATIONROLES, TEMPLATE_MANAGE_USERAPPLICATIONROLES, model );
    }

    /**
     * Returns the form to create a userapplicationrole
     *
     * @param request The Http request
     * @return the html code of the userapplicationrole form
     */
    @View( VIEW_CREATE_USERAPPLICATIONROLE )
    public String getCreateUserApplicationRole( HttpServletRequest request )
    {
        _userapplicationrole = ( _userapplicationrole != null ) ? _userapplicationrole : new UserApplicationRole(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_USERAPPLICATIONROLE, _userapplicationrole );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_USERAPPLICATIONROLE, TEMPLATE_CREATE_USERAPPLICATIONROLE, model );
    }

    /**
     * Process the data capture form of a new userapplicationrole
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_USERAPPLICATIONROLE )
    public String doCreateUserApplicationRole( HttpServletRequest request )
    {
        populate( _userapplicationrole, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _userapplicationrole, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_USERAPPLICATIONROLE );
        }

        UserApplicationRoleHome.create( _userapplicationrole );
        addInfo( INFO_USERAPPLICATIONROLE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_USERAPPLICATIONROLES );
    }

    /**
     * Manages the removal form of a userapplicationrole whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_USERAPPLICATIONROLE )
    public String getConfirmRemoveUserApplicationRole( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_USERAPPLICATIONROLE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_USERAPPLICATIONROLE ) );
        url.addParameter( PARAMETER_ID_USERAPPLICATIONROLE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_USERAPPLICATIONROLE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a userapplicationrole
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage userapplicationroles
     */
    @Action( ACTION_REMOVE_USERAPPLICATIONROLE )
    public String doRemoveUserApplicationRole( HttpServletRequest request )
    {
        int nRoleId = Integer.parseInt( request.getParameter( PARAMETER_ID_ROLE ) );
        int nApplicationId = Integer.parseInt(  request.getParameter( PARAMETER_ID_APPLICATION ) );
        String strUserId = request.getParameter( PARAMETER_ID_USER );
        
        UserApplicationRoleHome.remove( nRoleId, nApplicationId, strUserId );
        addInfo( INFO_USERAPPLICATIONROLE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_USERAPPLICATIONROLES );
    }




}