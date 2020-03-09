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

import fr.paris.lutece.plugins.appcenter.business.organization.Organization;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage Organization features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageOrganizations.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class OrganizationJspBean extends AbstractManageOrganizationJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_ORGANIZATIONS = "/admin/plugins/appcenter/manage_organizations.html";
    private static final String TEMPLATE_CREATE_ORGANIZATION = "/admin/plugins/appcenter/create_organization.html";
    private static final String TEMPLATE_MODIFY_ORGANIZATION = "/admin/plugins/appcenter/modify_organization.html";

    // Parameters
    private static final String PARAMETER_ID_ORGANIZATION = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ORGANIZATIONS = "appcenter.manage_organizations.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_ORGANIZATION = "appcenter.modify_organization.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ORGANIZATION = "appcenter.create_organization.pageTitle";

    // Markers
    private static final String MARK_ORGANIZATION_LIST = "organization_list";
    private static final String MARK_ORGANIZATION = "organization";

    private static final String JSP_MANAGE_ORGANIZATIONS = "jsp/admin/plugins/appcenter/ManageOrganizations.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ORGANIZATION = "appcenter.message.confirmRemoveOrganization";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.organization.attribute.";

    // Views
    private static final String VIEW_MANAGE_ORGANIZATIONS = "manageOrganizations";
    private static final String VIEW_CREATE_ORGANIZATION = "createOrganization";
    private static final String VIEW_MODIFY_ORGANIZATION = "modifyOrganization";

    // Actions
    private static final String ACTION_CREATE_ORGANIZATION = "createOrganization";
    private static final String ACTION_MODIFY_ORGANIZATION = "modifyOrganization";
    private static final String ACTION_REMOVE_ORGANIZATION = "removeOrganization";
    private static final String ACTION_CONFIRM_REMOVE_ORGANIZATION = "confirmRemoveOrganization";

    // Infos
    private static final String INFO_ORGANIZATION_CREATED = "appcenter.info.organization.created";
    private static final String INFO_ORGANIZATION_UPDATED = "appcenter.info.organization.updated";
    private static final String INFO_ORGANIZATION_REMOVED = "appcenter.info.organization.removed";

    // Session variable to store working values
    private Organization _organization;

    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ORGANIZATIONS, defaultView = true )
    public String getManageOrganizations( HttpServletRequest request )
    {
        _organization = null;
        List<Organization> listOrganizations = OrganizationHome.getOrganizationsList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_ORGANIZATION_LIST, listOrganizations, JSP_MANAGE_ORGANIZATIONS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ORGANIZATIONS, TEMPLATE_MANAGE_ORGANIZATIONS, model );
    }

    /**
     * Returns the form to create a organization
     *
     * @param request The Http request
     * @return the html code of the organization form
     */
    @View( VIEW_CREATE_ORGANIZATION )
    public String getCreateOrganization( HttpServletRequest request )
    {
        _organization = ( _organization != null ) ? _organization : new Organization(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_ORGANIZATION, _organization );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_ORGANIZATION ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ORGANIZATION, TEMPLATE_CREATE_ORGANIZATION, model );
    }

    /**
     * Process the data capture form of a new organization
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_ORGANIZATION )
    public String doCreateOrganization( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _organization, request, request.getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_ORGANIZATION ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _organization, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ORGANIZATION );
        }

        OrganizationHome.create( _organization );
        addInfo( INFO_ORGANIZATION_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ORGANIZATIONS );
    }

    /**
     * Manages the removal form of a organization whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ORGANIZATION )
    public String getConfirmRemoveOrganization( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ORGANIZATION ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ORGANIZATION ) );
        url.addParameter( PARAMETER_ID_ORGANIZATION, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ORGANIZATION, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a organization
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage organizations
     */
    @Action( ACTION_REMOVE_ORGANIZATION )
    public String doRemoveOrganization( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ORGANIZATION ) );
        OrganizationHome.remove( nId );
        addInfo( INFO_ORGANIZATION_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ORGANIZATIONS );
    }

    /**
     * Returns the form to update info about a organization
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_ORGANIZATION )
    public String getModifyOrganization( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ORGANIZATION ) );

        if ( _organization == null || ( _organization.getIdOrganization( ) != nId ) )
        {
            _organization = OrganizationHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_ORGANIZATION, _organization );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_ORGANIZATION ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_ORGANIZATION, TEMPLATE_MODIFY_ORGANIZATION, model );
    }

    /**
     * Process the change form of a organization
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_ORGANIZATION )
    public String doModifyOrganization( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _organization, request, request.getLocale( ) );

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_ORGANIZATION ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _organization, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_ORGANIZATION, PARAMETER_ID_ORGANIZATION, _organization.getIdOrganization( ) );
        }

        OrganizationHome.update( _organization );
        addInfo( INFO_ORGANIZATION_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ORGANIZATIONS );
    }
}
