/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.Permission;
import java.util.Collection;

public interface IPermissionProvider
{
    /**
     * Provide a list of permissions (each AppCenter module can implements and declare its own provider)
     * @return a list of Permissions thoughout all the Appcenter modules
     */
    public Collection<Permission> providePermissionList( );
}
