/*
 * Copyright (c) 2002-2021, Mairie de Paris
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

import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * Class ApplicationUrlHome
 *
 */
public final class ApplicationUrlHome
{
    private static Plugin _plugin =  PluginService.getPlugin( "appcenter" );
    private static IApplicationUrlDAO _dao = SpringContextService.getBean( "appcenter.applicationUrlDAO" );
    
    
    /**
     * Create an instance of the applicationUrl class
     * 
     * @param applicationUrl
     *            The instance of the ApplicationUrl which contains the informations to store
     */
    public static void create ( ApplicationUrl applicationUrl )
    {
        _dao.insert( applicationUrl, _plugin );
    }
    
    /**
     * Update of the applicationUrl which is specified in parameter
     * 
     * @param applicationUrl
     *            The instance of the ApplicationUrl which contains the data to store
     */
    public static void update ( ApplicationUrl applicationUrl )
    {
        _dao.store( applicationUrl, _plugin );
    }
    
    /**
     * Remove the applicationUrl whose identifier is specified in parameter
     * 
     * @param nKey
     * nIdApplication
     */
    public static void remove ( int nId)
    {
        _dao.delete( nId, _plugin );
    }
    
    /**
     * Returns an instance of a applicationUrl whose identifier is specified in parameter
     * 
     * @param nKey
     *            The applicationUrl primary key
     * @return an instance of Documentation
     */
    public static ApplicationUrl findByPrimaryKey ( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    
    /**
     * Load the data of all the applicationUrl objects and returns them as a list
     * @param nIdApplication
     *            The nIdApplication
     * @param strEnvironment
     *            The strEnvironment
     * @return the list which contains the data of all the applicationUrl objects
     */
    public static List<ApplicationUrl> findAllByIdApplication( int nIdApplication, String strEnvironment )
    {
        return _dao.selectApplicationUrlsList( nIdApplication, strEnvironment, _plugin );
    }
    
}