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
 	
package fr.paris.lutece.plugins.appcenter.modules.guichetpro.web;

import fr.paris.lutece.plugins.appcenter.web.ManageAppCenterJspBean;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.DemandTypeCategory;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.DemandTypeCategoryHome;
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
 * This class provides the user interface to manage DemandTypeCategory features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDemandTypeCategories.jsp", controllerPath = "jsp/admin/plugins/appcenter/modules/guichetpro/", right = "APPCENTER_MANAGEMENT" )
public class DemandTypeCategoryJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_DEMAND_TYPE_CATEGORIES = "/admin/plugins/appcenter/modules/guichetpro/manage_demandtypecategories.html";
    private static final String TEMPLATE_CREATE_DEMAND_TYPE_CATEGORY = "/admin/plugins/appcenter/modules/guichetpro/create_demandtypecategory.html";
    private static final String TEMPLATE_MODIFY_DEMAND_TYPE_CATEGORY = "/admin/plugins/appcenter/modules/guichetpro/modify_demandtypecategory.html";

    // Parameters
    private static final String PARAMETER_ID_DEMAND_TYPE_CATEGORY = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DEMAND_TYPE_CATEGORIES = "module.appcenter.guichetpro.manage_demandtypecategories.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_DEMAND_TYPE_CATEGORY = "module.appcenter.guichetpro.modify_demandtypecategory.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_DEMAND_TYPE_CATEGORY = "module.appcenter.guichetpro.create_demandtypecategory.pageTitle";

    // Markers
    private static final String MARK_DEMAND_TYPE_CATEGORY_LIST = "demandTypeCategory_list";
    private static final String MARK_DEMAND_TYPE_CATEGORY = "demandTypeCategory";

    private static final String JSP_MANAGE_DEMAND_TYPE_CATEGORIES = "jsp/admin/plugins/appcenter/ManageDemandTypeCategories.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_DEMAND_TYPE_CATEGORY = "module.appcenter.guichetpro.message.confirmRemoveDemandTypeCategory";

    // Validations
    private static final String VALIDATION_DEMAND_TYPE_CATEGORIES_PREFIX = "module.appcenter.guichetpro.model.entity.demandtypecategory.attribute.";

    // Views
    private static final String VIEW_MANAGE_DEMAND_TYPE_CATEGORIES = "manageDemandTypeCategorys";
    private static final String VIEW_CREATE_DEMAND_TYPE_CATEGORY = "createDemandTypeCategory";
    private static final String VIEW_MODIFY_DEMAND_TYPE_CATEGORY = "modifyDemandTypeCategory";

    // Actions
    private static final String ACTION_CREATE_DEMAND_TYPE_CATEGORY = "createDemandTypeCategory";
    private static final String ACTION_MODIFY_DEMAND_TYPE_CATEGORY = "modifyDemandTypeCategory";
    private static final String ACTION_REMOVE_DEMAND_TYPE_CATEGORY = "removeDemandTypeCategory";
    private static final String ACTION_CONFIRM_REMOVE_DEMAND_TYPE_CATEGORY = "confirmRemoveDemandTypeCategory";

    // Infos
    private static final String INFO_DEMAND_TYPE_CATEGORY_CREATED = "module.appcenter.guichetpro.info.demandtypecategory.created";
    private static final String INFO_DEMAND_TYPE_CATEGORY_UPDATED = "module.appcenter.guichetpro.info.demandtypecategory.updated";
    private static final String INFO_DEMAND_TYPE_CATEGORY_REMOVED = "module.appcenter.guichetpro.info.demandtypecategory.removed";
    
    // Session variable to store working values
    private DemandTypeCategory _demandTypeCategory;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DEMAND_TYPE_CATEGORIES, defaultView = true )
    public String getManageDemandTypeCategories( HttpServletRequest request )
    {
        _demandTypeCategory = null;
        List<DemandTypeCategory> listDemandTypeCategories = DemandTypeCategoryHome.getDemandTypeCategoriesList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_DEMAND_TYPE_CATEGORY_LIST, listDemandTypeCategories, JSP_MANAGE_DEMAND_TYPE_CATEGORIES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DEMAND_TYPE_CATEGORIES, TEMPLATE_MANAGE_DEMAND_TYPE_CATEGORIES, model );
    }

    /**
     * Returns the form to create a demandTypeCategory
     *
     * @param request The Http request
     * @return the html code of the demandTypeCategory form
     */
    @View( VIEW_CREATE_DEMAND_TYPE_CATEGORY )
    public String getCreateDemandTypeCategory( HttpServletRequest request )
    {
        _demandTypeCategory = ( _demandTypeCategory != null ) ? _demandTypeCategory : new DemandTypeCategory(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_DEMAND_TYPE_CATEGORY, _demandTypeCategory );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_DEMAND_TYPE_CATEGORY, TEMPLATE_CREATE_DEMAND_TYPE_CATEGORY, model );
    }

    /**
     * Process the data capture form of a new demandTypeCategory
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_DEMAND_TYPE_CATEGORY )
    public String doCreateDemandTypeCategory( HttpServletRequest request )
    {
        populate( _demandTypeCategory, request );

        // Check constraints
        if ( !validateBean( _demandTypeCategory, VALIDATION_DEMAND_TYPE_CATEGORIES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_DEMAND_TYPE_CATEGORY );
        }

        DemandTypeCategoryHome.create( _demandTypeCategory );
        addInfo( INFO_DEMAND_TYPE_CATEGORY_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_DEMAND_TYPE_CATEGORIES );
    }

    /**
     * Manages the removal form of a demandTypeCategory whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_DEMAND_TYPE_CATEGORY )
    public String getConfirmRemoveDemandTypeCategory( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND_TYPE_CATEGORY ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_DEMAND_TYPE_CATEGORY ) );
        url.addParameter( PARAMETER_ID_DEMAND_TYPE_CATEGORY, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_DEMAND_TYPE_CATEGORY, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a demandTypeCategory
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage demandtypecategories
     */
    @Action( ACTION_REMOVE_DEMAND_TYPE_CATEGORY )
    public String doRemoveDemandTypeCategory( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND_TYPE_CATEGORY ) );
        DemandTypeCategoryHome.remove( nId );
        addInfo( INFO_DEMAND_TYPE_CATEGORY_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_DEMAND_TYPE_CATEGORIES );
    }

    /**
     * Returns the form to update info about a demandTypeCategory
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_DEMAND_TYPE_CATEGORY )
    public String getModifyDemandTypeCategory( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND_TYPE_CATEGORY ) );

        if ( _demandTypeCategory == null || ( _demandTypeCategory.getId(  ) != nId ))
        {
            _demandTypeCategory = DemandTypeCategoryHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_DEMAND_TYPE_CATEGORY, _demandTypeCategory );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_DEMAND_TYPE_CATEGORY, TEMPLATE_MODIFY_DEMAND_TYPE_CATEGORY, model );
    }

    /**
     * Process the change form of a demandTypeCategory
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_DEMAND_TYPE_CATEGORY )
    public String doModifyDemandTypeCategory( HttpServletRequest request )
    {
        populate( _demandTypeCategory, request );

        // Check constraints
        if ( !validateBean( _demandTypeCategory, VALIDATION_DEMAND_TYPE_CATEGORIES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_DEMAND_TYPE_CATEGORY, PARAMETER_ID_DEMAND_TYPE_CATEGORY, _demandTypeCategory.getId( ) );
        }

        DemandTypeCategoryHome.update( _demandTypeCategory );
        addInfo( INFO_DEMAND_TYPE_CATEGORY_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_DEMAND_TYPE_CATEGORIES );
    }
}
