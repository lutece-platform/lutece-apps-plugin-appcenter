/*
 * Copyright (c) 2002-2016, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.business.history;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for DemandHistory objects
 */
public final class DemandHistoryHome
{
    // Static variable pointed at the DAO instance
    private static IDemandHistoryDAO _dao = SpringContextService.getBean( "appcenter.resourceHistoryDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DemandHistoryHome( )
    {
    }

    /**
     * Create an instance of the resourceHistory class
     * 
     * @param resourceHistory
     *            The instance of the DemandHistory which contains the informations to store
     * @return The instance of resourceHistory which has been created with its primary key.
     */
    public static DemandHistory create( DemandHistory resourceHistory )
    {
        _dao.insert( resourceHistory, _plugin );

        return resourceHistory;
    }

    /**
     * Update of the resourceHistory which is specified in parameter
     * 
     * @param resourceHistory
     *            The instance of the DemandHistory which contains the data to store
     * @return The instance of the resourceHistory which has been updated
     */
    public static DemandHistory update( DemandHistory resourceHistory )
    {
        _dao.store( resourceHistory, _plugin );

        return resourceHistory;
    }

    /**
     * Remove the resourceHistory whose identifier is specified in parameter
     * 
     * @param nKey
     *            The resourceHistory Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a resourceHistory whose identifier is specified in parameter
     * 
     * @param nKey
     *            The resourceHistory primary key
     * @return an instance of DemandHistory
     */
    public static DemandHistory findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the resourceHistory objects and returns them as a list
     * 
     * @return the list which contains the data of all the resourceHistory objects
     */
    public static List<DemandHistory> getDemandHistorysList( )
    {
        return _dao.selectDemandHistorysList( _plugin );
    }

    /**
     * Load the id of all the resourceHistory objects and returns them as a list
     * 
     * @return the list which contains the id of all the resourceHistory objects
     */
    public static List<Integer> getIdDemandHistorysList( )
    {
        return _dao.selectIdDemandHistorysList( _plugin );
    }

    /**
     * Load the data of all the resourceHistory objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the resourceHistory objects
     */
    public static ReferenceList getDemandHistorysReferenceList( )
    {
        return _dao.selectDemandHistorysReferenceList( _plugin );
    }
}
