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
 * This class provides Data Access methods for Profile objects
 */
public final class ProfileDAO implements IProfileDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_profile, code, label FROM appcenter_profile WHERE id_profile = ?";
    private static final String SQL_QUERY_SELECT_BY_USER_AND_APPLICATION = "select p.id_profile, p.code, p.label from appcenter_profile p, appcenter_user_application_profile ap where p.id_profile = ap.id_profile and id_user = ? and id_application = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_profile ( code, label ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_profile WHERE id_profile = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_profile SET id_profile = ?, code = ?, label = ? WHERE id_profile = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_profile, code, label FROM appcenter_profile";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_profile FROM appcenter_profile";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Profile profile, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , profile.getCode( ) );
            daoUtil.setString( nIndex++ , profile.getLabel( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                profile.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public Profile load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        Profile profile = null;

        if ( daoUtil.next( ) )
        {
            profile = new Profile();
            int nIndex = 1;
            
            profile.setId( daoUtil.getInt( nIndex++ ) );
            profile.setCode( daoUtil.getString( nIndex++ ) );
            profile.setLabel( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return profile;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Profile loadByUserIdAndApplicationId( String strUserId, int nApplicationId, Plugin plugin ) 
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_USER_AND_APPLICATION, plugin );
        daoUtil.setString( 1 , strUserId );
        daoUtil.setInt( 2 , nApplicationId );
        
        daoUtil.executeQuery( );
        Profile profile = null;

        if ( daoUtil.next( ) )
        {
            profile = new Profile();
            int nIndex = 1;
            
            profile.setId( daoUtil.getInt( nIndex++ ) );
            profile.setCode( daoUtil.getString( nIndex++ ) );
            profile.setLabel( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return profile;
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
    public void store( Profile profile, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , profile.getId( ) );
        daoUtil.setString( nIndex++ , profile.getCode( ) );
        daoUtil.setString( nIndex++ , profile.getLabel( ) );
        daoUtil.setInt( nIndex , profile.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Profile> selectProfilesList( Plugin plugin )
    {
        List<Profile> profileList = new ArrayList<Profile>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Profile profile = new Profile(  );
            int nIndex = 1;
            
            profile.setId( daoUtil.getInt( nIndex++ ) );
            profile.setCode( daoUtil.getString( nIndex++ ) );
            profile.setLabel( daoUtil.getString( nIndex++ ) );

            profileList.add( profile );
        }

        daoUtil.free( );
        return profileList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdProfilesList( Plugin plugin )
    {
        List<Integer> profileList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            profileList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return profileList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectProfilesReferenceList( Plugin plugin )
    {
        ReferenceList profileList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            profileList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return profileList;
    }
}
