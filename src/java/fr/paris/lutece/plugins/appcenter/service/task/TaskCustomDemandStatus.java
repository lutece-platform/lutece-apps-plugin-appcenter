/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.service.task;

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.task.CustomDemandStatusTaskConfig;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.config.TaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.Locale;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class TaskCustomDemandStatus extends SimpleTask
{
    private final TaskConfigService _taskConfigService = SpringContextService.getBean( "appcenter.taskCustomDemandStatusConfigService" );

    // SERVICES
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public String getTitle( Locale locale )
    {
        // TODO
        return "CustomDemandStatusText";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );

        // get the demand
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );

        // get the task config
        CustomDemandStatusTaskConfig config = _taskConfigService.findByPrimaryKey( getId( ) );
        demand.setStatusText( config.getCustomDemandStatus( ) );

        // Save the demand
        DemandHome.update( demand );
    }
}
