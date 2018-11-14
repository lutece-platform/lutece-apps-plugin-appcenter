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
    private static final String SQL_QUERY_SELECT = "SELECT appcenter_application.id_application, name, description, application_data,code, environment_code FROM appcenter_application LEFT JOIN appcenter_application_environment ON appcenter_application.id_application = appcenter_application_environment.id_application WHERE appcenter_application.id_application = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_application ( id_application, name, description, application_data,code ) VALUES ( ?, ?, ?, ? , ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_application WHERE id_application = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_application SET name = ?, description = ? , code = ?  WHERE id_application = ?";
    private static final String SQL_QUERY_UPDATE_DATA = "UPDATE appcenter_application SET application_data = ? WHERE id_application = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_application, name, description, application_data, code FROM appcenter_application";
    private static final String SQL_QUERY_DELETE_AUTHORIZED = "DELETE FROM appcenter_user_application_role WHERE id_application = ? ";
    private static final String SQL_QUERY_SELECT_USER_ROLE = "SELECT id_role FROM appcenter_user_application_role WHERE id_application = ? AND id_user = ? ";
    private static final String SQL_QUERY_INSERT_ENVIRONMENT = " INSERT INTO appcenter_application_environment ( id_application, environment_code ) VALUES ( ? , ? ) ";
    private static final String SQL_QUERY_DELETE_ENVIRONMENT = " DELETE FROM appcenter_application_environment WHERE id_application = ? ";
    
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
        daoUtil.setString( nIndex++, application.getCode() );
        
        daoUtil.executeUpdate( );
        daoUtil.free( );
        
        for ( Environment envi : application.getListEnvironment( ) )
        {
            daoUtil = new DAOUtil( SQL_QUERY_INSERT_ENVIRONMENT, plugin );
            nIndex = 1;

            daoUtil.setInt( nIndex++, application.getId( ) );
            daoUtil.setString( nIndex++, envi.getPrefix( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
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
        List<String> listEnvironmentCode = new ArrayList<>();
        List<Environment> listEnvironment = new ArrayList<>();

        if ( daoUtil.next( ) )
        {
            
            application = new Application( );
            int nIndex = 1;

            application.setId( daoUtil.getInt( nIndex++ ) );
            application.setName( daoUtil.getString( nIndex++ ) );
            application.setDescription( daoUtil.getString( nIndex++ ) );
            application.setApplicationData( daoUtil.getString( nIndex++ ) );
            application.setCode(daoUtil.getString( nIndex++ ) );
            String strEnviCode = daoUtil.getString( nIndex++ );
            if ( strEnviCode != null )
            {
                listEnvironmentCode.add( strEnviCode );
            }
            while ( daoUtil.next( ) )
            {
                strEnviCode = daoUtil.getString( 6 );
                if ( strEnviCode != null )
                {
                    listEnvironmentCode.add( strEnviCode );
                }
            }
            for ( String enviCode : listEnvironmentCode )
            {
                Environment envi = Environment.getEnvironment( enviCode );
                if ( envi != null )
                {
                    listEnvironment.add( envi );
                }
            }
            application.setListEnvironment( listEnvironment );
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

        daoUtil.setString( nIndex++, application.getName( ) );
        daoUtil.setString( nIndex++, application.getDescription( ) );
        daoUtil.setString( nIndex++, application.getCode() );
        
        daoUtil.setInt( nIndex, application.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
        
        //Delete the environments for the application
        daoUtil = new DAOUtil( SQL_QUERY_DELETE_ENVIRONMENT, plugin );
        daoUtil.setInt( 1, application.getId( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
        
        //Add the environments modified
        for ( Environment envi : application.getListEnvironment( ) )
        {
            daoUtil = new DAOUtil( SQL_QUERY_INSERT_ENVIRONMENT, plugin );
            nIndex = 1;

            daoUtil.setInt( nIndex++, application.getId( ) );
            daoUtil.setString( nIndex++, envi.getPrefix( ) );

            daoUtil.executeUpdate( );
            daoUtil.free( );
        }
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void storeData( int nApplicationId, String strData, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_DATA, plugin );
        int nIndex = 1;

        daoUtil.setString( nIndex++, strData );
        daoUtil.setInt( nIndex, nApplicationId );

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
            application.setCode(daoUtil.getString( nIndex++ ) );

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
    public int getUserRole( int nApplicationId, String strUserId, int nDefaultRole,  Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_USER_ROLE );
        daoUtil.setInt( 1, nApplicationId );
        daoUtil.setString( 2, strUserId );
        daoUtil.executeQuery( );

        int nRole = nDefaultRole;
        if ( daoUtil.next( ) )
        {
            nRole = daoUtil.getInt( 1 );
        }

        daoUtil.free( );
        return nRole;

    }
}
