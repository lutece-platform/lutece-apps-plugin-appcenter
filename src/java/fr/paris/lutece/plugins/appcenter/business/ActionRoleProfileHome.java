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
 * This class provides instances management methods (create, find, ...) for ActionRoleProfile objects
 */
public final class ActionRoleProfileHome
{
    // Static variable pointed at the DAO instance
    private static IActionRoleProfileDAO _dao = SpringContextService.getBean( "appcenter.actionRoleProfileDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ActionRoleProfileHome(  )
    {
    }

    /**
     * Create an instance of the actionRoleProfile class
     * @param actionRoleProfile The instance of the ActionRoleProfile which contains the informations to store
     * @return The  instance of actionRoleProfile which has been created with its primary key.
     */
    public static ActionRoleProfile create( ActionRoleProfile actionRoleProfile )
    {
        _dao.insert( actionRoleProfile, _plugin );

        return actionRoleProfile;
    }


    /**
     * Remove the actionRoleProfile whose identifier is specified in parameter
     * @param strActionRoleCode
     * @param strProfileCode
     * @param strResourceCode
     */
    public static void remove( String strActionRoleCode, String strProfileCode, String strResourceCode )
    {
        _dao.delete(  strActionRoleCode,  strProfileCode,  strResourceCode, _plugin );
    }

    /**
     * Returns an instance of a actionRoleProfile whose identifier is specified in parameter
     * @param strActionRoleCode
     * @param strProfileCode
     * @param strResourceCode
     * @return an instance of ActionRoleProfile
     */
    public static ActionRoleProfile findByPrimaryKey( String strActionRoleCode, String strProfileCode, String strResourceCode )
    {
        return _dao.load( strActionRoleCode, strProfileCode, strResourceCode, _plugin );
    }

    /**
     * Load the data of all the actionRoleProfile objects and returns them as a list
     * @return the list which contains the data of all the actionRoleProfile objects
     */
    public static List<ActionRoleProfile> getActionRoleProfilesList( )
    {
        return _dao.selectActionRoleProfilesList( _plugin );
    }
    
    
    /**
     * Load the data of all the actionRoleProfile objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the actionRoleProfile objects
     */
    public static ReferenceList getActionRoleProfilesReferenceList( )
    {
        return _dao.selectActionRoleProfilesReferenceList( _plugin );
    }
}

