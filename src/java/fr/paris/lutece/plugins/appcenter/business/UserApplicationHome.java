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
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for UserApplication objects
 */
public final class UserApplicationHome
{
    // Static variable pointed at the DAO instance
    private static IUserApplicationDAO _dao = SpringContextService.getBean( "appcenter.userApplicationDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private UserApplicationHome( )
    {
    }

    /**
     * Create an instance of the userApplication class
     * 
     * @param userApplication
     *            The instance of the UserApplication which contains the informations to store
     * @return The instance of userApplication which has been created with its primary key.
     */
    public static UserApplication create( UserApplication userApplication )
    {
        _dao.insert( userApplication, _plugin );

        return userApplication;
    }

    /**
     * Update of the userApplication which is specified in parameter
     * 
     * @param userApplication
     *            The instance of the UserApplication which contains the data to store
     * @return The instance of the userApplication which has been updated
     */
    public static UserApplication update( UserApplication userApplication )
    {
        _dao.store( userApplication, _plugin );

        return userApplication;
    }

    /**
     * Remove the userApplication whose identifier is specified in parameter
     * 
     * @param nKey
     *            The userApplication Id
     * @param strUserId
     *            The user ID
     */
    public static void remove( int nKey , String strUserId )
    {
        _dao.delete( nKey, strUserId, _plugin );
    }

    /**
     * Returns an instance of a userApplication whose identifier is specified in parameter
     * 
     * @param nKey
     *            The application primary key
     * @param strUserId
     *            The user ID
     * @return an instance of UserApplication
     */
    public static UserApplication findByPrimaryKey( int nKey , String strUserId )
    {
        return _dao.load( nKey, strUserId , _plugin );
    }

    /**
     * Load the data of all the userApplication objects and returns them as a list
     * 
     * @return the list which contains the data of all the userApplication objects
     */
    public static List<UserApplication> getUserApplicationsList( )
    {
        return _dao.selectUserApplicationsList( _plugin );
    }

    /**
     * Load the id of all the userApplication objects and returns them as a list
     * 
     * @return the list which contains the id of all the userApplication objects
     */
    public static List<Integer> getIdUserApplicationsList( )
    {
        return _dao.selectIdUserApplicationsList( _plugin );
    }

    /**
     * Load the data of all the userApplication objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the userApplication objects
     */
    public static ReferenceList getUserApplicationsReferenceList( )
    {
        return _dao.selectUserApplicationsReferenceList( _plugin );
    }
}
