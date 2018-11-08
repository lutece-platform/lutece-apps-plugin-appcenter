/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.service;

import fr.paris.lutece.plugins.appcenter.business.CategoryAction;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.List;

/**
 *
 * @author alexandre
 */
public class ActionService
{
    /**
     * Get the category action list
     * @return the category action list
     */
    public static List<CategoryAction> getCategoryActionsList( )
    {
        return SpringContextService.getBeansOfType( CategoryAction.class );
    }
}
