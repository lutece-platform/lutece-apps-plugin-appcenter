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
 * This class provides instances management methods (create, find, ...) for Permission objects
 */
public final class PermissionHome
{
    // Static variable pointed at the DAO instance
    private static IPermissionDAO _dao = SpringContextService.getBean( "appcenter.permissionDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private PermissionHome(  )
    {
    }

    /**
     * Create an instance of the permission class
     * @param permission The instance of the Permission which contains the informations to store
     * @return The  instance of permission which has been created with its primary key.
     */
    public static Permission create( Permission permission )
    {
        _dao.insert( permission, _plugin );

        return permission;
    }

    /**
     * Update of the permission which is specified in parameter
     * @param permission The instance of the Permission which contains the data to store
     * @return The instance of the  permission which has been updated
     */
    public static Permission update( Permission permission )
    {
        _dao.store( permission, _plugin );

        return permission;
    }

    /**
     * Remove the permission whose identifier is specified in parameter
     * @param nKey The permission Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a permission whose identifier is specified in parameter
     * @param nKey The permission primary key
     * @return an instance of Permission
     */
    public static Permission findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns an instance of a permission whose identifier is specified in parameter
     * @param nKey The permission primary key
     * @return an instance of Permission
     */
    public static Permission findByCodeAndRoleAndResource( String codePermission, int nRoleId, String strResource )
    {
        return _dao.loadByCodeAndRoleAndResource( codePermission, nRoleId, strResource , _plugin );
    }
    
    /**
     * Load the data of all the permission objects and returns them as a list
     * @return the list which contains the data of all the permission objects
     */
    public static List<Permission> getPermissionsList( )
    {
        return _dao.selectPermissionsList( _plugin );
    }
    
    /**
     * Load the id of all the permission objects and returns them as a list
     * @return the list which contains the id of all the permission objects
     */
    public static List<Integer> getIdPermissionsList( )
    {
        return _dao.selectIdPermissionsList( _plugin );
    }
    
    /**
     * Load the data of all the permission objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the permission objects
     */
    public static ReferenceList getPermissionsReferenceList( )
    {
        return _dao.selectPermissionsReferenceList( _plugin );
    }
}

