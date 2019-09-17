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

/**
 * This class provides Data Access methods for CategoryDemandType objects
 */
public final class CategoryDemandTypeDAO implements ICategoryDemandTypeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id, label, question, is_depending_of_environment, n_order FROM appcenter_category_demand_type WHERE id = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_category_demand_type SELECT MAX(id+1), ?, ? , ?,  COALESCE( MAX(n_order+1) )  FROM appcenter_category_demand_type ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_category_demand_type WHERE id = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_category_demand_type SET id = ?, label = ?, question = ? , is_depending_of_environment = ?, n_order = ? WHERE id = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id, label, question, is_depending_of_environment, n_order FROM appcenter_category_demand_type ORDER BY n_order";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id FROM appcenter_category_demand_type ORDER BY n_order";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( CategoryDemandType categoryDemandType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, categoryDemandType.getLabel( ) );
            daoUtil.setString( nIndex++, categoryDemandType.getQuestion( ) );
            daoUtil.setBoolean( nIndex++, categoryDemandType.getIsDependingOfEnvironment( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                categoryDemandType.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public CategoryDemandType load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        CategoryDemandType categoryDemandType = null;

        if ( daoUtil.next( ) )
        {
            categoryDemandType = new CategoryDemandType( );
            int nIndex = 1;

            categoryDemandType.setId( daoUtil.getInt( nIndex++ ) );
            categoryDemandType.setLabel( daoUtil.getString( nIndex++ ) );
            categoryDemandType.setQuestion( daoUtil.getString( nIndex++ ) );
            categoryDemandType.setIsDependingOfEnvironment( daoUtil.getBoolean( nIndex++ ) );
            categoryDemandType.setOrder( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free( );
        return categoryDemandType;
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
    public void store( CategoryDemandType categoryDemandType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, categoryDemandType.getId( ) );
        daoUtil.setString( nIndex++, categoryDemandType.getLabel( ) );
        daoUtil.setString( nIndex++, categoryDemandType.getQuestion( ) );
        daoUtil.setBoolean( nIndex++, categoryDemandType.getIsDependingOfEnvironment( ) );
        daoUtil.setInt( nIndex++, categoryDemandType.getOrder( ) );
        daoUtil.setInt( nIndex, categoryDemandType.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<CategoryDemandType> selectCategoryDemandTypesList( Plugin plugin )
    {
        List<CategoryDemandType> categoryDemandTypeList = new ArrayList<CategoryDemandType>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            CategoryDemandType categoryDemandType = new CategoryDemandType( );
            int nIndex = 1;

            categoryDemandType.setId( daoUtil.getInt( nIndex++ ) );
            categoryDemandType.setLabel( daoUtil.getString( nIndex++ ) );
            categoryDemandType.setQuestion( daoUtil.getString( nIndex++ ) );
            categoryDemandType.setIsDependingOfEnvironment( daoUtil.getBoolean( nIndex++ ) );
            categoryDemandType.setOrder( daoUtil.getInt( nIndex++ ) );

            categoryDemandTypeList.add( categoryDemandType );
        }

        daoUtil.free( );
        return categoryDemandTypeList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdCategoryDemandTypesList( Plugin plugin )
    {
        List<Integer> categoryDemandTypeList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            categoryDemandTypeList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return categoryDemandTypeList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectCategoryDemandTypesReferenceList( Plugin plugin )
    {
        ReferenceList categoryDemandTypeList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            categoryDemandTypeList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return categoryDemandTypeList;
    }
}
