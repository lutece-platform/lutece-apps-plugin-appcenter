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
 	
package fr.paris.lutece.plugins.appcenter.modules.identitystore.web;

import fr.paris.lutece.plugins.appcenter.web.ManageAppCenterJspBean;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.Attribute;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.AttributeHome;
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
 * This class provides the user interface to manage Attribute features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageAttributes.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class AttributeJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_ATTRIBUTES = "/admin/plugins/appcenter/modules/identitystore/manage_attributes.html";
    private static final String TEMPLATE_CREATE_ATTRIBUTE = "/admin/plugins/appcenter/modules/identitystore/create_attribute.html";
    private static final String TEMPLATE_MODIFY_ATTRIBUTE = "/admin/plugins/appcenter/modules/identitystore/modify_attribute.html";

    // Parameters
    private static final String PARAMETER_ID_ATTRIBUTE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_ATTRIBUTES = "appcenter.manage_attributes.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_ATTRIBUTE = "appcenter.modify_attribute.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_ATTRIBUTE = "appcenter.create_attribute.pageTitle";

    // Markers
    private static final String MARK_ATTRIBUTE_LIST = "attribute_list";
    private static final String MARK_ATTRIBUTE = "attribute";

    private static final String JSP_MANAGE_ATTRIBUTES = "jsp/admin/plugins/appcenter/ManageAttributes.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_ATTRIBUTE = "appcenter.message.confirmRemoveAttribute";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.attribute.attribute.";

    // Views
    private static final String VIEW_MANAGE_ATTRIBUTES = "manageAttributes";
    private static final String VIEW_CREATE_ATTRIBUTE = "createAttribute";
    private static final String VIEW_MODIFY_ATTRIBUTE = "modifyAttribute";

    // Actions
    private static final String ACTION_CREATE_ATTRIBUTE = "createAttribute";
    private static final String ACTION_MODIFY_ATTRIBUTE = "modifyAttribute";
    private static final String ACTION_REMOVE_ATTRIBUTE = "removeAttribute";
    private static final String ACTION_CONFIRM_REMOVE_ATTRIBUTE = "confirmRemoveAttribute";

    // Infos
    private static final String INFO_ATTRIBUTE_CREATED = "appcenter.info.attribute.created";
    private static final String INFO_ATTRIBUTE_UPDATED = "appcenter.info.attribute.updated";
    private static final String INFO_ATTRIBUTE_REMOVED = "appcenter.info.attribute.removed";
    
    // Session variable to store working values
    private Attribute _attribute;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_ATTRIBUTES, defaultView = true )
    public String getManageAttributes( HttpServletRequest request )
    {
        _attribute = null;
        List<Attribute> listAttributes = AttributeHome.getAttributesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_ATTRIBUTE_LIST, listAttributes, JSP_MANAGE_ATTRIBUTES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_ATTRIBUTES, TEMPLATE_MANAGE_ATTRIBUTES, model );
    }

    /**
     * Returns the form to create a attribute
     *
     * @param request The Http request
     * @return the html code of the attribute form
     */
    @View( VIEW_CREATE_ATTRIBUTE )
    public String getCreateAttribute( HttpServletRequest request )
    {
        _attribute = ( _attribute != null ) ? _attribute : new Attribute(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_ATTRIBUTE, _attribute );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_ATTRIBUTE, TEMPLATE_CREATE_ATTRIBUTE, model );
    }

    /**
     * Process the data capture form of a new attribute
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_ATTRIBUTE )
    public String doCreateAttribute( HttpServletRequest request )
    {
        populate( _attribute, request );

        // Check constraints
        if ( !validateBean( _attribute, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_ATTRIBUTE );
        }

        AttributeHome.create( _attribute );
        addInfo( INFO_ATTRIBUTE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ATTRIBUTES );
    }

    /**
     * Manages the removal form of a attribute whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_ATTRIBUTE )
    public String getConfirmRemoveAttribute( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ATTRIBUTE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ATTRIBUTE ) );
        url.addParameter( PARAMETER_ID_ATTRIBUTE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ATTRIBUTE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a attribute
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage attributes
     */
    @Action( ACTION_REMOVE_ATTRIBUTE )
    public String doRemoveAttribute( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ATTRIBUTE ) );
        AttributeHome.remove( nId );
        addInfo( INFO_ATTRIBUTE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ATTRIBUTES );
    }

    /**
     * Returns the form to update info about a attribute
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_ATTRIBUTE )
    public String getModifyAttribute( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_ATTRIBUTE ) );

        if ( _attribute == null || ( _attribute.getId(  ) != nId ))
        {
            _attribute = AttributeHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_ATTRIBUTE, _attribute );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_ATTRIBUTE, TEMPLATE_MODIFY_ATTRIBUTE, model );
    }

    /**
     * Process the change form of a attribute
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_ATTRIBUTE )
    public String doModifyAttribute( HttpServletRequest request )
    {
        populate( _attribute, request );

        // Check constraints
        if ( !validateBean( _attribute, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_ATTRIBUTE, PARAMETER_ID_ATTRIBUTE, _attribute.getId( ) );
        }

        AttributeHome.update( _attribute );
        addInfo( INFO_ATTRIBUTE_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_ATTRIBUTES );
    }
}
