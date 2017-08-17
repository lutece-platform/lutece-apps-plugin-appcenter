/*
 * Copyright (c) 2002-2016, Mairie de Paris
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
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage Demand features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDemands.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class DemandJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_DEMANDS = "/admin/plugins/appcenter/manage_demands.html";
    private static final String TEMPLATE_MODIFY_DEMAND = "/admin/plugins/appcenter/modify_demand.html";

    // Parameters
    private static final String PARAMETER_ID_DEMAND = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DEMANDS = "appcenter.manage_demands.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_DEMAND = "appcenter.modify_demand.pageTitle";

    // Markers
    private static final String MARK_DEMAND_LIST = "demand_list";
    private static final String MARK_DEMAND = "demand";
    private static final String MARK_APPLICATION_MAP = "applications";
    private static final String MARK_STATES_MAP = "states";
    

    private static final String JSP_MANAGE_DEMANDS = "jsp/admin/plugins/appcenter/ManageDemands.jsp";

    // Properties

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "appcenter.model.entity.demand.attribute.";

    // Views
    private static final String VIEW_MANAGE_DEMANDS = "manageDemands";
    private static final String VIEW_MODIFY_DEMAND = "modifyDemand";

    // Actions
    private static final String ACTION_MODIFY_DEMAND = "modifyDemand";

    // Infos
    private static final String INFO_DEMAND_UPDATED = "appcenter.info.demand.updated";
    
    // Session variable to store working values
    private Demand _demand;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DEMANDS, defaultView = true )
    public String getManageDemands( HttpServletRequest request )
    {
        _demand = null;
        List<Demand> listDemands = DemandHome.getDemandsList(  );
        
        Map<String,Application> mapApplications = new HashMap<>();
        Map<String,State> mapStates = new HashMap<>();
        
        for ( Application app : ApplicationHome.getApplicationsList() )
        {
            mapApplications.put( Integer.toString( app.getId() ), app);
        }
        
        for ( Demand demand : DemandHome.getDemandsList() )
        {
            State state = WorkflowService.getInstance( ).getState( demand.getId( ), Demand.WORKFLOW_RESOURCE_TYPE, DemandTypeService.getIdWorkflow( demand.getDemandType() ), -1 );
            mapStates.put( Integer.toString( demand.getId() ), state );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_DEMAND_LIST, listDemands, JSP_MANAGE_DEMANDS );
        model.put( MARK_APPLICATION_MAP, mapApplications );
        model.put( MARK_STATES_MAP, mapStates );
        
        
        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DEMANDS, TEMPLATE_MANAGE_DEMANDS, model );
    }

    /**
     * Returns the form to update info about a demand
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_DEMAND )
    public String getModifyDemand( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND ) );

        if ( _demand == null || ( _demand.getId(  ) != nId ))
        {
            _demand = DemandHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_DEMAND, _demand );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_DEMAND, TEMPLATE_MODIFY_DEMAND, model );
    }

    /**
     * Process the change form of a demand
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_DEMAND )
    public String doModifyDemand( HttpServletRequest request )
    {
        populate( _demand, request );

        // Check constraints
        if ( !validateBean( _demand, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_DEMAND, PARAMETER_ID_DEMAND, _demand.getId( ) );
        }

        DemandHome.update( _demand );
        addInfo( INFO_DEMAND_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_DEMANDS );
    }
}
