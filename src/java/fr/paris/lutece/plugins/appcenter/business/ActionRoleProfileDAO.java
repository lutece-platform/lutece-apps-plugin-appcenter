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
 * This class provides Data Access methods for ActionRoleProfile objects
 */
public final class ActionRoleProfileDAO implements IActionRoleProfileDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT code_action_role, code_profile, code_resource FROM appcenter_action_role_profile WHERE code_action_role,  = ? and code_profile = ? and code_resource = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_action_role_profile ( code_action_role, code_profile, code_resource ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_action_role_profile WHERE code_action_role= ? and code_profile = ? and code_resource = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT code_action_role, code_profile, code_resource FROM appcenter_action_role_profile ORDER BY code_profile, code_action_role, code_resource ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( ActionRoleProfile actionRoleProfile, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , actionRoleProfile.getCodeActionRole( ) );
            daoUtil.setString( nIndex++ , actionRoleProfile.getCodeProfile( ) );
            daoUtil.setString( nIndex++ , actionRoleProfile.getCodeResource( ) );
            
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
    public ActionRoleProfile load( String strActionRoleCode, String strProfileCode, String strResourceCode,  Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString(1 , strActionRoleCode );
        daoUtil.setString(2 , strProfileCode );
        daoUtil.setString(3 , strResourceCode );
        
        daoUtil.executeQuery( );
        ActionRoleProfile actionRoleProfile = null;

        if ( daoUtil.next( ) )
        {
            actionRoleProfile = new ActionRoleProfile();
            int nIndex = 1;
            
            actionRoleProfile.setCodeActionRole( daoUtil.getString( nIndex++ ) );
            actionRoleProfile.setCodeProfile( daoUtil.getString( nIndex++ ) );
            actionRoleProfile.setCodeResource( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return actionRoleProfile;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( String strActionRoleCode, String strProfileCode, String strResourceCode,  Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        
        daoUtil.setString(1 , strActionRoleCode );
        daoUtil.setString(2 , strProfileCode );
        daoUtil.setString(3 , strResourceCode );
        
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public List<ActionRoleProfile> selectActionRoleProfilesList( Plugin plugin )
    {
        List<ActionRoleProfile> actionRoleProfileList = new ArrayList<ActionRoleProfile>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            ActionRoleProfile actionRoleProfile = new ActionRoleProfile(  );
            int nIndex = 1;
            
            actionRoleProfile.setCodeActionRole( daoUtil.getString( nIndex++ ) );
            actionRoleProfile.setCodeProfile( daoUtil.getString( nIndex++ ) );
            actionRoleProfile.setCodeResource( daoUtil.getString( nIndex++ ) );

            actionRoleProfileList.add( actionRoleProfile );
        }

        daoUtil.free( );
        return actionRoleProfileList;
    }
    
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectActionRoleProfilesReferenceList( Plugin plugin )
    {
        ReferenceList actionRoleProfileList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            actionRoleProfileList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return actionRoleProfileList;
    }
}
