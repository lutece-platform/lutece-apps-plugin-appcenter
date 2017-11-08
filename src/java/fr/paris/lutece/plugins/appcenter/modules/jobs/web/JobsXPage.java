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

package fr.paris.lutece.plugins.appcenter.modules.jobs.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobDemand;
import fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobsData;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USER;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

/**
 * SourcesXPage
 */
@Controller( xpageName = "jobs", pageTitleI18nKey = "module.appcenter.jobs.xpage.jobs.pageTitle", pagePathI18nKey = "module.appcenter.jobs.xpage.jobs.pagePathLabel" )
public class JobsXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_JOBS = "/skin/plugins/appcenter/modules/jobs/manage_jobs.html";

    // Markers

    // VIEW
    private static final String VIEW_MANAGE_JOBS = "managejobs";

    // ACTION
    private static final String ACTION_ADD_JOB = "addJob";

    @View( value = VIEW_MANAGE_JOBS, defaultView = true )
    public XPage getManageJobs( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {

        Application application = getApplication( request );
        JobsData dataSubset = ApplicationService.loadApplicationDataSubset( application, JobsData.DATA_JOBS_NAME ,
                JobsData.class );

        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION, application );
        model.put( Constants.MARK_DATA, dataSubset );
        model.put( MARK_USER, UserService.getCurrentUser( request, application.getId( ) ));
        addListDemand( request, application, model, JobDemand.ID_DEMAND_TYPE, JobDemand.class );

        return getXPage( TEMPLATE_MANAGE_JOBS , request.getLocale( ), model );
    }

    @Action( ACTION_ADD_JOB )
    public XPage doAddJob( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = getApplication( request );

        JobDemand jobDemand = new JobDemand( );
        jobDemand.setIdApplication( application.getId( ) );

        populate( jobDemand, request );
        
        // Check constraints
        if ( !validateBean( jobDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_JOBS );
        }

        // get the name of the plugin (last term of the URL)
        jobDemand.setPluginName( jobDemand.getPluginUrl( ).substring( jobDemand.getPluginUrl( ).lastIndexOf( "/" ) + 1 ));

        
        DemandService.saveDemand( jobDemand, application );

        return redirect(request, VIEW_MANAGE_JOBS, Constants.PARAM_ID_APPLICATION, nId );
    }

}
