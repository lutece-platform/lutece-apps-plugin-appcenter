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
import java.util.List;

/**
 * IUserApplicationRoleDAO Interface
 */
public interface IUserApplicationRoleDAO
{
    /**
     * Insert a new record in the table.
     * 
     * @param userApplicationRole
     *            instance of the UserApplicationRole object to insert
     * @param plugin
     *            the Plugin
     */
    void insert( UserApplicationRole userApplicationRole, Plugin plugin );

    /**
     * Update the record in the table
     * 
     * @param userApplicationRoleOld
     *            the reference of the former UserApplicationRole
     * @param userApplicationRole
     *            the reference of the new UserApplicationRole
     * @param plugin
     *            the Plugin
     */
    void store( UserApplicationRole userApplicationRoleOld, UserApplicationRole userApplicationRole, Plugin plugin );

    /**
     * Delete a record from the table
     * 
     * @param nRoleId
     * @param nApplicationId
     * @param strUserId
     * @param plugin
     *            the Plugin
     */
    void delete( int nRoleId, int nApplicationId, String strUserId, Plugin plugin );

    /**
     * Delete records from the table, identified by given id user
     * 
     * @param strUserId
     * @param plugin
     *            the Plugin
     */
    void deleteByIdUser( String strUserId, Plugin plugin );

    /**
     * Delete records from the table, identified by given id user and id application
     * 
     * @param nApplicationId
     *            The id application
     * @param strUserId
     *            The user id
     * @param plugin
     *            The plugin
     */
    void deleteByApplicationIdAndUserId( int nApplicationId, String strUserId, Plugin plugin );

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * 
     * @param nRoleId
     * @param nApplicationId
     * @param strUserId
     * @param plugin
     *            the Plugin
     * @return The instance of the userApplicationRole
     */
    UserApplicationRole load( int nRoleId, int nApplicationId, String strUserId, Plugin plugin );

    /**
     * Load the data of all the userApplicationRole objects and returns them as a list
     * 
     * @param plugin
     *            the Plugin
     * @return The list which contains the data of all the userApplicationRole objects
     */
    List<UserApplicationRole> selectUserApplicationRolesList( Plugin plugin );

    /**
     * Load the data of a selection of userApplicationRole objects and returns them as a list
     * 
     * @param plugin
     *            the Plugin
     * @return The list which contains the data of the selection of userApplicationRole objects
     */
    List<UserApplicationRole> selectUserApplicationRolesListByFilter( UserApplicationRoleFilter filter, Plugin plugin );

    /**
     * Load the data of all the userApplicationRole objects by id user and returns them as a list
     * 
     * @param nIdUser
     * @param plugin
     *            the Plugin
     * @return The list which contains the data of all the userApplicationRole objects by id user
     */
    List<UserApplicationRole> selectUserApplicationRolesListByIdUser( String nIdUser, Plugin plugin );

    /**
     * Load the data of all the userApplicationRole objects by id user and returns them as a list
     * 
     * @param nIdApplication
     * @param plugin
     *            the Plugin
     * @return The list which contains the data of all the userApplicationRole objects by id user
     */
    List<UserApplicationRole> selectUserApplicationRolesListByIdApplication( int strIdApplication, Plugin plugin );

    /**
     * Load the data of all the userApplicationRole objects by id user and returns them as a list
     * 
     * @param nIdApplication
     * @param plugin
     *            the Plugin
     * @return The list which contains the data of all the userApplicationRole objects by id user
     */
    List<UserApplicationRole> selectUserApplicationRolesListByIdApplicationAndIdUser( int strIdApplication, String strIdUser, Plugin plugin );

    /**
     * Load the data of all the userApplicationRole objects and returns them as a referenceList
     * 
     * @param plugin
     *            the Plugin
     * @return The referenceList which contains the data of all the userApplicationRole objects
     */
    ReferenceList selectUserApplicationRolesReferenceList( Plugin plugin );

    /**
     * Load the id of all the user and returns them as a referenceList
     * 
     * @param plugin
     *            the Plugin
     * @return The referenceList which contains the id of all the user
     */
    ReferenceList selectIdUserReferenceList( Plugin plugin );
}
