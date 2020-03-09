/*
 * Copyright (c) 2002-2020, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.web.organization;

import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManager;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManagerHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage OrganizationManager features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageOrganizationManagers.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class OrganizationManagerJspBean extends AbstractManageOrganizationJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_ORGANIZATIONMANAGERS = "/admin/plugins/appcenter/manage_organizationmanagers.html";
    private static final String TEMPLATE_CREATE_ORGANIZATIONMANAGER = "/admin/plugins/appcenter/create_organizationmanager.html";
    private static final String TEMPLATE_MODIFY_ORGANIZATIONMANAGER = "/admin/plugins/appcenter/modify_organizationmanager.html";

    // Parameters
    private static final String PARAMETER_ID_ORGANIZATION = "id_organization";
    private static final String PARAMETER_ID_ORGANIZATIONMANAGER = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ORGANIZATIONMANAGERS = "appcenter.manage_organizationmanagers.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_ORGANIZATIONMANAGER = "appcenter.modify_organizationmanager.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ORGANIZATIONMANAGER = "appcenter.create_organizationmanager.pageTitle";

    // Markers
    private static final String MARK_ORGANIZATIONMANAGER_LIST = "organizationManager_list";
    private static final String MARK_ORGANIZATIONMANAGER = "organizationManager";
    private static final String MARK_ID_ORGANIZATION = "id_organization";

    private static final String JSP_MANAGE_ORGANIZATIONMANAGERS = "jsp/admin/plugins/appcenter/ManageOrganizationManagers.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ORGANIZATIONMANAGER = "appcenter.message.confirmRemoveOrganizationManager";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.organizationmanager.attribute.";

    // Views
    private static final String VIEW_MANAGE_ORGANIZATIONMANAGERS = "manageOrganizationManagers";
    private static final String VIEW_CREATE_ORGANIZATIONMANAGER = "createOrganizationManager";
    private static final String VIEW_MODIFY_ORGANIZATIONMANAGER = "modifyOrganizationManager";

    // Actions
    private static final String ACTION_CREATE_ORGANIZATIONMANAGER = "createOrganizationManager";
    private static final String ACTION_MODIFY_ORGANIZATIONMANAGER = "modifyOrganizationManager";
    private static final String ACTION_REMOVE_ORGANIZATIONMANAGER = "removeOrganizationManager";
    private static final String ACTION_CONFIRM_REMOVE_ORGANIZATIONMANAGER = "confirmRemoveOrganizationManager";

    // Infos
    private static final String INFO_ORGANIZATIONMANAGER_CREATED = "appcenter.info.organizationmanager.created";
    private static final String INFO_ORGANIZATIONMANAGER_UPDATED = "appcenter.info.organizationmanager.updated";
    private static final String INFO_ORGANIZATIONMANAGER_REMOVED = "appcenter.info.organizationmanager.removed";

    // Session variable to store working values
    private OrganizationManager _organizationmanager;

    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ORGANIZATIONMANAGERS, defaultView = true )
    public String getManageOrganizationManagers( HttpServletRequest request )
    {
        _organizationmanager = null;
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ORGANIZATION ) );

        List<OrganizationManager> listOrganizationManagers = OrganizationManagerHome.getOrganizationManagersList(  );

        List<OrganizationManager> listFilteredOrganizationManagers = new ArrayList<>( );

        for (OrganizationManager organizationManager : listOrganizationManagers )
        {
            if ( organizationManager.getIdOrganization( ) == nId )
            {
                listFilteredOrganizationManagers.add( organizationManager );
            }
        }

        Map<String, Object> model = getPaginatedListModel( request, MARK_ORGANIZATIONMANAGER_LIST, listFilteredOrganizationManagers, JSP_MANAGE_ORGANIZATIONMANAGERS );
        model.put( MARK_ID_ORGANIZATION, nId );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ORGANIZATIONMANAGERS, TEMPLATE_MANAGE_ORGANIZATIONMANAGERS, model );
    }

    /**
     * Returns the form to create a organizationmanager
     *
     * @param request The Http request
     * @return the html code of the organizationmanager form
     */
    @View( VIEW_CREATE_ORGANIZATIONMANAGER )
    public String getCreateOrganizationManager( HttpServletRequest request )
    {
        _organizationmanager = ( _organizationmanager != null ) ? _organizationmanager : new OrganizationManager(  );
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ORGANIZATION ) );

        Map<String, Object> model = getModel(  );
        model.put( MARK_ID_ORGANIZATION, nId );
        model.put( MARK_ORGANIZATIONMANAGER, _organizationmanager );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_ORGANIZATIONMANAGER ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ORGANIZATIONMANAGER, TEMPLATE_CREATE_ORGANIZATIONMANAGER, model );
    }

    /**
     * Process the data capture form of a new organizationmanager
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_ORGANIZATIONMANAGER )
    public String doCreateOrganizationManager( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _organizationmanager, request, request.getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_ORGANIZATIONMANAGER ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _organizationmanager, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ORGANIZATIONMANAGER );
        }

        OrganizationManagerHome.create( _organizationmanager );
        addInfo( INFO_ORGANIZATIONMANAGER_CREATED, getLocale(  ) );

        return redirect( request, VIEW_MANAGE_ORGANIZATIONMANAGERS, PARAMETER_ID_ORGANIZATION, _organizationmanager.getIdOrganization( ) );
    }

    /**
     * Manages the removal form of a organizationmanager whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ORGANIZATIONMANAGER )
    public String getConfirmRemoveOrganizationManager( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ORGANIZATIONMANAGER ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ORGANIZATIONMANAGER ) );
        url.addParameter( PARAMETER_ID_ORGANIZATIONMANAGER, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ORGANIZATIONMANAGER, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a organizationmanager
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage organizationmanagers
     */
    @Action( ACTION_REMOVE_ORGANIZATIONMANAGER )
    public String doRemoveOrganizationManager( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ORGANIZATIONMANAGER ) );

        if ( _organizationmanager == null || ( _organizationmanager.getIdOrganizationManager(  ) != nId ) )
        {
            _organizationmanager = OrganizationManagerHome.findByPrimaryKey( nId );
        }

        OrganizationManagerHome.remove( nId );
        addInfo( INFO_ORGANIZATIONMANAGER_REMOVED, getLocale(  ) );

        return redirect( request, VIEW_MANAGE_ORGANIZATIONMANAGERS, PARAMETER_ID_ORGANIZATION, _organizationmanager.getIdOrganization( ) );
    }

    /**
     * Returns the form to update info about a organizationmanager
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_ORGANIZATIONMANAGER )
    public String getModifyOrganizationManager( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ORGANIZATIONMANAGER ) );

        if ( _organizationmanager == null || ( _organizationmanager.getIdOrganizationManager(  ) != nId ) )
        {
            _organizationmanager = OrganizationManagerHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_ORGANIZATIONMANAGER, _organizationmanager );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_ORGANIZATIONMANAGER ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_ORGANIZATIONMANAGER, TEMPLATE_MODIFY_ORGANIZATIONMANAGER, model );
    }

    /**
     * Process the change form of a organizationmanager
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_ORGANIZATIONMANAGER )
    public String doModifyOrganizationManager( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _organizationmanager, request, request.getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_ORGANIZATIONMANAGER ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _organizationmanager, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_ORGANIZATIONMANAGER, PARAMETER_ID_ORGANIZATIONMANAGER, _organizationmanager.getIdOrganizationManager( ) );
        }

        OrganizationManagerHome.update( _organizationmanager );
        addInfo( INFO_ORGANIZATIONMANAGER_UPDATED, getLocale(  ) );

        return redirect( request, VIEW_MANAGE_ORGANIZATIONMANAGERS, PARAMETER_ID_ORGANIZATION, _organizationmanager.getIdOrganization( ) );
    }
}
