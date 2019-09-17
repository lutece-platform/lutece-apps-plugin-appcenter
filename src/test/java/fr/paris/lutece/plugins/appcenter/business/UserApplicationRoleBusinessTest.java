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

import fr.paris.lutece.test.LuteceTestCase;

/**
 * This is the business class test for the object UserApplicationRole
 */
public class UserApplicationRoleBusinessTest extends LuteceTestCase
{
    private static final int IDROLE1 = 1;
    private static final int IDROLE2 = 2;
    private static final int IDAPPLICATION1 = 1;
    private static final int IDAPPLICATION2 = 2;
    private static final String IDUSER1 = "user1";
    private static final String IDUSER2 = "user2";

    /**
     * test UserApplicationRole
     */
    public void testBusiness( )
    {
        // Initialize an object
        UserApplicationRole userApplicationRole = new UserApplicationRole( );
        userApplicationRole.setIdRole( IDROLE1 );
        userApplicationRole.setIdApplication( IDAPPLICATION1 );
        userApplicationRole.setIdUser( IDUSER1 );

        // Create test
        UserApplicationRoleHome.create( userApplicationRole );
        UserApplicationRole userApplicationRoleStored = UserApplicationRoleHome.findByPrimaryKey( userApplicationRole.getIdRole( ),
                userApplicationRole.getIdApplication( ), userApplicationRole.getIdUser( ) );
        assertEquals( userApplicationRoleStored.getIdRole( ), userApplicationRole.getIdRole( ) );
        assertEquals( userApplicationRoleStored.getIdApplication( ), userApplicationRole.getIdApplication( ) );
        assertEquals( userApplicationRoleStored.getIdUser( ), userApplicationRole.getIdUser( ) );

        // List test
        UserApplicationRoleHome.getUserApplicationRolesList( );

        // Delete test
        UserApplicationRoleHome.remove( userApplicationRole.getIdRole( ), userApplicationRole.getIdApplication( ), userApplicationRole.getIdUser( ) );
        userApplicationRoleStored = UserApplicationRoleHome.findByPrimaryKey( userApplicationRole.getIdRole( ), userApplicationRole.getIdApplication( ),
                userApplicationRole.getIdUser( ) );
        assertNull( userApplicationRoleStored );

    }

}
