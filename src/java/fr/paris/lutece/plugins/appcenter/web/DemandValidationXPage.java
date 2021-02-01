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
package fr.paris.lutece.plugins.appcenter.web;


import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandType;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.DemandValidation;
import fr.paris.lutece.plugins.appcenter.business.DemandValidationHome;
import fr.paris.lutece.plugins.appcenter.business.User;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManager;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManagerHome;
import fr.paris.lutece.plugins.appcenter.service.task.RequestAuthenticatorService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.plugins.workflowcore.service.action.ActionService;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.state.IStateService;
import fr.paris.lutece.plugins.workflowcore.service.state.StateService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.plugins.workflowcore.service.task.ITaskService;
import fr.paris.lutece.plugins.workflowcore.service.task.TaskService;
import fr.paris.lutece.portal.service.daemon.AppDaemonService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import java.util.List;
import java.util.Map;

@Controller( xpageName = "demand_validation", pageTitleI18nKey = "appcenter.xpage.demand_validation.pageTitle", pagePathI18nKey = "appcenter.xpage.demand_validation.pagePathLabel" )
public class DemandValidationXPage extends MVCApplication
{
    // Templates
    private static final String TEMPLATE_DEMAND_VALIDATION = "/skin/plugins/appcenter/demand_validation.html";

    // Parameters
    private static final String PARAM_ID_DEMAND = "id_demand";
    private static final String PARAM_ID_TASK = "id_task";
    private static final String PARAM_STATUS = "status";

    // Markers
    private static final String MARK_DEMAND = "demand";
    private static final String MARK_DEMAND_TYPE = "demandtype";
    private static final String MARK_ID_TASK = "id_task";
    private static final String MARK_APPLICATION = "application";

    // Views
    private static final String VIEW_DEMAND_VALIDATION = "demandValidation";

    // Actions
    private static final String ACTION_SAVE_DEMAND_VALIDATION = "saveDemandValidation";

    //Url
    private static final String URL_FRONT_HOME = "#";

    // MESSAGES
    private static final String MESSAGE_WRONG_FIELDS = "appcenter.xpage.demand_validation.message.wrong_fields";
    private static final String MESSAGE_VALIDATATION_NOT_REQUIRED = "appcenter.xpage.demand_validation.message.validation_not_required";
    private static final String MESSAGE_NOT_AUTHENTICATED = "appcenter.xpage.demand_validation.message.not_authenticated";
    private static final String MESSAGE_NOT_AUTHORIZED = "appcenter.xpage.demand_validation.message.not_authorized";
    private static final String MESSAGE_APP_ERROR = "appcenter.xpage.demand_validation.message.app_error";
    private static final String MESSAGE_VALIDATION_COMPLETE = "appcenter.xpage.demand_validation.message.validation_complete";

    private ITaskService _taskService = SpringContextService.getBean( TaskService.BEAN_SERVICE );
    private IActionService _actionService = SpringContextService.getBean( ActionService.BEAN_SERVICE );
    private IStateService _stateService = SpringContextService.getBean( StateService.BEAN_SERVICE );

    private static final String AUTOMATIC_ACTION_DAEMON_ID = "automaticActionDaemon";

