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

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for UserApplication objects
 */
public final class UserApplicationDAO implements IUserApplicationDAO
{
    // Constants
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_user_application ( id_application, user_id, user_role ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_user_application WHERE id_application = ?  AND user_id = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_user_application SET id_application = ?, user_id = ?, user_role = ? WHERE id_application = ?  AND user_id = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT a.id_application, a.user_id, user_role, b.name "
            + " FROM appcenter_user_application a , appcenter_application b " + " WHERE a.id_application = b.id_application ";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECTALL + " AND a.id_application = ? AND a.user_id = ?";
    private static final String SQL_QUERY_SELECT_BY_APPLICATION = SQL_QUERY_SELECTALL + " AND a.id_application = ?" ;
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_application FROM appcenter_user_application";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( UserApplication userApplication, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, userApplication.getId( ) );
        daoUtil.setString( nIndex++, userApplication.getUserId( ) );
        daoUtil.setInt( nIndex++, userApplication.getUserRole( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UserApplication load( int nKey, String strUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.setString( 2, strUserId );
        daoUtil.executeQuery( );
        UserApplication userApplication = null;

        if ( daoUtil.next( ) )
        {
            userApplication = new UserApplication( );
            int nIndex = 1;

            userApplication.setId( daoUtil.getInt( nIndex++ ) );
            userApplication.setUserId( daoUtil.getString( nIndex++ ) );
            userApplication.setUserRole( daoUtil.getInt( nIndex++ ) );
            userApplication.setApplicationName( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return userApplication;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, String strUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.setString( 2, strUserId );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( UserApplication userApplication, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, userApplication.getId( ) );
        daoUtil.setString( nIndex++, userApplication.getUserId( ) );
        daoUtil.setInt( nIndex++, userApplication.getUserRole( ) );
        daoUtil.setInt( nIndex++, userApplication.getId( ) );
        daoUtil.setString( nIndex++, userApplication.getUserId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserApplication> selectUserApplicationsList( Plugin plugin )
    {
        List<UserApplication> userApplicationList = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            UserApplication userApplication = new UserApplication( );
            int nIndex = 1;

            userApplication.setId( daoUtil.getInt( nIndex++ ) );
            userApplication.setUserId( daoUtil.getString( nIndex++ ) );
            userApplication.setUserRole( daoUtil.getInt( nIndex++ ) );
            userApplication.setApplicationName( daoUtil.getString( nIndex++ ) );

            userApplicationList.add( userApplication );
        }

        daoUtil.free( );
        return userApplicationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdUserApplicationsList( Plugin plugin )
    {
        List<Integer> userApplicationList = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            userApplicationList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return userApplicationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectUserApplicationsReferenceList( Plugin plugin )
    {
        ReferenceList userApplicationList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            userApplicationList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return userApplicationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean exists( int nApplicationId, String strUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nApplicationId );
        daoUtil.setString( 2, strUserId );
        daoUtil.executeQuery( );
        boolean bExists = daoUtil.next( );
        daoUtil.free( );
        return bExists;
     }

    @Override
    public List<UserApplication> selectByApplication( int nApplicationId, Plugin plugin )
    {
        List<UserApplication> userApplicationList = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_APPLICATION, plugin );
        daoUtil.setInt( 1 , nApplicationId );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            UserApplication userApplication = new UserApplication( );
            int nIndex = 1;

            userApplication.setId( daoUtil.getInt( nIndex++ ) );
            userApplication.setUserId( daoUtil.getString( nIndex++ ) );
            userApplication.setUserRole( daoUtil.getInt( nIndex++ ) );
            userApplication.setApplicationName( daoUtil.getString( nIndex++ ) );

            userApplicationList.add( userApplication );
        }

        daoUtil.free( );
        return userApplicationList;
  
    }
}
