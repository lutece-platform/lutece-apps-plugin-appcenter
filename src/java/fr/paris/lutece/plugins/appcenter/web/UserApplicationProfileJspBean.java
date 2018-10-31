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

import fr.paris.lutece.plugins.appcenter.business.UserApplicationProfile;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationProfileHome;
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
 * This class provides the user interface to manage UserApplicationProfile features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageUserApplicationProfiles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_USER_APPLICATION_PROFILE_MANAGEMENT" )
public class UserApplicationProfileJspBean extends AbstractManageUserApplicationProfileJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_USERAPPLICATIONPROFILES = "/admin/plugins/appcenter/manage_userapplicationprofiles.html";
    private static final String TEMPLATE_CREATE_USERAPPLICATIONPROFILE = "/admin/plugins/appcenter/create_userapplicationprofile.html";

    // Parameters
    private static final String PARAMETER_ID_USERAPPLICATIONPROFILE = "id";
    private static final String PARAMETER_ID_PROFILE = "idProfile" ;
    private static final String PARAMETER_ID_APPLICATION = "idApplication" ;
    private static final String PARAMETER_ID_USER = "idUser" ;
               

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_USERAPPLICATIONPROFILES = "appcenter.manage_userapplicationprofiles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_USERAPPLICATIONPROFILE = "appcenter.create_userapplicationprofile.pageTitle";

    // Markers
    private static final String MARK_USERAPPLICATIONPROFILE_LIST = "userapplicationprofile_list";
    private static final String MARK_USERAPPLICATIONPROFILE = "userapplicationprofile";

    private static final String JSP_MANAGE_USERAPPLICATIONPROFILES = "jsp/admin/plugins/appcenter/ManageUserApplicationProfiles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_USERAPPLICATIONPROFILE = "appcenter.message.confirmRemoveUserApplicationProfile";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.userapplicationprofile.attribute.";

    // Views
    private static final String VIEW_MANAGE_USERAPPLICATIONPROFILES = "manageUserApplicationProfiles";
    private static final String VIEW_CREATE_USERAPPLICATIONPROFILE = "createUserApplicationProfile";

    // Actions
    private static final String ACTION_CREATE_USERAPPLICATIONPROFILE = "createUserApplicationProfile";
    private static final String ACTION_REMOVE_USERAPPLICATIONPROFILE = "removeUserApplicationProfile";
    private static final String ACTION_CONFIRM_REMOVE_USERAPPLICATIONPROFILE = "confirmRemoveUserApplicationProfile";

    // Infos
    private static final String INFO_USERAPPLICATIONPROFILE_CREATED = "appcenter.info.userapplicationprofile.created";
    private static final String INFO_USERAPPLICATIONPROFILE_REMOVED = "appcenter.info.userapplicationprofile.removed";
    
    // Session variable to store working values
    private UserApplicationProfile _userapplicationprofile;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_USERAPPLICATIONPROFILES, defaultView = true )
    public String getManageUserApplicationProfiles( HttpServletRequest request )
    {
        _userapplicationprofile = null;
        List<UserApplicationProfile> listUserApplicationProfiles = UserApplicationProfileHome.getUserApplicationProfilesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_USERAPPLICATIONPROFILE_LIST, listUserApplicationProfiles, JSP_MANAGE_USERAPPLICATIONPROFILES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_USERAPPLICATIONPROFILES, TEMPLATE_MANAGE_USERAPPLICATIONPROFILES, model );
    }

    /**
     * Returns the form to create a userapplicationprofile
     *
     * @param request The Http request
     * @return the html code of the userapplicationprofile form
     */
    @View( VIEW_CREATE_USERAPPLICATIONPROFILE )
    public String getCreateUserApplicationProfile( HttpServletRequest request )
    {
        _userapplicationprofile = ( _userapplicationprofile != null ) ? _userapplicationprofile : new UserApplicationProfile(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_USERAPPLICATIONPROFILE, _userapplicationprofile );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_USERAPPLICATIONPROFILE, TEMPLATE_CREATE_USERAPPLICATIONPROFILE, model );
    }

    /**
     * Process the data capture form of a new userapplicationprofile
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_USERAPPLICATIONPROFILE )
    public String doCreateUserApplicationProfile( HttpServletRequest request )
    {
        populate( _userapplicationprofile, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _userapplicationprofile, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_USERAPPLICATIONPROFILE );
        }

        UserApplicationProfileHome.create( _userapplicationprofile );
        addInfo( INFO_USERAPPLICATIONPROFILE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_USERAPPLICATIONPROFILES );
    }

    /**
     * Manages the removal form of a userapplicationprofile whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_USERAPPLICATIONPROFILE )
    public String getConfirmRemoveUserApplicationProfile( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_USERAPPLICATIONPROFILE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_USERAPPLICATIONPROFILE ) );
        url.addParameter( PARAMETER_ID_USERAPPLICATIONPROFILE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_USERAPPLICATIONPROFILE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a userapplicationprofile
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage userapplicationprofiles
     */
    @Action( ACTION_REMOVE_USERAPPLICATIONPROFILE )
    public String doRemoveUserApplicationProfile( HttpServletRequest request )
    {
        int nProfileId = Integer.parseInt( request.getParameter( PARAMETER_ID_PROFILE ) );
        int nApplicationId = Integer.parseInt(  request.getParameter( PARAMETER_ID_APPLICATION ) );
        String strUserId = request.getParameter( PARAMETER_ID_USER );
        
        UserApplicationProfileHome.remove( nProfileId, nApplicationId, strUserId );
        addInfo( INFO_USERAPPLICATIONPROFILE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_USERAPPLICATIONPROFILES );
    }




}