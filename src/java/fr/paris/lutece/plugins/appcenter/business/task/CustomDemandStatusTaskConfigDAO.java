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
package fr.paris.lutece.plugins.appcenter.business.task;

import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * This class provides Data Access methods for CustomDemandStatusTaskConfig objects
 */
public class CustomDemandStatusTaskConfigDAO implements ITaskConfigDAO<CustomDemandStatusTaskConfig>
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_task ) FROM appcenter_task_custom_demand_status_config";
    private static final String SQL_QUERY_SELECT = "SELECT id_task, custom_demand_status FROM appcenter_task_custom_demand_status_config WHERE id_task = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_task_custom_demand_status_config ( id_task, custom_demand_status ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_task_custom_demand_status_config WHERE id_task = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_task_custom_demand_status_config SET id_task = ?, custom_demand_status = ? WHERE id_task = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( CustomDemandStatusTaskConfig customDemandStatusTaskConfig )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );
        int nIndex = 0;
        daoUtil.setInt( ++nIndex, customDemandStatusTaskConfig.getIdTask( ) );
        daoUtil.setString( ++nIndex, customDemandStatusTaskConfig.getCustomDemandStatus( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public CustomDemandStatusTaskConfig load( int nId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery( );

        CustomDemandStatusTaskConfig customDemandStatusTaskConfig = null;

        if ( daoUtil.next( ) )
        {
            int nIndex = 0;
            customDemandStatusTaskConfig = new CustomDemandStatusTaskConfig( );
            customDemandStatusTaskConfig.setIdTask( daoUtil.getInt( ++nIndex ) );
            customDemandStatusTaskConfig.setCustomDemandStatus( daoUtil.getString( ++nIndex ) );
        }

        daoUtil.free( );

        return customDemandStatusTaskConfig;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nCustomDemandStatusTaskConfigId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE );
        daoUtil.setInt( 1, nCustomDemandStatusTaskConfigId );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( CustomDemandStatusTaskConfig customDemandStatusTaskConfig )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE );
        int nIndex = 0;
        daoUtil.setInt( ++nIndex, customDemandStatusTaskConfig.getIdTask( ) );
        daoUtil.setString( ++nIndex, customDemandStatusTaskConfig.getCustomDemandStatus( ) );
        daoUtil.setInt( ++nIndex, customDemandStatusTaskConfig.getIdTask( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }
}
