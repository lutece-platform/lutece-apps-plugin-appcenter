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

import fr.paris.lutece.test.LuteceTestCase;


/**
 * This is the business class test for the object PermissionRole
 */
public class PermissionRoleBusinessTest extends LuteceTestCase
{
    private static final String CODEACTIONROLE1 = "CodePermission1";
    private static final String CODEACTIONROLE2 = "CodePermission2";
    private static final String CODEROLE1 = "CodeRole1";
    private static final String CODEROLE2 = "CodeRole2";
    private static final String CODERESOURCE1 = "CodeResource1";
    private static final String CODERESOURCE2 = "CodeResource2";

	/**
	* test PermissionRole
	*/
    public void testBusiness(  )
    {
        // Initialize an object
        PermissionRole permissionRole = new PermissionRole();
        permissionRole.setCodePermission( CODEACTIONROLE1 );
        permissionRole.setCodeRole( CODEROLE1 );
        permissionRole.setCodeResource( CODERESOURCE1 );

        // Create test
        PermissionRoleHome.create( permissionRole );
        PermissionRole permissionRoleStored = PermissionRoleHome.findByPrimaryKey( permissionRole.getCodePermission( ) , permissionRole.getCodeRole( ), permissionRole.getCodeResource( )  );
        assertEquals( permissionRoleStored.getCodePermission() , permissionRole.getCodePermission( ) );
        assertEquals( permissionRoleStored.getCodeRole() , permissionRole.getCodeRole( ) );
        assertEquals( permissionRoleStored.getCodeResource() , permissionRole.getCodeResource( ) );

        // Update test
        permissionRole.setCodePermission( CODEACTIONROLE2 );
        permissionRole.setCodeRole( CODEROLE2 );
        permissionRole.setCodeResource( CODERESOURCE2 );
        permissionRoleStored = PermissionRoleHome.findByPrimaryKey( permissionRole.getCodePermission( ) , permissionRole.getCodeRole( ), permissionRole.getCodeResource( )  );
        assertEquals( permissionRoleStored.getCodePermission() , permissionRole.getCodePermission( ) );
        assertEquals( permissionRoleStored.getCodeRole() , permissionRole.getCodeRole( ) );
        assertEquals( permissionRoleStored.getCodeResource() , permissionRole.getCodeResource( ) );

        // List test
        PermissionRoleHome.getPermissionRolesList();

        // Delete test
        PermissionRoleHome.remove( permissionRole.getCodePermission( ) , permissionRole.getCodeRole( ), permissionRole.getCodeResource( ) );
        permissionRoleStored = PermissionRoleHome.findByPrimaryKey( permissionRole.getCodePermission( ) , permissionRole.getCodeRole( ), permissionRole.getCodeResource( ) );
        assertNull( permissionRoleStored );
        
    }

}