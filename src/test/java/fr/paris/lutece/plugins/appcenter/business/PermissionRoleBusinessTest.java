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

import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.business.rbac.RBACHome;
import fr.paris.lutece.test.LuteceTestCase;

/**
 * This is the business class test for the object PermissionRole
 */
public class PermissionRoleBusinessTest extends LuteceTestCase
{
    private static final String CODEACTIONROLE1 = "CodePermission1";
    private static final String CODEACTIONROLE2 = "CodePermission2";
    private static final String CODEROLE1 = "appcenter_projet_manager";
    private static final String CODEROLE2 = "appcenter_project_manager_deleg";
    private static final String CODERESOURCE1 = "CodeResource1";
    private static final String CODERESOURCE2 = "CodeResource2";

    /**
     * test PermissionRole
     */
    public void testBusiness( )
    {
        // Initialize an object
        RBAC permissionRole = new RBAC( );
        permissionRole.setPermissionKey( CODEACTIONROLE1 );
        permissionRole.setRoleKey( CODEROLE1 );
        permissionRole.setResourceId( CODERESOURCE1 );

        // Create test
        int id = RBACHome.create( permissionRole ).getRBACId( );
        RBAC permissionRoleStored = RBACHome.findByPrimaryKey( id );
        assertEquals( permissionRoleStored.getPermissionKey( ), permissionRole.getPermissionKey( ) );
        assertEquals( permissionRoleStored.getRoleKey( ), permissionRole.getRoleKey( ) );
        assertEquals( permissionRoleStored.getResourceId( ), permissionRole.getResourceId( ) );

        // Update test
        permissionRole.setPermissionKey( CODEACTIONROLE2 );
        permissionRole.setRoleKey( CODEROLE2 );
        permissionRole.setResourceId( CODERESOURCE2 );
        permissionRole.setRBACId( id );
        permissionRoleStored = RBACHome.update( permissionRole );
        
        assertEquals( permissionRoleStored.getPermissionKey( ), permissionRole.getPermissionKey( ) );
        assertEquals( permissionRoleStored.getRoleKey( ), permissionRole.getRoleKey( ) );
        assertEquals( permissionRoleStored.getResourceId( ), permissionRole.getResourceId( ) );

        // List test
        RBACHome.findAll( );

        // Delete test
        RBACHome.remove( id );
        permissionRoleStored = RBACHome.findByPrimaryKey( id );
        assertNull( permissionRoleStored );

    }

}
