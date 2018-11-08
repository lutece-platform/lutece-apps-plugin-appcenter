/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.Permission;
import java.util.ArrayList;
import java.util.List;

public class PermissionService
{
    /**
     * Get the permission list
     * @return the permission list
     */
    public static List<Permission> getPermissionList( )
    {
        List<Permission> listPermission = new ArrayList<>();
        
        Permission permissionViewApp = new Permission();
        permissionViewApp.setCode( "PERMISSION_VIEW_APP");
        permissionViewApp.setLabel( "Permission vue app" );
        permissionViewApp.setResourceTypeKey( "APP" );
        listPermission.add(permissionViewApp);
        
        Permission permissionDeployApp = new Permission();
        permissionDeployApp.setCode( "PERMISSION_DEPLOY_APP");
        permissionDeployApp.setLabel( "Permission de déployer app" );
        permissionDeployApp.setResourceTypeKey( "ENV" );
        listPermission.add(permissionDeployApp);
        
        return listPermission;
    }
}
