/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class provides Data Access methods for DemandType objects
 */
public final class DemandTypeDAO implements IDemandTypeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id, id_demand_type, id_workflow, java_class , label, description, question,  id_category_demand_type, n_order FROM appcenter_demand_type WHERE id = ?";
    private static final String SQL_QUERY_SELECT_BY_ID_DEMAND_TYPE = "SELECT id, id_demand_type, id_workflow , java_class , label, description, question,  id_category_demand_type, n_order FROM appcenter_demand_type WHERE id_demand_type = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_demand_type SELECT MAX(id+1), ?, ? ,? , ?,  ?, ?, ?,  COALESCE( MAX(n_order+1) , 1 )  FROM appcenter_demand_type ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_demand_type WHERE id = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_demand_type SET id = ?, id_demand_type = ?, id_workflow = ?, java_class = ?, label = ?, description = ?, question = ? , id_category_demand_type = ?, n_order = ? WHERE id = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT appcenter_demand_type.id, appcenter_demand_type.id_demand_type, appcenter_demand_type.id_workflow, appcenter_demand_type.java_class , appcenter_demand_type.label, appcenter_demand_type.description, appcenter_demand_type.question, appcenter_demand_type.id_category_demand_type, appcenter_demand_type.n_order, appcenter_documentation.id_documentation, appcenter_documentation.id_demand_type, appcenter_documentation.label, appcenter_documentation.url, appcenter_documentation.category FROM appcenter_demand_type LEFT JOIN appcenter_documentation ON appcenter_documentation.id_demand_type = appcenter_demand_type.id ORDER BY n_order";
    private static final String SQL_QUERY_SELECTALL_BY_ID_CATEGORY = "SELECT id, id_demand_type, id_workflow , java_class ,label, description, question , id_category_demand_type, n_order FROM appcenter_demand_type WHERE id_category_demand_type = ? ORDER BY n_order ";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id FROM appcenter_demand_type ORDER BY n_order";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( DemandType demandType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, demandType.getIdDemandType( ) );
            daoUtil.setInt( nIndex++, demandType.getIdWorkflow( ) );
            daoUtil.setString( nIndex++, demandType.getJavaClass( ) );
            daoUtil.setString( nIndex++, demandType.getLabel( ) );
            daoUtil.setString( nIndex++, demandType.getDescription( ) );
            daoUtil.setString( nIndex++, demandType.getQuestion( ) );
            daoUtil.setInt( nIndex++, demandType.getIdCategoryDemandType( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                demandType.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        finally
        {
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DemandType load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        DemandType demandType = null;

        if ( daoUtil.next( ) )
        {
            demandType = new DemandType( );
            int nIndex = 1;

            demandType.setId( daoUtil.getInt( nIndex++ ) );
            demandType.setIdDemandType( daoUtil.getString( nIndex++ ) );
            demandType.setIdWorkflow( daoUtil.getInt( nIndex++ ) );
            demandType.setJavaClass( daoUtil.getString( nIndex++ ) );
            demandType.setLabel( daoUtil.getString( nIndex++ ) );
            demandType.setDescription( daoUtil.getString( nIndex++ ) );
            demandType.setQuestion( daoUtil.getString( nIndex++ ) );
            demandType.setIdCategoryDemandType( daoUtil.getInt( nIndex++ ) );
            demandType.setOrder( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free( );
        return demandType;
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
    public void store( DemandType demandType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, demandType.getId( ) );
        daoUtil.setString( nIndex++, demandType.getIdDemandType( ) );
        daoUtil.setInt( nIndex++, demandType.getIdWorkflow( ) );
        daoUtil.setString( nIndex++, demandType.getJavaClass( ) );
        daoUtil.setString( nIndex++, demandType.getLabel( ) );
        daoUtil.setString( nIndex++, demandType.getDescription( ) );
        daoUtil.setString( nIndex++, demandType.getQuestion( ) );
        daoUtil.setInt( nIndex++, demandType.getIdCategoryDemandType( ) );
        daoUtil.setInt( nIndex++, demandType.getOrder( ) );
        daoUtil.setInt( nIndex, demandType.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DemandType> selectDemandTypesList( Plugin plugin )
    {
        List<DemandType> demandTypeList = new ArrayList<DemandType>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );
        while ( daoUtil.next( ) )
        {
            if ( !demandTypeList.stream( ).anyMatch( demType -> demType.getId( ) == daoUtil.getInt( 1 ) ) )
            {

                DemandType demandType = new DemandType( );
                demandType.setId( daoUtil.getInt( 1 ) );
                demandType.setIdDemandType( daoUtil.getString( 2 ) );
                demandType.setIdWorkflow( daoUtil.getInt( 3 ) );
                demandType.setJavaClass( daoUtil.getString( 4 ) );
                demandType.setLabel( daoUtil.getString( 5 ) );
                demandType.setDescription( daoUtil.getString( 6 ) );
                demandType.setQuestion( daoUtil.getString( 7 ) );
                demandType.setIdCategoryDemandType( daoUtil.getInt( 8 ) );
                demandType.setOrder( daoUtil.getInt( 9 ) );
                demandTypeList.add( demandType );
            }
            Documentation documentation = new Documentation( );
            documentation.setId( daoUtil.getInt( 10 ) );

            documentation.setIdDemandType( daoUtil.getInt( 11 ) );
            documentation.setLabel( daoUtil.getString( 12 ) );
            documentation.setUrl( daoUtil.getString( 13 ) );
            documentation.setCategory( daoUtil.getString( 14 ) );
            demandTypeList.stream( ).filter( demType -> demType.getId( ) == daoUtil.getInt( 1 ) ).forEach( demType -> {
                if ( documentation.getLabel( ) != null )
                {
                    demType.addDoc( documentation );
                }
            } );
        }

        daoUtil.free( );
        return demandTypeList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDemandTypesList( Plugin plugin )
    {
        List<Integer> demandTypeList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            demandTypeList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return demandTypeList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDemandTypesReferenceList( Plugin plugin )
    {
        ReferenceList demandTypeList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            demandTypeList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return demandTypeList;
    }

    @Override
    public List<DemandType> selectDemandTypesListByIdCategory( int nIdCategory, Plugin plugin )
    {
        List<DemandType> demandTypeList = new ArrayList<DemandType>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_ID_CATEGORY, plugin );
        daoUtil.setInt( 1, nIdCategory );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            DemandType demandType = new DemandType( );
            int nIndex = 1;

            demandType.setId( daoUtil.getInt( nIndex++ ) );
            demandType.setIdDemandType( daoUtil.getString( nIndex++ ) );
            demandType.setIdWorkflow( daoUtil.getInt( nIndex++ ) );
            demandType.setJavaClass( daoUtil.getString( nIndex++ ) );
            demandType.setLabel( daoUtil.getString( nIndex++ ) );
            demandType.setDescription( daoUtil.getString( nIndex++ ) );
            demandType.setQuestion( daoUtil.getString( nIndex++ ) );
            demandType.setIdCategoryDemandType( daoUtil.getInt( nIndex++ ) );
            demandType.setOrder( daoUtil.getInt( nIndex++ ) );

            demandTypeList.add( demandType );
        }

        daoUtil.free( );
        return demandTypeList;
    }

    @Override
    public DemandType loadByIdDemandType( String strIdDemandType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_DEMAND_TYPE, plugin );
        daoUtil.setString( 1, strIdDemandType );
        daoUtil.executeQuery( );
        DemandType demandType = null;

        if ( daoUtil.next( ) )
        {
            demandType = new DemandType( );
            int nIndex = 1;

            demandType.setId( daoUtil.getInt( nIndex++ ) );
            demandType.setIdDemandType( daoUtil.getString( nIndex++ ) );
            demandType.setIdWorkflow( daoUtil.getInt( nIndex++ ) );
            demandType.setJavaClass( daoUtil.getString( nIndex++ ) );
            demandType.setLabel( daoUtil.getString( nIndex++ ) );
            demandType.setDescription( daoUtil.getString( nIndex++ ) );
            demandType.setQuestion( daoUtil.getString( nIndex++ ) );
            demandType.setIdCategoryDemandType( daoUtil.getInt( nIndex++ ) );
            demandType.setOrder( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free( );
        return demandType;
    }
}
