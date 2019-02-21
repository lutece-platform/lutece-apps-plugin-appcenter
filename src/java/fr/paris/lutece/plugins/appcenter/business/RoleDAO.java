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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides Data Access methods for Role objects
 */
public final class RoleDAO implements IRoleDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_role, code, label FROM appcenter_role WHERE id_role = ?";
    private static final String SQL_QUERY_SELECT_BY_CODE = "SELECT id_role, code, label FROM appcenter_role WHERE code = ?";
    private static final String SQL_QUERY_SELECT_BY_USER_AND_APPLICATION = "select p.id_role, p.code, p.label from appcenter_role p, appcenter_user_application_role ap where p.id_role = ap.id_role and id_user = ? and id_application = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_role ( code, label ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_role WHERE id_role = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_role SET id_role = ?, code = ?, label = ? WHERE id_role = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_role, code, label FROM appcenter_role";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_role FROM appcenter_role";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Role role, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , role.getCode( ) );
            daoUtil.setString( nIndex++ , role.getLabel( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                role.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public Role load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        Role role = null;

        if ( daoUtil.next( ) )
        {
            role = new Role();
            int nIndex = 1;
            
            role.setId( daoUtil.getInt( nIndex++ ) );
            role.setCode( daoUtil.getString( nIndex++ ) );
            role.setLabel( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return role;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Role loadByUserIdAndApplicationId( String strUserId, int nApplicationId, Plugin plugin ) 
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_USER_AND_APPLICATION, plugin );
        daoUtil.setString( 1 , strUserId );
        daoUtil.setInt( 2 , nApplicationId );
        
        daoUtil.executeQuery( );
        Role role = null;

        if ( daoUtil.next( ) )
        {
            role = new Role();
            int nIndex = 1;
            
            role.setId( daoUtil.getInt( nIndex++ ) );
            role.setCode( daoUtil.getString( nIndex++ ) );
            role.setLabel( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return role;
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
    public void store( Role role, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , role.getId( ) );
        daoUtil.setString( nIndex++ , role.getCode( ) );
        daoUtil.setString( nIndex++ , role.getLabel( ) );
        daoUtil.setInt( nIndex , role.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Role> selectRolesList( Plugin plugin )
    {
        List<Role> roleList = new ArrayList<Role>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Role role = new Role(  );
            int nIndex = 1;
            
            role.setId( daoUtil.getInt( nIndex++ ) );
            role.setCode( daoUtil.getString( nIndex++ ) );
            role.setLabel( daoUtil.getString( nIndex++ ) );

            roleList.add( role );
        }

        daoUtil.free( );
        return roleList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdRolesList( Plugin plugin )
    {
        List<Integer> roleList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            roleList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return roleList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectRolesReferenceList( Plugin plugin )
    {
        ReferenceList roleList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            roleList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 3 ) );
        }

        daoUtil.free( );
        return roleList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, Role> selectRolesMap( Plugin plugin )
    {
        Map<String, Role> rolesMap = new HashMap<String, Role>(  );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Role role = new Role(  );
            int nIndex = 1;

            role.setId( daoUtil.getInt( nIndex++ ) );
            role.setCode( daoUtil.getString( nIndex++ ) );
            role.setLabel( daoUtil.getString( nIndex++ ) );

            rolesMap.put( Integer.toString( role.getId( ) ), role );
        }

        daoUtil.free( );

        return rolesMap;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Role loadByCode( String strCode, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CODE, plugin );
        daoUtil.setString( 1 , strCode );
        daoUtil.executeQuery( );
        Role role = null;

        if ( daoUtil.next( ) )
        {
            role = new Role();
            int nIndex = 1;
            
            role.setId( daoUtil.getInt( nIndex++ ) );
            role.setCode( daoUtil.getString( nIndex++ ) );
            role.setLabel( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return role;
    }
}
