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
 package fr.paris.lutece.plugins.appcenter.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for ActionRole objects
 */
public final class ActionRoleHome
{
    // Static variable pointed at the DAO instance
    private static IActionRoleDAO _dao = SpringContextService.getBean( "appcenter.actionRoleDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ActionRoleHome(  )
    {
    }

    /**
     * Create an instance of the actionRole class
     * @param actionRole The instance of the ActionRole which contains the informations to store
     * @return The  instance of actionRole which has been created with its primary key.
     */
    public static ActionRole create( ActionRole actionRole )
    {
        _dao.insert( actionRole, _plugin );

        return actionRole;
    }

    /**
     * Update of the actionRole which is specified in parameter
     * @param actionRole The instance of the ActionRole which contains the data to store
     * @return The instance of the  actionRole which has been updated
     */
    public static ActionRole update( ActionRole actionRole )
    {
        _dao.store( actionRole, _plugin );

        return actionRole;
    }

    /**
     * Remove the actionRole whose identifier is specified in parameter
     * @param nKey The actionRole Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a actionRole whose identifier is specified in parameter
     * @param nKey The actionRole primary key
     * @return an instance of ActionRole
     */
    public static ActionRole findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns an instance of a actionRole whose identifier is specified in parameter
     * @param nKey The actionRole primary key
     * @return an instance of ActionRole
     */
    public static ActionRole findByCodeAndProfileAndResource( String codeActionRole, int nProfileId, String strResource )
    {
        return _dao.loadByCodeAndProfileAndResource( codeActionRole, nProfileId, strResource , _plugin );
    }
    
    /**
     * Load the data of all the actionRole objects and returns them as a list
     * @return the list which contains the data of all the actionRole objects
     */
    public static List<ActionRole> getActionRolesList( )
    {
        return _dao.selectActionRolesList( _plugin );
    }
    
    /**
     * Load the id of all the actionRole objects and returns them as a list
     * @return the list which contains the id of all the actionRole objects
     */
    public static List<Integer> getIdActionRolesList( )
    {
        return _dao.selectIdActionRolesList( _plugin );
    }
    
    /**
     * Load the data of all the actionRole objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the actionRole objects
     */
    public static ReferenceList getActionRolesReferenceList( )
    {
        return _dao.selectActionRolesReferenceList( _plugin );
    }
}

