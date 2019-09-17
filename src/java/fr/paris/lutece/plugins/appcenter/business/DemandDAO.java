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

import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paris.lutece.plugins.appcenter.service.DemandTypeService;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * This class provides Data Access methods for Demand objects
 */
public final class DemandDAO implements IDemandDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_demand, id_user_front ,status_text, id_demand_type, demand_type, id_application, demand_content,creation_date, is_closed, environment FROM appcenter_demand WHERE id_demand = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_demand ( id_user_front, status_text, id_demand_type,  demand_type, id_application, demand_content,creation_date,is_closed, environment ) VALUES ( ?, ?, ?, ?, ? , ?, ?, ?, ?) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_demand WHERE id_demand = ? ";

    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_demand SET  id_demand = ?, id_user_front = ? ,status_text = ?, id_demand_type = ?, demand_type = ?, id_application = ?, demand_content = ?, creation_date = ?, is_closed = ?, environment = ? WHERE id_demand = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_demand, id_user_front, status_text, id_demand_type, demand_type, id_application, demand_content,creation_date, is_closed, environment FROM appcenter_demand";
    private static final String SQL_QUERY_SELECTALL_BY_APPLICATION = SQL_QUERY_SELECTALL + " where id_application = ? ";
    private static final String SQL_QUERY_SELECTALL_BY_APPLICATION_AND_TYPE = SQL_QUERY_SELECTALL_BY_APPLICATION + " and id_demand_type = ? ";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_demand FROM appcenter_demand";

    // Constants
    private static final String CONSTANT_WHERE = " WHERE ";
    private static final String CONSTANT_AND = " AND ";
    private static final String CONSTANT_ORDER_BY = " ORDER BY";

    private static final String CONSTANT_WHERE_ENVIRONMENT = " environment = ? ";
    private static final String CONSTANT_WHERE_ID_APPLICATION = " id_application = ? ";
    private static final String CONSTANT_WHERE_ID_DEMAND_TYPE = " id_demand_type = ? ";
    private static final String CONSTANT_WHERE_IS_CLOSED = " is_closed = ? ";
    private static final String CONSTANT_ORDER_BY_CREATION_DATE = " creation_date DESC";

    private static ObjectMapper _mapper = new ObjectMapper( );

    static
    {
        _mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Demand demand, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        int nIndex = 1;

        daoUtil.setString( nIndex++, demand.getIdUserFront( ) );
        daoUtil.setString( nIndex++, demand.getStatusText( ) );
        daoUtil.setString( nIndex++, demand.getIdDemandType( ) );
        daoUtil.setString( nIndex++, demand.getDemandType( ) );
        daoUtil.setInt( nIndex++, demand.getIdApplication( ) );
        daoUtil.setString( nIndex++, demand.getDemandData( ) );
        daoUtil.setTimestamp( nIndex++, demand.getCreationDate( ) );
        daoUtil.setBoolean( nIndex++, demand.isClosed( ) );
        if ( demand.getEnvironment( ) != null )
        {
            daoUtil.setString( nIndex++, demand.getEnvironment( ).getPrefix( ) );
        }
        else
        {
            daoUtil.setString( nIndex++, "" );
        }

        daoUtil.executeUpdate( );
        daoUtil.nextGeneratedKey( );

        demand.setId( daoUtil.getGeneratedKeyInt( 1 ) );

        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Demand load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        Demand demand = null;

        if ( daoUtil.next( ) )
        {
            // TODO use finally for free because this throws
            demand = getRow( daoUtil );
        }

        daoUtil.free( );
        return demand;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T extends Demand> T load( int nKey, Class<T> demandClass, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        T demand = null;

        if ( daoUtil.next( ) )
        {
            // TODO use finally for free because this throws
            demand = getRow( daoUtil, demandClass );
        }

        daoUtil.free( );
        return demand;
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
    public void store( Demand demand, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, demand.getId( ) );
        daoUtil.setString( nIndex++, demand.getIdUserFront( ) );
        daoUtil.setString( nIndex++, demand.getStatusText( ) );
        daoUtil.setString( nIndex++, demand.getIdDemandType( ) );
        daoUtil.setString( nIndex++, demand.getDemandType( ) );
        daoUtil.setInt( nIndex++, demand.getIdApplication( ) );
        daoUtil.setString( nIndex++, demand.getDemandData( ) );
        daoUtil.setTimestamp( nIndex++, demand.getCreationDate( ) );
        daoUtil.setBoolean( nIndex++, demand.isClosed( ) );
        if ( demand.getEnvironment( ) != null )
        {
            daoUtil.setString( nIndex++, demand.getEnvironment( ).getPrefix( ) );
        }
        else
        {
            daoUtil.setString( nIndex++, "" );
        }

        daoUtil.setInt( nIndex, demand.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Demand> selectDemandsList( Plugin plugin )
    {
        List<Demand> demandList = new ArrayList<Demand>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            // TODO use finally for free because this throws
            Demand demand = getRow( daoUtil );

            demandList.add( demand );
        }

        daoUtil.free( );
        return demandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Demand> selectDemandsListByApplication( int nIdApplication, Plugin plugin )
    {
        List<Demand> demandList = new ArrayList<Demand>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_APPLICATION, plugin );
        int nIndex = 1;
        daoUtil.setInt( nIndex++, nIdApplication );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            // TODO use finally for free because this throws
            Demand demand = getRow( daoUtil );

            demandList.add( demand );
        }

        daoUtil.free( );
        return demandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T extends Demand> List<T> selectDemandsListByApplicationAndType( int nIdApplication, String strDemandType, Class<T> demandClass, Plugin plugin )
    {
        List<T> demandList = new ArrayList<T>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_APPLICATION_AND_TYPE, plugin );
        int nIndex = 1;
        daoUtil.setInt( nIndex++, nIdApplication );
        daoUtil.setString( nIndex++, strDemandType );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            // TODO use finally for free because this throws
            T demand = getRow( daoUtil, demandClass );

            demandList.add( demand );
        }

        daoUtil.free( );
        return demandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDemandsList( Plugin plugin )
    {
        List<Integer> demandList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            demandList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return demandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDemandsReferenceList( Plugin plugin )
    {
        ReferenceList demandList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            demandList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return demandList;
    }

    private Demand getRow( DAOUtil daoUtil )
    {
        return getRow( daoUtil, Demand.class );
    }

    private <T extends Demand> T getRow( DAOUtil daoUtil, Class<T> demandClass )
    {
        try
        {
            T demand = _mapper.readValue( daoUtil.getString( 7 ), demandClass );
            int nIndex = 1;
            demand.setId( daoUtil.getInt( nIndex++ ) );
            demand.setIdUserFront( daoUtil.getString( nIndex++ ) );
            demand.setStatusText( daoUtil.getString( nIndex++ ) );
            demand.setIdDemandType( daoUtil.getString( nIndex++ ) );
            demand.setDemandType( daoUtil.getString( nIndex++ ) );
            demand.setIdApplication( daoUtil.getInt( nIndex++ ) );
            demand.setDemandData( daoUtil.getString( nIndex++ ) );
            demand.setCreationDate( daoUtil.getTimestamp( nIndex++ ) );
            demand.setIsClosed( daoUtil.getBoolean( nIndex++ ) );
            demand.setEnvironment( Environment.getEnvironment( daoUtil.getString( nIndex++ ) ) );
            return demand;
        }
        catch( IOException e )
        {
            AppLogService.error( "Unable to convert demand data to obj ", e );
            throw new RuntimeException( e );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T extends Demand> List<T> selectListFullDemands( int nIdApplication, List<DemandType> listDemandType, Plugin plugin )
    {
        List<T> demandList = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_APPLICATION, plugin );
        daoUtil.setInt( 1, nIdApplication );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            try
            {
                // TODO use finally for free because this throws
                String strDemandData = daoUtil.getString( 7 );
                String strDemandType = daoUtil.getString( 4 );
                T demand = (T) _mapper.readValue( strDemandData, DemandTypeService.getClassByDemandTypeId( strDemandType, listDemandType ) );
                demand.setId( daoUtil.getInt( 1 ) );
                demand.setIdUserFront( daoUtil.getString( 2 ) );
                demand.setStatusText( daoUtil.getString( 3 ) );
                demand.setIdDemandType( strDemandType );
                demand.setDemandType( daoUtil.getString( 5 ) );
                demand.setIdApplication( daoUtil.getInt( 6 ) );
                demand.setDemandData( strDemandData );
                demand.setCreationDate( daoUtil.getTimestamp( 8 ) );
                demand.setIsClosed( daoUtil.getBoolean( 9 ) );
                demand.setEnvironment( Environment.getEnvironment( daoUtil.getString( 10 ) ) );

                demandList.add( demand );
            }
            catch( IOException e )
            {
                AppLogService.error( "Unable to parse demand data JSON to demand type " );
            }
        }

        daoUtil.free( );
        return demandList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Demand> selectDemandsListByFilter( DemandFilter filter, Plugin plugin )
    {
        List<Demand> demandList = new ArrayList<Demand>( );
        StringBuilder strSqlQuery = new StringBuilder( SQL_QUERY_SELECTALL );

        if ( filter.hasEnvironmentPrefix( ) || filter.hasIdApplication( ) || filter.hasIdDemandType( ) || filter.hasIsClosed( ) )
        {
            strSqlQuery.append( CONSTANT_WHERE );
        }
        boolean bFirstFilterStatement = false;
        if ( filter.hasEnvironmentPrefix( ) )
        {
            strSqlQuery.append( CONSTANT_WHERE_ENVIRONMENT );
            bFirstFilterStatement = true;
        }
        if ( filter.hasIdApplication( ) )
        {
            if ( bFirstFilterStatement )
            {
                strSqlQuery.append( CONSTANT_AND );
            }
            strSqlQuery.append( CONSTANT_WHERE_ID_APPLICATION );
            bFirstFilterStatement = true;
        }
        if ( filter.hasIdDemandType( ) )
        {
            if ( bFirstFilterStatement )
            {
                strSqlQuery.append( CONSTANT_AND );
            }
            strSqlQuery.append( CONSTANT_WHERE_ID_DEMAND_TYPE );
            bFirstFilterStatement = true;
        }
        if ( filter.hasIsClosed( ) )
        {
            if ( bFirstFilterStatement )
            {
                strSqlQuery.append( CONSTANT_AND );
            }
            strSqlQuery.append( CONSTANT_WHERE_IS_CLOSED );
        }

        strSqlQuery.append( CONSTANT_ORDER_BY );
        strSqlQuery.append( CONSTANT_ORDER_BY_CREATION_DATE );

        DAOUtil daoUtil = new DAOUtil( strSqlQuery.toString( ), plugin );
        int nIndex = 1;
        if ( filter.hasEnvironmentPrefix( ) )
        {
            daoUtil.setString( nIndex++, filter.getEnvironmentPrefix( ) );

        }
        if ( filter.hasIdApplication( ) )
        {
            daoUtil.setInt( nIndex++, filter.getIdApplication( ) );
        }
        if ( filter.hasIdDemandType( ) )
        {
            daoUtil.setString( nIndex++, filter.getIdDemandType( ) );
        }
        if ( filter.hasIsClosed( ) )
        {
            daoUtil.setBoolean( nIndex++, filter.isClosed( ) );
        }

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Demand demand = getRow( daoUtil );

            demandList.add( demand );
        }

        daoUtil.free( );
        return demandList;
    }
}
