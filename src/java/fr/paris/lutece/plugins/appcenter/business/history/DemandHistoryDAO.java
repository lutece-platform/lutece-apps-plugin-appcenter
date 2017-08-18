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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for DemandHistory objects
 */
public final class DemandHistoryDAO implements IDemandHistoryDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_history, id_user_front FROM appcenter_workflow_resource_history_demand WHERE id_demand_history = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_workflow_resource_history_demand ( id_history, id_user_front ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_workflow_resource_history_demand WHERE id_history = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_workflow_resource_history_demand SET id_history = ?, id_user_front = ? WHERE id_demand_history = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_history, id_user_front FROM appcenter_workflow_resource_history_demand";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_history FROM appcenter_workflow_resource_history_demand";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( DemandHistory resourceHistory, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, resourceHistory.getIdWorkflowHistory( ) );
        daoUtil.setInt( nIndex++, resourceHistory.getIdUserFront( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DemandHistory load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        DemandHistory resourceHistory = null;

        if ( daoUtil.next( ) )
        {
            resourceHistory = new DemandHistory( );
            int nIndex = 1;

            resourceHistory.setIdWorkflowHistory( daoUtil.getInt( nIndex++ ) );
            resourceHistory.setIdUserFront( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free( );
        return resourceHistory;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( DemandHistory resourceHistory, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, resourceHistory.getIdWorkflowHistory( ) );
        daoUtil.setInt( nIndex++, resourceHistory.getIdUserFront( ) );
        daoUtil.setInt( nIndex, resourceHistory.getIdWorkflowHistory( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DemandHistory> selectDemandHistorysList( Plugin plugin )
    {
        List<DemandHistory> resourceHistoryList = new ArrayList<DemandHistory>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            DemandHistory resourceHistory = new DemandHistory( );
            int nIndex = 1;

            resourceHistory.setIdWorkflowHistory( daoUtil.getInt( nIndex++ ) );
            resourceHistory.setIdUserFront( daoUtil.getInt( nIndex++ ) );

            resourceHistoryList.add( resourceHistory );
        }

        daoUtil.free( );
        return resourceHistoryList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDemandHistorysList( Plugin plugin )
    {
        List<Integer> resourceHistoryList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            resourceHistoryList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return resourceHistoryList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDemandHistorysReferenceList( Plugin plugin )
    {
        ReferenceList resourceHistoryList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            resourceHistoryList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return resourceHistoryList;
    }
}
