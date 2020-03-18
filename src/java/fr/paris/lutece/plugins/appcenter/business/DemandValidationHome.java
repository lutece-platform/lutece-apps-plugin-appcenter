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
package fr.paris.lutece.plugins.appcenter.business;

import fr.paris.lutece.plugins.appcenter.business.DemandValidation;
import fr.paris.lutece.plugins.appcenter.business.IDemandValidationDAO;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for DemandValidation objects
 */
public final class DemandValidationHome
{
    // Static variable pointed at the DAO instance
    private static IDemandValidationDAO _dao = SpringContextService.getBean( "appcenter.demandValidationDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );


    /**
     * Private constructor - this class need not be instantiated
     */
    private DemandValidationHome(  )
    {
    }

    /**
     * Create an instance of the demandValidation class
     * @param demandValidation The instance of the DemandValidation which contains the informations to store
     * @return The  instance of demandValidation which has been created with its primary key.
     */
    public static DemandValidation create( DemandValidation demandValidation )
    {
        _dao.insert( demandValidation, _plugin );

        return demandValidation;
    }

    /**
     * Update of the demandValidation which is specified in parameter
     * @param demandValidation The instance of the DemandValidation which contains the data to store
     * @return The instance of the  demandValidation which has been updated
     */
    public static DemandValidation update( DemandValidation demandValidation )
    {
        _dao.store( demandValidation, _plugin );

        return demandValidation;
    }

    /**
     * Remove the demandValidation whose identifier is specified in parameter
     * @param nId The demandValidation Id
     */
    public static void remove( int nId )
    {
        _dao.delete( nId, _plugin );
    }

    /**
     * Returns an instance of a demandValidation whose identifier are specified in parameter
     * @param nId The demandValidation id
     * @return an instance of DemandValidation
     */
    public static DemandValidation findByPrimaryKey( int nId )
    {
        return _dao.load( nId, _plugin);
    }

    /**
     * Returns an instance of a demandValidation whose identifiers are specified in parameter
     * @param nIdDemand The demand id
     * @return an instance of DemandValidation
     */
    public static List<DemandValidation> findByDemand( int nIdDemand )
    {
        return _dao.loadByDemand( nIdDemand, _plugin );
    }

    /**
     * Load the data of all the demandValidation objects and returns them in form of a list
     * @return the list which contains the data of all the demandValidation objects
     */
    public static List<DemandValidation> getDemandValidationsList( )
    {
        return _dao.selectDemandValidationsList( _plugin );
    }
}