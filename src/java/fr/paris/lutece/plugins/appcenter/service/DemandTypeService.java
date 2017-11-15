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
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationDemand;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDemand;
import fr.paris.lutece.plugins.appcenter.modules.jobs.business.JobDemand;
import fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business.MonCompteSettingDemand;
import fr.paris.lutece.plugins.appcenter.modules.notificationgru.business.NotificationGruDemand;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamDemand;
import fr.paris.lutece.plugins.appcenter.modules.sources.business.SourcesDemand;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

public class DemandTypeService
{
    private static final String ID_WORKFLOW_DEMAND_TYPE_PREFIX = "idWorkflow.demandType.";

    public static int getIdWorkflow( String strDemandTypeKey )
    {
        int nIdWorkflow = AppPropertiesService.getPropertyInt( ID_WORKFLOW_DEMAND_TYPE_PREFIX + strDemandTypeKey, -1 );
        return nIdWorkflow;
    }

    public static String getWorkflowResourceType( String strDemandTypeKey )
    {
        return Demand.WORKFLOW_RESOURCE_TYPE;
    }
    
    public static Class getClassByDemandTypeId( String strDemandTypeId )
    {
        Class classObj = Demand.class;
        
         switch (strDemandTypeId) 
         {
            case "sources":  
                classObj = SourcesDemand.class;
                     break;
            case "identitystore":  
                classObj = IdentitystoreDemand.class;
                     break;
            case "openam":  
                classObj = OpenamDemand.class;
                     break;
            case "fastdeployapplication":
                classObj = FastDeployApplicationDemand.class;
                     break;
            case "jobs":
                classObj = JobDemand.class;
                     break;
            case "moncomptesettings":
                classObj = MonCompteSettingDemand.class;
                     break;
            case "notificationgru":
                classObj = NotificationGruDemand.class;
        }
        return classObj;
    }
    
}
