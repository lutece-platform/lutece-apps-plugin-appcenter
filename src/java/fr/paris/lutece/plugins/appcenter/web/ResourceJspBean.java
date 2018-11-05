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

import fr.paris.lutece.plugins.appcenter.business.Resource;
import fr.paris.lutece.plugins.appcenter.business.ResourceHome;
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
 * This class provides the user interface to manage Resource features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageResources.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_RESOURCE_MANAGEMENT" )
public class ResourceJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_RESOURCES = "/admin/plugins/appcenter/manage_resources.html";
    private static final String TEMPLATE_CREATE_RESOURCE = "/admin/plugins/appcenter/create_resource.html";
    private static final String TEMPLATE_MODIFY_RESOURCE = "/admin/plugins/appcenter/modify_resource.html";

    // Parameters
    private static final String PARAMETER_ID_RESOURCE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_RESOURCES = "appcenter.manage_resources.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_RESOURCE = "appcenter.modify_resource.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_RESOURCE = "appcenter.create_resource.pageTitle";

    // Markers
    private static final String MARK_RESOURCE_LIST = "resource_list";
    private static final String MARK_RESOURCE = "resource";

    private static final String JSP_MANAGE_RESOURCES = "jsp/admin/plugins/appcenter/ManageResources.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_RESOURCE = "appcenter.message.confirmRemoveResource";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.resource.attribute.";

    // Views
    private static final String VIEW_MANAGE_RESOURCES = "manageResources";
    private static final String VIEW_CREATE_RESOURCE = "createResource";
    private static final String VIEW_MODIFY_RESOURCE = "modifyResource";

    // Actions
    private static final String ACTION_CREATE_RESOURCE = "createResource";
    private static final String ACTION_MODIFY_RESOURCE = "modifyResource";
    private static final String ACTION_REMOVE_RESOURCE = "removeResource";
    private static final String ACTION_CONFIRM_REMOVE_RESOURCE = "confirmRemoveResource";

    // Infos
    private static final String INFO_RESOURCE_CREATED = "appcenter.info.resource.created";
    private static final String INFO_RESOURCE_UPDATED = "appcenter.info.resource.updated";
    private static final String INFO_RESOURCE_REMOVED = "appcenter.info.resource.removed";
    
    // Session variable to store working values
    private Resource _resource;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_RESOURCES, defaultView = true )
    public String getManageResources( HttpServletRequest request )
    {
        _resource = null;
        List<Resource> listResources = ResourceHome.getResourcesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_RESOURCE_LIST, listResources, JSP_MANAGE_RESOURCES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_RESOURCES, TEMPLATE_MANAGE_RESOURCES, model );
    }

    /**
     * Returns the form to create a resource
     *
     * @param request The Http request
     * @return the html code of the resource form
     */
    @View( VIEW_CREATE_RESOURCE )
    public String getCreateResource( HttpServletRequest request )
    {
        _resource = ( _resource != null ) ? _resource : new Resource(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_RESOURCE, _resource );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_RESOURCE, TEMPLATE_CREATE_RESOURCE, model );
    }

    /**
     * Process the data capture form of a new resource
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_RESOURCE )
    public String doCreateResource( HttpServletRequest request )
    {
        populate( _resource, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _resource, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_RESOURCE );
        }

        ResourceHome.create( _resource );
        addInfo( INFO_RESOURCE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RESOURCES );
    }

    /**
     * Manages the removal form of a resource whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_RESOURCE )
    public String getConfirmRemoveResource( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_RESOURCE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_RESOURCE ) );
        url.addParameter( PARAMETER_ID_RESOURCE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_RESOURCE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a resource
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage resources
     */
    @Action( ACTION_REMOVE_RESOURCE )
    public String doRemoveResource( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_RESOURCE ) );
        ResourceHome.remove( nId );
        addInfo( INFO_RESOURCE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RESOURCES );
    }

    /**
     * Returns the form to update info about a resource
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_RESOURCE )
    public String getModifyResource( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_RESOURCE ) );

        if ( _resource == null || ( _resource.getId(  ) != nId ) )
        {
            _resource = ResourceHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_RESOURCE, _resource );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_RESOURCE, TEMPLATE_MODIFY_RESOURCE, model );
    }

    /**
     * Process the change form of a resource
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_RESOURCE )
    public String doModifyResource( HttpServletRequest request )
    {
        populate( _resource, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _resource, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_RESOURCE, PARAMETER_ID_RESOURCE, _resource.getId( ) );
        }

        ResourceHome.update( _resource );
        addInfo( INFO_RESOURCE_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RESOURCES );
    }
}