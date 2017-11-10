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

import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USER;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.rometools.rome.feed.rss.Source;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourceUserDemand;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDatas;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDemand;
import fr.paris.lutece.plugins.appcenter.modules.sources.service.SourcesUtil;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
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
@Controller( xpageName = "sources", pageTitleI18nKey = "module.appcenter.sources.xpage.sources.pageTitle", pagePathI18nKey = "module.appcenter.sources.xpage.sources.pagePathLabel" )
public class SourcesXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_SOURCES = "/skin/plugins/appcenter/modules/sources/manage_sources.html";
    private static final String PARAMETER_SITE_REPOSITORY = "site_repository";

    private static final String VIEW_MANAGE_SOURCES = "sources";
    private static final String ACTION_ADD_REPOSITORY = "addRepository";
    private static final String ACTION_ADD_ACCESS_DEMAND = "addAccessDemand";
    private static final String PARAM_USER_NAME = "user_name";
    private static final String PARAM_MAIL = "email";
    
    
    
    private static final int NB_MAX_USER=100;
    
    


    /**
     * Maganage sources view
     * @param request The HttpServletRequest
     * @return the view for managing sources
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @View( value = VIEW_MANAGE_SOURCES, defaultView = true )
    public XPage getManageSources( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );
       
        ReferenceList refListRepoType=SourcesUtil.getAllRepositoryType(getLocale(request));
        model.put(SourcesUtil.MARK_REPOSITORY_TYPES, refListRepoType);
        model.put(SourcesUtil.MARK_REPOSITORY_TYPES_MAP, refListRepoType.toMap());
       
        return getXPage( TEMPLATE_MANAGE_SOURCES, request.getLocale( ), model );
    }

    /**
     * Action add site repository
     * @param request The HttpServletRequest
     * @return the manage view after processing action
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @Action( ACTION_ADD_REPOSITORY )
    public XPage doAddRepository (HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = getApplication( request );
        SourcesDemand sourcesDemand = new SourcesDemand( );
        sourcesDemand.setIdApplication( application.getId( ) );
        populate( sourcesDemand, request );
        //Add Users Authorized
        String strUserName=null;
        String strMail=null;
        SourceUserDemand sourceUserDemand;
        sourcesDemand.setListSourceUserDemand( new ArrayList<>( ) );
        for ( int i = 0; i < NB_MAX_USER; i++ )
        {
            strUserName=request.getParameter( PARAM_USER_NAME+"_"+i );
            if(!StringUtils.isEmpty( strUserName ))
            {
                
                strMail=request.getParameter( PARAM_MAIL+"_"+i );
                sourceUserDemand=new SourceUserDemand( );
                sourceUserDemand.setUserName( strUserName );
                sourceUserDemand.setEmail( strMail );
                sourcesDemand.getListSourceUserDemand( ).add( sourceUserDemand );
                
            }
        }
        // Check constraints
        if ( !validateBean( sourcesDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_SOURCES );
        }
        DemandService.saveDemand( sourcesDemand, application );

        return redirect(request, VIEW_MANAGE_SOURCES, Constants.PARAM_ID_APPLICATION, nId );
    }

    /**
     * Action : add access demand
     * @param request the HttpServletRequest
     * @return the manage view, after processing action
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @Action( ACTION_ADD_ACCESS_DEMAND )
    public XPage doAddAccessDemand( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = getApplication( request );
        SourcesDemand sourcesDemand = new SourcesDemand( );
        sourcesDemand.setIdApplication( application.getId( ) );

        populate( sourcesDemand, request );
        populateCommonsDemand( sourcesDemand, request);
        
        //Get the source repository from dataSubset
        SourcesDatas sourcesDatas = ApplicationService.loadApplicationDataSubset( application, SourcesDatas.DATA_SOURCES_NAME, SourcesDatas.class );
        //sourcesDemand.setSiteRepository( sourcesDatas.getSiteRepository( ) );
        
        // Check constraints
        if ( !validateBean( sourcesDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_SOURCES );
        }

        DemandService.saveDemand( sourcesDemand, application );

        return redirect(request, VIEW_MANAGE_SOURCES, Constants.PARAM_ID_APPLICATION, nId );
    }

    @Override
    protected String getDemandType()
    {
        return SourcesDemand.DEMAND_TYPE;
    }

    @Override
    protected Class getDemandClass()
    {
        return SourcesDemand.class;
    }

    @Override
    protected String getDatasName()
    {
        return SourcesDatas.DATA_SOURCES_NAME;
    }

    @Override
    protected Class getDatasClass()
    {
        return SourcesDatas.class;
    }
}
