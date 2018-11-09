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
    public Collection<Permission> providePermissionList()
    {
        List<Permission> listPermission = new ArrayList<>();
        
        Permission permissionViewApp = new Permission();
        permissionViewApp.setCode( "PERMISSION_VIEW_APPLICATION");
        permissionViewApp.setLabel( "Permission de voir l'application" );
        permissionViewApp.setResourceTypeKey( "APP" );
        listPermission.add(permissionViewApp);
                
        Permission permissionModifyApp = new Permission();
        permissionModifyApp.setCode( "PERMISSION_MODIFY_APPLICATION");
        permissionModifyApp.setLabel( "Permission de modifier les applications" );
        permissionModifyApp.setResourceTypeKey( "APP" );
        listPermission.add(permissionModifyApp);
        
        Permission permissionAddUsers = new Permission();
        permissionAddUsers.setCode( "PERMISSION_ADD_USERS");
        permissionAddUsers.setLabel( "Permission d'ajouter des utilisateurs a une application" );
        permissionAddUsers.setResourceTypeKey( "APP" );
        listPermission.add(permissionAddUsers);
        
        Permission permissionRemoveUser = new Permission();
        permissionRemoveUser.setCode( "PERMISSION_REMOVE_USER");
        permissionRemoveUser.setLabel( "Permission de supprimer un utilisateur a une application" );
        permissionRemoveUser.setResourceTypeKey( "APP" );
        listPermission.add(permissionRemoveUser);
        
        Permission permissionViewDemands = new Permission();
        permissionViewDemands.setCode( "PERMISSION_VIEW_DEMANDS");
        permissionViewDemands.setLabel( "Permission de voir les demandes" );
        permissionViewDemands.setResourceTypeKey( "APP" );
        listPermission.add(permissionViewDemands);
        
        return listPermission;
    }
    
}
