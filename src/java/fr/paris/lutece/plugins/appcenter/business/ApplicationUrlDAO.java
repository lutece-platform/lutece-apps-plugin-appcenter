/*
 * Copyright (c) 2002-2021, Mairie de Paris
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

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * Class ApplicationUrlDAO
 *
 */
public class ApplicationUrlDAO implements IApplicationUrlDAO
{
    private static final String SQL_QUERY_SELECT_ALL = "SELECT id_application_url, id_application, type, url, environment, description FROM appcenter_application_url WHERE id_application=? and environment like concat(?,'%') ";
    private static final String SQL_QUERY_SELECT     = "SELECT id_application_url, id_application, type, url, environment, description FROM appcenter_application_url WHERE id_application_url=?";
    private static final String SQL_QUERY_INSERT     = "INSERT INTO appcenter_application_url ( id_application, type, url, environment, description ) VALUES ( ?, ?, ?, ?, ?)";
    private static final String SQL_QUERY_UPDATE     = "UPDATE appcenter_application_url SET id_application=?, type=?, url=?, environment=?, description=? WHERE id_application_url=?";
    private static final String SQL_QUERY_REMOVE     = "DELETE FROM appcenter_application_url WHERE id_application_url = ?";
    
    @Override
    public void insert( ApplicationUrl applicationUrl, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )  
        {
            int nIndex = 1;
            
            daoUtil.setInt( nIndex++, applicationUrl.getIdApplication( ) );
            daoUtil.setString( nIndex++, applicationUrl.getType( ) );
            daoUtil.setString( nIndex++, applicationUrl.getUrl( ) );
            daoUtil.setString( nIndex++, applicationUrl.getEnvironment( ) );
            daoUtil.setString( nIndex++, applicationUrl.getDescription( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                applicationUrl.setIdApplicationUrl( daoUtil.getGeneratedKeyInt( 1 ) );
            }
            daoUtil.free( );
        }
    }

    @Override
    public void store( ApplicationUrl applicationUrl, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )  
        {
            int nIndex = 1;
            
            daoUtil.setInt( nIndex++, applicationUrl.getIdApplication( ) );
            daoUtil.setString( nIndex++, applicationUrl.getType( ) );
            daoUtil.setString( nIndex++, applicationUrl.getUrl( ) );
            daoUtil.setString( nIndex++, applicationUrl.getEnvironment( ) );
            daoUtil.setString( nIndex++, applicationUrl.getDescription( ) );
            
            daoUtil.setInt( nIndex++, applicationUrl.getIdApplicationUrl( ) );
            daoUtil.executeUpdate( );
            
            daoUtil.free( );
        }
    }

    @Override
    public void delete( int nId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_REMOVE, plugin ) )  
        {
            daoUtil.setInt( 1, nId );
            daoUtil.executeUpdate( );

            daoUtil.free( );
        }
    }

    @Override
    public ApplicationUrl load( int nId, Plugin plugin )
    {
       ApplicationUrl applicationUrl = null;
        
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )  
        {
            daoUtil.setInt( 1, nId );
            daoUtil.executeQuery( );
            
            if( daoUtil.next( ) )
            {
                int nIndex = 1;
                applicationUrl = new ApplicationUrl( );
                applicationUrl.setIdApplicationUrl( daoUtil.getInt( nIndex++ ) );
                applicationUrl.setIdApplication( daoUtil.getInt( nIndex++ ) );
                applicationUrl.setType( daoUtil.getString( nIndex++ ) );
                applicationUrl.setUrl( daoUtil.getString( nIndex++ ) );
                applicationUrl.setEnvironment( daoUtil.getString( nIndex++ ) );
                applicationUrl.setDescription( daoUtil.getString( nIndex++ )  );
              
            }
            
            daoUtil.free( );
        }
        return applicationUrl;
    }

    @Override
    public List<ApplicationUrl> selectApplicationUrlsList( int nIdApplication, String strEnvironment, Plugin plugin )
    {
        List<ApplicationUrl> listApplicationUrl = new ArrayList<>();
        
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin ) )  
        {
            daoUtil.setInt( 1, nIdApplication );
            daoUtil.setString( 2, strEnvironment );
            daoUtil.executeQuery( );
            
            while( daoUtil.next( ) )
            {
                int nIndex = 1;
                ApplicationUrl applicationUrl = new ApplicationUrl( );
                applicationUrl.setIdApplicationUrl( daoUtil.getInt( nIndex++ ) );
                applicationUrl.setIdApplication( daoUtil.getInt( nIndex++ ) );
                applicationUrl.setType( daoUtil.getString( nIndex++ ) );
                applicationUrl.setUrl( daoUtil.getString( nIndex++ ) );
                applicationUrl.setEnvironment( daoUtil.getString( nIndex++ ) );
                applicationUrl.setDescription( daoUtil.getString( nIndex++ )  );
                
                listApplicationUrl.add( applicationUrl );                
            }
            
            daoUtil.free( );
        }
        return listApplicationUrl;
    }

}