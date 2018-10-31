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

import fr.paris.lutece.plugins.appcenter.business.ActionRole;
import fr.paris.lutece.plugins.appcenter.business.ActionRoleHome;
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
 * This class provides the user interface to manage ActionRole features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageActionRoles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_ACTION_ROLES_MANAGEMENT" )
public class ActionRoleJspBean extends AbstractManageActionRolesJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_ACTIONROLES = "/admin/plugins/appcenter/manage_actionroles.html";
    private static final String TEMPLATE_CREATE_ACTIONROLE = "/admin/plugins/appcenter/create_actionrole.html";
    private static final String TEMPLATE_MODIFY_ACTIONROLE = "/admin/plugins/appcenter/modify_actionrole.html";

    // Parameters
    private static final String PARAMETER_ID_ACTIONROLE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ACTIONROLES = "appcenter.manage_actionroles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_ACTIONROLE = "appcenter.modify_actionrole.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ACTIONROLE = "appcenter.create_actionrole.pageTitle";

    // Markers
    private static final String MARK_ACTIONROLE_LIST = "actionrole_list";
    private static final String MARK_ACTIONROLE = "actionrole";

    private static final String JSP_MANAGE_ACTIONROLES = "jsp/admin/plugins/appcenter/ManageActionRoles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ACTIONROLE = "appcenter.message.confirmRemoveActionRole";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.actionrole.attribute.";

    // Views
    private static final String VIEW_MANAGE_ACTIONROLES = "manageActionRoles";
    private static final String VIEW_CREATE_ACTIONROLE = "createActionRole";
    private static final String VIEW_MODIFY_ACTIONROLE = "modifyActionRole";

    // Actions
    private static final String ACTION_CREATE_ACTIONROLE = "createActionRole";
    private static final String ACTION_MODIFY_ACTIONROLE = "modifyActionRole";
    private static final String ACTION_REMOVE_ACTIONROLE = "removeActionRole";
    private static final String ACTION_CONFIRM_REMOVE_ACTIONROLE = "confirmRemoveActionRole";

    // Infos
    private static final String INFO_ACTIONROLE_CREATED = "appcenter.info.actionrole.created";
    private static final String INFO_ACTIONROLE_UPDATED = "appcenter.info.actionrole.updated";
    private static final String INFO_ACTIONROLE_REMOVED = "appcenter.info.actionrole.removed";
    
    // Session variable to store working values
    private ActionRole _actionrole;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ACTIONROLES, defaultView = true )
    public String getManageActionRoles( HttpServletRequest request )
    {
        _actionrole = null;
        List<ActionRole> listActionRoles = ActionRoleHome.getActionRolesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_ACTIONROLE_LIST, listActionRoles, JSP_MANAGE_ACTIONROLES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ACTIONROLES, TEMPLATE_MANAGE_ACTIONROLES, model );
    }

    /**
     * Returns the form to create a actionrole
     *
     * @param request The Http request
     * @return the html code of the actionrole form
     */
    @View( VIEW_CREATE_ACTIONROLE )
    public String getCreateActionRole( HttpServletRequest request )
    {
        _actionrole = ( _actionrole != null ) ? _actionrole : new ActionRole(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_ACTIONROLE, _actionrole );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ACTIONROLE, TEMPLATE_CREATE_ACTIONROLE, model );
    }

    /**
     * Process the data capture form of a new actionrole
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_ACTIONROLE )
    public String doCreateActionRole( HttpServletRequest request )
    {
        populate( _actionrole, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _actionrole, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ACTIONROLE );
        }

        ActionRoleHome.create( _actionrole );
        addInfo( INFO_ACTIONROLE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLES );
    }

    /**
     * Manages the removal form of a actionrole whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ACTIONROLE )
    public String getConfirmRemoveActionRole( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ACTIONROLE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ACTIONROLE ) );
        url.addParameter( PARAMETER_ID_ACTIONROLE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ACTIONROLE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a actionrole
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage actionroles
     */
    @Action( ACTION_REMOVE_ACTIONROLE )
    public String doRemoveActionRole( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ACTIONROLE ) );
        ActionRoleHome.remove( nId );
        addInfo( INFO_ACTIONROLE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLES );
    }

    /**
     * Returns the form to update info about a actionrole
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_ACTIONROLE )
    public String getModifyActionRole( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ACTIONROLE ) );

        if ( _actionrole == null || ( _actionrole.getId(  ) != nId ) )
        {
            _actionrole = ActionRoleHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_ACTIONROLE, _actionrole );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_ACTIONROLE, TEMPLATE_MODIFY_ACTIONROLE, model );
    }

    /**
     * Process the change form of a actionrole
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_ACTIONROLE )
    public String doModifyActionRole( HttpServletRequest request )
    {
        populate( _actionrole, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _actionrole, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_ACTIONROLE, PARAMETER_ID_ACTIONROLE, _actionrole.getId( ) );
        }

        ActionRoleHome.update( _actionrole );
        addInfo( INFO_ACTIONROLE_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLES );
    }
}