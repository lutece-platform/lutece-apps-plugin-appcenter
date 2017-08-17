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

package fr.paris.lutece.plugins.appcenter.modules.sources.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesData;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDemand;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

/**
 * SourcesXPage
 */
@Controller( xpageName = "sources", pageTitleI18nKey = "appcenter.xpage.sources.pageTitle", pagePathI18nKey = "appcenter.xpage.sources.pagePathLabel" )
public class SourcesXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_SOURCES = "/skin/plugins/appcenter/modules/sources/manage_sources.html";

    private static final String PARAMETER_SITE_REPOSITORY = "site_repository";
    private static final String PARAMETER_SVN_USERNAMES = "svn_usernames";
    private static final String PARAMETER_SVN_EMAILS = "svn_emails";
    
    private static final String VIEW_MANAGE_SOURCES = "sources";
    private static final String ACTION_ADD_SITE_REPOSITORY = "addSiteRepository";
    private static final String ACTION_ADD_ACCESS_DEMAND = "addAccessDemand";

    public static final String DEMAND_TYPE = "sources";

    @View( value = VIEW_MANAGE_SOURCES, defaultView = true )
    public XPage getManageApplications( HttpServletRequest request )  throws UserNotSignedException, SiteMessageException
    {
       
    	Application application = getApplication(request);
        SourcesData dataSubset = ApplicationService.loadApplicationDataSubset( application, SourcesData.DATA_SUBSET_NAME, SourcesData.class );

        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION, application );
        model.put( Constants.MARK_DATA, dataSubset );
        addListDemand( request, application, model, DEMAND_TYPE, SourcesDemand.class );
        

        return getXPage( TEMPLATE_MANAGE_SOURCES, request.getLocale( ), model );
    }

    @Action( ACTION_ADD_SITE_REPOSITORY )
    public XPage doAddSiteRepository( HttpServletRequest request )  throws UserNotSignedException, SiteMessageException
    {
        String strSiteDirectory = request.getParameter( PARAMETER_SITE_REPOSITORY );
        Application application = getApplication(request);
        SourcesData dataSubset = ApplicationService.loadApplicationDataSubset( application, SourcesData.DATA_SUBSET_NAME, SourcesData.class );
        if ( dataSubset == null )
        {
            dataSubset = new SourcesData( );
        }
        dataSubset.setSiteRepository( strSiteDirectory );
        ApplicationService.saveApplicationData( application, dataSubset );

        return redirect( request, VIEW_MANAGE_SOURCES, Constants.PARAMETER_ID_APPLICATION, application.getId() );
    }


    @Action( ACTION_ADD_ACCESS_DEMAND )
    public XPage doAddAccessDemand( HttpServletRequest request )  throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter( Constants.PARAMETER_ID_APPLICATION ) );
        Application application = getApplication(request);
        SourcesDemand sourcesDemand = new SourcesDemand( );
        sourcesDemand.setIdDemandType( DEMAND_TYPE );
        sourcesDemand.setDemandType( DEMAND_TYPE );
        sourcesDemand.setIdApplication( application.getId( ) );
        
        String strSVNUserNames = request.getParameter( PARAMETER_SVN_USERNAMES );
        String strSVNUserEmails = request.getParameter( PARAMETER_SVN_EMAILS );
        
        sourcesDemand.setUserName( strSVNUserNames );
        sourcesDemand.setEmail( strSVNUserEmails );
        
        DemandService.saveDemand( sourcesDemand, application ) ;

        return redirect( request, VIEW_MANAGE_SOURCES, Constants.PARAMETER_ID_APPLICATION, nId );
    }
}
