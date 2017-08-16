/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.UserApplication;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationHome;
import fr.paris.lutece.plugins.appcenter.service.RoleService;
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
 * This class provides the user interface to manage UserApplication features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageUserApplications.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class UserApplicationJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_USERAPPLICATIONS = "/admin/plugins/appcenter/manage_userapplications.html";
    private static final String TEMPLATE_CREATE_USERAPPLICATION = "/admin/plugins/appcenter/create_userapplication.html";
    private static final String TEMPLATE_MODIFY_USERAPPLICATION = "/admin/plugins/appcenter/modify_userapplication.html";

    // Parameters
    private static final String PARAMETER_ID_USERAPPLICATION = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_USERAPPLICATIONS = "appcenter.manage_userapplications.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_USERAPPLICATION = "appcenter.modify_userapplication.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_USERAPPLICATION = "appcenter.create_userapplication.pageTitle";

    // Markers
    private static final String MARK_USERAPPLICATION_LIST = "userapplication_list";
    private static final String MARK_USERAPPLICATION = "userapplication";
    private static final String MARK_APPLICATION_LIST = "applications_list";
    private static final String MARK_ROLES_LIST = "roles_list";

    private static final String JSP_MANAGE_USERAPPLICATIONS = "jsp/admin/plugins/appcenter/ManageUserApplications.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_USERAPPLICATION = "appcenter.message.confirmRemoveUserApplication";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.userapplication.attribute.";

    // Views
    private static final String VIEW_MANAGE_USERAPPLICATIONS = "manageUserApplications";
    private static final String VIEW_CREATE_USERAPPLICATION = "createUserApplication";
    private static final String VIEW_MODIFY_USERAPPLICATION = "modifyUserApplication";

    // Actions
    private static final String ACTION_CREATE_USERAPPLICATION = "createUserApplication";
    private static final String ACTION_MODIFY_USERAPPLICATION = "modifyUserApplication";
    private static final String ACTION_REMOVE_USERAPPLICATION = "removeUserApplication";
    private static final String ACTION_CONFIRM_REMOVE_USERAPPLICATION = "confirmRemoveUserApplication";

    // Infos
    private static final String INFO_USERAPPLICATION_CREATED = "appcenter.info.userapplication.created";
    private static final String INFO_USERAPPLICATION_UPDATED = "appcenter.info.userapplication.updated";
    private static final String INFO_USERAPPLICATION_REMOVED = "appcenter.info.userapplication.removed";

    // Session variable to store working values
    private UserApplication _userapplication;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_USERAPPLICATIONS, defaultView = true )
    public String getManageUserApplications( HttpServletRequest request )
    {
        _userapplication = null;
        List<UserApplication> listUserApplications = UserApplicationHome.getUserApplicationsList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_USERAPPLICATION_LIST, listUserApplications, JSP_MANAGE_USERAPPLICATIONS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_USERAPPLICATIONS, TEMPLATE_MANAGE_USERAPPLICATIONS, model );
    }

    /**
     * Returns the form to create a userapplication
     *
     * @param request
     *            The Http request
     * @return the html code of the userapplication form
     */
    @View( VIEW_CREATE_USERAPPLICATION )
    public String getCreateUserApplication( HttpServletRequest request )
    {
        _userapplication = ( _userapplication != null ) ? _userapplication : new UserApplication( );

        Map<String, Object> model = getModel( );
        model.put( MARK_USERAPPLICATION, _userapplication );
        model.put( MARK_APPLICATION_LIST , ApplicationHome.getApplicationsReferenceList() );
        model.put( MARK_ROLES_LIST , RoleService.getRolesList() );
        return getPage( PROPERTY_PAGE_TITLE_CREATE_USERAPPLICATION, TEMPLATE_CREATE_USERAPPLICATION, model );
    }

    /**
     * Process the data capture form of a new userapplication
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_USERAPPLICATION )
    public String doCreateUserApplication( HttpServletRequest request )
    {
        populate( _userapplication, request );

        // Check constraints
        if ( !validateBean( _userapplication, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_USERAPPLICATION );
        }

        UserApplicationHome.create( _userapplication );
        addInfo( INFO_USERAPPLICATION_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_USERAPPLICATIONS );
    }

    /**
     * Manages the removal form of a userapplication whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_USERAPPLICATION )
    public String getConfirmRemoveUserApplication( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_USERAPPLICATION ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_USERAPPLICATION ) );
        url.addParameter( PARAMETER_ID_USERAPPLICATION, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_USERAPPLICATION, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a userapplication
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage userapplications
     */
    @Action( ACTION_REMOVE_USERAPPLICATION )
    public String doRemoveUserApplication( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_USERAPPLICATION ) );
        UserApplicationHome.remove( nId );
        addInfo( INFO_USERAPPLICATION_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_USERAPPLICATIONS );
    }

    /**
     * Returns the form to update info about a userapplication
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_USERAPPLICATION )
    public String getModifyUserApplication( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_USERAPPLICATION ) );

        if ( _userapplication == null || ( _userapplication.getId( ) != nId ) )
        {
            _userapplication = UserApplicationHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_USERAPPLICATION, _userapplication );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_USERAPPLICATION, TEMPLATE_MODIFY_USERAPPLICATION, model );
    }

    /**
     * Process the change form of a userapplication
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_USERAPPLICATION )
    public String doModifyUserApplication( HttpServletRequest request )
    {
        populate( _userapplication, request );

        // Check constraints
        if ( !validateBean( _userapplication, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_USERAPPLICATION, PARAMETER_ID_USERAPPLICATION, _userapplication.getId( ) );
        }

        UserApplicationHome.update( _userapplication );
        addInfo( INFO_USERAPPLICATION_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_USERAPPLICATIONS );
    }
}
