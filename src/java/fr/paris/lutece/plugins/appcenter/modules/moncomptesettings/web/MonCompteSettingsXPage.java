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

package fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.web;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingDemand;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingsData;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.ReferenceList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * SourcesXPage
 */
@Controller( xpageName = "moncomptesettings", pageTitleI18nKey = "appcenter.xpage.moncompte_settings.pageTitle", pagePathI18nKey = "appcenter.xpage.moncompte_settings.pagePathLabel" )
public class MonCompteSettingsXPage extends AppCenterXPage
{
    public static final String DEMAND_TYPE_KEY = "moncompte_settings";

    private static final String MARK_ENVIRONMENT = "environment";
    private static final String MARK_LIST_DEMANDS = "list_demands";
    private static final String MARK_LIST_DEMANDS_DATA = "list_demands_data";
    public static final String MARK_STATUS_TEXT_CREATED = "created";

    // Templates
    private static final String TEMPLATE_MANAGE_MONCOMPTE_SETTINGS_DEMAND = "/skin/plugins/appcenter/modules/moncompte_settings/manage_moncompte_settings_demand.html";
    private static final String TEMPLATE_CREATE_MONCOMPTE_SETTINGS_DEMAND = "/skin/plugins/appcenter/modules/moncompte_settings/create_moncompte_settings_demand.html";

    private static final String VIEW_MANAGE_MONCOMPTE_SETTINGS_DEMAND = "manageMoncompteSettingsDemand";
    private static final String VIEW_CREATE_MONCOMPTE_SETTINGS_DEMAND = "createMoncompteSettingsDemand";

    private static final String ACTION_CREATE_MONCOMPTE_SETTINGS_DEMAND = "doCreateMonCompteSettingsDemand";

    private MonCompteSettingDemand _demand = new MonCompteSettingDemand(  );

    /**
     * Returns the form to manage the MonCompteSettingsDemands
     *
     * @param request
     *            The Http request
     * @return the html form to manage MonCompteSettingsDemands
     */
    @View( value = VIEW_MANAGE_MONCOMPTE_SETTINGS_DEMAND, defaultView = true )
    public XPage getManageMoncompteSettingsDemands( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( Constants.PARAMETER_ID_APPLICATION ) );
        Application application = ApplicationHome.findByPrimaryKey( nId );
        MonCompteSettingsData dataSubset = ApplicationService.loadApplicationDataSubset( application, MonCompteSettingsData.DATA_SUBSET_NAME, MonCompteSettingsData.class );
        List listDemands = DemandHome.getDemandsListByApplicationAndType( nId, DEMAND_TYPE_KEY, MonCompteSettingDemand.class );
        List listDemandsData = DemandService.getDemandsListByApplicationAndType( application, DEMAND_TYPE_KEY, MonCompteSettingDemand.class );

        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION, application );
        model.put( Constants.MARK_DATA, dataSubset );
        model.put( MARK_LIST_DEMANDS, listDemands );
        model.put( MARK_LIST_DEMANDS_DATA, listDemandsData );

        return getXPage( TEMPLATE_MANAGE_MONCOMPTE_SETTINGS_DEMAND, request.getLocale( ), model );
    }

    /**
     * Returns the form to create a MonCompteSettingsDemand
     *
     * @param request
     *            The Http request
     * @return the html code of the MonCompteSettingsDemand form
     */
    @View( value = VIEW_CREATE_MONCOMPTE_SETTINGS_DEMAND )
    public XPage getCreateMoncompteSettingsDemands( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( Constants.PARAMETER_ID_APPLICATION ) );
        Application application = ApplicationHome.findByPrimaryKey( nId );

        Map<String, Object> model = getModel( );
        model.put( Constants.MARK_APPLICATION, application );
        model.put( MARK_ENVIRONMENT, ReferenceList.convert( Arrays.asList( Environment.values(  ) ), "prefix", "prefix", false ) );

        return getXPage( TEMPLATE_CREATE_MONCOMPTE_SETTINGS_DEMAND, request.getLocale( ), model );
    }

    @Action( ACTION_CREATE_MONCOMPTE_SETTINGS_DEMAND )
    public XPage doCreateMonCompteSettingsDemand( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( Constants.PARAMETER_ID_APPLICATION ) );
        Application application = ApplicationHome.findByPrimaryKey( nId );

        populate( _demand, request );

        // Check constraints
        if ( !validateBean( _demand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_CREATE_MONCOMPTE_SETTINGS_DEMAND );
        }
        
        _demand.setIdApplication( nId );
        _demand.setStatusText( MARK_STATUS_TEXT_CREATED );
        _demand.setDemandType( DEMAND_TYPE_KEY );
        _demand.setIdDemandType( DEMAND_TYPE_KEY );
        
        DemandService.saveDemand( _demand, application );

        return redirect( request, VIEW_MANAGE_MONCOMPTE_SETTINGS_DEMAND, Constants.PARAMETER_ID_APPLICATION, nId );
    }
}
