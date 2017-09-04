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

package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.User;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.util.ReferenceList;
import javax.servlet.http.HttpServletRequest;

/**
 * UserService
 */
public class UserService
{
    private static final String MOCK_USER = "john.doe@nowhere.com";

    /**
     * Get the list of available users
     * 
     * @return The list
     */
    public static ReferenceList getUserList( )
    {
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            ReferenceList list = new ReferenceList( );
            for ( LuteceUser user : SecurityService.getInstance( ).getUsers( ) )
            {
                list.addItem( user.getEmail( ), user.getEmail( ) );
            }
            return list;

        }
        else
        {
            return getMockUserList( );
        }
    }

    /**
     * Gets the Id of the current connected user
     * 
     * @param request
     *            The HTTP request
     * @return The user Id
     */
    public static String getCurrentUserId( HttpServletRequest request )
    {
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            LuteceUser user = SecurityService.getInstance( ).getRegisteredUser( request );
            return user.getEmail( );
        }
        else
        {
            return MOCK_USER;
        }

    }
    
    /**
     * Gets the current connected user
     * 
     * @param request
     *            The HTTP request
     * @param nApplicationId The applcation Id
     * @return The user Id
     */
    public static User getCurrentUser( HttpServletRequest request , int nApplicationId )
    {
        User user = new User();
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            LuteceUser luteceUser = SecurityService.getInstance( ).getRegisteredUser( request );
            if( luteceUser != null )
            {
                user.setId( luteceUser.getEmail( ) );
                int nRole = ApplicationHome.getUserRole( nApplicationId, luteceUser.getEmail( ) );
                boolean bAdmin = (nRole == RoleService.ROLE_ADMIN ) || ( nRole == RoleService.ROLE_OWNER );
                user.setAdmin( bAdmin );
                if( bAdmin )
                {
                    user.setDelegateRoles( RoleService.getRolesList() );
                }
            }
        }
        else
        {
            user.setId( MOCK_USER );
            user.setAdmin( true );
            user.setDelegateRoles( RoleService.getRolesList() );
        }
        return user;

    }

  

    /**
     * Mock list for dev and tests without MyLutece
     * 
     * @return a mock list
     */
    private static ReferenceList getMockUserList( )
    {
        ReferenceList list = new ReferenceList( );
        list.addItem( MOCK_USER, MOCK_USER );
        return list;
    }

}
