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
 * This is the business class test for the object ActionRoleProfile
 */
public class ActionRoleProfileBusinessTest extends LuteceTestCase
{
    private static final String CODEACTIONROLE1 = "CodeActionRole1";
    private static final String CODEACTIONROLE2 = "CodeActionRole2";
    private static final String CODEPROFILE1 = "CodeProfile1";
    private static final String CODEPROFILE2 = "CodeProfile2";
    private static final String CODERESOURCE1 = "CodeResource1";
    private static final String CODERESOURCE2 = "CodeResource2";

	/**
	* test ActionRoleProfile
	*/
    public void testBusiness(  )
    {
        // Initialize an object
        ActionRoleProfile actionRoleProfile = new ActionRoleProfile();
        actionRoleProfile.setCodeActionRole( CODEACTIONROLE1 );
        actionRoleProfile.setCodeProfile( CODEPROFILE1 );
        actionRoleProfile.setCodeResource( CODERESOURCE1 );

        // Create test
        ActionRoleProfileHome.create( actionRoleProfile );
        ActionRoleProfile actionRoleProfileStored = ActionRoleProfileHome.findByPrimaryKey( actionRoleProfile.getCodeActionRole( ) , actionRoleProfile.getCodeProfile( ), actionRoleProfile.getCodeResource( )  );
        assertEquals( actionRoleProfileStored.getCodeActionRole() , actionRoleProfile.getCodeActionRole( ) );
        assertEquals( actionRoleProfileStored.getCodeProfile() , actionRoleProfile.getCodeProfile( ) );
        assertEquals( actionRoleProfileStored.getCodeResource() , actionRoleProfile.getCodeResource( ) );

        // Update test
        actionRoleProfile.setCodeActionRole( CODEACTIONROLE2 );
        actionRoleProfile.setCodeProfile( CODEPROFILE2 );
        actionRoleProfile.setCodeResource( CODERESOURCE2 );
        actionRoleProfileStored = ActionRoleProfileHome.findByPrimaryKey( actionRoleProfile.getCodeActionRole( ) , actionRoleProfile.getCodeProfile( ), actionRoleProfile.getCodeResource( )  );
        assertEquals( actionRoleProfileStored.getCodeActionRole() , actionRoleProfile.getCodeActionRole( ) );
        assertEquals( actionRoleProfileStored.getCodeProfile() , actionRoleProfile.getCodeProfile( ) );
        assertEquals( actionRoleProfileStored.getCodeResource() , actionRoleProfile.getCodeResource( ) );

        // List test
        ActionRoleProfileHome.getActionRoleProfilesList();

        // Delete test
        ActionRoleProfileHome.remove( actionRoleProfile.getCodeActionRole( ) , actionRoleProfile.getCodeProfile( ), actionRoleProfile.getCodeResource( ) );
        actionRoleProfileStored = ActionRoleProfileHome.findByPrimaryKey( actionRoleProfile.getCodeActionRole( ) , actionRoleProfile.getCodeProfile( ), actionRoleProfile.getCodeResource( ) );
        assertNull( actionRoleProfileStored );
        
    }

}