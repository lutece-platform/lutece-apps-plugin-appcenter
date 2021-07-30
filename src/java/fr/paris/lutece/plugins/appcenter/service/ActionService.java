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

import fr.paris.lutece.plugins.appcenter.business.Action;
import fr.paris.lutece.plugins.appcenter.business.CategoryAction;
import fr.paris.lutece.plugins.appcenter.business.User;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ActionService
{
    /**
     * Get the category action list
     * 
     * @return the category action list
     */
    private static List<CategoryAction> getCategoryActionsList( )
    {
        List<CategoryAction> listCategoryAction = new ArrayList<>( );

        for ( CategoryAction categoryAction : SpringContextService.getBeansOfType( CategoryAction.class ) )
        {
            listCategoryAction.add( new CategoryAction( categoryAction ) );
        }

        return listCategoryAction;
    }

    /**
     * Get the category actions list of the user and application
     * 
     * @param request
     * @param nIdApplication
     * @param resourceTypeConfig
     * @return The list of authorized actions
     */
    public static List<CategoryAction> getCategoryActionsListOfUserForApplication( HttpServletRequest request, int nIdApplication,
            ResourceTypeConfig resourceTypeConfig ) throws UserNotSignedException
    {
        List<CategoryAction> listCategories = getCategoryActionsList( );
        for ( CategoryAction catAction : listCategories )
        {
            List<Action> listAuthorizedAction = new ArrayList<>( );
            for ( Action action : catAction.getListActions( ) )
            {
                String strPermissionCode = action.getPermissionCode( );
                if ( strPermissionCode == null || strPermissionCode.isEmpty( ) )
                {
                    listAuthorizedAction.add( action );
                    continue;
                }
                RBAC permission = PermissionService.getPermissionByCode( strPermissionCode );
                User user = UserService.getCurrentUserInAppContext( request, nIdApplication );
                if ( AuthorizationService.isAuthorized( user.getId( ), nIdApplication, strPermissionCode,
                        resourceTypeConfig.getResourceCode( permission.getResourceId( )) ) )
                {
                    listAuthorizedAction.add( action );
                }
            }
            catAction.setListActions( listAuthorizedAction );
        }
        return listCategories;
    }
}
