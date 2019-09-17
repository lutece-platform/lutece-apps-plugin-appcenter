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

package fr.paris.lutece.plugins.appcenter.web;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDemandTypesEnable;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Parent
 *
 */
public abstract class AppCenterDemandXPage extends AppCenterXPage
{

    private static final long serialVersionUID = -490960650523760757L;

    /**
     * Populate demand
     * 
     * @param <D>
     *            The extended Demand object
     * @param demand
     *            the demande
     * @param request
     *            the request
     */
    protected <D extends Demand> void populate( D demand, HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {

        // Set Id Application
        demand.setIdApplication( getApplication( request ).getId( ) );
        // Set the demand owner
        LuteceUser user = SecurityService.getInstance( ).getRegisteredUser( request );
        demand.setIdUserFront( ( user != null ) ? UserService.getEmailUser( user ) : StringUtils.EMPTY );
        if ( demand.isDependingOfEnvironment( ) )
        {
            // Get the active environment in session
            HttpSession session = request.getSession( true );
            Environment environment = getActiveEnvironment( request );
            if ( environment != null )
            {
                demand.setEnvironment( Environment.getEnvironment( environment.getPrefix( ) ) );
            }
        }
        super.populate( demand, request );
    }

    @Override
    protected void fillAppCenterCommons( Map<String, Object> model, HttpServletRequest request ) throws SiteMessageException, UserNotSignedException
    {
        super.fillAppCenterCommons( model, request );

        // Add the application Datas relative to the demand type
        addDatas( request, _application, model, getDatasName( ), getDatasClass( ) );

    }

    protected abstract Class getDemandClass( );

    protected abstract String getDatasName( );

    protected abstract Class getDatasClass( );

}
