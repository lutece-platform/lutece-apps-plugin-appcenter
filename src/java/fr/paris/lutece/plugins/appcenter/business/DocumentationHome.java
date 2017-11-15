/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for Documentation objects
 */

public final class DocumentationHome
{

    // Static variable pointed at the DAO instance

    private static IDocumentationDAO _dao = ( IDocumentationDAO ) SpringContextService.getPluginBean( "appcenter", "documentationDAO" );


    /**
     * Private constructor - this class need not be instantiated
     */

    private DocumentationHome(  )
    {
    }

    /**
     * Create an instance of the documentation class
     * @param documentation The instance of the Documentation which contains the informations to store
     * @param plugin the Plugin
     * @return The  instance of documentation which has been created with its primary key.
     */

    public static Documentation create( Documentation documentation, Plugin plugin )
    {
        _dao.insert( documentation, plugin );

        return documentation;
    }


    /**
     * Update of the documentation which is specified in parameter
     * @param documentation The instance of the Documentation which contains the data to store
     * @param plugin the Plugin
     * @return The instance of the  documentation which has been updated
     */

    public static Documentation update( Documentation documentation, Plugin plugin )
    {
        _dao.store( documentation, plugin );

        return documentation;
    }


    /**
     * Remove the documentation whose identifier is specified in parameter
     * @param nDocumentationId The documentation Id
     * @param plugin the Plugin
     */


    public static void remove( int nDocumentationId, Plugin plugin )
    {
        _dao.delete( nDocumentationId, plugin );
    }


    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a documentation whose identifier is specified in parameter
     * @param nKey The documentation primary key
     * @param plugin the Plugin
     * @return an instance of Documentation
     */

    public static Documentation findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin);
    }


    /**
     * Load the data of all the documentation objects and returns them in form of a list
     * @param plugin the Plugin
     * @return the list which contains the data of all the documentation objects
     */

    public static List<Documentation> getDocumentationsList( Plugin plugin )
    {
        return _dao.selectDocumentationsList( plugin );
    }

}
