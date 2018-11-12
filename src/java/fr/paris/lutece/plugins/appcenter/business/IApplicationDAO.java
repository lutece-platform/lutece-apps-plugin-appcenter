/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
 * IApplicationDAO Interface
 */
public interface IApplicationDAO
{
    /**
     * Insert a new record in the table.
     * 
     * @param application
     *            instance of the Application object to insert
     * @param plugin
     *            the Plugin
     */
    void insert( Application application, Plugin plugin );

    /**
     * Update the record in the table
     * 
     * @param application
     *            the reference of the Application
     * @param plugin
     *            the Plugin
     */
    void store( Application application, Plugin plugin );

    /**
     * Delete a record from the table
     * 
     * @param nKey
     *            The identifier of the Application to delete
     * @param plugin
     *            the Plugin
     */
    void delete( int nKey, Plugin plugin );

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * 
     * @param nKey
     *            The identifier of the application
     * @param plugin
     *            the Plugin
     * @return The instance of the application
     */
    Application load( int nKey, Plugin plugin );

    /**
     * Load the data of all the application objects and returns them as a list
     * 
     * @param plugin
     *            the Plugin
     * @return The list which contains the data of all the application objects
     */
    List<Application> selectApplicationsList( Plugin plugin );

    /**
     * Load the data of all the application objects and returns them as a referenceList
     * 
     * @param plugin
     *            the Plugin
     * @return The referenceList which contains the data of all the application objects
     */
    ReferenceList selectApplicationsReferenceList( Plugin plugin );

    /**
     * Get the role of the user for a given application
     * 
     * @param nApplicationId
     *            The application Id
     * @param strUserId
     *            The user Id
     * @param plugin
     *            The plugin
     * @param nDefaultRole 
     *            The default role
     * @return The role
     */
    int getUserRole( int nApplicationId, String strUserId, int nDefaultRole, Plugin plugin );

    /**
     * Update of the application data
     * 
     * @param nApplicationId
     *            The application ID
     * 
     *            The instance of the Application which contains the data to store
     * @param strData
     *            The data
     * @param plugin
     *            The plugin
     */
    void storeData( int nApplicationId, String strData, Plugin plugin );

}
