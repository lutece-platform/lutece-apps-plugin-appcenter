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

package fr.paris.lutece.plugins.appcenter.modules.guichetpro.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.DemandTypeCategoryHome;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProData;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProDatas;
import fr.paris.lutece.plugins.appcenter.modules.guichetpro.business.GuichetProDemand;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.appcenter.util.AppCenterUtils;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import static fr.paris.lutece.plugins.appcenter.web.Constants.MARK_USER;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import java.util.Arrays;

/**
 * GuichetProXPage
 */
@Controller( xpageName = "guichetpro", pageTitleI18nKey = "appcenter.xpage.guichetpro.pageTitle", pagePathI18nKey = "appcenter.xpage.guichetpro.pagePathLabel" )
public class GuichetProXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_GUICHET_PRO = "/skin/plugins/appcenter/modules/guichetpro/manage_guichetpro.html";

    private static final String VIEW_MANAGE_GUICHET_PRO_DEMANDS = "manageGuichetProDemands";
    private static final String ACTION_CREATE_GUICHET_PRO_DEMAND = "doCreateGuichetProDemand";
    
    private static final String MARK_CATEGORIES = "category_guichet_pro_demand_types";
    private static final String NEW_CATEGORIE = "-- Proposition d'une nouvelle catégorie --";

    /**
     * Maganage guichetpro view
     * @param request The HttpServletRequest
     * @return the view for managing guichetpro
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @View( value = VIEW_MANAGE_GUICHET_PRO_DEMANDS, defaultView = true )
    public XPage getManageNtoficiationGuichetProDemands( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        Map<String, Object> model = getModel( );
        fillAppCenterCommons( model, request );

        ReferenceList refListCategory=DemandTypeCategoryHome.getDemandTypeCategoriesReferenceList( );
        refListCategory.addItem("new", NEW_CATEGORIE );
        AppCenterUtils.addEmptyItem(refListCategory, getLocale(request));
        model.put(MARK_CATEGORIES, refListCategory);

        return getXPage( TEMPLATE_MANAGE_GUICHET_PRO, request.getLocale( ), model );
    }

    /**
     * Action : add access demand
     * @param request the HttpServletRequest
     * @return the manage view, after processing action
     * @throws UserNotSignedException
     * @throws SiteMessageException 
     */
    @Action( ACTION_CREATE_GUICHET_PRO_DEMAND )
    public XPage doCreateGuichetProDemand( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter(Constants.PARAM_ID_APPLICATION ) );
        Application application = getApplication( request );
        GuichetProDemand guichetProDemand = new GuichetProDemand( );
        guichetProDemand.setIdApplication( application.getId( ) );

        populate( guichetProDemand, request );
        
        // Check constraints
        if ( !validateBean( guichetProDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_GUICHET_PRO_DEMANDS );
        }

        DemandService.saveDemand( guichetProDemand, application );

        return redirect(request, VIEW_MANAGE_GUICHET_PRO_DEMANDS, Constants.PARAM_ID_APPLICATION, nId );
    }

    @Override
    protected String getDemandType()
    {
        return GuichetProDemand.DEMAND_TYPE;
    }

    @Override
    protected Class getDemandClass()
    {
        return GuichetProDemand.class;
    }

    @Override
    protected String getDatasName()
    {
        return GuichetProDatas.DATA_GUICHET_PRO_NAME;
    }

    @Override
    protected Class getDatasClass()
    {
        return GuichetProDatas.class;
    }
}
