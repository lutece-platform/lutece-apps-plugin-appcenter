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
import fr.paris.lutece.plugins.appcenter.business.ApplicationDatas;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandFilter;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandType;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.appcenter.util.AppCenterUtils;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.content.XPageAppService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.portal.web.xpages.XPageApplicationEntry;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sort.AttributeComparator;
import fr.paris.lutece.util.url.UrlItem;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

/**
 * This class provides the user interface to manage Demand features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDemands.jsp", controllerPath = "jsp/admin/plugins/appcenter/", right = "APPCENTER_MANAGEMENT" )
public class DemandJspBean extends ManageAppCenterJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_DEMANDS = "/admin/plugins/appcenter/manage_demands.html";
    private static final String TEMPLATE_TASK_FORM = "/admin/plugins/appcenter/task_form.html";
    private static final String TEMPLATE_DEMAND_HISTORY = "/admin/plugins/appcenter/demand_history.html";
    

    // Parameters
    private static final String PARAMETER_ID_DEMAND = "id";
    private static final String PARAMETER_ID_ACTION = "id_action";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DEMANDS = "appcenter.manage_demands.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_TASK_FORM = "appcenter.task_form.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_DEMAND_HISTORY = "appcenter.demand_history.pageTitle";
    

    // Markers
    private static final String MARK_DEMAND_LIST = "demand_list";
    private static final String MARK_DEMAND = "demand";
    private static final String MARK_ID_ACTION = "id_action";
    private static final String MARK_APPLICATION = "application";
    private static final String MARK_DEMAND_TYPE_REF_LIST = "demand_type_ref_list";
    private static final String MARK_ENVIRONMENT_REF_LIST = "environment_ref_list";
    private static final String MARK_APPLICATION_REF_LIST = "application_ref_list";
    private static final String MARK_DEMAND_FILTER = "demand_filter";

    private static final String MARK_TASK_FORM = "task_form";

    private static final String MARK_APPLICATION_MAP = "applications";
    private static final String MARK_STATES_MAP = "states";

    private static final String MARK_ACTIONS_MAP = "actions";
    private static final String MARK_HISTORY = "history";
    private static final String MARK_JSON_DATA = "json_data";
    private static final String JSP_MANAGE_DEMANDS = "jsp/admin/plugins/appcenter/ManageDemands.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_DEMAND = "appcenter.message.confirmRemoveDemand";

    // Views
    private static final String VIEW_MANAGE_DEMANDS = "manageDemands";
    private static final String VIEW_TASK_FORM = "taskForm";
    private static final String VIEW_HISTORY = "demandHistory";
    

    // Actions
    private static final String ACTION_PROCESS_ACTION = "processAction";
    private static final String ACTION_SAVE_TASK_FORM = "saveTaskForm";
    private static final String ACTION_FILTER_DEMAND = "filterDemands";
    private static final String ACTION_REMOVE_DEMAND = "removeDemand";
    private static final String ACTION_CONFIRM_REMOVE_DEMAND = "confirmRemoveDemand";
    
    //Constant
    private static final String CONSTANT_LABEL_DEMAND_TYPE = "labelDemandType";
    private static final String CONSTANT_CODE_APPLICATION = "codeApplication";
    private static final String CONSTANT_NAME_APPLICATION = "nameApplication";
    private static final String CONSTANT_WORKFLOW_STATE = "workflowState";
    private static final String CONSTANT_ID_USER_FRONT = "idUserFront";
    private static final String CONSTANT_CREATION_DATE = "creationDate";

    // Infos
    private static final String INFO_DEMAND_REMOVED = "appcenter.info.demand.removed";

    //Sessions variable
    private DemandFilter _filter;

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

        //Initialize the demand filter
        if ( _filter == null )
        { 
            _filter = new DemandFilter( );
            
            // open demands filter default
            _filter.setHasIsClosed( true );
            _filter.setIsClosed( false );
        }
        
        List<Demand> listDemands = DemandHome.getDemandsListByFilter( _filter );
        int nIdWorkflow;

        Map<String,Application> mapApplications = new HashMap<>();
        Map<String,State> mapStates = new HashMap<>();
        Map<String,DemandType> mapDemandTypes = new HashMap<>();
        
        Map<String,Collection<fr.paris.lutece.plugins.workflowcore.business.action.Action>> mapActions = new HashMap<>();

        for ( Application app : ApplicationHome.getApplicationsList( ) )
        {
            mapApplications.put( Integer.toString( app.getId( ) ), app );
        }

        for ( DemandType demandType : DemandTypeHome.getDemandTypesList( ) )
        {
            mapDemandTypes.put( demandType.getIdDemandType( ), demandType );
        }

        for ( Demand demand : listDemands)
        {

        	nIdWorkflow=DemandTypeService.getIdWorkflow( demand.getDemandType());
            State state = WorkflowService.getInstance( ).getState( demand.getId( ), Demand.WORKFLOW_RESOURCE_TYPE,nIdWorkflow , -1 );
            mapStates.put( Integer.toString( demand.getId() ), state );
            Collection<fr.paris.lutece.plugins.workflowcore.business.action.Action> listActions = WorkflowService.getInstance( ).getActions( demand.getId( ),  Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow,
                    getUser() );
            mapActions.put(Integer.toString( demand.getId() ), listActions);
         
            
       }
        
        Comparator<ReferenceItem> comparator = Comparator.comparing( ( ReferenceItem x ) -> x.getName( ) );
       
        //Construct demand type ref list
        ReferenceList demandTypeRefList = ReferenceList.convert( mapDemandTypes.values( ), "idDemandType","label",false );
        
        //Filter demand list and demand type reference list by RBAC on demandType
        DemandTypeService.filterWithRBAC( listDemands, demandTypeRefList, getUser() );
        Collections.sort( demandTypeRefList , comparator);
        
        AppCenterUtils.addFirstItem( demandTypeRefList, request.getLocale( ) );
        
        //Construct envi ref list
        ReferenceList refListEnvi = ReferenceList.convert( Arrays.asList( Environment.values( ) ), "prefix", "labelKey", false );
        for ( ReferenceItem item : refListEnvi )
        {
            item.setName( I18nService.getLocalizedString( item.getName( ), request.getLocale( ) ) );
        }
        Collections.sort( refListEnvi , comparator);
        AppCenterUtils.addFirstItem( refListEnvi, request.getLocale( ) );
        
        //Construct application ref list
        ReferenceList applicationRefList = ReferenceList.convert( mapApplications.values( ), "id", "name", true );
        Collections.sort( applicationRefList , comparator);
        AppCenterUtils.addFirstItem( applicationRefList, request.getLocale( ) );
        
        // SORT
        String strSortedAttributeName = request.getParameter( Parameters.SORTED_ATTRIBUTE_NAME );
        String strAscSort = null;

        if ( strSortedAttributeName != null )
        {
            strAscSort = request.getParameter( Parameters.SORTED_ASC );

            boolean bIsAscSort = Boolean.parseBoolean( strAscSort );
            
            if ( strSortedAttributeName.equals( CONSTANT_ID_USER_FRONT ) || strSortedAttributeName.equals( CONSTANT_CREATION_DATE ) )
            {
                Collections.sort( listDemands, new AttributeComparator( strSortedAttributeName, bIsAscSort ) );
            }
            else
            {
                Comparator<Demand> c = null;
                
                if ( strSortedAttributeName.equals( CONSTANT_CODE_APPLICATION ) )
                {
                    c = Comparator.comparing( ( Demand x ) -> mapApplications.get( Integer.toString( x.getIdApplication( ) ) ).getCode( ) );
                }
                if ( strSortedAttributeName.equals( CONSTANT_NAME_APPLICATION ) )
                {
                    c = Comparator.comparing( ( Demand x ) -> mapApplications.get( Integer.toString( x.getIdApplication( ) ) ).getName( ) );
                }
                else if ( strSortedAttributeName.equals( CONSTANT_LABEL_DEMAND_TYPE ) )
                {
                    c = Comparator.comparing( ( Demand x ) -> mapDemandTypes.get( x.getIdDemandType( ) ).getLabel( ) );
                }
                else if ( strSortedAttributeName.equals( CONSTANT_WORKFLOW_STATE ) )
                {
                    c = Comparator.comparing( ( Demand x ) -> mapStates.get( Integer.toString( x.getId( ) ) ).getName( ) );
                }
                
                if ( c != null )
                {
                    if (bIsAscSort)
                    {
                        Collections.sort( listDemands, Collections.reverseOrder( c ) );
                    }
                    else
                    {
                        Collections.sort( listDemands, c );
                    }
                }
            }
        }
        
        UrlItem url = new UrlItem( JSP_MANAGE_DEMANDS );
        
        if ( strSortedAttributeName != null )
        {
            url.addParameter( Parameters.SORTED_ATTRIBUTE_NAME, strSortedAttributeName );
        }
        
        if ( strAscSort != null )
        {
            url.addParameter( Parameters.SORTED_ASC, strAscSort );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_DEMAND_LIST, listDemands, url.getUrl( ) );

        model.put( MARK_DEMAND_TYPE_REF_LIST, demandTypeRefList  );
        model.put( MARK_ENVIRONMENT_REF_LIST, refListEnvi );
        model.put( MARK_APPLICATION_REF_LIST, applicationRefList );
        model.put( MARK_DEMAND_FILTER, _filter );
        model.put( MARK_APPLICATION_MAP, mapApplications );
        model.put( MARK_STATES_MAP, mapStates );
        model.put( MARK_ACTIONS_MAP, mapActions );
 
        
        
        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DEMANDS, TEMPLATE_MANAGE_DEMANDS, model );
    }
    
    /**
     * Process the action of filtering demands; set the filter
     * @param request
     * @return The manage demands view
     */
    @Action( ACTION_FILTER_DEMAND )
    public String doFilterDemand( HttpServletRequest request )
    {
        _filter = DemandService.computeDemandFilter( request );
        return redirectView( request, VIEW_MANAGE_DEMANDS );
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
     * Returns the task form associate to the workflow action
     *
     * @param request
     *            The Http request
     * @return The HTML form the task form associate to the workflow action
     */
    @View( VIEW_HISTORY )
    public String getViewHistory(HttpServletRequest request )
    {
        // Demand
        Demand demand = null;
        Integer nIdDemand = request.getParameter( PARAMETER_ID_DEMAND ) != null ? Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND ) ) : null;
       
       
        demand = DemandHome.findByPrimaryKey( nIdDemand );
        Class demandClass = DemandTypeService.getClassByDemandTypeId( demand.getIdDemandType( ), DemandTypeHome.getDemandTypesList( ) );
        demand = DemandHome.findByPrimaryKey( nIdDemand, demandClass );
        int  nIdWorkflow=DemandTypeService.getIdWorkflow( demand.getDemandType());
        
        String strHistoryHtml = WorkflowService.getInstance( ).getDisplayDocumentHistory(
                demand.getId( ), Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow, request, request.getLocale( )
        		);
  
        // Aplication
        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        String strJsonData = DemandService.getPrettyPrintDemandData( demand );
       
        Map<String, Object> model = getModel( );
        model.put( MARK_DEMAND, demand );
        model.put( MARK_APPLICATION, application );
        model.put( MARK_HISTORY, strHistoryHtml );
        model.put( MARK_JSON_DATA, strJsonData );
   

        return getPage( PROPERTY_PAGE_TITLE_DEMAND_HISTORY, TEMPLATE_DEMAND_HISTORY, model );
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
	            String strError = WorkflowService.getInstance( ).doSaveTasksForm( nIdDemand, Demand.WORKFLOW_RESOURCE_TYPE, nIdAction, -1,
	                    request, getLocale() );
	            if(strError!=null)
	            {
	            	addError(strError);
	            	return redirect(request, VIEW_TASK_FORM, PARAMETER_ID_DEMAND, nIdDemand, PARAMETER_ID_ACTION, nIdAction);
	            }
	          
	        }
	        catch( Exception e )
	        {
	             AppLogService.error( "Error processing action for demand '" + nIdDemand , e );
	         
	        }

        }
        else
        {
            return redirectView( request, VIEW_TASK_FORM );
        }

        return redirectView( request, VIEW_MANAGE_DEMANDS );
    }

    /**
     * Manages the removal form of a application whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_DEMAND )
    public String getConfirmRemoveDemand( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_DEMAND ) );
        url.addParameter( PARAMETER_ID_DEMAND, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_DEMAND, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a demand
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage demands
     */
    @Action( ACTION_REMOVE_DEMAND )
    public String doRemoveDemand( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DEMAND ) );
        DemandService.remove( nId );
        addInfo( INFO_DEMAND_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_DEMANDS );
    }

    public static Class getClassApplicationDatasByDemandTypeKey( String strDemandTypeKey )
    {
        Collection<XPageApplicationEntry> listXPageApplicationEntry = XPageAppService.getXPageApplicationsList( );
        XPageApplication xPageApplication = null;

        for (XPageApplicationEntry xPageApplicationEntry : listXPageApplicationEntry)
        {
            xPageApplication = XPageAppService.getApplicationInstance( xPageApplicationEntry );
            if ( xPageApplication instanceof AppCenterDemandXPage )
            {
                AppCenterDemandXPage appCenterDemandXPage = ( AppCenterDemandXPage ) xPageApplication;
                if ( appCenterDemandXPage.getDemandType( ) != null && appCenterDemandXPage.getDemandType( ).equals( strDemandTypeKey ) )
                {
                    return appCenterDemandXPage.getDatasClass( );
                }
            }
        }

        return null;
    }
}
