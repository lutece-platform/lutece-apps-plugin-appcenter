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
 * This class provides Data Access methods for ActionRole objects
 */
public final class ActionRoleDAO implements IActionRoleDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_action, code, label, resource_type FROM appcenter_action_role WHERE id_action = ?";
    private static final String SQL_QUERY_SELECT_BY_CODE_AND_PROFILE_AND_RESOURCE = "SELECT a.id_action, a.code, a.label, a.resource_type FROM appcenter_action_role a, appcenter_action_role_profile ap WHERE  a.code = ap.code_action_role and ap.code_action_role = ? and ap.code_profile= ? and ap.code_resource in ( '*', ? ) ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_action_role ( code, label, resource_type ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_action_role WHERE id_action = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_action_role SET id_action = ?, code = ?, label = ?, resource_type = ? WHERE id_action = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_action, code, label, resource_type FROM appcenter_action_role";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_action FROM appcenter_action_role";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( ActionRole actionRole, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , actionRole.getCode( ) );
            daoUtil.setString( nIndex++ , actionRole.getLabel( ) );
            daoUtil.setString( nIndex++ , actionRole.getResourceType( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                actionRole.setId( daoUtil.getGeneratedKeyInt( 1 ) );
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
    public ActionRole load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        ActionRole actionRole = null;

        if ( daoUtil.next( ) )
        {
            actionRole = new ActionRole();
            int nIndex = 1;
            
            actionRole.setId( daoUtil.getInt( nIndex++ ) );
            actionRole.setCode( daoUtil.getString( nIndex++ ) );
            actionRole.setLabel( daoUtil.getString( nIndex++ ) );
            actionRole.setResourceType( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return actionRole;
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
    public void store( ActionRole actionRole, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , actionRole.getId( ) );
        daoUtil.setString( nIndex++ , actionRole.getCode( ) );
        daoUtil.setString( nIndex++ , actionRole.getLabel( ) );
        daoUtil.setString( nIndex++ , actionRole.getResourceType( ) );
        daoUtil.setInt( nIndex , actionRole.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ActionRole> selectActionRolesList( Plugin plugin )
    {
        List<ActionRole> actionRoleList = new ArrayList<ActionRole>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            ActionRole actionRole = new ActionRole(  );
            int nIndex = 1;
            
            actionRole.setId( daoUtil.getInt( nIndex++ ) );
            actionRole.setCode( daoUtil.getString( nIndex++ ) );
            actionRole.setLabel( daoUtil.getString( nIndex++ ) );
            actionRole.setResourceType( daoUtil.getString( nIndex++ ) );

            actionRoleList.add( actionRole );
        }

        daoUtil.free( );
        return actionRoleList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdActionRolesList( Plugin plugin )
    {
        List<Integer> actionRoleList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            actionRoleList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return actionRoleList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectActionRolesReferenceList( Plugin plugin )
    {
        ReferenceList actionRoleList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            actionRoleList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return actionRoleList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ActionRole loadByCodeAndProfileAndResource(String codeActionRole, int nProfileId, String strResource, Plugin plugin) 
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_CODE_AND_PROFILE_AND_RESOURCE, plugin );
        daoUtil.setString( 1 , codeActionRole );
        daoUtil.setInt( 2, nProfileId );
        daoUtil.setString( 3, strResource );
        
        daoUtil.executeQuery( );
        ActionRole actionRole = null;

        if ( daoUtil.next( ) )
        {
            actionRole = new ActionRole();
            int nIndex = 1;
            
            actionRole.setId( daoUtil.getInt( nIndex++ ) );
            actionRole.setCode( daoUtil.getString( nIndex++ ) );
            actionRole.setLabel( daoUtil.getString( nIndex++ ) );
            actionRole.setResourceType( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return actionRole;
    }

}
