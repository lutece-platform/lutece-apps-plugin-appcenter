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
 * This class provides instances management methods (create, find, ...) for Profile objects
 */
public final class ProfileHome
{
    // Static variable pointed at the DAO instance
    private static IProfileDAO _dao = SpringContextService.getBean( "appcenter.profileDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ProfileHome(  )
    {
    }

    /**
     * Create an instance of the profile class
     * @param profile The instance of the Profile which contains the informations to store
     * @return The  instance of profile which has been created with its primary key.
     */
    public static Profile create( Profile profile )
    {
        _dao.insert( profile, _plugin );

        return profile;
    }

    /**
     * Update of the profile which is specified in parameter
     * @param profile The instance of the Profile which contains the data to store
     * @return The instance of the  profile which has been updated
     */
    public static Profile update( Profile profile )
    {
        _dao.store( profile, _plugin );

        return profile;
    }

    /**
     * Remove the profile whose identifier is specified in parameter
     * @param nKey The profile Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a profile whose identifier is specified in parameter
     * @param nKey The profile primary key
     * @return an instance of Profile
     */
    public static Profile findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns an instance of a profile whose identifier is specified in parameter
     * @param nUserId
     * @return an instance of Profile
     */
    public static Profile findByUserIdAndApplicationId( String strUserId, int nApplicationId )
    {
        return _dao.loadByUserIdAndApplicationId( strUserId, nApplicationId, _plugin );
    }
            
    /**
     * Load the data of all the profile objects and returns them as a list
     * @return the list which contains the data of all the profile objects
     */
    public static List<Profile> getProfilesList( )
    {
        return _dao.selectProfilesList( _plugin );
    }
    
    /**
     * Load the id of all the profile objects and returns them as a list
     * @return the list which contains the id of all the profile objects
     */
    public static List<Integer> getIdProfilesList( )
    {
        return _dao.selectIdProfilesList( _plugin );
    }
    
    /**
     * Load the data of all the profile objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the profile objects
     */
    public static ReferenceList getProfilesReferenceList( )
    {
        return _dao.selectProfilesReferenceList( _plugin );
    }
}

