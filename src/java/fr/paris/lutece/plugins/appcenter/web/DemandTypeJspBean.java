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

import fr.paris.lutece.plugins.appcenter.business.CategoryDemandType;
import fr.paris.lutece.plugins.appcenter.business.CategoryDemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.DemandType;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.Documentation;
import fr.paris.lutece.plugins.appcenter.business.DocumentationCategory;
import fr.paris.lutece.plugins.appcenter.business.DocumentationHome;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage DemandType features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDemandTypes.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class DemandTypeJspBean extends ApplicationJspBean
{
    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DEMANDTYPES = "appcenter.manage_demandtypes.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_DEMANDTYPE = "appcenter.modify_demandtype.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_DEMANDTYPE = "appcenter.create_demandtype.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_CATEGORYDEMANDTYPE = "appcenter.modify_categorydemandtype.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_CATEGORYDEMANDTYPE = "appcenter.create_categorydemandtype.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DOCUMENTATIONS = "appcenter.manage_documentations.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_DOCUMENTATION = "appcenter.modify_documentation.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_DOCUMENTATION = "appcenter.create_documentation.pageTitle";

    // Markers
    private static final String MARK_DEMANDTYPE_LIST = "demandtype_list";
    private static final String MARK_DEMANDTYPE = "demandtype";
    private static final String MARK_MAP_DEMAND_TYPES = "mapDemandType";
    private static final String MARK_LIST_CATEGORY_DEMAND_TYPES = "categorydemandtype_list";
    private static final String MARK_CATEGORYDEMANDTYPE = "categorydemandtype";
    private static final String MARK_DOCUMENTATION_LIST = "documentation_list";
    private static final String MARK_DOCUMENTATION = "documentation";
    private static final String MARK_ID_DEMAND_TYPE = "id_demand_type";
    private static final String MARK_DOCUMENTATION_CATEGORIES = "documentation_categories";
    private static final String MARK_DEFAULT_DOCUMENTATION_CATEGORY = "default_documentation_category";

    // Jsp
    private static final String JSP_MANAGE_DEMANDTYPES = "jsp/admin/plugins/appcenter/ManageDemandTypes.jsp";
    private static final String JSP_MANAGE_DOCUMENTATIONS = "jsp/admin/plugins/appcenter/ManageDemandTypes.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_DEMANDTYPE = "appcenter.message.confirmRemoveDemandType";
    private static final String MESSAGE_CONFIRM_REMOVE_DOCUMENTATION = "appcenter.message.confirmRemoveDocumentation";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.demandtype.attribute.";

    // Views
    private static final String VIEW_MANAGE_DEMANDTYPES = "manageDemandTypes";
    private static final String VIEW_CREATE_DEMANDTYPE = "createDemandType";
    private static final String VIEW_MODIFY_DEMANDTYPE = "modifyDemandType";
    private static final String VIEW_MANAGE_CATEGORYDEMANDTYPES = "manageCategoryDemandTypes";
    private static final String VIEW_CREATE_CATEGORYDEMANDTYPE = "createCategoryDemandType";
    private static final String VIEW_MODIFY_CATEGORYDEMANDTYPE = "modifyCategoryDemandType";
    private static final String VIEW_MANAGE_DOCUMENTATIONS = "manageDocumentations";
    private static final String VIEW_CREATE_DOCUMENTATION = "createDocumentation";
    private static final String VIEW_MODIFY_DOCUMENTATION = "modifyDocumentation";

    // Actions
    private static final String ACTION_CREATE_DEMANDTYPE = "createDemandType";
    private static final String ACTION_MODIFY_DEMANDTYPE = "modifyDemandType";
    private static final String ACTION_REMOVE_DEMANDTYPE = "removeDemandType";
    private static final String ACTION_CONFIRM_REMOVE_DEMANDTYPE = "confirmRemoveDemandType";
    private static final String ACTION_MOVE_DEMAND_TYPE_UP = "doMoveDemandTypeUp";
    private static final String ACTION_MOVE_DEMAND_TYPE_DOWN = "doMoveDemandTypeDown";
    private static final String ACTION_CREATE_CATEGORYDEMANDTYPE = "createCategoryDemandType";
    private static final String ACTION_MODIFY_CATEGORYDEMANDTYPE = "modifyCategoryDemandType";
    private static final String ACTION_REMOVE_CATEGORYDEMANDTYPE = "removeCategoryDemandType";
    private static final String ACTION_CONFIRM_REMOVE_CATEGORYDEMANDTYPE = "confirmRemoveCategoryDemandType";
    private static final String ACTION_MOVE_CATEGORY_DEMAND_TYPE_UP = "doMoveCategoryDemandTypeUp";
    private static final String ACTION_MOVE_CATEGORY_DEMAND_TYPE_DOWN = "doMoveCategoryDemandTypeDown";
    private static final String ACTION_CREATE_DOCUMENTATION = "createDocumentation";
    private static final String ACTION_MODIFY_DOCUMENTATION = "modifyDocumentation";
    private static final String ACTION_REMOVE_DOCUMENTATION = "removeDocumentation";
    private static final String ACTION_CONFIRM_REMOVE_DOCUMENTATION = "confirmRemoveDocumentation";

    // Infos
    private static final String INFO_DEMANDTYPE_CREATED = "appcenter.info.demandtype.created";
    private static final String INFO_DEMANDTYPE_UPDATED = "appcenter.info.demandtype.updated";
    private static final String INFO_DEMANDTYPE_REMOVED = "appcenter.info.demandtype.removed";
    private static final String INFO_CATEGORYDEMANDTYPE_CREATED = "appcenter.info.categorydemandtype.created";
    private static final String INFO_CATEGORYDEMANDTYPE_UPDATED = "appcenter.info.categorydemandtype.updated";
    private static final String INFO_CATEGORYDEMANDTYPE_REMOVED = "appcenter.info.categorydemandtype.removed";
    private static final String INFO_DOCUMENTATION_CREATED = "appcenter.info.documentation.created";
    private static final String INFO_DOCUMENTATION_UPDATED = "appcenter.info.documentation.updated";
    private static final String INFO_DOCUMENTATION_REMOVED = "appcenter.info.documentation.removed";

    // Templates
    private static final String TEMPLATE_CREATE_CATEGORYDEMANDTYPE = "/admin/plugins/appcenter/create_categorydemandtype.html";
    private static final String TEMPLATE_MODIFY_CATEGORYDEMANDTYPE = "/admin/plugins/appcenter/modify_categorydemandtype.html";
    private static final String TEMPLATE_MANAGE_DEMANDTYPES = "/admin/plugins/appcenter/manage_demandtypes.html";
    private static final String TEMPLATE_CREATE_DEMANDTYPE = "/admin/plugins/appcenter/create_demandtype.html";
    private static final String TEMPLATE_MODIFY_DEMANDTYPE = "/admin/plugins/appcenter/modify_demandtype.html";
    private static final String TEMPLATE_MANAGE_DOCUMENTATIONS = "/admin/plugins/appcenter/manage_documentations.html";
    private static final String TEMPLATE_CREATE_DOCUMENTATION = "/admin/plugins/appcenter/create_documentation.html";
    private static final String TEMPLATE_MODIFY_DOCUMENTATION = "/admin/plugins/appcenter/modify_documentation.html";

    // Parameters
    private static final String PARAMETER_ID_CATEGORYDEMANDTYPE = "id";
    private static final String PARAMETER_DEPENDING_OF_ENVIRONMENT = "is_depending_of_environment";
    private static final String PARAMETER_ID_DEMANDTYPE = "id";
    private static final String PARAMETER_ID_DOCUMENTATION = "id";
    private static final String PARAMETER_ID_DEMAND_TYPE = "id_demand_type";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_CATEGORYDEMANDTYPE = "appcenter.message.confirmRemoveCategoryDemandType";

    // Session variable to store working values
    private CategoryDemandType _categorydemandtype;
    private DemandType _demandtype;
    private Documentation _documentation;
    private DemandType _docDemandType;

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DEMANDTYPES, defaultView = true )
    public String getManageDemandTypes( HttpServletRequest request )
    {
        _demandtype = null;
        List<DemandType> listDemandTypes = DemandTypeHome.getDemandTypesList( );
        List<CategoryDemandType> listCategoryDemandTypes = CategoryDemandTypeHome.getCategoryDemandTypesList( );
        Map<String, List<DemandType>> mapDemandTypes = new HashMap<>( );

        for ( CategoryDemandType cat : listCategoryDemandTypes )
        {
            List<DemandType> listDemandType = listDemandTypes.stream( ).filter( demandType -> demandType.getIdCategoryDemandType( ) == cat.getId( ) )
                    .collect( Collectors.toList( ) );
            mapDemandTypes.put( Integer.toString( cat.getId( ) ), listDemandType );
        }

        Map<String, Object> model = getPaginatedListModel( request, MARK_DEMANDTYPE_LIST, listDemandTypes, JSP_MANAGE_DEMANDTYPES );

        model.put( MARK_MAP_DEMAND_TYPES, mapDemandTypes );
        model.put( MARK_LIST_CATEGORY_DEMAND_TYPES, listCategoryDemandTypes );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DEMANDTYPES, TEMPLATE_MANAGE_DEMANDTYPES, model );
    }

    /**
     * Returns the form to create a demandtype
     *
     * @param request
     *            The Http request
     * @return the html code of the demandtype form
     */
    @View( VIEW_CREATE_DEMANDTYPE )
    public String getCreateDemandType( HttpServletRequest request )
    {
        _demandtype = ( _demandtype != null ) ? _demandtype : new DemandType( );

        String strCategoryDemandType = request.getParameter( "id" );

        Map<String, Object> model = getModel( );
        if ( strCategoryDemandType != null )
        {
            model.put( "id_category_demand_type", Integer.parseInt( strCategoryDemandType ) );
        }
        model.put( MARK_DEMANDTYPE, _demandtype );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_DEMANDTYPE, TEMPLATE_CREATE_DEMANDTYPE, model );
    }

    /**
     * Process the data capture form of a new demandtype
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_DEMANDTYPE )
    public String doCreateDemandType( HttpServletRequest request )
    {
        populate( _demandtype, request );

        // Check constraints
        if ( !validateBean( _demandtype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_DEMANDTYPE );
        }

        DemandTypeHome.create( _demandtype );
        addInfo( INFO_DEMANDTYPE_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_DEMANDTYPES );
    }

    /**
     * Manages the removal form of a demandtype whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_DEMANDTYPE )
    public String getConfirmRemoveDemandType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMANDTYPE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_DEMANDTYPE ) );
        url.addParameter( PARAMETER_ID_DEMANDTYPE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_DEMANDTYPE, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a demandtype
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage demandtypes
     */
    @Action( ACTION_REMOVE_DEMANDTYPE )
    public String doRemoveDemandType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMANDTYPE ) );
        DemandTypeHome.remove( nId );
        addInfo( INFO_DEMANDTYPE_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_DEMANDTYPES );
    }

    /**
     * Returns the form to update info about a demandtype
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_DEMANDTYPE )
    public String getModifyDemandType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMANDTYPE ) );

        if ( _demandtype == null || ( _demandtype.getId( ) != nId ) )
        {
            _demandtype = DemandTypeHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_DEMANDTYPE, _demandtype );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_DEMANDTYPE, TEMPLATE_MODIFY_DEMANDTYPE, model );
    }

    /**
     * Process the change form of a demandtype
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_DEMANDTYPE )
    public String doModifyDemandType( HttpServletRequest request )
    {
        populate( _demandtype, request );

        // Check constraints
        if ( !validateBean( _demandtype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_DEMANDTYPE, PARAMETER_ID_DEMANDTYPE, _demandtype.getId( ) );
        }

        DemandTypeHome.update( _demandtype );
        addInfo( INFO_DEMANDTYPE_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_DEMANDTYPES );
    }

    @Action( ACTION_MOVE_DEMAND_TYPE_UP )
    public String doMoveDemandUp( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMANDTYPE ) );
        int nIdCategoryDemandType = Integer.parseInt( request.getParameter( "id_category_demand_type" ) );

        if ( _demandtype == null || ( _demandtype.getId( ) != nId ) )
        {
            _demandtype = DemandTypeHome.findByPrimaryKey( nId );
        }

        // Get the category demand type list
        List<DemandType> listDemandTypes = DemandTypeHome.getDemandTypesListByIdCategory( nIdCategoryDemandType );

        // Find the _categorydemandtype and the before element
        ListIterator<DemandType> it = listDemandTypes.listIterator( );
        boolean bContinue = true;
        while ( it.hasNext( ) && bContinue )
        {
            DemandType demandType = it.next( );

            if ( demandType.getId( ) == _demandtype.getId( ) )
            {
                int nIndexPrevious = it.previousIndex( ) - 1;
                DemandType beforeCategory = listDemandTypes.get( nIndexPrevious );
                int currentOrder = _demandtype.getOrder( );
                _demandtype.setOrder( beforeCategory.getOrder( ) );
                beforeCategory.setOrder( currentOrder );
                DemandTypeHome.update( _demandtype );
                DemandTypeHome.update( beforeCategory );
                bContinue = false;
            }
        }

        return redirectView( request, VIEW_MANAGE_CATEGORYDEMANDTYPES );
    }

    @Action( ACTION_MOVE_DEMAND_TYPE_DOWN )
    public String doMoveDemandDown( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMANDTYPE ) );
        int nIdCategoryDemandType = Integer.parseInt( request.getParameter( "id_category_demand_type" ) );

        if ( _demandtype == null || ( _demandtype.getId( ) != nId ) )
        {
            _demandtype = DemandTypeHome.findByPrimaryKey( nId );
        }

        // Get the category demand type list
        List<DemandType> listDemandTypes = DemandTypeHome.getDemandTypesListByIdCategory( nIdCategoryDemandType );

        Collections.reverse( listDemandTypes );

        // Find the _categorydemandtype and the before element
        ListIterator<DemandType> it = listDemandTypes.listIterator( );
        boolean bContinue = true;
        while ( it.hasNext( ) && bContinue )
        {
            DemandType demandType = it.next( );

            if ( demandType.getId( ) == _demandtype.getId( ) )
            {
                int nIndexPrevious = it.previousIndex( ) - 1;
                DemandType beforeCategory = listDemandTypes.get( nIndexPrevious );
                int currentOrder = _demandtype.getOrder( );
                _demandtype.setOrder( beforeCategory.getOrder( ) );
                beforeCategory.setOrder( currentOrder );
                DemandTypeHome.update( _demandtype );
                DemandTypeHome.update( beforeCategory );
                bContinue = false;
            }
        }

        return redirectView( request, VIEW_MANAGE_DEMANDTYPES );
    }

    // /////////////////////////////////////
    // /////////////////////////////////////
    // ///Category Demand Type
    // ////////////////////////////////////
    // ////////////////////////////////////

    /**
     * Returns the form to create a categorydemandtype
     *
     * @param request
     *            The Http request
     * @return the html code of the categorydemandtype form
     */
    @View( VIEW_CREATE_CATEGORYDEMANDTYPE )
    public String getCreateCategoryDemandType( HttpServletRequest request )
    {
        _categorydemandtype = ( _categorydemandtype != null ) ? _categorydemandtype : new CategoryDemandType( );

        Map<String, Object> model = getModel( );
        model.put( MARK_CATEGORYDEMANDTYPE, _categorydemandtype );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_CATEGORYDEMANDTYPE, TEMPLATE_CREATE_CATEGORYDEMANDTYPE, model );
    }

    /**
     * Process the data capture form of a new categorydemandtype
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_CATEGORYDEMANDTYPE )
    public String doCreateCategoryDemandType( HttpServletRequest request )
    {
        populate( _categorydemandtype, request );
        String strIsDependingOfEnvironment = request.getParameter( PARAMETER_DEPENDING_OF_ENVIRONMENT );
        if ( strIsDependingOfEnvironment != null )
        {
            _categorydemandtype.setIsDependingOfEnvironment( true );
        }
        else
        {
            _categorydemandtype.setIsDependingOfEnvironment( false );
        }
        // Check constraints
        if ( !validateBean( _categorydemandtype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_CATEGORYDEMANDTYPE );
        }

        CategoryDemandTypeHome.create( _categorydemandtype );
        addInfo( INFO_CATEGORYDEMANDTYPE_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_CATEGORYDEMANDTYPES );
    }

    /**
     * Manages the removal form of a categorydemandtype whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_CATEGORYDEMANDTYPE )
    public String getConfirmRemoveCategoryDemandType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CATEGORYDEMANDTYPE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_CATEGORYDEMANDTYPE ) );
        url.addParameter( PARAMETER_ID_CATEGORYDEMANDTYPE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_CATEGORYDEMANDTYPE, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a categorydemandtype
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage categorydemandtypes
     */
    @Action( ACTION_REMOVE_CATEGORYDEMANDTYPE )
    public String doRemoveCategoryDemandType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CATEGORYDEMANDTYPE ) );
        CategoryDemandTypeHome.remove( nId );
        addInfo( INFO_CATEGORYDEMANDTYPE_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_CATEGORYDEMANDTYPES );
    }

    /**
     * Returns the form to update info about a categorydemandtype
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_CATEGORYDEMANDTYPE )
    public String getModifyCategoryDemandType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CATEGORYDEMANDTYPE ) );
        _categorydemandtype = CategoryDemandTypeHome.findByPrimaryKey( nId );

        Map<String, Object> model = getModel( );
        model.put( MARK_CATEGORYDEMANDTYPE, _categorydemandtype );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_CATEGORYDEMANDTYPE, TEMPLATE_MODIFY_CATEGORYDEMANDTYPE, model );
    }

    /**
     * Process the change form of a categorydemandtype
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_CATEGORYDEMANDTYPE )
    public String doModifyCategoryDemandType( HttpServletRequest request )
    {
        populate( _categorydemandtype, request );

        // Check constraints
        if ( !validateBean( _categorydemandtype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_CATEGORYDEMANDTYPE, PARAMETER_ID_CATEGORYDEMANDTYPE, _categorydemandtype.getId( ) );
        }

        CategoryDemandTypeHome.update( _categorydemandtype );
        addInfo( INFO_CATEGORYDEMANDTYPE_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_CATEGORYDEMANDTYPES );
    }

    @Action( ACTION_MOVE_CATEGORY_DEMAND_TYPE_UP )
    public String doMoveCategoryDemandUp( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CATEGORYDEMANDTYPE ) );

        if ( _categorydemandtype == null || ( _categorydemandtype.getId( ) != nId ) )
        {
            _categorydemandtype = CategoryDemandTypeHome.findByPrimaryKey( nId );
        }

        // Get the category demand type list
        List<CategoryDemandType> listCategories = CategoryDemandTypeHome.getCategoryDemandTypesList( );

        // Find the _categorydemandtype and the before element
        ListIterator<CategoryDemandType> it = listCategories.listIterator( );
        boolean bContinue = true;
        while ( it.hasNext( ) && bContinue )
        {
            CategoryDemandType category = it.next( );

            if ( category.getId( ) == _categorydemandtype.getId( ) )
            {
                int nIndexPrevious = it.previousIndex( ) - 1;
                CategoryDemandType beforeCategory = listCategories.get( nIndexPrevious );
                int currentOrder = _categorydemandtype.getOrder( );
                _categorydemandtype.setOrder( beforeCategory.getOrder( ) );
                beforeCategory.setOrder( currentOrder );
                CategoryDemandTypeHome.update( _categorydemandtype );
                CategoryDemandTypeHome.update( beforeCategory );
                bContinue = false;
            }
        }

        return redirectView( request, VIEW_MANAGE_DEMANDTYPES );
    }

    @Action( ACTION_MOVE_CATEGORY_DEMAND_TYPE_DOWN )
    public String doMoveCategoryDemandDown( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_CATEGORYDEMANDTYPE ) );

        if ( _categorydemandtype == null || ( _categorydemandtype.getId( ) != nId ) )
        {
            _categorydemandtype = CategoryDemandTypeHome.findByPrimaryKey( nId );
        }

        // Get the category demand type list
        List<CategoryDemandType> listCategories = CategoryDemandTypeHome.getCategoryDemandTypesList( );

        Collections.reverse( listCategories );

        // Find the _categorydemandtype and the before element
        ListIterator<CategoryDemandType> it = listCategories.listIterator( );
        boolean bContinue = true;
        while ( it.hasNext( ) && bContinue )
        {
            CategoryDemandType category = it.next( );

            if ( category.getId( ) == _categorydemandtype.getId( ) )
            {
                int nIndexPrevious = it.previousIndex( ) - 1;
                CategoryDemandType beforeCategory = listCategories.get( nIndexPrevious );
                int currentOrder = _categorydemandtype.getOrder( );
                _categorydemandtype.setOrder( beforeCategory.getOrder( ) );
                beforeCategory.setOrder( currentOrder );
                CategoryDemandTypeHome.update( _categorydemandtype );
                CategoryDemandTypeHome.update( beforeCategory );
                bContinue = false;
            }
        }

        return redirectView( request, VIEW_MANAGE_DEMANDTYPES );
    }

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DOCUMENTATIONS, defaultView = true )
    public String getManageDocumentations( HttpServletRequest request )
    {
        _documentation = null;
        String strIdDemandType = request.getParameter( PARAMETER_ID_DEMAND_TYPE );
        if ( strIdDemandType != null )
        {
            try
            {
                int nIdDemandType = Integer.parseInt( strIdDemandType );
                _docDemandType = DemandTypeHome.findByPrimaryKey( nIdDemandType );

            }
            catch( NumberFormatException e )
            {
                AppLogService.error( "Unable to parse given id demand type to int", e );
                return redirectView( request, VIEW_MANAGE_DEMANDTYPES );
            }
        }
        if ( _docDemandType != null )
        {
            List<Documentation> listDocumentations = DocumentationHome.getDocumentationsListByIdDemandType( _docDemandType.getId( ) );
            Map<String, Object> model = getPaginatedListModel( request, MARK_DOCUMENTATION_LIST, listDocumentations, JSP_MANAGE_DOCUMENTATIONS );
            model.put( MARK_ID_DEMAND_TYPE, strIdDemandType );
            model.put( MARK_DOCUMENTATION_CATEGORIES,
                    Arrays.asList( DocumentationCategory.values( ) ).stream( ).collect( Collectors.toMap( DocumentationCategory::getPrefix, docCat -> docCat ) ) );

            return getPage( PROPERTY_PAGE_TITLE_MANAGE_DOCUMENTATIONS, TEMPLATE_MANAGE_DOCUMENTATIONS, model );
        }
        else
        {
            return redirectView( request, VIEW_MANAGE_DEMANDTYPES );
        }
    }

    /**
     * Returns the form to create a documentation
     *
     * @param request
     *            The Http request
     * @return the html code of the documentation form
     */
    @View( VIEW_CREATE_DOCUMENTATION )
    public String getCreateDocumentation( HttpServletRequest request )
    {
        _documentation = ( _documentation != null ) ? _documentation : new Documentation( );
        _documentation.setIdDemandType( _docDemandType.getId( ) );
        Map<String, Object> model = getModel( );
        ReferenceList listDocCategories = ReferenceList.convert( Arrays.asList( DocumentationCategory.values( ) ), "prefix", "labelKey", false );
        for ( ReferenceItem item : listDocCategories )
        {
            item.setName( I18nService.getLocalizedString( item.getName( ), request.getLocale( ) ) );
        }
        model.put( MARK_DOCUMENTATION_CATEGORIES, listDocCategories );
        model.put( MARK_DOCUMENTATION, _documentation );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_DOCUMENTATION, TEMPLATE_CREATE_DOCUMENTATION, model );
    }

    /**
     * Process the data capture form of a new documentation
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_DOCUMENTATION )
    public String doCreateDocumentation( HttpServletRequest request )
    {
        populate( _documentation, request );
        // Check constraints
        if ( !validateBean( _documentation, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_DOCUMENTATION );
        }

        DocumentationHome.create( _documentation );
        addInfo( INFO_DOCUMENTATION_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_DOCUMENTATIONS );
    }

    /**
     * Manages the removal form of a documentation whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_DOCUMENTATION )
    public String getConfirmRemoveDocumentation( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DOCUMENTATION ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_DOCUMENTATION ) );
        url.addParameter( PARAMETER_ID_DOCUMENTATION, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_DOCUMENTATION, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a documentation
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage documentations
     */
    @Action( ACTION_REMOVE_DOCUMENTATION )
    public String doRemoveDocumentation( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DOCUMENTATION ) );
        DocumentationHome.remove( nId );
        addInfo( INFO_DOCUMENTATION_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_DOCUMENTATIONS );
    }

    /**
     * Returns the form to update info about a documentation
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_DOCUMENTATION )
    public String getModifyDocumentation( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DOCUMENTATION ) );

        if ( _documentation == null || ( _documentation.getId( ) != nId ) )
        {
            _documentation = DocumentationHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        ReferenceList listDocCategories = ReferenceList.convert( Arrays.asList( DocumentationCategory.values( ) ), "prefix", "labelKey", false );
        for ( ReferenceItem item : listDocCategories )
        {
            item.setName( I18nService.getLocalizedString( item.getName( ), request.getLocale( ) ) );
        }
        model.put( MARK_DEFAULT_DOCUMENTATION_CATEGORY, DocumentationCategory.getDocumentationCategory( _documentation.getCategory( ) ) );
        model.put( MARK_DOCUMENTATION_CATEGORIES, listDocCategories );
        model.put( MARK_DOCUMENTATION, _documentation );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_DOCUMENTATION, TEMPLATE_MODIFY_DOCUMENTATION, model );
    }

    /**
     * Process the change form of a documentation
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_DOCUMENTATION )
    public String doModifyDocumentation( HttpServletRequest request )
    {
        populate( _documentation, request );

        // Check constraints
        if ( !validateBean( _documentation, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_DOCUMENTATION, PARAMETER_ID_DOCUMENTATION, _documentation.getId( ) );
        }

        DocumentationHome.update( _documentation );
        addInfo( INFO_DOCUMENTATION_UPDATED, getLocale( ) );
        return redirectView( request, VIEW_MANAGE_DOCUMENTATIONS );
    }
}
