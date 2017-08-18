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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;

/**
 * This class provides the user interface to manage Demand features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDemands.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class DemandJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_DEMANDS = "/admin/plugins/appcenter/manage_demands.html";
    private static final String TEMPLATE_TASK_FORM = "/admin/plugins/appcenter/task_form.html";

    // Parameters
    private static final String PARAMETER_ID_DEMAND = "id";
    private static final String PARAMETER_ID_ACTION = "id_action";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DEMANDS = "appcenter.manage_demands.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_TASK_FORM = "appcenter.task_form.pageTitle";

    // Markers
    private static final String MARK_DEMAND_LIST = "demand_list";
    private static final String MARK_DEMAND = "demand";
    private static final String MARK_ID_ACTION = "id_action";
    private static final String MARK_APPLICATION = "application";

    private static final String MARK_TASK_FORM = "task_form";

    private static final String MARK_APPLICATION_MAP = "applications";
    private static final String MARK_STATES_MAP = "states";
    private static final String MARK_ACTIONS = "actions";

    private static final String JSP_MANAGE_DEMANDS = "jsp/admin/plugins/appcenter/ManageDemands.jsp";

    // Properties

    // Views
    private static final String VIEW_MANAGE_DEMANDS = "manageDemands";
    private static final String VIEW_TASK_FORM = "taskForm";

    // Actions
    private static final String ACTION_PROCESS_ACTION = "processAction";
    private static final String ACTION_SAVE_TASK_FORM = "saveTaskForm";

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DEMANDS, defaultView = true )
    public String getManageDemands( HttpServletRequest request )
    {

        List<Demand> listDemands = DemandHome.getDemandsList( );
        int nIdWorkflow;
        Map<String, Application> mapApplications = new HashMap<>( );
        Map<String, State> mapStates = new HashMap<>( );
        Map<String, Collection<fr.paris.lutece.plugins.workflowcore.business.action.Action>> mapActions = new HashMap<>( );

        for ( Application app : ApplicationHome.getApplicationsList( ) )
        {
            mapApplications.put( Integer.toString( app.getId( ) ), app );
        }

        for ( Demand demand : DemandHome.getDemandsList( ) )
        {
            nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getDemandType( ) );
            State state = WorkflowService.getInstance( ).getState( demand.getId( ), Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow, -1 );
            mapStates.put( Integer.toString( demand.getId( ) ), state );
            Collection<fr.paris.lutece.plugins.workflowcore.business.action.Action> listActions = WorkflowService.getInstance( ).getActions( demand.getId( ),
                    Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow, getUser( ) );
            mapActions.put( Integer.toString( demand.getId( ) ), listActions );
        }

        Map<String, Object> model = getPaginatedListModel( request, MARK_DEMAND_LIST, listDemands, JSP_MANAGE_DEMANDS );
        model.put( MARK_APPLICATION_MAP, mapApplications );
        model.put( MARK_STATES_MAP, mapStates );
        model.put( MARK_ACTIONS, mapActions );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DEMANDS, TEMPLATE_MANAGE_DEMANDS, model );
    }

    /**
     * Returns the task form associate to the workflow action
     *
     * @param request
     *            The Http request
     * @return The HTML form the task form associate to the workflow action
     */
    @View( VIEW_TASK_FORM )
    public String getTaskForm( HttpServletRequest request )
    {
        // Demand
        Demand demand = null;
        Integer nIdDemand = request.getParameter( PARAMETER_ID_DEMAND ) != null ? Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND ) ) : null;
        Integer nIdAction = request.getParameter( PARAMETER_ID_ACTION ) != null ? Integer.parseInt( request.getParameter( PARAMETER_ID_ACTION ) ) : null;

        if ( nIdAction == null || nIdDemand == null )
        {
            return redirectView( request, VIEW_MANAGE_DEMANDS );

        }

        demand = DemandHome.findByPrimaryKey( nIdDemand );

        // Aplication
        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        String strHtmlTasksForm = WorkflowService.getInstance( )
                .getDisplayTasksForm( nIdDemand, Demand.WORKFLOW_RESOURCE_TYPE, nIdAction, request, getLocale( ) );

        Map<String, Object> model = getModel( );
        model.put( MARK_DEMAND, demand );
        model.put( MARK_APPLICATION, application );
        model.put( MARK_ID_ACTION, nIdAction );

        model.put( MARK_TASK_FORM, strHtmlTasksForm );

        return getPage( PROPERTY_PAGE_TITLE_TASK_FORM, TEMPLATE_TASK_FORM, model );
    }

    /**
     * Process workflow action
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_PROCESS_ACTION )
    public String doProcessAction( HttpServletRequest request )
    {

        Integer nIdDemand = request.getParameter( PARAMETER_ID_DEMAND ) != null ? Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND ) ) : null;
        Integer nIdAction = request.getParameter( PARAMETER_ID_ACTION ) != null ? Integer.parseInt( request.getParameter( PARAMETER_ID_ACTION ) ) : null;

        if ( WorkflowService.getInstance( ).isDisplayTasksForm( nIdAction, getLocale( ) ) )
        {
            return redirect( request, VIEW_TASK_FORM, PARAMETER_ID_DEMAND, nIdDemand, PARAMETER_ID_ACTION, nIdAction );
        }

        WorkflowService.getInstance( ).doProcessAction( nIdDemand, Demand.WORKFLOW_RESOURCE_TYPE, nIdAction, -1, request, getLocale( ), true );

        return redirectView( request, VIEW_MANAGE_DEMANDS );
    }

    /**
     * Process workflow action
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_SAVE_TASK_FORM )
    public String doSaveTaskForm( HttpServletRequest request )
    {

        Integer nIdDemand = request.getParameter( PARAMETER_ID_DEMAND ) != null ? Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND ) ) : null;
        Integer nIdAction = request.getParameter( PARAMETER_ID_ACTION ) != null ? Integer.parseInt( request.getParameter( PARAMETER_ID_ACTION ) ) : null;

        if ( WorkflowService.getInstance( ).canProcessAction( nIdDemand, Demand.WORKFLOW_RESOURCE_TYPE, nIdAction, -1, request, false ) )
        {
            try
            {
                String strError = WorkflowService.getInstance( )
                        .doSaveTasksForm( nIdDemand, Demand.WORKFLOW_RESOURCE_TYPE, nIdAction, -1, request, getLocale( ) );

                if ( strError != null )
                {
                    return strError;
                }

            }
            catch( Exception e )
            {
                AppLogService.error( "Error processing action for demand '" + nIdDemand, e );

            }
        }
        else
        {
            return redirectView( request, VIEW_TASK_FORM );
        }

        return redirectView( request, VIEW_MANAGE_DEMANDS );
    }
}
