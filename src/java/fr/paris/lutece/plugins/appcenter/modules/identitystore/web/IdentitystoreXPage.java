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

package fr.paris.lutece.plugins.appcenter.modules.identitystore.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.Attribute;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.AttributeHome;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDatas;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDemand;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.service.IAttributesProvider;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.service.IdentityStoreDemandService;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USER;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceList;
import java.util.Arrays;


/**
 * IdentitystoreXPage
 */
@Controller( xpageName = "identitystore", pageTitleI18nKey = "appcenter.xpage.identitystore.pageTitle", pagePathI18nKey = "appcenter.xpage.identitystore.pagePathLabel" )
public class IdentitystoreXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_IDENTITYSTORE = "/skin/plugins/appcenter/modules/identitystore/manage_identitystore_demand.html";


    private static final String VIEW_MANAGE_IDENTITYSTORE = "identitystore";
    private static final String ACTION_ADD_IDENTITYSTORE_DEMAND = "addIdentitystoreDemand";

    private static final String MARK_ATTRIBUTES = "attributes";
    private static final String MARK_ENVIRONMENT = "environment";
    private static final String MARK_MAP_ATTRIBUTES = "mapAttributes";
    
    private final IAttributesProvider _attributesProviderService = SpringContextService.getBean( "appcenter.attributesProvider" );
    
    /**
     * Maganage identitystore view
     * @param request The HttpServletRequest
     * @return the view for managing identitystore
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @View( value = VIEW_MANAGE_IDENTITYSTORE, defaultView = true )
    public XPage getManageApplications( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        checkRole( request , Constants.PROPERTY_MAPPING_XPAGE_ROLE + VIEW_MANAGE_IDENTITYSTORE.toLowerCase( ) );
        
        Map<String,Attribute> mapAttributes = AttributeHome.getAttributesMap();
        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );
        model.put( MARK_ATTRIBUTES, _attributesProviderService.getListAttributes( ) );
        model.put( MARK_MAP_ATTRIBUTES, mapAttributes );

        return getXPage( TEMPLATE_MANAGE_IDENTITYSTORE, request.getLocale( ), model );
    }

    /**
     * Action : add access demand
     * @param request the HttpServletRequest
     * @return the manage view, after processing action
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @Action( value = ACTION_ADD_IDENTITYSTORE_DEMAND )
    public XPage doAddAccessDemand( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        checkRole( request , Constants.PROPERTY_MAPPING_XPAGE_ROLE + ACTION_ADD_IDENTITYSTORE_DEMAND.toLowerCase( ) );
        
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = getApplication( request );
        IdentitystoreDemand identitystoreDemand = new IdentitystoreDemand( );
        identitystoreDemand.setIdApplication( application.getId( ) );

        populate( identitystoreDemand, request );
        //Populate the identitystore attribute rights to the demand
        identitystoreDemand.setAttributeRights( IdentityStoreDemandService.getMapAttributeRights( request ) );
        
        // Check constraints
        if ( !validateBean( identitystoreDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_IDENTITYSTORE );
        }

        DemandService.saveDemand( identitystoreDemand, application );

        return redirect(request, VIEW_MANAGE_IDENTITYSTORE, Constants.PARAM_ID_APPLICATION, nId );
    }

    @Override
    protected String getDemandType()
    {
        return IdentitystoreDemand.DEMAND_TYPE;
    }

    @Override
    protected Class getDemandClass()
    {
        return IdentitystoreDemand.class;
    }

    @Override
    protected String getDatasName()
    {
        return IdentitystoreDatas.DATA_SUBSET_NAME;
    }

    @Override
    protected Class getDatasClass()
    {
        return IdentitystoreDatas.class;
    }
}
