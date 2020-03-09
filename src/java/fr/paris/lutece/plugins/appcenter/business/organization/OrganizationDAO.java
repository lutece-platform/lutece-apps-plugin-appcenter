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
 * This class provides Data Access methods for Organization objects
 */
public final class OrganizationDAO implements IOrganizationDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_organization, name FROM appcenter_organization WHERE id_organization = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_organization ( name ) VALUES ( ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_organization WHERE id_organization = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_organization SET id_organization = ?, name = ? WHERE id_organization = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_organization, name FROM appcenter_organization";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_organization FROM appcenter_organization";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Organization organization, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , organization.getName( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                organization.setIdOrganization( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public Organization load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        Organization organization = null;

        if ( daoUtil.next( ) )
        {
            organization = new Organization();
            int nIndex = 1;

            organization.setIdOrganization( daoUtil.getInt( nIndex++ ) );
            organization.setName( daoUtil.getString( nIndex ) );
        }

        daoUtil.free( );
        return organization;
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
    public void store( Organization organization, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++ , organization.getIdOrganization( ) );
        daoUtil.setString( nIndex++ , organization.getName( ) );
        daoUtil.setInt( nIndex , organization.getIdOrganization( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Organization> selectOrganizationsList( Plugin plugin )
    {
        List<Organization> organizationList = new ArrayList<>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Organization organization = new Organization(  );
            int nIndex = 1;

            organization.setIdOrganization( daoUtil.getInt( nIndex++ ) );
            organization.setName( daoUtil.getString( nIndex ) );

            organizationList.add( organization );
        }

        daoUtil.free( );
        return organizationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdOrganizationsList( Plugin plugin )
    {
        List<Integer> organizationList = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            organizationList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return organizationList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectOrganizationsReferenceList( Plugin plugin )
    {
        ReferenceList organizationList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            organizationList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return organizationList;
    }
}
