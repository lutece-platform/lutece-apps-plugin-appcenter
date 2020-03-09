/*
 * Copyright (c) 2002-2019, Mairie de Paris
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

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationFilter;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.organization.Organization;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationHome;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManager;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManagerHome;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sort.AttributeComparator;
import fr.paris.lutece.util.url.UrlItem;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage Application features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageApplications.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class ApplicationJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_APPLICATIONS = "/admin/plugins/appcenter/manage_applications.html";
    private static final String TEMPLATE_CREATE_APPLICATION = "/admin/plugins/appcenter/create_application.html";
    private static final String TEMPLATE_MODIFY_APPLICATION = "/admin/plugins/appcenter/modify_application.html";
    private static final String TEMPLATE_APPLICATION_DETAIL = "/admin/plugins/appcenter/application_detail.html";

    // Parameters
    private static final String PARAMETER_ID_APPLICATION = "id";
    private static final String PARAMETER_SEARCH = "search";
    private static final String PARAMETER_ID_ORGANIZATION_MANAGER = "id_organization_manager";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_APPLICATIONS = "appcenter.manage_applications.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_APPLICATION = "appcenter.modify_application.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_APPLICATION = "appcenter.create_application.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_APPLICATION_DETAIL = "appcenter.application_detail.pageTitle";

    // Markers
    private static final String MARK_APPLICATION_LIST = "application_list";
    private static final String MARK_APPLICATION = "application";
    private static final String MARK_APPLICATION_FILTER = "application_filter";
    private static final String MARK_JSON_DATA = "json_data";
    private static final String MARK_ORGANIZATIONS = "list_organizations";
    private static final String MARK_ORGANIZATION_MANAGERS = "list_organization_managers";

    private static final String JSP_MANAGE_APPLICATIONS = "jsp/admin/plugins/appcenter/ManageApplications.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_APPLICATION = "appcenter.message.confirmRemoveApplication";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.application.attribute.";

    // Views
    private static final String VIEW_MANAGE_APPLICATIONS = "manageApplications";
    private static final String VIEW_CREATE_APPLICATION = "createApplication";
    private static final String VIEW_MODIFY_APPLICATION = "modifyApplication";
    private static final String VIEW_APPLICATION_DETAIL = "applicationDetail";

    // Actions
    private static final String ACTION_CREATE_APPLICATION = "createApplication";
    private static final String ACTION_MODIFY_APPLICATION = "modifyApplication";
    private static final String ACTION_REMOVE_APPLICATION = "removeApplication";
    private static final String ACTION_CONFIRM_REMOVE_APPLICATION = "confirmRemoveApplication";
    private static final String ACTION_FILTER_APPLICATION = "filterApplications";

    // Infos
    private static final String INFO_APPLICATION_CREATED = "appcenter.info.application.created";
    private static final String INFO_APPLICATION_UPDATED = "appcenter.info.application.updated";
    private static final String INFO_APPLICATION_REMOVED = "appcenter.info.application.removed";

    // Session variable to store working values
    private Application _application;
    private ApplicationFilter _filter;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_APPLICATIONS, defaultView = true )
    public String getManageApplications( HttpServletRequest request )
    {
        // Initialize the demand filter
        if ( _filter == null )
        {
            _filter = new ApplicationFilter( );
        }

        _application = null;
        List<Application> listApplications = ApplicationHome.getApplicationsListByFilter( _filter );

        // SORT
        String strSortedAttributeName = request.getParameter( Parameters.SORTED_ATTRIBUTE_NAME );
        String strAscSort = null;

        if ( strSortedAttributeName != null )
        {
            strAscSort = request.getParameter( Parameters.SORTED_ASC );

            boolean bIsAscSort = Boolean.parseBoolean( strAscSort );

            Collections.sort( listApplications, new AttributeComparator( strSortedAttributeName, bIsAscSort ) );
        }

        UrlItem url = new UrlItem( JSP_MANAGE_APPLICATIONS );

        if ( strSortedAttributeName != null )
        {
            url.addParameter( Parameters.SORTED_ATTRIBUTE_NAME, strSortedAttributeName );
        }

        if ( strAscSort != null )
        {
            url.addParameter( Parameters.SORTED_ASC, strAscSort );
        }

        Map<String, Object> model = getPaginatedListModel( request, MARK_APPLICATION_LIST, listApplications, url.getUrl( ) );

        model.put( MARK_APPLICATION_FILTER, _filter );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_APPLICATIONS, TEMPLATE_MANAGE_APPLICATIONS, model );
    }

    /**
     * Process the action of filtering applications; set the filter
     * 
     * @param request
     * @return The manage applications view
     */
    @Action( ACTION_FILTER_APPLICATION )
    public String doFilterApplication( HttpServletRequest request )
    {
        String strSearch = request.getParameter( PARAMETER_SEARCH );
        _filter = new ApplicationFilter( );
        if ( strSearch != null && !strSearch.equals( "-1" ) )
        {
            _filter.setSearch( strSearch );
            _filter.setHasSearch( true );
        }
        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * Returns the form to create a application
     *
     * @param request
     *            The Http request
     * @return the html code of the application form
     */
    @View( VIEW_CREATE_APPLICATION )
    public String getCreateApplication( HttpServletRequest request )
    {
        _application = ( _application != null ) ? _application : new Application( );

        Map<String, Object> model = getModel( );
        model.put( MARK_APPLICATION, _application );
        model.put( MARK_ORGANIZATIONS, OrganizationHome.getOrganizationsReferenceList( ) );
        model.put( MARK_ORGANIZATION_MANAGERS, OrganizationManagerHome.getOrganizationManagersList( ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_APPLICATION, TEMPLATE_CREATE_APPLICATION, model );
    }

    /**
     * Process the data capture form of a new application
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_APPLICATION )
    public String doCreateApplication( HttpServletRequest request )
    {
        populate( _application, request );

        String strIdOrganizationManager = request.getParameter( PARAMETER_ID_ORGANIZATION_MANAGER );
        int nIdOrganizationManager = Integer.parseInt( strIdOrganizationManager );
        OrganizationManager organizationManager = OrganizationManagerHome.findByPrimaryKey( nIdOrganizationManager );
        _application.setOrganizationManager( organizationManager );

        // Check constraints
        if ( !validateBean( _application, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_APPLICATION );
        }

        ApplicationHome.create( _application );
        addInfo( INFO_APPLICATION_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * Manages the removal form of a application whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_APPLICATION )
    public String getConfirmRemoveApplication( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATION ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_APPLICATION ) );
        url.addParameter( PARAMETER_ID_APPLICATION, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_APPLICATION, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a application
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage applications
     */
    @Action( ACTION_REMOVE_APPLICATION )
    public String doRemoveApplication( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATION ) );
        ApplicationService.remove( nId );
        addInfo( INFO_APPLICATION_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * Returns the form to update info about a application
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_APPLICATION )
    public String getModifyApplication( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATION ) );

        if ( _application == null || ( _application.getId( ) != nId ) )
        {
            _application = ApplicationHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_APPLICATION, _application );
        model.put( MARK_ORGANIZATIONS, OrganizationHome.getOrganizationsReferenceList( ) );
        model.put( MARK_ORGANIZATION_MANAGERS, OrganizationManagerHome.getOrganizationManagersList( ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_APPLICATION, TEMPLATE_MODIFY_APPLICATION, model );
    }

    /**
     * Process the change form of a application
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_APPLICATION )
    public String doModifyApplication( HttpServletRequest request )
    {
        populate( _application, request );

        String strIdOrganizationManager = request.getParameter( PARAMETER_ID_ORGANIZATION_MANAGER );
        int nIdOrganizationManager = Integer.parseInt( strIdOrganizationManager );
        OrganizationManager organizationManager = OrganizationManagerHome.findByPrimaryKey( nIdOrganizationManager );
        _application.setOrganizationManager( organizationManager );

        // Check constraints
        if ( !validateBean( _application, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_APPLICATION, PARAMETER_ID_APPLICATION, _application.getId( ) );
        }

        ApplicationHome.update( _application );
        ApplicationHome.updateData( _application.getId( ), _application.getApplicationData( ) );
        addInfo( INFO_APPLICATION_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_APPLICATIONS );
    }

    /**
     * Returns the task form associate to the workflow action
     *
     * @param request
     *            The Http request
     * @return The HTML form the task form associate to the workflow action
     */
    @View( VIEW_APPLICATION_DETAIL )
    public String getApplicationDetail( HttpServletRequest request )
    {
        Application application = null;
        Integer nIdApplication = request.getParameter( PARAMETER_ID_APPLICATION ) != null ? Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATION ) )
                : null;

        application = ApplicationHome.findByPrimaryKey( nIdApplication );

        if ( application == null )
        {
            return redirect( request, VIEW_MANAGE_APPLICATIONS );
        }

        String strJsonData = ApplicationService.getPrettyPrintApplicationData( application );

        Map<String, Object> model = getModel( );
        model.put( MARK_APPLICATION, application );
        model.put( MARK_JSON_DATA, strJsonData );

        return getPage( PROPERTY_PAGE_TITLE_APPLICATION_DETAIL, TEMPLATE_APPLICATION_DETAIL, model );
    }
}
