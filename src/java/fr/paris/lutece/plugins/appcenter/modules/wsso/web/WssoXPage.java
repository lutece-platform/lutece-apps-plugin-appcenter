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

package fr.paris.lutece.plugins.appcenter.modules.wsso.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;

/**
 * WssoXPage
 */
@Controller( xpageName = "wsso", pageTitleI18nKey = "appcenter.xpage.wsso.pageTitle", pagePathI18nKey = "appcenter.xpage.wsso.pagePathLabel" )
public class WssoXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_WSSO = "/skin/plugins/appcenter/modules/wsso/manage_wsso.html";

    private static final String VIEW_MANAGE_WSSO_DEMANDS = "manageWssoDemands";


    /**
     * Maganage wsso view
     * @param request The HttpServletRequest
     * @return the view for managing wsso
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @View( value = VIEW_MANAGE_WSSO_DEMANDS, defaultView = true )
    public XPage getManageNtoficiationGruDemands( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );

        return getXPage( TEMPLATE_MANAGE_WSSO, request.getLocale( ), model );
    }

    @Override
    protected String getDemandType()
    {
        return "";
    }

    @Override
    protected Class getDemandClass()
    {
        return null;
    }

    @Override
    protected String getDatasName()
    {
        return "";
    }

    @Override
    protected Class getDatasClass()
    {
        return null;

    }
}
