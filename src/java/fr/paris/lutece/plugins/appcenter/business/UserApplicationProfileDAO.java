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
 * This class provides Data Access methods for UserApplicationProfile objects
 */
public final class UserApplicationProfileDAO implements IUserApplicationProfileDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_profile, id_application, id_user FROM appcenter_user_application_profile WHERE id_profile = ? and id_application = ? and id_user = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_user_application_profile ( id_profile, id_application, id_user ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_user_application_profile WHERE id_profile = ? and id_application = ? and id_user = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT  id_profile, id_application, id_user FROM appcenter_user_application_profile";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( UserApplicationProfile userApplicationProfile, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++ , userApplicationProfile.getIdProfile( ) );
            daoUtil.setInt( nIndex++ , userApplicationProfile.getIdApplication( ) );
            daoUtil.setString( nIndex++ , userApplicationProfile.getIdUser( ) );
            
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
    public UserApplicationProfile load( int nProfileId, int nApplicationId, String strUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nProfileId );
        daoUtil.setInt( 2 , nApplicationId );
        daoUtil.setString( 3 , strUserId );
        
        daoUtil.executeQuery( );
        UserApplicationProfile userApplicationProfile = null;

        if ( daoUtil.next( ) )
        {
            userApplicationProfile = new UserApplicationProfile();
            int nIndex = 1;
            
            userApplicationProfile.setIdProfile( daoUtil.getInt( nIndex++ ) );
            userApplicationProfile.setIdApplication( daoUtil.getInt( nIndex++ ) );
            userApplicationProfile.setIdUser( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return userApplicationProfile;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nProfileId, int nApplicationId, String strUserId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1 , nProfileId );
        daoUtil.setInt( 2 , nApplicationId );
        daoUtil.setString(3 , strUserId );
        
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserApplicationProfile> selectUserApplicationProfilesList( Plugin plugin )
    {
        List<UserApplicationProfile> userApplicationProfileList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            UserApplicationProfile userApplicationProfile = new UserApplicationProfile(  );
            int nIndex = 1;
            
            userApplicationProfile.setIdProfile( daoUtil.getInt( nIndex++ ) );
            userApplicationProfile.setIdApplication( daoUtil.getInt( nIndex++ ) );
            userApplicationProfile.setIdUser( daoUtil.getString( nIndex++ ) );

            userApplicationProfileList.add( userApplicationProfile );
        }

        daoUtil.free( );
        return userApplicationProfileList;
    }

    
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectUserApplicationProfilesReferenceList( Plugin plugin )
    {
        ReferenceList userApplicationProfileList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            userApplicationProfileList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return userApplicationProfileList;
    }
}
