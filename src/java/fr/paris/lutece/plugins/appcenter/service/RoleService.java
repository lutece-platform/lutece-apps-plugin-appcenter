/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.Role;
import fr.paris.lutece.plugins.appcenter.business.RoleHome;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

public class RoleService
{
    private static final String PROPERTY_ID_APP_ROLE_OWNER = "appcenter.role.appOwner.code";
    
    public static Role getAppOwnerRole( )
    {
        String strIdOwnerRole = AppPropertiesService.getProperty( PROPERTY_ID_APP_ROLE_OWNER, "app_owner");
        Role ownerRole = RoleHome.findByCode( strIdOwnerRole );
        return ownerRole;
    }
}
