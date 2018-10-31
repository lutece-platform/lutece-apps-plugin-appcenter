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
 * This is the business class test for the object ActionRole
 */
public class ActionRoleBusinessTest extends LuteceTestCase
{
    private static final String CODE1 = "Code1";
    private static final String CODE2 = "Code2";
    private static final String LABEL1 = "Label1";
    private static final String LABEL2 = "Label2";
    private static final String RESOURCETYPE1 = "ResourceType1";
    private static final String RESOURCETYPE2 = "ResourceType2";

	/**
	* test ActionRole
	*/
    public void testBusiness(  )
    {
        // Initialize an object
        ActionRole actionRole = new ActionRole();
        actionRole.setCode( CODE1 );
        actionRole.setLabel( LABEL1 );
        actionRole.setResourceType( RESOURCETYPE1 );

        // Create test
        ActionRoleHome.create( actionRole );
        ActionRole actionRoleStored = ActionRoleHome.findByPrimaryKey( actionRole.getId( ) );
        assertEquals( actionRoleStored.getCode() , actionRole.getCode( ) );
        assertEquals( actionRoleStored.getLabel() , actionRole.getLabel( ) );
        assertEquals( actionRoleStored.getResourceType() , actionRole.getResourceType( ) );

        // Update test
        actionRole.setCode( CODE2 );
        actionRole.setLabel( LABEL2 );
        actionRole.setResourceType( RESOURCETYPE2 );
        ActionRoleHome.update( actionRole );
        actionRoleStored = ActionRoleHome.findByPrimaryKey( actionRole.getId( ) );
        assertEquals( actionRoleStored.getCode() , actionRole.getCode( ) );
        assertEquals( actionRoleStored.getLabel() , actionRole.getLabel( ) );
        assertEquals( actionRoleStored.getResourceType() , actionRole.getResourceType( ) );

        // List test
        ActionRoleHome.getActionRolesList();

        // Delete test
        ActionRoleHome.remove( actionRole.getId( ) );
        actionRoleStored = ActionRoleHome.findByPrimaryKey( actionRole.getId( ) );
        assertNull( actionRoleStored );
        
    }

}