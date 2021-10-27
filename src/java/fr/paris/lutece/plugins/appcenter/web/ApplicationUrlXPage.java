/*
 * Copyright (c) 2002-2021, Mairie de Paris
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

import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USERS_LIST;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.appcenter.business.ApplicationUrl;
import fr.paris.lutece.plugins.appcenter.business.ApplicationUrlHome;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRoleHome;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceList;

/**
 * GestionUrlXPage
 *
 */
@Controller( xpageName = "gestion_url", pageTitleI18nKey = "appcenter.xpage.demand_validation.pageTitle", pagePathI18nKey = "appcenter.xpage.demand_validation.pagePathLabel" )
public class ApplicationUrlXPage extends AppCenterXPage
{
    /**
     * 
     */
    private static final long   serialVersionUID          = -5942592987268247377L;

    // TEMPLATE
    private static final String TEMPLATE_DEFAULT          = "/skin/plugins/appcenter/manage_url.html";

    // VIEW
    private static final String VIEW_DEFAULT              = "default_view";

    // ACTION
    private static final String ACTION_SAVE               = "save_url";
    private static final String ACTION_REMOVE             = "remove";

    // MARK
    private static final String MARK_APPLICATION_URL_LIST = "applicationUrlList";
    private static final String MARK_ENVIRONMENTS         = "environments";
    private static final String MARK_ACTIVE_ENVIRONMENT   = "activeEnvironment";
    private static final String MARK_TITLE                = "title";
    
    // PARAM
    private static final String PARAM_ID_APPLICATION_URL  = "idApplicationUrl";
    private static final String PARAM_ACTIVE_ENVIRONMENT  = "activeEnvironment";

    // PROPERTY
    private static final String PROPERTY_ENVIRONMENT_TITLE = "appcenter.manage_applicationUrl.environment.title";
    private static final String PROPERTY_ALL_TITLE         = "appcenter.manage_applicationUrl.all.title";

    @View( value = VIEW_DEFAULT, defaultView = true )
    public XPage getDefaultView( HttpServletRequest request ) throws SiteMessageException, UserNotSignedException
    {
        Map<String, Object> model = getModel( );

        String strIdApplication = request.getParameter( Constants.PARAM_ID_APPLICATION );
        String strActiveEnvironment = request.getParameter( MARK_ACTIVE_ENVIRONMENT );
        getApplication( request );
        model.put( MARK_USERS_LIST, UserApplicationRoleHome.getUserApplicationRolesListByIdApplication( _application.getId( ) ) );
        model.put( MARK_ENVIRONMENTS, ReferenceList.convert( Arrays.asList( Environment.values( ) ), "prefix", "labelKey", false ) );
       
        if ( StringUtils.isNotEmpty( strIdApplication ) )
        {
            strActiveEnvironment = StringUtils.isNotEmpty( strActiveEnvironment ) ? strActiveEnvironment : StringUtils.EMPTY;         
            
            addTitleByEnvironmentToModel( request, model, strActiveEnvironment );
            
            List<ApplicationUrl> listApplicationUrl = ApplicationUrlHome.findAllByIdApplication( Integer.parseInt( strIdApplication ), strActiveEnvironment );
            model.put( MARK_APPLICATION_URL_LIST, listApplicationUrl );

            fillAppCenterCommons( model, request );
        }
        return getXPage( TEMPLATE_DEFAULT, request.getLocale( ), model );
    }

    private void addTitleByEnvironmentToModel( HttpServletRequest request, Map<String, Object> model, String strActiveEnvironment )
    {
        String strTitle;
        
        if( !strActiveEnvironment.isEmpty( ) )
        {
            strTitle = MessageFormat.format( I18nService.getLocalizedString ( PROPERTY_ENVIRONMENT_TITLE , request.getLocale( ) )
                    , Environment.valueOf( strActiveEnvironment ).getLabel( ).toLowerCase( ));
        } else 
        {
            strTitle = I18nService.getLocalizedString ( PROPERTY_ALL_TITLE , request.getLocale( ) );
        }
        
        model.put( MARK_TITLE, strTitle );
    }

    @Action( ACTION_SAVE )
    public XPage doCreateUrl( HttpServletRequest request )
    {
        ApplicationUrl applicationUrl = new ApplicationUrl( );
        populate( applicationUrl, request );

        if ( applicationUrl.getIdApplicationUrl( ) != 0 )
        {
            ApplicationUrlHome.update( applicationUrl );
        } else
        {
            ApplicationUrlHome.create( applicationUrl );
        }
        Map<String, String> additionalParameters = new HashMap<>( );
        additionalParameters.put( Constants.PARAM_ID_APPLICATION, String.valueOf( applicationUrl.getIdApplication( ) ) );
        additionalParameters.put( MARK_ACTIVE_ENVIRONMENT, request.getParameter( PARAM_ACTIVE_ENVIRONMENT ) );

        return redirect( request, VIEW_DEFAULT, additionalParameters );
    }

    @Action( ACTION_REMOVE )
    public XPage doRemoveUrl( HttpServletRequest request )
    {
        String strIdApplication = request.getParameter( Constants.PARAM_ID_APPLICATION );
        String strIdApplicationUrl = request.getParameter( PARAM_ID_APPLICATION_URL );

        if ( StringUtils.isNotEmpty( strIdApplication ) && StringUtils.isNotEmpty( strIdApplicationUrl ) )
        {
            ApplicationUrlHome.remove( Integer.parseInt( strIdApplicationUrl ) );
        }

        Map<String, String> additionalParameters = new HashMap<>( );
        additionalParameters.put( Constants.PARAM_ID_APPLICATION, strIdApplication );
        additionalParameters.put( MARK_ACTIVE_ENVIRONMENT, request.getParameter( PARAM_ACTIVE_ENVIRONMENT ) );
        
        return redirect( request, VIEW_DEFAULT, additionalParameters );
    }
}