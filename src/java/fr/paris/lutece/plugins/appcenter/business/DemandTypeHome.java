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
package fr.paris.lutece.plugins.appcenter.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for DemandType objects
 */
public final class DemandTypeHome
{
    // Static variable pointed at the DAO instance
    private static IDemandTypeDAO _dao = SpringContextService.getBean( "appcenter.demandTypeDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DemandTypeHome( )
    {
    }

    /**
     * Create an instance of the demandType class
     * 
     * @param demandType
     *            The instance of the DemandType which contains the informations to store
     * @return The instance of demandType which has been created with its primary key.
     */
    public static DemandType create( DemandType demandType )
    {
        _dao.insert( demandType, _plugin );

        return demandType;
    }

    /**
     * Update of the demandType which is specified in parameter
     * 
     * @param demandType
     *            The instance of the DemandType which contains the data to store
     * @return The instance of the demandType which has been updated
     */
    public static DemandType update( DemandType demandType )
    {
        _dao.store( demandType, _plugin );

        return demandType;
    }

    /**
     * Remove the demandType whose identifier is specified in parameter
     * 
     * @param nKey
     *            The demandType Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a demandType whose identifier is specified in parameter
     * 
     * @param nKey
     *            The demandType primary key
     * @return an instance of DemandType
     */
    public static DemandType findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns an instance of a demandType whose demand type is specified in parameter
     * 
     * @param strIdDemandType
     *            the demand type
     * @return an instance of DemandType
     */
    public static DemandType findByIdDemandType( String strIdDemandType )
    {
        return _dao.loadByIdDemandType( strIdDemandType, _plugin );
    }

    /**
     * Load the data of all the demandType objects and returns them as a list
     * 
     * @return the list which contains the data of all the demandType objects
     */
    public static List<DemandType> getDemandTypesList( )
    {
        return _dao.selectDemandTypesList( _plugin );
    }

    /**
     * Load the id of all the demandType objects and returns them as a list
     * 
     * @return the list which contains the id of all the demandType objects
     */
    public static List<Integer> getIdDemandTypesList( )
    {
        return _dao.selectIdDemandTypesList( _plugin );
    }

    /**
     * Load the id of all the demandType objects and returns them as a list by id category
     * 
     * @param nIdCategory
     * @return the list which contains the id of all the demandType objects
     */
    public static List<DemandType> getDemandTypesListByIdCategory( int nIdCategory )
    {
        return _dao.selectDemandTypesListByIdCategory( nIdCategory, _plugin );
    }

    /**
     * Load the data of all the demandType objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the demandType objects
     */
    public static ReferenceList getDemandTypesReferenceList( )
    {
        return _dao.selectDemandTypesReferenceList( _plugin );
    }

}
