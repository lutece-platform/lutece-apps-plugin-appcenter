/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.resourcetype.IAppCenterResourceType;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.List;

public class ResourceTypeService
{
     /**
      * Get the resource type list
      * @return the resource type list
      */
    public static List<IAppCenterResourceType> getResourceTypeList( )
    {
        return SpringContextService.getBeansOfType( IAppCenterResourceType.class );
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