    /**
     * Returns the task form associate to the workflow action
     *
     * @param request
     *            The Http request
     * @return The HTML form the task form associate to the workflow action
     */
    @View( value = VIEW_DEMAND_VALIDATION, defaultView = true )
    public XPage getDemandValidation( HttpServletRequest request ) throws SiteMessageException, UserNotSignedException
    {
        User user = UserService.getCurrentUser( request );

        if ( RequestAuthenticatorService.getRequestAuthenticatorForUrl(  ).isRequestAuthenticated( request ) )
        {
            SiteMessageService.setMessage( request, MESSAGE_NOT_AUTHENTICATED, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
        }

        Integer nIdDemand = request.getParameter( PARAM_ID_DEMAND ) != null ? Integer.parseInt( request.getParameter( PARAM_ID_DEMAND ) ) : null;
        Integer nIdTask = request.getParameter( PARAM_ID_TASK ) != null ? Integer.parseInt( request.getParameter( PARAM_ID_TASK ) ) : null;

        if ( nIdTask == null || nIdDemand == null )
        {
            SiteMessageService.setMessage( request, MESSAGE_WRONG_FIELDS, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
        }

        ITask task = _taskService.findByPrimaryKey( nIdTask, request.getLocale( ) );

        if ( task == null )
        {
            SiteMessageService.setMessage( request, MESSAGE_APP_ERROR, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
        }

        String strIdUser = user.getId( );

        Demand demand = DemandHome.findByPrimaryKey( nIdDemand );
        Class demandClass = DemandTypeService.getClassByDemandTypeId( demand.getIdDemandType( ), DemandTypeHome.getDemandTypesList( ) );
        demand = DemandHome.findByPrimaryKey( nIdDemand, demandClass );
        DemandType demandType = DemandTypeHome.findByIdDemandType( demand.getIdDemandType( ) );
        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        OrganizationManager organizationManager = application.getOrganizationManager( );

        if ( organizationManager == null )
        {
            SiteMessageService.setMessage( request, MESSAGE_APP_ERROR, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
        }

        if ( !strIdUser.equals( organizationManager.getMail( ) ) )
        {
            SiteMessageService.setMessage( request, MESSAGE_NOT_AUTHORIZED, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_DEMAND, demand );
        model.put( MARK_DEMAND_TYPE, demandType );
        model.put( MARK_APPLICATION, application );
        model.put( MARK_ID_TASK, task.getId( ) );

        return getXPage( TEMPLATE_DEMAND_VALIDATION, request.getLocale( ), model );
    }

    /**
     * Process workflow action
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @fr.paris.lutece.portal.util.mvc.commons.annotations.Action( ACTION_SAVE_DEMAND_VALIDATION )
    public XPage doSaveDemandValidation( HttpServletRequest request ) throws SiteMessageException
    {
        String strIdDemand = request.getParameter( PARAM_ID_DEMAND );
        String strIdTask = request.getParameter( PARAM_ID_TASK );
        String strStatus = request.getParameter( PARAM_STATUS );
        int nIdDemand = Integer.parseInt( strIdDemand );
        int nIdTask = Integer.parseInt( strIdTask );
        int nStatus = Integer.parseInt( strStatus );

        if ( RequestAuthenticatorService.getRequestAuthenticatorForUrl(  ).isRequestAuthenticated( request ) )
        {
            SiteMessageService.setMessage( request, MESSAGE_NOT_AUTHENTICATED, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
        }


        LuteceUser user = SecurityService.getInstance( ).getRegisteredUser( request );
        String strIdUser = UserService.getEmailUser( user );

        if ( strIdUser == null )
        {
            SiteMessageService.setMessage( request, MESSAGE_NOT_AUTHENTICATED, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
        }


        Demand demand = DemandHome.findByPrimaryKey( nIdDemand );
        int nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getDemandType( ) );


        ITask task = _taskService.findByPrimaryKey( nIdTask, request.getLocale( ) );
        Action action = _actionService.findByPrimaryKey( task.getAction( ).getId( ) );

        if ( task == null || nIdWorkflow != action.getWorkflow( ).getId( ) )
        {
            SiteMessageService.setMessage( request, MESSAGE_WRONG_FIELDS, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
        }

        List<DemandValidation> listDemandValidations = DemandValidationHome.findByDemand( nIdDemand );
        for ( DemandValidation demandValidation : listDemandValidations )
        {
            if ( demandValidation.getIdTask( ) == nIdTask && demandValidation.getIdUser( ) == strIdUser )
            {
                SiteMessageService.setMessage( request, MESSAGE_VALIDATATION_NOT_REQUIRED, SiteMessage.TYPE_ERROR, URL_FRONT_HOME );
            }
        }

        DemandValidation demandValidation = new DemandValidation( );
        demandValidation.setIdDemand( nIdDemand );
        demandValidation.setIdTask( nIdTask );
        demandValidation.setStatus( nStatus );
        demandValidation.setIdUser( strIdUser );
        DemandValidationHome.create( demandValidation );

        AppDaemonService.signalDaemon( AUTOMATIC_ACTION_DAEMON_ID );

        SiteMessageService.setMessage( request, MESSAGE_VALIDATION_COMPLETE, SiteMessage.TYPE_INFO, URL_FRONT_HOME );

        return null;
    }
}
