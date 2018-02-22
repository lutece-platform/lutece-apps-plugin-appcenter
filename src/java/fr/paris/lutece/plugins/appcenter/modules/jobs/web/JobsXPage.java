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
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesData;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDatas;
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
        checkRole( request , Constants.PROPERTY_MAPPING_XPAGE_ROLE + VIEW_MANAGE_JOBS.toLowerCase( ) );
        
        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );
        model.put(Constants.MARK_REPO_LIST, ApplicationService.getRefLisApplicationDatas(getApplication( request ), SourcesDatas.class,  getLocale(request), true,SourcesData::getRepositoryUrl, SourcesData::getRepositoryUrl));
        return getXPage( TEMPLATE_MANAGE_JOBS , request.getLocale( ), model );
    }

    @Action( ACTION_ADD_JOB )
    public XPage doAddJob( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        checkRole( request , Constants.PROPERTY_MAPPING_XPAGE_ROLE + ACTION_ADD_JOB.toLowerCase( ) );
        
        Application application = getApplication( request );
        JobDemand jobDemand = new JobDemand( );
        populate( jobDemand, request );
       // Check constraints
        if ( !validateBean( jobDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_JOBS );
        }

      
        
        DemandService.saveDemand( jobDemand, application );

        return redirect(request, VIEW_MANAGE_JOBS, Constants.PARAM_ID_APPLICATION, application.getId() );
    }

    @Override
    protected String getDemandType()
    {
        return JobDemand.DEMAND_TYPE;
    }

    @Override
    protected Class getDemandClass()
    {
        return JobDemand.class;
    }

    @Override
    protected String getDatasName()
    {
        return JobsData.DATA_JOBS_NAME;
    }

    @Override
    protected Class getDatasClass()
    {
        return JobsData.class;
    }

}
