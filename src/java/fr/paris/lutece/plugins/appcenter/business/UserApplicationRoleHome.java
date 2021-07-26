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
 * This class provides instances management methods (create, find, ...) for UserApplicationRole objects
 */
public final class UserApplicationRoleHome
{
    // Static variable pointed at the DAO instance
    private static IUserApplicationRoleDAO _dao = SpringContextService.getBean( "appcenter.userApplicationRoleDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private UserApplicationRoleHome( )
    {
    }

    /**
     * Create an instance of the userApplicationRole class
     * 
     * @param userApplicationRole
     *            The instance of the UserApplicationRole which contains the informations to store
     * @return The instance of userApplicationRole which has been created with its primary key.
     */
    public static UserApplicationRole create( UserApplicationRole userApplicationRole )
    {
        _dao.insert( userApplicationRole, _plugin );

        return userApplicationRole;
    }

    /**
     * Update of the userApplicationRole which is specified in parameter
     * 
     * @param userApplicationRole
     *            The instance of the userApplicationRole which contains the data to store
     * @return The instance of the userApplicationRole which has been updated
     */
    public static UserApplicationRole update( UserApplicationRole userApplicationRoleOld, UserApplicationRole userApplicationRole )
    {
        _dao.store( userApplicationRoleOld, userApplicationRole, _plugin );

        return userApplicationRole;
    }

    /**
     * Remove the userApplicationRole whose identifier is specified in parameter
     * 
     * @param strRoleId
     * @param nApplicationId
     * @param strUserId
     */
    public static void remove( String strRoleId, int nApplicationId, String strUserId )
    {
        _dao.delete( strRoleId, nApplicationId, strUserId, _plugin );
    }

    /**
     * Remove all the userApplicationRoles of a user
     * 
     * @param strUserId
     */
    public static void removeByIdUser( String strUserId )
    {
        _dao.deleteByIdUser( strUserId, _plugin );
    }

    /**
     * Remove the userApplicationRole whose identifier is specified in parameter
     * 
     * @param nApplicationId
     * @param strUserId
     */
    public static void removeByApplicationIdAndUserId( int nApplicationId, String strUserId )
    {
        _dao.deleteByApplicationIdAndUserId( nApplicationId, strUserId, _plugin );
    }

    /**
     * Returns an instance of a userApplicationRole whose identifier is specified in parameter
     * 
     * @param strRoleId
     * @param nApplicationId
     * @param strUserId
     * @return an instance of UserApplicationRole
     */
    public static UserApplicationRole findByPrimaryKey( String strRoleId, int nApplicationId, String strUserId )
    {
        return _dao.load( strRoleId, nApplicationId, strUserId, _plugin );
    }

    /**
     * Load the data of all the userApplicationRole objects and returns them as a list
     * 
     * @return the list which contains the data of all the userApplicationRole objects
     */
    public static List<UserApplicationRole> getUserApplicationRolesList( )
    {
        return _dao.selectUserApplicationRolesList( _plugin );
    }

    /**
     * Load the data of a selection of userApplicationRole objects and returns them as a list
     * 
     * @return the list which contains the data of the selection of userApplicationRole objects
     */
    public static List<UserApplicationRole> getUserApplicationRolesListByFilter( UserApplicationRoleFilter filter )
    {
        return _dao.selectUserApplicationRolesListByFilter( filter, _plugin );
    }

    /**
     * Load the data of all the userApplicationRole objects by id user and returns them as a list
     * 
     * @return the list which contains the data of all the userApplicationRole objects by id user
     */
    public static List<UserApplicationRole> getUserApplicationRolesListByIdUser( String strIdUser )
    {
        return _dao.selectUserApplicationRolesListByIdUser( strIdUser, _plugin );
    }

    /**
     * Load the data of all the userApplicationRole objects by id application and returns them as a list
     * 
     * @return the list which contains the data of all the userApplicationRole objects by id application
     */
    public static List<UserApplicationRole> getUserApplicationRolesListByIdApplication( int nIdApplication )
    {
        return _dao.selectUserApplicationRolesListByIdApplication( nIdApplication, _plugin );
    }

    /**
     * Load the data of all the userApplicationRole objects by id application and returns them as a list
     * 
     * @return the list which contains the data of all the userApplicationRole objects by id application
     */
    public static List<UserApplicationRole> getUserApplicationRolesListByIdApplicationAndIdUser( int nIdApplication, String strIdUser )
    {
        return _dao.selectUserApplicationRolesListByIdApplicationAndIdUser( nIdApplication, strIdUser, _plugin );
    }

    /**
     * Load the data of all the userApplicationRole objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the userApplicationRole objects
     */
    public static ReferenceList getUserApplicationRolesReferenceList( )
    {
        return _dao.selectUserApplicationRolesReferenceList( _plugin );
    }

    /**
     * Load the id of all the user and returns them as a referenceList
     * 
     * @return the referenceList which contains the id of all the user
     */
    public static ReferenceList getIdUserReferenceList( )
    {
        return _dao.selectIdUserReferenceList( _plugin );
    }
}
