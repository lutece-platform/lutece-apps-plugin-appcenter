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
 * This class provides Data Access methods for Documentation objects
 */
public final class DocumentationDAO implements IDocumentationDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_documentation, id_demand_type, label, url, category FROM appcenter_documentation WHERE id_documentation = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_documentation ( id_demand_type, label, url, category ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_documentation WHERE id_documentation = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_documentation SET id_documentation = ?, id_demand_type = ?, label = ?, url = ?, category = ? WHERE id_documentation = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_documentation, id_demand_type, label, url, category FROM appcenter_documentation";
    private static final String SQL_QUERY_SELECTALL_BY_ID_DEMAND_TYPE = "SELECT id_documentation, id_demand_type, label, url, category FROM appcenter_documentation WHERE id_demand_type = ? ";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_documentation FROM appcenter_documentation";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Documentation documentation, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++, documentation.getIdDemandType( ) );
            daoUtil.setString( nIndex++, documentation.getLabel( ) );
            daoUtil.setString( nIndex++, documentation.getUrl( ) );
            daoUtil.setString( nIndex++, documentation.getCategory( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                documentation.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public Documentation load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        Documentation documentation = null;

        if ( daoUtil.next( ) )
        {
            documentation = new Documentation( );
            int nIndex = 1;

            documentation.setId( daoUtil.getInt( nIndex++ ) );
            documentation.setIdDemandType( daoUtil.getInt( nIndex++ ) );
            documentation.setLabel( daoUtil.getString( nIndex++ ) );
            documentation.setUrl( daoUtil.getString( nIndex++ ) );
            documentation.setCategory( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return documentation;
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
    public void store( Documentation documentation, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, documentation.getId( ) );
        daoUtil.setInt( nIndex++, documentation.getIdDemandType( ) );
        daoUtil.setString( nIndex++, documentation.getLabel( ) );
        daoUtil.setString( nIndex++, documentation.getUrl( ) );
        daoUtil.setString( nIndex++, documentation.getCategory( ) );
        daoUtil.setInt( nIndex, documentation.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Documentation> selectDocumentationsList( Plugin plugin )
    {
        List<Documentation> documentationList = new ArrayList<Documentation>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Documentation documentation = new Documentation( );
            int nIndex = 1;

            documentation.setId( daoUtil.getInt( nIndex++ ) );
            documentation.setIdDemandType( daoUtil.getInt( nIndex++ ) );
            documentation.setLabel( daoUtil.getString( nIndex++ ) );
            documentation.setUrl( daoUtil.getString( nIndex++ ) );
            documentation.setCategory( daoUtil.getString( nIndex++ ) );

            documentationList.add( documentation );
        }

        daoUtil.free( );
        return documentationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDocumentationsList( Plugin plugin )
    {
        List<Integer> documentationList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            documentationList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return documentationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDocumentationsReferenceList( Plugin plugin )
    {
        ReferenceList documentationList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            documentationList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return documentationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Documentation> selectDocumentationsListByIdDemandType( int nIdDemandType, Plugin plugin )
    {
        List<Documentation> documentationList = new ArrayList<Documentation>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_ID_DEMAND_TYPE, plugin );
        daoUtil.setInt( 1, nIdDemandType );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Documentation documentation = new Documentation( );
            int nIndex = 1;

            documentation.setId( daoUtil.getInt( nIndex++ ) );
            documentation.setIdDemandType( daoUtil.getInt( nIndex++ ) );
            documentation.setLabel( daoUtil.getString( nIndex++ ) );
            documentation.setUrl( daoUtil.getString( nIndex++ ) );
            documentation.setCategory( daoUtil.getString( nIndex++ ) );

            documentationList.add( documentation );
        }

        daoUtil.free( );
        return documentationList;
    }
}
