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

import fr.paris.lutece.plugins.appcenter.business.ActionRoleProfile;
import fr.paris.lutece.plugins.appcenter.business.ActionRoleProfileHome;
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
 * This class provides the user interface to manage ActionRoleProfile features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageActionRoleProfiles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_ACTION_ROLES_PROFILE_MANAGEMENT" )
public class ActionRoleProfileJspBean extends AbstractManageActionRolesProfileJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_ACTIONROLEPROFILES = "/admin/plugins/appcenter/manage_actionroleprofiles.html";
    private static final String TEMPLATE_CREATE_ACTIONROLEPROFILE = "/admin/plugins/appcenter/create_actionroleprofile.html";

    // Parameters
    private static final String PARAMETER_CODE_ACTIONROLE = "codeActionRole";
    private static final String PARAMETER_CODE_PROFILE = "codeProfile";
    private static final String PARAMETER_CODE_RESOURCE = "codeResource";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ACTIONROLEPROFILES = "appcenter.manage_actionroleprofiles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ACTIONROLEPROFILE = "appcenter.create_actionroleprofile.pageTitle";

    // Markers
    private static final String MARK_ACTIONROLEPROFILE_LIST = "actionroleprofile_list";
    private static final String MARK_ACTIONROLEPROFILE = "actionroleprofile";

    private static final String JSP_MANAGE_ACTIONROLEPROFILES = "jsp/admin/plugins/appcenter/ManageActionRoleProfiles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ACTIONROLEPROFILE = "appcenter.message.confirmRemoveActionRoleProfile";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.actionroleprofile.attribute.";

    // Views
    private static final String VIEW_MANAGE_ACTIONROLEPROFILES = "manageActionRoleProfiles";
    private static final String VIEW_CREATE_ACTIONROLEPROFILE = "createActionRoleProfile";

    // Actions
    private static final String ACTION_CREATE_ACTIONROLEPROFILE = "createActionRoleProfile";
    private static final String ACTION_REMOVE_ACTIONROLEPROFILE = "removeActionRoleProfile";
    private static final String ACTION_CONFIRM_REMOVE_ACTIONROLEPROFILE = "confirmRemoveActionRoleProfile";

    // Infos
    private static final String INFO_ACTIONROLEPROFILE_CREATED = "appcenter.info.actionroleprofile.created";
    private static final String INFO_ACTIONROLEPROFILE_REMOVED = "appcenter.info.actionroleprofile.removed";
    
    // Session variable to store working values
    private ActionRoleProfile _actionroleprofile;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ACTIONROLEPROFILES, defaultView = true )
    public String getManageActionRoleProfiles( HttpServletRequest request )
    {
        _actionroleprofile = null;
        List<ActionRoleProfile> listActionRoleProfiles = ActionRoleProfileHome.getActionRoleProfilesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_ACTIONROLEPROFILE_LIST, listActionRoleProfiles, JSP_MANAGE_ACTIONROLEPROFILES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ACTIONROLEPROFILES, TEMPLATE_MANAGE_ACTIONROLEPROFILES, model );
    }

    /**
     * Returns the form to create a actionroleprofile
     *
     * @param request The Http request
     * @return the html code of the actionroleprofile form
     */
    @View( VIEW_CREATE_ACTIONROLEPROFILE )
    public String getCreateActionRoleProfile( HttpServletRequest request )
    {
        _actionroleprofile = ( _actionroleprofile != null ) ? _actionroleprofile : new ActionRoleProfile(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_ACTIONROLEPROFILE, _actionroleprofile );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ACTIONROLEPROFILE, TEMPLATE_CREATE_ACTIONROLEPROFILE, model );
    }

    /**
     * Process the data capture form of a new actionroleprofile
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_ACTIONROLEPROFILE )
    public String doCreateActionRoleProfile( HttpServletRequest request )
    {
        populate( _actionroleprofile, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _actionroleprofile, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ACTIONROLEPROFILE );
        }

        ActionRoleProfileHome.create( _actionroleprofile );
        addInfo( INFO_ACTIONROLEPROFILE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLEPROFILES );
    }

    /**
     * Manages the removal form of a actionroleprofile whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ACTIONROLEPROFILE )
    public String getConfirmRemoveActionRoleProfile( HttpServletRequest request )
    {
        String strActionRoleCode = request.getParameter( PARAMETER_CODE_ACTIONROLE );
        String strProfileCode = request.getParameter( PARAMETER_CODE_PROFILE );
        String strResourceCode = request.getParameter( PARAMETER_CODE_RESOURCE );
        
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ACTIONROLEPROFILE ) );
        
        url.addParameter( PARAMETER_CODE_ACTIONROLE, strActionRoleCode );
        url.addParameter( PARAMETER_CODE_PROFILE, strProfileCode );
        url.addParameter( PARAMETER_CODE_RESOURCE, strResourceCode );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ACTIONROLEPROFILE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a actionroleprofile
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage actionroleprofiles
     */
    @Action( ACTION_REMOVE_ACTIONROLEPROFILE )
    public String doRemoveActionRoleProfile( HttpServletRequest request )
    {
        String strActionRoleCode = request.getParameter( PARAMETER_CODE_ACTIONROLE );
        String strProfileCode = request.getParameter( PARAMETER_CODE_PROFILE );
        String strResourceCode = request.getParameter( PARAMETER_CODE_RESOURCE );
        
        
        ActionRoleProfileHome.remove( strActionRoleCode, strProfileCode, strResourceCode );
        addInfo( INFO_ACTIONROLEPROFILE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ACTIONROLEPROFILES );
    }


}