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

import fr.paris.lutece.plugins.appcenter.business.Permission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApplicationPermissionProvider implements IPermissionProvider
{
    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<Permission> providePermissionList( )
    {
        List<Permission> listPermission = new ArrayList<>( );

        Permission permissionViewApp = new Permission( );
        permissionViewApp.setCode( "PERMISSION_VIEW_APPLICATION" );
        permissionViewApp.setLabel( "Permission de voir l'application" );
        permissionViewApp.setResourceTypeKey( "APP" );
        listPermission.add( permissionViewApp );

        Permission permissionRemoveApp = new Permission( );
        permissionRemoveApp.setCode( "PERMISSION_REMOVE_APPLICATION" );
        permissionRemoveApp.setLabel( "Permission de supprimer l'application" );
        permissionRemoveApp.setResourceTypeKey( "APP" );
        listPermission.add( permissionRemoveApp );

        Permission permissionModifyApp = new Permission( );
        permissionModifyApp.setCode( "PERMISSION_MODIFY_APPLICATION" );
        permissionModifyApp.setLabel( "Permission de modifier les applications" );
        permissionModifyApp.setResourceTypeKey( "APP" );
        listPermission.add( permissionModifyApp );

        Permission permissionAddUsers = new Permission( );
        permissionAddUsers.setCode( "PERMISSION_ADD_USERS" );
        permissionAddUsers.setLabel( "Permission d'ajouter des utilisateurs a une application" );
        permissionAddUsers.setResourceTypeKey( "APP" );
        listPermission.add( permissionAddUsers );

        Permission permissionRemoveUser = new Permission( );
        permissionRemoveUser.setCode( "PERMISSION_REMOVE_USER" );
        permissionRemoveUser.setLabel( "Permission de supprimer un utilisateur a une application" );
        permissionRemoveUser.setResourceTypeKey( "APP" );
        listPermission.add( permissionRemoveUser );

        Permission permissionViewDemands = new Permission( );
        permissionViewDemands.setCode( "PERMISSION_VIEW_DEMANDS" );
        permissionViewDemands.setLabel( "Permission de voir les demandes" );
        permissionViewDemands.setResourceTypeKey( "APP" );
        listPermission.add( permissionViewDemands );

        return listPermission;
    }

}
