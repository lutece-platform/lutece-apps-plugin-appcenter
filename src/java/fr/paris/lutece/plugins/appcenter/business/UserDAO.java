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
 * This class provides Data Access methods for User objects
 */
public final class UserDAO implements IUserDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_user, user_infos FROM appcenter_user WHERE id_user = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_user ( id_user ) VALUES ( ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_user WHERE id_user = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_user SET id_user = ?, user_infos = ? WHERE id_user = ?";
    private static final String SQL_QUERY_UPDATE_DATA = "UPDATE appcenter_user SET user_infos = ? WHERE id_user = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_user, user_infos FROM appcenter_user";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_user FROM appcenter_user";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( User user, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, user.getId( ) );

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
    public User load( String strKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString( 1, strKey );
        daoUtil.executeQuery( );
        User user = null;

        if ( daoUtil.next( ) )
        {
            user = new User( );
            int nIndex = 1;

            user.setId( daoUtil.getString( nIndex++ ) );
            user.setUserInfos( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return user;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( String strKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setString( 1, strKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( User user, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setString( nIndex++, user.getId( ) );
        daoUtil.setString( nIndex++, user.getUserInfos( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<User> selectUsersList( Plugin plugin )
    {
        List<User> userList = new ArrayList<User>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            User user = new User( );
            int nIndex = 1;

            user.setId( daoUtil.getString( nIndex++ ) );
            user.setUserInfos( daoUtil.getString( nIndex++ ) );

            userList.add( user );
        }

        daoUtil.free( );
        return userList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<String> selectIdUsersList( Plugin plugin )
    {
        List<String> userList = new ArrayList<String>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            userList.add( daoUtil.getString( 1 ) );
        }

        daoUtil.free( );
        return userList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectUsersReferenceList( Plugin plugin )
    {
        ReferenceList userList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            userList.addItem( daoUtil.getString( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return userList;
    }

    @Override
    public void storeUserInfos( String strIdUser, String strData, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_DATA, plugin );
        int nIndex = 1;

        daoUtil.setString( nIndex++, strData );
        daoUtil.setString( nIndex, strIdUser );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }
}
