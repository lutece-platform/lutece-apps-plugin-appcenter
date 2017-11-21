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

package fr.paris.lutece.plugins.appcenter.modules.guichetpro.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for DemandTypeCategory objects
 */
public final class DemandTypeCategoryDAO implements IDemandTypeCategoryDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_demand_type_category, name, description FROM appcenter_demand_type_category WHERE id_demand_type_category = ?";
    private static final String SQL_QUERY_SELECT_BY_KEY = "SELECT id_demand_type_category, name, description FROM appcenter_demand_type_category WHERE key_name = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_demand_type_category ( name, description ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_demand_type_category WHERE id_demand_type_category = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_demand_type_category SET id_demand_type_category = ?, name = ?, description = ? WHERE id_demand_type_category = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_demand_type_category, name, description FROM appcenter_demand_type_category";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_demand_type_category FROM appcenter_demand_type_category";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( DemandTypeCategory demandTypeCategory, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , demandTypeCategory.getName( ) );
            daoUtil.setString( nIndex++ , demandTypeCategory.getDescription( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                demandTypeCategory.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public DemandTypeCategory load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        DemandTypeCategory demandTypeCategory = null;

        if ( daoUtil.next( ) )
        {
            demandTypeCategory = new DemandTypeCategory();
            int nIndex = 1;
            
            demandTypeCategory.setId( daoUtil.getInt( nIndex++ ) );
            demandTypeCategory.setName( daoUtil.getString( nIndex++ ) );
            demandTypeCategory.setDescription( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return demandTypeCategory;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( DemandTypeCategory demandTypeCategory, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , demandTypeCategory.getId( ) );
        daoUtil.setString( nIndex++ , demandTypeCategory.getName( ) );
        daoUtil.setString( nIndex++ , demandTypeCategory.getDescription( ) );
        daoUtil.setInt( nIndex , demandTypeCategory.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DemandTypeCategory> selectDemandTypeCategoriesList( Plugin plugin )
    {
        List<DemandTypeCategory> demandTypeCategoryList = new ArrayList<DemandTypeCategory>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            DemandTypeCategory demandTypeCategory = new DemandTypeCategory(  );
            int nIndex = 1;
            
            demandTypeCategory.setId( daoUtil.getInt( nIndex++ ) );
            demandTypeCategory.setName( daoUtil.getString( nIndex++ ) );
            demandTypeCategory.setDescription( daoUtil.getString( nIndex++ ) );

            demandTypeCategoryList.add( demandTypeCategory );
        }

        daoUtil.free( );
        return demandTypeCategoryList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDemandTypeCategoriesList( Plugin plugin )
    {
        List<Integer> demandTypeCategoryList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            demandTypeCategoryList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return demandTypeCategoryList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDemandTypeCategoriesReferenceList( Plugin plugin )
    {
        ReferenceList demandTypeCategoryList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            demandTypeCategoryList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return demandTypeCategoryList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DemandTypeCategory loadByDemandTypeCategoryKey( String strDemandTypeCategoryKey, Plugin plugin )
    {
       DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_KEY, plugin );
        daoUtil.setString( 1 , strDemandTypeCategoryKey );
        daoUtil.executeQuery( );
        DemandTypeCategory demandTypeCategory = null;

        if ( daoUtil.next( ) )
        {
            demandTypeCategory = new DemandTypeCategory();
            int nIndex = 1;
            
            demandTypeCategory.setId( daoUtil.getInt( nIndex++ ) );
            demandTypeCategory.setName( daoUtil.getString( nIndex++ ) );
            demandTypeCategory.setDescription( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return demandTypeCategory;
    }
}
