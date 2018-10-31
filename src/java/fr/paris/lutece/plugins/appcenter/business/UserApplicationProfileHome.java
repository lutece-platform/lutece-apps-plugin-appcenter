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
 * This class provides instances management methods (create, find, ...) for UserApplicationProfile objects
 */
public final class UserApplicationProfileHome
{
    // Static variable pointed at the DAO instance
    private static IUserApplicationProfileDAO _dao = SpringContextService.getBean( "appcenter.userApplicationProfileDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private UserApplicationProfileHome(  )
    {
    }

    /**
     * Create an instance of the userApplicationProfile class
     * @param userApplicationProfile The instance of the UserApplicationProfile which contains the informations to store
     * @return The  instance of userApplicationProfile which has been created with its primary key.
     */
    public static UserApplicationProfile create( UserApplicationProfile userApplicationProfile )
    {
        _dao.insert( userApplicationProfile, _plugin );

        return userApplicationProfile;
    }



    /**
     * Remove the userApplicationProfile whose identifier is specified in parameter
     * @param nProfileId
     * @param nApplicationId
     * @param strUserId
     */
    public static void remove( int nProfileId, int nApplicationId, String strUserId )
    {
        _dao.delete( nProfileId, nApplicationId, strUserId, _plugin );
    }

    /**
     * Returns an instance of a userApplicationProfile whose identifier is specified in parameter
     * @param nProfileId
     * @param nApplicationId
     * @param strUserId
     * @return an instance of UserApplicationProfile
     */
    public static UserApplicationProfile findByPrimaryKey( int nProfileId, int nApplicationId, String strUserId )
    {
        return _dao.load( nProfileId, nApplicationId, strUserId, _plugin );
    }

    /**
     * Load the data of all the userApplicationProfile objects and returns them as a list
     * @return the list which contains the data of all the userApplicationProfile objects
     */
    public static List<UserApplicationProfile> getUserApplicationProfilesList( )
    {
        return _dao.selectUserApplicationProfilesList( _plugin );
    }
    
    
    /**
     * Load the data of all the userApplicationProfile objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the userApplicationProfile objects
     */
    public static ReferenceList getUserApplicationProfilesReferenceList( )
    {
        return _dao.selectUserApplicationProfilesReferenceList( _plugin );
    }
}

