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

package fr.paris.lutece.plugins.appcenter.modules.identitystore.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for Attribute objects
 */
public final class AttributeDAO implements IAttributeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_attribute, key_name, label, description FROM appcenter_attribute WHERE id_attribute = ?";
    private static final String SQL_QUERY_SELECT_BY_KEY = "SELECT id_attribute, key_name, label, description FROM appcenter_attribute WHERE key_name = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_attribute ( key_name, label, description ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_attribute WHERE id_attribute = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_attribute SET id_attribute = ?, key_name = ?, label = ?, description = ? WHERE id_attribute = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_attribute, key_name, label, description FROM appcenter_attribute";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_attribute FROM appcenter_attribute";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Attribute attribute, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , attribute.getKey( ) );
            daoUtil.setString( nIndex++ , attribute.getLabel( ) );
            daoUtil.setString( nIndex++ , attribute.getDescription( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                attribute.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public Attribute load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        Attribute attribute = null;

        if ( daoUtil.next( ) )
        {
            attribute = new Attribute();
            int nIndex = 1;
            
            attribute.setId( daoUtil.getInt( nIndex++ ) );
            attribute.setKey( daoUtil.getString( nIndex++ ) );
            attribute.setLabel( daoUtil.getString( nIndex++ ) );
            attribute.setDescription( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return attribute;
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
    public void store( Attribute attribute, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , attribute.getId( ) );
        daoUtil.setString( nIndex++ , attribute.getKey( ) );
        daoUtil.setString( nIndex++ , attribute.getLabel( ) );
        daoUtil.setString( nIndex++ , attribute.getDescription( ) );
        daoUtil.setInt( nIndex , attribute.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Attribute> selectAttributesList( Plugin plugin )
    {
        List<Attribute> attributeList = new ArrayList<Attribute>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Attribute attribute = new Attribute(  );
            int nIndex = 1;
            
            attribute.setId( daoUtil.getInt( nIndex++ ) );
            attribute.setKey( daoUtil.getString( nIndex++ ) );
            attribute.setLabel( daoUtil.getString( nIndex++ ) );
            attribute.setDescription( daoUtil.getString( nIndex++ ) );

            attributeList.add( attribute );
        }

        daoUtil.free( );
        return attributeList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdAttributesList( Plugin plugin )
    {
        List<Integer> attributeList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            attributeList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return attributeList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectAttributesReferenceList( Plugin plugin )
    {
        ReferenceList attributeList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            attributeList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return attributeList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Attribute loadByAttributeKey( String strAttributeKey, Plugin plugin )
    {
       DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_KEY, plugin );
        daoUtil.setString( 1 , strAttributeKey );
        daoUtil.executeQuery( );
        Attribute attribute = null;

        if ( daoUtil.next( ) )
        {
            attribute = new Attribute();
            int nIndex = 1;
            
            attribute.setId( daoUtil.getInt( nIndex++ ) );
            attribute.setKey( daoUtil.getString( nIndex++ ) );
            attribute.setLabel( daoUtil.getString( nIndex++ ) );
            attribute.setDescription( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return attribute;
    }
}
