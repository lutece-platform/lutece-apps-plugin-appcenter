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

import fr.paris.lutece.plugins.appcenter.business.Profile;
import fr.paris.lutece.plugins.appcenter.business.ProfileHome;
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
 * This class provides the user interface to manage Profile features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageProfiles.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_PROFILE_MANAGEMENT" )
public class ProfileJspBean extends AbstractManageProfileJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_PROFILES = "/admin/plugins/appcenter/manage_profiles.html";
    private static final String TEMPLATE_CREATE_PROFILE = "/admin/plugins/appcenter/create_profile.html";
    private static final String TEMPLATE_MODIFY_PROFILE = "/admin/plugins/appcenter/modify_profile.html";

    // Parameters
    private static final String PARAMETER_ID_PROFILE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_PROFILES = "appcenter.manage_profiles.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_PROFILE = "appcenter.modify_profile.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_PROFILE = "appcenter.create_profile.pageTitle";

    // Markers
    private static final String MARK_PROFILE_LIST = "profile_list";
    private static final String MARK_PROFILE = "profile";

    private static final String JSP_MANAGE_PROFILES = "jsp/admin/plugins/appcenter/ManageProfiles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_PROFILE = "appcenter.message.confirmRemoveProfile";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.profile.attribute.";

    // Views
    private static final String VIEW_MANAGE_PROFILES = "manageProfiles";
    private static final String VIEW_CREATE_PROFILE = "createProfile";
    private static final String VIEW_MODIFY_PROFILE = "modifyProfile";

    // Actions
    private static final String ACTION_CREATE_PROFILE = "createProfile";
    private static final String ACTION_MODIFY_PROFILE = "modifyProfile";
    private static final String ACTION_REMOVE_PROFILE = "removeProfile";
    private static final String ACTION_CONFIRM_REMOVE_PROFILE = "confirmRemoveProfile";

    // Infos
    private static final String INFO_PROFILE_CREATED = "appcenter.info.profile.created";
    private static final String INFO_PROFILE_UPDATED = "appcenter.info.profile.updated";
    private static final String INFO_PROFILE_REMOVED = "appcenter.info.profile.removed";
    
    // Session variable to store working values
    private Profile _profile;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_PROFILES, defaultView = true )
    public String getManageProfiles( HttpServletRequest request )
    {
        _profile = null;
        List<Profile> listProfiles = ProfileHome.getProfilesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_PROFILE_LIST, listProfiles, JSP_MANAGE_PROFILES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_PROFILES, TEMPLATE_MANAGE_PROFILES, model );
    }

    /**
     * Returns the form to create a profile
     *
     * @param request The Http request
     * @return the html code of the profile form
     */
    @View( VIEW_CREATE_PROFILE )
    public String getCreateProfile( HttpServletRequest request )
    {
        _profile = ( _profile != null ) ? _profile : new Profile(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_PROFILE, _profile );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_PROFILE, TEMPLATE_CREATE_PROFILE, model );
    }

    /**
     * Process the data capture form of a new profile
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_PROFILE )
    public String doCreateProfile( HttpServletRequest request )
    {
        populate( _profile, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _profile, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_PROFILE );
        }

        ProfileHome.create( _profile );
        addInfo( INFO_PROFILE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_PROFILES );
    }

    /**
     * Manages the removal form of a profile whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_PROFILE )
    public String getConfirmRemoveProfile( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_PROFILE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_PROFILE ) );
        url.addParameter( PARAMETER_ID_PROFILE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_PROFILE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a profile
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage profiles
     */
    @Action( ACTION_REMOVE_PROFILE )
    public String doRemoveProfile( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_PROFILE ) );
        ProfileHome.remove( nId );
        addInfo( INFO_PROFILE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_PROFILES );
    }

    /**
     * Returns the form to update info about a profile
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_PROFILE )
    public String getModifyProfile( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_PROFILE ) );

        if ( _profile == null || ( _profile.getId(  ) != nId ) )
        {
            _profile = ProfileHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_PROFILE, _profile );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_PROFILE, TEMPLATE_MODIFY_PROFILE, model );
    }

    /**
     * Process the change form of a profile
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_PROFILE )
    public String doModifyProfile( HttpServletRequest request )
    {
        populate( _profile, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _profile, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_PROFILE, PARAMETER_ID_PROFILE, _profile.getId( ) );
        }

        ProfileHome.update( _profile );
        addInfo( INFO_PROFILE_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_PROFILES );
    }
}