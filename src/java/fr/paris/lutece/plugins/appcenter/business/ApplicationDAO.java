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
 * This class provides Data Access methods for Application objects
 */
public final class ApplicationDAO implements IApplicationDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_application ) FROM appcenter_application";
    private static final String SQL_QUERY_SELECT = "SELECT id_application, name, description, application_data FROM appcenter_application WHERE id_application = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_application ( id_application, name, description, application_data ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_application WHERE id_application = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_application SET id_application = ?, name = ?, description = ?, application_data = ? WHERE id_application = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_application, name, description, application_data FROM appcenter_application";
    private static final String SQL_QUERY_SELECT_BY_USER = "SELECT a.id_application, a.name, a.description, a.application_data, b.user_role "
            + " FROM appcenter_application a, appcenter_user_application b "
            + " WHERE a.id_application = b.id_application AND b.user_id = ? ";
    private static final String SQL_QUERY_SELECT_AUTHORIZED = " SELECT * FROM appcenter_user_application WHERE id_application = ? AND user_id = ? ";
    private static final String SQL_QUERY_DELETE_AUTHORIZED = "DELETE FROM appcenter_user_application WHERE id_application = ? ";
            
    /**
     * Generates a new primary key
     * 
     * @param plugin
     *            The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );
        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );
        return nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Application application, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        application.setId( newPrimaryKey( plugin ) );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, application.getId( ) );
        daoUtil.setString( nIndex++, application.getName( ) );
        daoUtil.setString( nIndex++, application.getDescription( ) );
        daoUtil.setString( nIndex++, application.getApplicationData( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Application load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        Application application = null;

        if ( daoUtil.next( ) )
        {
            application = new Application( );
            int nIndex = 1;

            application.setId( daoUtil.getInt( nIndex++ ) );
            application.setName( daoUtil.getString( nIndex++ ) );
            application.setDescription( daoUtil.getString( nIndex++ ) );
            application.setApplicationData( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return application;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
        
        // Remove authorizations
        daoUtil = new DAOUtil( SQL_QUERY_DELETE_AUTHORIZED, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( Application application, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, application.getId( ) );
        daoUtil.setString( nIndex++, application.getName( ) );
        daoUtil.setString( nIndex++, application.getDescription( ) );
        daoUtil.setString( nIndex++, application.getApplicationData( ) );
        daoUtil.setInt( nIndex, application.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Application> selectApplicationsList( Plugin plugin )
    {
        List<Application> applicationList = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            Application application = new Application( );
            int nIndex = 1;

            application.setId( daoUtil.getInt( nIndex++ ) );
            application.setName( daoUtil.getString( nIndex++ ) );
            application.setDescription( daoUtil.getString( nIndex++ ) );
            application.setApplicationData( daoUtil.getString( nIndex++ ) );

            applicationList.add( application );
        }

        daoUtil.free( );
        return applicationList;
    }

    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectApplicationsReferenceList( Plugin plugin )
    {
        ReferenceList applicationList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            applicationList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return applicationList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<AuthorizedApp> selectByUserId(String strUserId, Plugin plugin)
    {
        List<AuthorizedApp> applicationList = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_USER, plugin );
        daoUtil.setString( 1 , strUserId );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            AuthorizedApp application = new AuthorizedApp( );
            int nIndex = 1;

            application.setId( daoUtil.getInt( nIndex++ ) );
            application.setName( daoUtil.getString( nIndex++ ) );
            application.setDescription( daoUtil.getString( nIndex++ ) );
            application.setApplicationData( daoUtil.getString( nIndex++ ) );
            application.setUserRole( daoUtil.getInt( nIndex++ ) );

            applicationList.add( application );
        }

        daoUtil.free( );
        return applicationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isAuthorized( int nApplicationId, String strUserId, Plugin plugin)
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_AUTHORIZED );
        daoUtil.setString( 2 , strUserId );
        daoUtil.executeQuery( );

        return daoUtil.next( );
    }

}
