/*
 * Copyright (c) 2002-2019, Mairie de Paris
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
 * This class provides instances management methods (create, find, ...) for PermissionRole objects
 */
public final class PermissionRoleHome
{
    // Static variable pointed at the DAO instance
    private static IPermissionRoleDAO _dao = SpringContextService.getBean( "appcenter.permissionRoleDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private PermissionRoleHome( )
    {
    }

    /**
     * Create an instance of the permissionRole class
     * 
     * @param permissionRole
     *            The instance of the PermissionRole which contains the informations to store
     * @return The instance of permissionRole which has been created with its primary key.
     */
    public static PermissionRole create( PermissionRole permissionRole )
    {
        _dao.insert( permissionRole, _plugin );

        return permissionRole;
    }

    /**
     * Remove the permissionRole whose identifier is specified in parameter
     * 
     * @param strPermissionCode
     * @param nIdRole
     * @param strResourceCode
     */
    public static void remove( String strPermissionCode, int nIdRole, String strResourceCode )
    {
        _dao.delete( strPermissionCode, nIdRole, strResourceCode, _plugin );
    }

    /**
     * Returns an instance of a permissionRole whose identifier is specified in parameter
     * 
     * @param strPermissionCode
     * @param nIdRole
     * @param strResourceCode
     * @return an instance of PermissionRole
     */
    public static PermissionRole findByPrimaryKey( String strPermissionCode, int nIdRole, String strResourceCode )
    {
        return _dao.load( strPermissionCode, nIdRole, strResourceCode, _plugin );
    }

    /**
     * Load the data of all the permissionRole objects and returns them as a list
     * 
     * @return the list which contains the data of all the permissionRole objects
     */
    public static List<PermissionRole> getPermissionRolesList( )
    {
        return _dao.selectPermissionRolesList( _plugin );
    }

    /**
     * Load the data of all the permissionRole objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the permissionRole objects
     */
    public static ReferenceList getPermissionRolesReferenceList( )
    {
        return _dao.selectPermissionRolesReferenceList( _plugin );
    }

    /**
     * Load the data of all the permissionRole objects by id role and returns them as a list
     * 
     * @param nIdRole
     *            The id Role
     * @return the list which contains the data of all the permissionRole objects by id role
     */
    public static List<PermissionRole> getPermissionRolesListByIdRole( int nIdRole )
    {
        return _dao.selectPermissionRolesListByIdRole( nIdRole, _plugin );
    }

    /**
     * Load the data of all the permissionRole objects by id role and returns them as a list
     * 
     * @param strPermissionCode
     * @param nIdRole
     *            The id Role
     * @return the list which contains the data of all the permissionRole objects by id role
     */
    public static List<PermissionRole> getPermissionRolesListByCodeAndIdRole( String strPermissionCode, int nIdRole )
    {
        return _dao.selectPermissionRolesListByCodeAndIdRole( strPermissionCode, nIdRole, _plugin );
    }
}
