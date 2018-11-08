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
 * This class provides Data Access methods for PermissionRole objects
 */
public final class PermissionRoleDAO implements IPermissionRoleDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT code_permission, id_role, code_resource FROM appcenter_permission_role WHERE code_permission,  = ? and id_role = ? and code_resource = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_permission_role ( code_permission, id_role, code_resource ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_permission_role WHERE code_permission= ? and id_role = ? and code_resource = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT code_permission, id_role, code_resource FROM appcenter_permission_role ";
    private static final String SQL_QUERY_SELECTALL_BY_ID_CODE = SQL_QUERY_SELECTALL + " WHERE id_role = ? ORDER BY code_permission, code_resource";
    private static final String SQL_QUERY_SELECTALL_BY_ID_CODE_AND_CODE_PERMISSION = SQL_QUERY_SELECTALL + " WHERE id_role = ? AND code_permission = ? ORDER BY code_permission, code_resource";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( PermissionRole permissionRole, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , permissionRole.getCodePermission( ) );
            daoUtil.setInt( nIndex++ , permissionRole.getIdRole( ) );
            daoUtil.setString( nIndex++ , permissionRole.getCodeResource( ) );
            
            daoUtil.executeUpdate( );

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
    public PermissionRole load( String strPermissionCode, int strIdRole, String strResourceCode,  Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString(1 , strPermissionCode );
        daoUtil.setInt(2 , strIdRole );
        daoUtil.setString(3 , strResourceCode );
        
        daoUtil.executeQuery( );
        PermissionRole permissionRole = null;

        if ( daoUtil.next( ) )
        {
            permissionRole = new PermissionRole();
            int nIndex = 1;
            
            permissionRole.setCodePermission( daoUtil.getString( nIndex++ ) );
            permissionRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            permissionRole.setCodeResource( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return permissionRole;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( String strPermissionCode, int nIdRole, String strResourceCode,  Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        
        daoUtil.setString(1 , strPermissionCode );
        daoUtil.setInt(2 , nIdRole );
        daoUtil.setString(3 , strResourceCode );
        
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public List<PermissionRole> selectPermissionRolesList( Plugin plugin )
    {
        List<PermissionRole> permissionRoleList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PermissionRole permissionRole = new PermissionRole(  );
            int nIndex = 1;
            
            permissionRole.setCodePermission( daoUtil.getString( nIndex++ ) );
            permissionRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            permissionRole.setCodeResource( daoUtil.getString( nIndex++ ) );

            permissionRoleList.add( permissionRole );
        }

        daoUtil.free( );
        return permissionRoleList;
    }
    
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectPermissionRolesReferenceList( Plugin plugin )
    {
        ReferenceList permissionRoleList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            permissionRoleList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return permissionRoleList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<PermissionRole> selectPermissionRolesListByIdRole(int nIdRole, Plugin plugin) 
    {
        List<PermissionRole> permissionRoleList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_ID_CODE, plugin );
        daoUtil.setInt( 1 , nIdRole );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PermissionRole permissionRole = new PermissionRole(  );
            int nIndex = 1;
            
            permissionRole.setCodePermission( daoUtil.getString( nIndex++ ) );
            permissionRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            permissionRole.setCodeResource( daoUtil.getString( nIndex++ ) );

            permissionRoleList.add( permissionRole );
        }

        daoUtil.free( );
        return permissionRoleList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<PermissionRole> selectPermissionRolesListByCodeAndIdRole(String strPermissionCode, int nIdRole, Plugin plugin)
    {
        List<PermissionRole> permissionRoleList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_ID_CODE, plugin );
        daoUtil.setInt( 1 , nIdRole );
        daoUtil.setString( 2 , strPermissionCode );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            PermissionRole permissionRole = new PermissionRole(  );
            int nIndex = 1;
            
            permissionRole.setCodePermission( daoUtil.getString( nIndex++ ) );
            permissionRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            permissionRole.setCodeResource( daoUtil.getString( nIndex++ ) );

            permissionRoleList.add( permissionRole );
        }

        daoUtil.free( );
        return permissionRoleList;
    }
}
