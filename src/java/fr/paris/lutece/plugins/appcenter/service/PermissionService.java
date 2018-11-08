/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.Permission;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.ArrayList;
import java.util.Collection;

public class PermissionService
{
    /**
     * Get the permission list
     * @return the permission list
     */
    public static Collection<Permission> getPermissionList( )
    {
        Collection<Permission> colPermissions = new ArrayList<>();
        for ( IPermissionProvider permissionProvider : SpringContextService.getBeansOfType( IPermissionProvider.class) )
        {
            colPermissions.addAll( permissionProvider.providePermissionList( ) );
        }
        return colPermissions;
    }
}
