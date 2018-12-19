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
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for Role objects
 */
public final class RoleHome
{
    // Static variable pointed at the DAO instance
    private static IRoleDAO _dao = SpringContextService.getBean( "appcenter.roleDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    public static final int GLOBAL_PERMISSION_WILDCARD_ID = 0 ;
    
    /**
     * Private constructor - this class need not be instantiated
     */
    private RoleHome(  )
    {
    }

    /**
     * Create an instance of the role class
     * @param role The instance of the Role which contains the informations to store
     * @return The  instance of role which has been created with its primary key.
     */
    public static Role create( Role role )
    {
        _dao.insert( role, _plugin );

        return role;
    }

    /**
     * Update of the role which is specified in parameter
     * @param role The instance of the Role which contains the data to store
     * @return The instance of the  role which has been updated
     */
    public static Role update( Role role )
    {
        _dao.store( role, _plugin );

        return role;
    }

    /**
     * Remove the role whose identifier is specified in parameter
     * @param nKey The role Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a role whose identifier is specified in parameter
     * @param nKey The role primary key
     * @return an instance of Role
     */
    public static Role findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    
    /**
     * Returns an instance of a role whose identifier is specified in parameter
     * @param strCode The role code
     * @return an instance of Role
     */
    public static Role findByCode( String strCode )
    {
        return _dao.loadByCode( strCode, _plugin );
    }

    /**
     * Returns an instance of a role whose identifier is specified in parameter
     * @param strUserId
     * @param nApplicationId
     * @return an instance of Role
     */
    public static Role findByUserIdAndApplicationId( String strUserId, int nApplicationId )
    {
        return _dao.loadByUserIdAndApplicationId( strUserId, nApplicationId, _plugin );
    }

    
    /**
     * Returns  a global role of the user
     * @param strUserId
     * @return an instance of Role
     */
    public static Role findGlobalRoleByUserId( String strUserId )
    {
        return _dao.loadByUserIdAndApplicationId( strUserId, GLOBAL_PERMISSION_WILDCARD_ID, _plugin );
    }            
            
    /**
     * Load the data of all the role objects and returns them as a list
     * @return the list which contains the data of all the role objects
     */
    public static List<Role> getRolesList( )
    {
        return _dao.selectRolesList( _plugin );
    }
    
    /**
     * Load the id of all the role objects and returns them as a list
     * @return the list which contains the id of all the role objects
     */
    public static List<Integer> getIdRolesList( )
    {
        return _dao.selectIdRolesList( _plugin );
    }
    
    /**
     * Load the data of all the role objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the role objects
     */
    public static ReferenceList getRolesReferenceList( )
    {
        return _dao.selectRolesReferenceList( _plugin );
    }
}

