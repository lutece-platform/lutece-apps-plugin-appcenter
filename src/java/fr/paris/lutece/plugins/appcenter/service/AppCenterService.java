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
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.CategoryAction;
import fr.paris.lutece.plugins.appcenter.business.resourcetype.IAppCenterResourceType;
import fr.paris.lutece.plugins.appcenter.business.Permission;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.ArrayList;
import java.util.List;

public class AppCenterService 
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
    
    private static List<IAppCenterResourceType> getResourceTypeList( )
    {
        return SpringContextService.getBeansOfType( IAppCenterResourceType.class );
    }
    
    /**
     * Get the category action list
     * @return the category action list
     */
    public static List<CategoryAction> getCategoryActionsList( )
    {
        return SpringContextService.getBeansOfType( CategoryAction.class );
    }
    
    /**
     * Get the IAppcenterResourceType from the resource type key
     * @param strResourceTypeCode
     * @return the IAppcenterResourceType from the given resource type key
     */
    public static IAppCenterResourceType getResourceType( String strResourceTypeCode )
    {
        for ( IAppCenterResourceType resourceType : getResourceTypeList( ) )
        {
            if ( strResourceTypeCode.equals( resourceType.getRessourceTypeKey( ) ) )
            {
                return resourceType;
            }
        }
        return null;
    }
    
}
