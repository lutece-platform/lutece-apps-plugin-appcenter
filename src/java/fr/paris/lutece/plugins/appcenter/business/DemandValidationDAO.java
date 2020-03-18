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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for DemandValidation objects
 */
public final class DemandValidationDAO implements IDemandValidationDAO
{
    // Constants
    private static final String SQL_QUERY_SELECTALL = "SELECT id, id_demand, id_task, status, id_user FROM appcenter_demand_validation";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECTALL + " WHERE id = ?";
    private static final String SQL_QUERY_SELECT_BY_DEMAND = SQL_QUERY_SELECTALL + " WHERE id_demand = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_demand_validation ( id_demand, id_task, status, id_user ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_demand_validation WHERE id = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_demand_validation SET status = ?, id_user = ? WHERE id = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( DemandValidation demandValidation, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT , plugin );

        daoUtil.setInt( 1, demandValidation.getIdDemand(  ) );
        daoUtil.setInt( 2, demandValidation.getIdTask(  ) );
        daoUtil.setInt( 3, demandValidation.getStatus(  ) );
        daoUtil.setString( 4, demandValidation.getIdUser(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DemandValidation load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery(  );

        DemandValidation demandValidation = null;

        if ( daoUtil.next(  ) )
        {
            demandValidation = new DemandValidation(  );
            int nIndex = 0;
            demandValidation.setId( daoUtil.getInt( ++nIndex ) );
            demandValidation.setIdDemand( daoUtil.getInt( ++nIndex ) );
            demandValidation.setIdTask( daoUtil.getInt( ++nIndex ) );
            demandValidation.setStatus( daoUtil.getInt( ++nIndex ) );
            demandValidation.setIdUser( daoUtil.getString( ++nIndex ) );
        }

        daoUtil.free(  );

        return demandValidation;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DemandValidation> loadByDemand( int nIdDemand, Plugin plugin )
    {
        List<DemandValidation> listDemandValidations = new ArrayList<>(  );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_DEMAND, plugin );
        daoUtil.setInt( 1 , nIdDemand );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            DemandValidation demandValidation = new DemandValidation(  );
            int nIndex = 0;
            demandValidation.setId( daoUtil.getInt( ++nIndex ) );
            demandValidation.setIdDemand( daoUtil.getInt( ++nIndex ) );
            demandValidation.setIdTask( daoUtil.getInt( ++nIndex ) );
            demandValidation.setStatus( daoUtil.getInt( ++nIndex ) );
            demandValidation.setIdUser( daoUtil.getString( ++nIndex ) );

            listDemandValidations.add( demandValidation );
        }

        daoUtil.free(  );

        return listDemandValidations;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( DemandValidation demandValidation, Plugin plugin )
    {
            DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

            int nIndex = 0;
            daoUtil.setInt( ++nIndex, demandValidation.getIdDemand(  ) );
            daoUtil.setInt( ++nIndex, demandValidation.getIdTask(  ) );
            daoUtil.setInt( ++nIndex, demandValidation.getStatus(  ) );
            daoUtil.setString( ++nIndex, demandValidation.getIdUser(  ) );
            daoUtil.setInt( ++nIndex, demandValidation.getId(  ) );

            daoUtil.executeUpdate(  );
            daoUtil.free(  );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DemandValidation> selectDemandValidationsList( Plugin plugin )
    {
        List<DemandValidation> listDemandValidations = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            DemandValidation demandValidation = new DemandValidation(  );

            demandValidation.setId( daoUtil.getInt( 1 ) );
            demandValidation.setIdDemand( daoUtil.getInt( 2 ) );
            demandValidation.setIdTask( daoUtil.getInt( 3 ) );
            demandValidation.setStatus( daoUtil.getInt( 4 ) );
            demandValidation.setIdUser( daoUtil.getString( 5 ) );

            listDemandValidations.add( demandValidation );
        }

        daoUtil.free(  );

        return listDemandValidations;
    }
}