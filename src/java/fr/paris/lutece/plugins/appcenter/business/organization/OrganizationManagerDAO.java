/*
 * Copyright (c) 2002-2020, Mairie de Paris
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

package fr.paris.lutece.plugins.appcenter.business.organization;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for OrganizationManager objects
 */
public final class OrganizationManagerDAO implements IOrganizationManagerDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_organization_manager, id_organization, first_name, family_name, mail FROM appcenter_organization_manager WHERE id_organization_manager = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_organization_manager ( id_organization, first_name, family_name, mail ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_organization_manager WHERE id_organization_manager = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_organization_manager SET id_organization_manager = ?, id_organization = ?, first_name = ?, family_name = ?, mail = ? WHERE id_organization_manager = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_organization_manager, id_organization, first_name, family_name, mail FROM appcenter_organization_manager";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_organization_manager FROM appcenter_organization_manager";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( OrganizationManager organizationManager, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++ , organizationManager.getIdOrganization( ) );
            daoUtil.setString( nIndex++ , organizationManager.getFirstName( ) );
            daoUtil.setString( nIndex++ , organizationManager.getFamilyName( ) );
            daoUtil.setString( nIndex++ , organizationManager.getMail( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                organizationManager.setIdOrganizationManager( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public OrganizationManager load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        OrganizationManager organizationManager = null;

        if ( daoUtil.next( ) )
        {
            organizationManager = new OrganizationManager();
            int nIndex = 1;

            organizationManager.setIdOrganizationManager( daoUtil.getInt( nIndex++ ) );
            organizationManager.setIdOrganization( daoUtil.getInt( nIndex++ ) );
            organizationManager.setFirstName( daoUtil.getString( nIndex++ ) );
            organizationManager.setFamilyName( daoUtil.getString( nIndex++ ) );
            organizationManager.setMail( daoUtil.getString( nIndex ) );
        }

        daoUtil.free( );
        return organizationManager;
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
    public void store( OrganizationManager organizationManager, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++ , organizationManager.getIdOrganizationManager( ) );
        daoUtil.setInt( nIndex++ , organizationManager.getIdOrganization( ) );
        daoUtil.setString( nIndex++ , organizationManager.getFirstName( ) );
        daoUtil.setString( nIndex++ , organizationManager.getFamilyName( ) );
        daoUtil.setString( nIndex++ , organizationManager.getMail( ) );
        daoUtil.setInt( nIndex , organizationManager.getIdOrganizationManager( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<OrganizationManager> selectOrganizationManagersList( Plugin plugin )
    {
        List<OrganizationManager> organizationManagerList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            OrganizationManager organizationManager = new OrganizationManager(  );
            int nIndex = 1;

            organizationManager.setIdOrganizationManager( daoUtil.getInt( nIndex++ ) );
            organizationManager.setIdOrganization( daoUtil.getInt( nIndex++ ) );
            organizationManager.setFirstName( daoUtil.getString( nIndex++ ) );
            organizationManager.setFamilyName( daoUtil.getString( nIndex++ ) );
            organizationManager.setMail( daoUtil.getString( nIndex ) );

            organizationManagerList.add( organizationManager );
        }

        daoUtil.free( );
        return organizationManagerList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdOrganizationManagersList( Plugin plugin )
    {
        List<Integer> organizationManagerList = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            organizationManagerList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return organizationManagerList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectOrganizationManagersReferenceList( Plugin plugin )
    {
        ReferenceList organizationManagerList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            organizationManagerList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return organizationManagerList;
    }
}
