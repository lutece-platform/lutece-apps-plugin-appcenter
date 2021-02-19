/*
 * Copyright (c) 2002-2020, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.service.prerequisite;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandValidation;
import fr.paris.lutece.plugins.appcenter.business.DemandValidationHome;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManager;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManagerHome;
import fr.paris.lutece.plugins.appcenter.business.prerequisite.PrerequisiteValidationConfig;
import fr.paris.lutece.plugins.workflowcore.business.prerequisite.IPrerequisiteConfig;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.prerequisite.IAutomaticActionPrerequisiteService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;

public class PrerequisiteValidation implements IAutomaticActionPrerequisiteService
{
    public static final String PREREQUISITE_TITLE_I18N = "appcenter.validation.prerequisite_title";
    public static final String CONFIG_DAO_BEAN_NAME = "appcenter.validationPrerequisiteConfigDAO";
    private static final String TEMPLATE_VALIDATION_PREREQUISITE_CONFIG = "admin/plugins/appcenter/prerequisite_validation_config.html";
    private static final String MARK_CONFIG = "config";
    @Inject
    private IResourceHistoryService _resourceHistoryService;
    @Inject
    private IActionService _actionService;

    public String getPrerequisiteType( )
    {
        return PrerequisiteValidationConfig.PREREQUISITE_TYPE;
    }

    public String getTitleI18nKey( )
    {
        return PREREQUISITE_TITLE_I18N;
    }

    public boolean hasConfiguration( )
    {
        return true;
    }

    public IPrerequisiteConfig getEmptyConfiguration( )
    {
        return new PrerequisiteValidationConfig( );
    }

    public String getConfigurationDaoBeanName( )
    {
        return CONFIG_DAO_BEAN_NAME;
    }

    public String getConfigHtml( IPrerequisiteConfig config, HttpServletRequest request, Locale locale )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_CONFIG, config );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_VALIDATION_PREREQUISITE_CONFIG, locale, model );

        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canActionBePerformed( int nIdResource, String strResourceType, IPrerequisiteConfig config, int nIdAction )
    {
        int idWorkflow = _actionService.findByPrimaryKeyWithoutIcon( nIdAction ).getWorkflow( ).getId( );
        ResourceHistory resourceHistory = _resourceHistoryService.getLastHistoryResource( nIdResource, strResourceType, idWorkflow );

        if ( resourceHistory == null )
        {
            // The prerequisite is configured on an automatic action on the initial state without AutomaticReflexiveActions,
            // so we don't have a resourceHistory yet. Since state creation occurs only after a call
            // to getState (state creation is lazy), it is not very meaningful to have a validation
            // for the initial state anyway. So don't delay.
            //
            // Note: If the client forces the state creation by calling getState to have a coherent behavior,
            // it could also use an extra state and execute an action or use automatic actions, or even use
            // AutomaticReflexiveAction on the initial state to ensure the resourceHistory is created.
            return true;
        }

        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );
        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );
        OrganizationManager organizationManager = application.getOrganizationManager( );

        if ( organizationManager == null )
        {
            return false;
        }

        int nIdTask = ( (PrerequisiteValidationConfig) config ).getIdTask( );
        int nStatus = ( (PrerequisiteValidationConfig) config ).getStatus( );
        List<DemandValidation> lisDemandValidation = DemandValidationHome.findByDemand( nIdResource );

        for ( DemandValidation demandValidation : lisDemandValidation )
        {
            if ( demandValidation.getIdTask( ) == nIdTask )
            {
                return demandValidation.getIdUser( ).equalsIgnoreCase( organizationManager.getMail( ) ) && demandValidation.getStatus( ) == nStatus;
            }
        }

        return false;
    }
}