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
package fr.paris.lutece.plugins.appcenter.service.task;

import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.CategoryDemandType;
import fr.paris.lutece.plugins.appcenter.business.CategoryDemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandType;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRole;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRoleHome;
import fr.paris.lutece.plugins.appcenter.business.task.NotifyTaskConfig;
import fr.paris.lutece.plugins.appcenter.business.task.NotifyTaskConfigHome;
import fr.paris.lutece.plugins.appcenter.service.AppcenterPlugin;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.business.mailinglist.Recipient;
import fr.paris.lutece.portal.business.rbac.RBACRole;
import fr.paris.lutece.portal.business.rbac.RBACRoleHome;
import fr.paris.lutece.portal.service.mail.MailItem;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.mailinglist.AdminMailingListService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class TaskNotify extends SimpleTask
{
    private static final String MARK_DEMAND = "demand";
    private static final String MARK_DEMAND_TYPE = "demandtype";
    private static final String MARK_CATEGORY_DEMAND_TYPE = "categorydemandtype";
    private static final String MARK_ENVIRONMENT = "environment";
    private static final String MARK_APPLICATION = "application";
    private static final String MARK_JSON_DATA = "json_data";
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        // Get the demand
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );
        Class demandClass = DemandTypeService.getClassByDemandTypeId( demand.getIdDemandType( ), DemandTypeHome.getDemandTypesList( ) );
        demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ), demandClass );

        // Get the config
        NotifyTaskConfig config = NotifyTaskConfigHome.findByPrimaryKey( getId( ), AppcenterPlugin.getPlugin( ) );

        // Process the remplacement of the subject and message by the markers in the config
        changeMarkers( config, demand, request );

        MailService.sendMailHtml( getRecipients( config, demand ), config.getSenderName( ), MailService.getNoReplyEmail( ), config.getSubject( ), config.getMessage( ) );
    }

    @Override
    public String getTitle( Locale locale )
    {
        return "AppCenter Notify";
    }

    /**
     * Populate the markers in subject and message
     * 
     * @param conf
     * @param demand
     * @param request
     */
    private void changeMarkers( NotifyTaskConfig conf, Demand demand, HttpServletRequest request )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_DEMAND, demand );
        model.put( MARK_APPLICATION, ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) ) );
        DemandType demandType = DemandTypeHome.findByIdDemandType( demand.getIdDemandType( ) );
        CategoryDemandType categoryDemandType = CategoryDemandTypeHome.findByPrimaryKey( demandType.getIdCategoryDemandType( ) );
        model.put( MARK_DEMAND_TYPE, demandType );
        if ( demand.getEnvironment( ) != null )
        {
            model.put( MARK_ENVIRONMENT, demand.getEnvironment( ).getLabel( ) );
        }
        model.put( MARK_CATEGORY_DEMAND_TYPE, categoryDemandType );
        String strJsonData = DemandService.getPrettyPrintDemandData( demand );
        model.put( MARK_JSON_DATA, strJsonData );
        conf.setMessage( AppTemplateService.getTemplateFromStringFtl( conf.getMessage( ), Locale.getDefault( ), model ).getHtml( ) );
        conf.setSubject( AppTemplateService.getTemplateFromStringFtl( conf.getSubject( ), Locale.getDefault( ), model ).getHtml( ) );
    }

    /**
     * Get the recipients depending of notification type
     * 
     * @param config
     * @param demand
     * @return the recipients string
     */
    private String getRecipients( NotifyTaskConfig config, Demand demand )
    {
        List<UserApplicationRole> userAppList = UserApplicationRoleHome.getUserApplicationRolesListByIdApplication( demand.getIdApplication( ) );
        List<String> listEmailAddresses = new ArrayList<>( );
        switch( config.getNotificationType( ) )
        {
            case "owner":
                return demand.getIdUserFront( );
            case "ownerApp":
                for ( UserApplicationRole userApp : userAppList )
                {
                    RBACRole role = RBACRoleHome.findByPrimaryKey( userApp.getIdRole( ) );
                    if ( role.getKey( ) == "app_owner" )
                    {
                        listEmailAddresses.add( userApp.getIdUser( ) );
                        break;
                    }
                }
                break;
            case "all":
                for ( UserApplicationRole userApp : userAppList )
                {
                    listEmailAddresses.add( userApp.getIdUser( ) );
                }
            case "mailing_list":
                Collection<Recipient> colRec = AdminMailingListService.getRecipients( config.getIdMailingList( ) );
                for ( Recipient recipient : colRec )
                {
                    listEmailAddresses.add( recipient.getEmail( ) );
                }
                break;
        }
        StringJoiner joiner = new StringJoiner( ";" );
        listEmailAddresses.forEach( addr -> joiner.add( addr ) );
        return joiner.toString( );
    }
}
