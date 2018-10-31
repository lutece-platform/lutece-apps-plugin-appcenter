/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
 * This class provides Data Access methods for Resource objects
 */
public final class ResourceDAO implements IResourceDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_resource, code, label, resource_type FROM appcenter_resource WHERE id_resource = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_resource ( code, label, resource_type ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_resource WHERE id_resource = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_resource SET id_resource = ?, code = ?, label = ?, resource_type = ? WHERE id_resource = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_resource, code, label, resource_type FROM appcenter_resource";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_resource FROM appcenter_resource";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Resource resource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , resource.getCode( ) );
            daoUtil.setString( nIndex++ , resource.getLabel( ) );
            daoUtil.setString( nIndex++ , resource.getResourceType( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                resource.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public Resource load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        Resource resource = null;

        if ( daoUtil.next( ) )
        {
            resource = new Resource();
            int nIndex = 1;
            
            resource.setId( daoUtil.getInt( nIndex++ ) );
            resource.setCode( daoUtil.getString( nIndex++ ) );
            resource.setLabel( daoUtil.getString( nIndex++ ) );
            resource.setResourceType( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return resource;
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
    public void store( Resource resource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , resource.getId( ) );
        daoUtil.setString( nIndex++ , resource.getCode( ) );
        daoUtil.setString( nIndex++ , resource.getLabel( ) );
        daoUtil.setString( nIndex++ , resource.getResourceType( ) );
        daoUtil.setInt( nIndex , resource.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Resource> selectResourcesList( Plugin plugin )
    {
        List<Resource> resourceList = new ArrayList<Resource>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Resource resource = new Resource(  );
            int nIndex = 1;
            
            resource.setId( daoUtil.getInt( nIndex++ ) );
            resource.setCode( daoUtil.getString( nIndex++ ) );
            resource.setLabel( daoUtil.getString( nIndex++ ) );
            resource.setResourceType( daoUtil.getString( nIndex++ ) );

            resourceList.add( resource );
        }

        daoUtil.free( );
        return resourceList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdResourcesList( Plugin plugin )
    {
        List<Integer> resourceList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            resourceList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return resourceList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectResourcesReferenceList( Plugin plugin )
    {
        ReferenceList resourceList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            resourceList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return resourceList;
    }
}
