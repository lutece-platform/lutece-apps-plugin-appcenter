/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.Action;
import fr.paris.lutece.plugins.appcenter.business.CategoryAction;
import fr.paris.lutece.plugins.appcenter.business.Permission;
import fr.paris.lutece.plugins.appcenter.business.User;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ActionService
{
    /**
     * Get the category action list
     * @return the category action list
     */
    private static List<CategoryAction> getCategoryActionsList( )
    {
        return SpringContextService.getBeansOfType( CategoryAction.class );
    }
    
    /**
     * Get the category actions list of the user and application
     * @param request
     * @param nIdApplication
     * @param resourceTypeConfig
     * @return The list of authorized actions
     */
    public static List<CategoryAction> getCategoryActionsListOfUserForApplication( HttpServletRequest request, int nIdApplication, ResourceTypeConfig resourceTypeConfig )
    {
        List<CategoryAction> listCategories = getCategoryActionsList( );
        for ( CategoryAction catAction : listCategories )
        {
            List<Action> listAuthorizedAction = new ArrayList<>();
            for ( Action action : catAction.getListActions( ) )
            {
                String strPermissionCode = action.getPermissionCode( );
                if ( strPermissionCode==null || strPermissionCode.isEmpty( ) )
                {
                    listAuthorizedAction.add( action );
                    continue;
                }
                Permission permission = PermissionService.getPermissionByCode( strPermissionCode );
                User user = UserService.getCurrentUserInAppContext(request, nIdApplication);
                if ( AuthorizationService.isAuthorized(user.getId( ), nIdApplication, strPermissionCode, 
                        resourceTypeConfig.getResourceCode( permission.getResourceType( ).getRessourceTypeKey( ) ) ) )
                {
                    listAuthorizedAction.add( action );
                }
            }
            catAction.setListActions( listAuthorizedAction );
        }
        return listCategories;
    }
}
