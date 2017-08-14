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

package fr.paris.lutece.plugins.appcenter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application Service
 */
public class ApplicationService
{
    private static ObjectMapper _mapper = new ObjectMapper( );

    /**
     * Save a data subset into the global JSON data of an application
     * 
     * @param application
     *            The application
     * @param dataSubset
     *            The data subset
     */
    public static void saveApplicationData( Application application, ApplicationDataSubset dataSubset )
    {
        try
        {
            String strUpdatedJson = getApplicationData( application, dataSubset );
            application.setApplicationData( strUpdatedJson );
            ApplicationHome.update( application );
        }
        catch( IOException ex )
        {
            Logger.getLogger( ApplicationService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param application
     *            The application
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    public static <T> T loadApplicationDataSubset( Application application, String strDataSubsetName, Class<T> valueType )
    {
        try
        {
            String strApplicationJson = application.getApplicationData( );
            return getDataSubset( strApplicationJson, strDataSubsetName, valueType );
        }
        catch( IOException ex )
        {
            Logger.getLogger( ApplicationService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
        return null;
    }

    /**
     * Build a global JSON data of an application by adding or replacing a data subset
     * 
     * @param application
     *            The application
     * @param dataSubset
     *            The data subset
     * @return The JSON
     * @throws IOException
     *             if an error occurs
     */
    static String getApplicationData( Application application, ApplicationDataSubset dataSubset ) throws IOException
    {
        String strApplicationJson = application.getApplicationData( );
        JsonNode nodeApplication = _mapper.readTree( strApplicationJson );
        JsonNode nodeData = nodeApplication.get( dataSubset.getName( ) );
        if ( nodeData != null )
        {
            ( (ObjectNode) nodeApplication ).replace( dataSubset.getName( ), _mapper.valueToTree( dataSubset ) );
        }
        else
        {
            ( (ObjectNode) nodeApplication ).set( dataSubset.getName( ), _mapper.valueToTree( dataSubset ) );
        }
        return nodeApplication.toString( );
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param strApplicationJson
     *            The global JSON of the application
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    static <T> T getDataSubset( String strApplicationJson, String strDataSubsetName, Class<T> valueType ) throws IOException
    {
        JsonNode nodeApplication = _mapper.readTree( strApplicationJson );
        JsonNode nodeData = nodeApplication.get( strDataSubsetName );
        if ( nodeData != null )
        {
            return _mapper.treeToValue( nodeData, valueType );
        }
        return null;

    }

}
