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
 * This class provides Data Access methods for Permission objects
 */
public final class PermissionDAO implements IPermissionDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_action, code, label, resource_type FROM appcenter_permission WHERE id_action = ?";
    private static final String SQL_QUERY_SELECT_BY_CODE_AND_ROLE_AND_RESOURCE = "SELECT a.id_action, a.code, a.label, a.resource_type FROM appcenter_permission a, appcenter_permission_role ap WHERE  a.code = ap.code_permission and ap.code_permission = ? and ap.code_role= ? and ap.code_resource in ( '*', ? ) ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_permission ( code, label, resource_type ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_permission WHERE id_action = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_permission SET id_action = ?, code = ?, label = ?, resource_type = ? WHERE id_action = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_action, code, label, resource_type FROM appcenter_permission";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_action FROM appcenter_permission";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Permission permission, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , permission.getCode( ) );
            daoUtil.setString( nIndex++ , permission.getLabel( ) );
            daoUtil.setString( nIndex++ , permission.getResourceType( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                permission.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public Permission load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        Permission permission = null;

        if ( daoUtil.next( ) )
        {
            permission = new Permission();
            int nIndex = 1;
            
            permission.setId( daoUtil.getInt( nIndex++ ) );
            permission.setCode( daoUtil.getString( nIndex++ ) );
            permission.setLabel( daoUtil.getString( nIndex++ ) );
            permission.setResourceType( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return permission;
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
    public void store( Permission permission, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , permission.getId( ) );
        daoUtil.setString( nIndex++ , permission.getCode( ) );
        daoUtil.setString( nIndex++ , permission.getLabel( ) );
        daoUtil.setString( nIndex++ , permission.getResourceType( ) );
        daoUtil.setInt( nIndex , permission.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Permission> selectPermissionsList( Plugin plugin )
    {
        List<Permission> permissionList = new ArrayList<Permission>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Permission permission = new Permission(  );
            int nIndex = 1;
            
            permission.setId( daoUtil.getInt( nIndex++ ) );
            permission.setCode( daoUtil.getString( nIndex++ ) );
            permission.setLabel( daoUtil.getString( nIndex++ ) );
            permission.setResourceType( daoUtil.getString( nIndex++ ) );

            permissionList.add( permission );
        }

        daoUtil.free( );
        return permissionList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdPermissionsList( Plugin plugin )
    {
        List<Integer> permissionList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            permissionList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return permissionList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectPermissionsReferenceList( Plugin plugin )
    {
        ReferenceList permissionList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            permissionList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return permissionList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Permission loadByCodeAndRoleAndResource(String codePermission, int nRoleId, String strResource, Plugin plugin) 
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CODE_AND_ROLE_AND_RESOURCE, plugin );
        daoUtil.setString( 1 , codePermission );
        daoUtil.setInt( 2, nRoleId );
        daoUtil.setString( 3, strResource );
        
        daoUtil.executeQuery( );
        Permission permission = null;

        if ( daoUtil.next( ) )
        {
            permission = new Permission();
            int nIndex = 1;
            
            permission.setId( daoUtil.getInt( nIndex++ ) );
            permission.setCode( daoUtil.getString( nIndex++ ) );
            permission.setLabel( daoUtil.getString( nIndex++ ) );
            permission.setResourceType( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return permission;
    }

}
