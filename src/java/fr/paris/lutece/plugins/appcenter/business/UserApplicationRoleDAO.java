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
 * This class provides Data Access methods for UserApplicationRole objects
 */
public final class UserApplicationRoleDAO implements IUserApplicationRoleDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_role, id_application, id_user FROM appcenter_user_application_role WHERE id_role = ? and id_application = ? and id_user = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_user_application_role ( id_role, id_application, id_user ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_user_application_role WHERE id_role = ? and id_application = ? and id_user = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT  id_role, id_application, id_user FROM appcenter_user_application_role";
    private static final String SQL_QUERY_SELECTALL_BY_ID_USER = SQL_QUERY_SELECTALL + " WHERE id_user = ? ";
    private static final String SQL_QUERY_SELECTALL_BY_ID_APPLICATION = SQL_QUERY_SELECTALL + " WHERE id_application = ? ";
    private static final String SQL_QUERY_SELECTALL_BY_ID_APPLICATION_AND_ID_USER = SQL_QUERY_SELECTALL + " WHERE id_application = ? AND id_user = ? ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( UserApplicationRole userApplicationRole, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++ , userApplicationRole.getIdRole( ) );
            daoUtil.setInt( nIndex++ , userApplicationRole.getIdApplication( ) );
            daoUtil.setString( nIndex++ , userApplicationRole.getIdUser( ) );
            
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
    public UserApplicationRole load( int nRoleId, int nApplicationId, String strUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nRoleId );
        daoUtil.setInt( 2 , nApplicationId );
        daoUtil.setString( 3 , strUserId );
        
        daoUtil.executeQuery( );
        UserApplicationRole userApplicationRole = null;

        if ( daoUtil.next( ) )
        {
            userApplicationRole = new UserApplicationRole();
            int nIndex = 1;
            
            userApplicationRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdApplication( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdUser( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return userApplicationRole;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nRoleId, int nApplicationId, String strUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1 , nRoleId );
        daoUtil.setInt( 2 , nApplicationId );
        daoUtil.setString(3 , strUserId );
        
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserApplicationRole> selectUserApplicationRolesList( Plugin plugin )
    {
        List<UserApplicationRole> userApplicationRoleList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            UserApplicationRole userApplicationRole = new UserApplicationRole(  );
            int nIndex = 1;
            
            userApplicationRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdApplication( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdUser( daoUtil.getString( nIndex++ ) );

            userApplicationRoleList.add( userApplicationRole );
        }

        daoUtil.free( );
        return userApplicationRoleList;
    }

    
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectUserApplicationRolesReferenceList( Plugin plugin )
    {
        ReferenceList userApplicationRoleList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            userApplicationRoleList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return userApplicationRoleList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserApplicationRole> selectUserApplicationRolesListByIdUser( String strIdUser, Plugin plugin)
    {
        List<UserApplicationRole> userApplicationRoleList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_ID_USER, plugin );
        daoUtil.setString( 1 , strIdUser );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            UserApplicationRole userApplicationRole = new UserApplicationRole(  );
            int nIndex = 1;
            
            userApplicationRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdApplication( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdUser( daoUtil.getString( nIndex++ ) );

            userApplicationRoleList.add( userApplicationRole );
        }

        daoUtil.free( );
        return userApplicationRoleList;    
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserApplicationRole> selectUserApplicationRolesListByIdApplication( int nIdApplication, Plugin plugin)
    {
        List<UserApplicationRole> userApplicationRoleList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_ID_APPLICATION, plugin );
        daoUtil.setInt( 1 , nIdApplication );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            UserApplicationRole userApplicationRole = new UserApplicationRole(  );
            int nIndex = 1;
            
            userApplicationRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdApplication( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdUser( daoUtil.getString( nIndex++ ) );

            userApplicationRoleList.add( userApplicationRole );
        }

        daoUtil.free( );
        return userApplicationRoleList; 
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserApplicationRole> selectUserApplicationRolesListByIdApplicationAndIdUser( int nIdApplication, String strIdUser, Plugin plugin)
    {
        List<UserApplicationRole> userApplicationRoleList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_ID_APPLICATION_AND_ID_USER, plugin );
        daoUtil.setInt( 1 , nIdApplication );
        daoUtil.setString( 2 , strIdUser );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            UserApplicationRole userApplicationRole = new UserApplicationRole(  );
            int nIndex = 1;
            
            userApplicationRole.setIdRole( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdApplication( daoUtil.getInt( nIndex++ ) );
            userApplicationRole.setIdUser( daoUtil.getString( nIndex++ ) );

            userApplicationRoleList.add( userApplicationRole );
        }

        daoUtil.free( );
        return userApplicationRoleList; 
    }
}
