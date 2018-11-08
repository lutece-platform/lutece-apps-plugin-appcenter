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
        permissionViewApp.setCode( "PERMISSION_VIEW_APP");
        permissionViewApp.setLabel( "Permission vue app" );
        permissionViewApp.setResourceTypeKey( "APP" );
        listPermission.add(permissionViewApp);
                
        return listPermission;
    }
    
}
