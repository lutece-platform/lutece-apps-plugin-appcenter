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
package fr.paris.lutece.plugins.appcenter.service.task;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.CategoryDemandType;
import fr.paris.lutece.plugins.appcenter.business.CategoryDemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandType;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManager;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManagerHome;
import fr.paris.lutece.plugins.appcenter.business.task.NotifyTaskConfig;
import fr.paris.lutece.plugins.appcenter.business.task.NotifyTaskConfigHome;
import fr.paris.lutece.plugins.appcenter.service.AppcenterPlugin;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.url.UrlItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class TaskNotifyToValidate extends TaskNotify
{
    // PARAMETERS
    private static final String PARAM_SIGNATURE = "signature";
    private static final String PARAM_TIMESTAMP = "timestamp";
    private static final String PARAM_ID_DEMAND = "id_demand";
    private static final String PARAM_ID_TASK = "id_task";

    private static final String MARK_URL_VALIDATE = "url_validate";
    private static final String MARK_SIGNATURE = "signature";
    private static final String MARK_TIMESTAMP = "timestamp";
    private static final String MARK_ID_TASK = "id_task";
    private static final String MARK_DEMAND = "demand";
    private static final String MARK_DEMAND_TYPE = "demandtype";
    private static final String MARK_CATEGORY_DEMAND_TYPE = "categorydemandtype";
    private static final String MARK_ENVIRONMENT = "environment";
    private static final String MARK_APPLICATION = "application";
    private static final String MARK_JSON_DATA = "json_data";

    private static final String URL_VALIDATE_DEMAND = "jsp/site/Portal.jsp?page=demand_validation";
    private static final String SLASH = "/";

    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );

        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );
        Class demandClass = DemandTypeService.getClassByDemandTypeId( demand.getIdDemandType( ), DemandTypeHome.getDemandTypesList( ) );
        demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ), demandClass );
        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );
        OrganizationManager organizationManager = application.getOrganizationManager( );
        DemandType demandType = DemandTypeHome.findByIdDemandType( demand.getIdDemandType( ) );
        CategoryDemandType categoryDemandType = CategoryDemandTypeHome.findByPrimaryKey( demandType.getIdCategoryDemandType( ) );
        String strJsonData = DemandService.getPrettyPrintDemandData( demand );

        //Build the validate url
        List<String> listElements = new ArrayList<>(  );
        listElements.add( Integer.toString( getId( ) ) );

        String strTime = Long.toString( new Date(  ).getTime(  ) );

        String strSignature = RequestAuthenticatorService.getRequestAuthenticatorForUrl(  )
                                                         .buildSignature( listElements, strTime );

//        StringBuilder sbUrl = new StringBuilder( getBaseUrl( request ) );
//
//        if ( !sbUrl.toString(  ).endsWith( SLASH ) )
//        {
//            sbUrl.append( SLASH );
//        }
//
//        UrlItem urlValidate = new UrlItem( sbUrl.toString(  ) + URL_VALIDATE_DEMAND );
        UrlItem urlValidate = new UrlItem( URL_VALIDATE_DEMAND );
        urlValidate.addParameter( PARAM_ID_DEMAND, resourceHistory.getIdResource( ) );
        urlValidate.addParameter( PARAM_ID_TASK, getId( ) );
        urlValidate.addParameter( PARAM_SIGNATURE, strSignature );
        urlValidate.addParameter( PARAM_TIMESTAMP, strTime );


        //buid the model for message and subject
        Map<String, Object> model = new HashMap<String,Object>();
        model.put( MARK_URL_VALIDATE, urlValidate.toString( ) );
        model.put( MARK_SIGNATURE, strSignature );
        model.put( MARK_TIMESTAMP, strTime );
        model.put( MARK_ID_TASK, getId( ) );
        model.put( MARK_DEMAND, demand );
        model.put( MARK_APPLICATION, application );
        model.put( MARK_DEMAND_TYPE, demandType );
        if ( demand.getEnvironment( ) != null )
        {
            model.put( MARK_ENVIRONMENT, demand.getEnvironment( ).getLabel( ) );
        }
        model.put( MARK_CATEGORY_DEMAND_TYPE, categoryDemandType );
        model.put( MARK_JSON_DATA, strJsonData );

        NotifyTaskConfig conf = NotifyTaskConfigHome.findByPrimaryKey( getId( ), AppcenterPlugin.getPlugin( ) );

        String strMessage = AppTemplateService.getTemplateFromStringFtl( conf.getMessage( ), Locale.getDefault( ), model ).getHtml( );
        String strSubject = AppTemplateService.getTemplateFromStringFtl( conf.getSubject( ), Locale.getDefault( ), model ).getHtml( );


        MailService.sendMailHtml( organizationManager.getMail( ), conf.getSenderName( ), MailService.getNoReplyEmail( ), strSubject, strMessage );
    }

    @Override
    public String getTitle( Locale locale )
    {
        return "AppCenter Sources Notify to Validate";
    }
}
