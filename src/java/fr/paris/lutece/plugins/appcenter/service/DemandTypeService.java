/*
 * Copyright (c) 2002-2019, Mairie de Paris
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
import fr.paris.lutece.plugins.appcenter.business.DemandType;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DemandTypeService
{

    private static String KEY_SUPER_ADMIN_ROLE = "super_admin";

    public static int getIdWorkflow( String strDemandTypeKey )
    {
        return DemandTypeHome.findByIdDemandType( strDemandTypeKey ).getIdWorkflow( );
    }

    public static String getWorkflowResourceType( String strDemandTypeKey )
    {
        return Demand.WORKFLOW_RESOURCE_TYPE;
    }

    public static Class getClassByDemandTypeId( String strDemandTypeId, List<DemandType> listDemandType )
    {
        Optional<DemandType> filteredDemandType = listDemandType.stream( ).filter( demandType -> demandType.getIdDemandType( ).equals( strDemandTypeId ) )
                .findAny( );

        if ( filteredDemandType.isPresent( ) )
        {
            try
            {
                Class className = Class.forName( filteredDemandType.get( ).getJavaClass( ) );
                return className;
            }
            catch( ClassNotFoundException e )
            {
                AppLogService.error( "Unable to find class", e );
                return null;
            }
        }
        return null;
    }

    /**
     * Filter the list of all demands and the filter with RBAC authorizations
     * 
     * @param listDemands
     * @param demandTypeRefList
     * @param user
     */
    public static void filterWithRBAC( List<Demand> listDemands, ReferenceList demandTypeRefList, AdminUser user )
    {
        List<DemandType> listDemandTypes = DemandTypeHome.getDemandTypesList( );

        if ( !RBACService.isUserInRole( user, KEY_SUPER_ADMIN_ROLE ) )
        {
            Collection<DemandType> listAuthorizedDemandType = RBACService.getAuthorizedCollection( listDemandTypes,
                    DemandTypeIdService.DEMAND_TYPE_PERMISSION_VIEW, user );

            listDemands.removeIf( demand -> {
                for ( DemandType demType : listAuthorizedDemandType )
                {
                    if ( demType.getIdDemandType( ).equals( demand.getDemandType( ) ) )
                    {
                        return false;
                    }
                }
                return true;
            } );

            demandTypeRefList.removeIf( item -> {
                for ( DemandType demType : listAuthorizedDemandType )
                {
                    if ( demType.getIdDemandType( ).equals( item.getCode( ) ) )
                    {
                        return false;
                    }
                }
                return true;
            } );
        }

    }

}
