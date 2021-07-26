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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.paris.lutece.plugins.appcenter.business.UserApplicationRole;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRoleHome;
import fr.paris.lutece.portal.business.rbac.RBACRole;
import fr.paris.lutece.portal.business.rbac.RBACRoleHome;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

public class RoleService
{
    private static final String PROPERTY_ID_APP_ROLE_OWNER = "appcenter.role.appOwner.code";
    private static final int GLOBAL_PERMISSION_WILDCARD_ID = 0;
    private static final String PREFIX_APPCENTER_ROLE = "appcenter_";
    
    public static RBACRole getAppOwnerRole( )
    {
        String strIdOwnerRole = AppPropertiesService.getProperty( PROPERTY_ID_APP_ROLE_OWNER, "app_owner" );
        return RBACRoleHome.findByPrimaryKey(strIdOwnerRole );
    }
    
    /**
     * 
     * @return front role
     */
    public static ReferenceList getRolesReferenceList()
    {
        ReferenceList referenceList = new ReferenceList( );
         
        for ( ReferenceItem item :  RBACRoleHome.getRolesList( ) )
        {
            if( item.getCode( ).contains( PREFIX_APPCENTER_ROLE ) )
            {
                referenceList.add( item );        
            }
        }
        
        return referenceList;
    }
    
    /**
     * Returns a global role of the user
     * @param strUserId
     * @return
     */
    public static RBACRole getGlobalRoleByUserId( String strUserId )
    {
        
        List<UserApplicationRole> listUserApplicationRole = UserApplicationRoleHome.getUserApplicationRolesListByIdApplicationAndIdUser( GLOBAL_PERMISSION_WILDCARD_ID, strUserId );
        if( !listUserApplicationRole.isEmpty( ) )
        {
            UserApplicationRole userApplicationRole = listUserApplicationRole.get( 0 );
            return RBACRoleHome.findByPrimaryKey( userApplicationRole.getIdRole( ) );
        }
        
        return null;
    }
    
    /**
     * Returns an instance of a role whose identifier is specified in parameter
     * 
     * @param strUserId
     * @param nApplicationId
     * @return an instance of Role
     */
    public static RBACRole getByUserIdAndApplicationId( String strUserId, int nApplicationId )
    {
        List<UserApplicationRole> listUserApplicationRole = UserApplicationRoleHome.getUserApplicationRolesListByIdApplicationAndIdUser( nApplicationId, strUserId );
        
        if( !listUserApplicationRole.isEmpty( ) )
        {
            UserApplicationRole userApplicationRole = listUserApplicationRole.get( 0 );
            return RBACRoleHome.findByPrimaryKey( userApplicationRole.getIdRole( ) );
        }
        
        return null;
    }
    
    /**
     * Load the data of all the role objects and returns them as a map
     * 
     * @return the map which contains the data of all the role objects
     */
    public static Map<String, RBACRole> getRolesMap( )
    {
        Map<String, RBACRole> rolesMap = new HashMap<>( );
        
        for ( RBACRole role :  RBACRoleHome.findAll( ))
        {
            if( role.getKey( ).contains( PREFIX_APPCENTER_ROLE ) )
            {
                rolesMap.put( role.getKey( ), role);
            }
        }
        return rolesMap;
    }
    
    /**
     * Load the data of all the role objects and returns them as a list
     * 
     * @return the list which contains the data of all the role objects
     */
    public static List<RBACRole> getRolesList( )
    {
        List<RBACRole> roleList = new ArrayList<>( );
        
        for ( RBACRole role :  RBACRoleHome.findAll( ))
        {
            if( role.getKey( ).contains( PREFIX_APPCENTER_ROLE ) )
            {
                roleList.add( role);
            }
        }
        return roleList;
    }
}
