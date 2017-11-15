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

package fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.web;

import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USER;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationDemand;
import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationsData;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDatas;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceList;

/**
 * SourcesXPage
 */
@Controller( xpageName = "fastdeployapplication", pageTitleI18nKey = "appcenter.xpage.fastdeployapplication.pageTitle", pagePathI18nKey = "appcenter.xpage.fastdeployapplication.pagePathLabel" )
public class FastDeployApplicationsXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_APPLICATIONS = "/skin/plugins/appcenter/modules/fastdeploy/manage_applications.html";

    // VIEW
    private static final String VIEW_MANAGE_APPLICATIONS = "manageapplications";
    

    // ACTION
    private static final String ACTION_ADD_APPLICATION = "addApplication";
    //MARK URL SITE
    private static final String MARK_DEFAULT_URL_SITE = "default_url_site";
    private static final String MARK_SERVICES = "services";
    
    
    public static final String  DATA_PREFIX_FAST_DEPLOY_SERVICES = "appcenter.fastdeployServices.";
    
    
    
    @View( value = VIEW_MANAGE_APPLICATIONS, defaultView = true )
    public XPage getManageApplications( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {

        Application application = getApplication( request );
        FastDeployApplicationsData dataSubset = ApplicationService.loadApplicationDataSubset( application, FastDeployApplicationsData.DATA_FASTDEPLOY_APPLICATIONS_NAME,
                FastDeployApplicationsData.class );

        
       SourcesDatas sourcesData = ApplicationService.loadApplicationDataSubset( application, SourcesDatas.DATA_SOURCES_NAME, SourcesDatas.class );

       // String strDefaultUrlSite= sourcesData!=null?sourcesData.getSiteRepository( ):null;
        String strDefaultUrlSite=null;
       
       ReferenceList refServices=DatastoreService.getDataByPrefix( DATA_PREFIX_FAST_DEPLOY_SERVICES );
        
        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );
     
        model.put( MARK_DEFAULT_URL_SITE, strDefaultUrlSite );
        model.put( MARK_SERVICES, refServices );

        return getXPage( TEMPLATE_MANAGE_APPLICATIONS, request.getLocale( ), model );
    }

    @Action( ACTION_ADD_APPLICATION )
    public XPage doAddApplication( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = getApplication( request );

        FastDeployApplicationDemand fastDeployApplicationDemand = new FastDeployApplicationDemand( );
        fastDeployApplicationDemand.setIdApplication( application.getId( ) );

        populate( fastDeployApplicationDemand, request );
        populateCommonsDemand( fastDeployApplicationDemand, request );

        // Check constraints
        if ( !validateBean( fastDeployApplicationDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_APPLICATIONS );
        }

        DemandService.saveDemand( fastDeployApplicationDemand, application );

        return redirect(request, VIEW_MANAGE_APPLICATIONS, Constants.PARAM_ID_APPLICATION, nId );
    }

    @Override
    protected String getDemandType()
    {
        return FastDeployApplicationDemand.DEMAND_TYPE;
    }

    @Override
    protected Class getDemandClass()
    {
        return FastDeployApplicationDemand.class;
    }

    @Override
    protected String getDatasName()
    {
        return FastDeployApplicationsData.DATA_FASTDEPLOY_APPLICATIONS_NAME;
    }

    @Override
    protected Class getDatasClass()
    {
        return FastDeployApplicationsData.class;
    }

}
