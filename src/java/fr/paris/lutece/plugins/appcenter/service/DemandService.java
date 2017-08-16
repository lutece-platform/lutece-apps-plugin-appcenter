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
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demand Service
 */
public class DemandService
{
    private static ObjectMapper _mapper = new ObjectMapper( );

    /**
     * Save a data subset into the global JSON data of a demand
     * 
     * @param demand
     *            The Demand
     * @param dataSubset
     *            The data subset
     */
    public static void saveDemandData( Demand demand, DataSubset dataSubset )
    {
        try
        {
            String strUpdatedJson = getDemandData( demand, dataSubset );
            demand.setDemandContent( strUpdatedJson );
            DemandHome.update( demand );
        }
        catch( IOException ex )
        {
            Logger.getLogger(DemandService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param demand
     *            The demand
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    public static <T> T loadDemandDataSubset( Demand demand, String strDataSubsetName, Class<T> valueType )
    {
        try
        {
            String strDemandJson = demand.getDemandContent();
            return getDataSubset( strDemandJson, strDataSubsetName, valueType );
        }
        catch( IOException ex )
        {
            Logger.getLogger(DemandService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
        return null;
    }

    /**
     * Build a global JSON data of an demand by adding or replacing a data subset
     * 
     * @param demand
     *            The demand
     * @param dataSubset
     *            The data subset
     * @return The JSON
     * @throws IOException
     *             if an error occurs
     */
    static String getDemandData( Demand demand, DataSubset dataSubset ) throws IOException
    {
        String strDemandJson = demand.getDemandContent( );
        JsonNode nodeDemand = _mapper.readTree( strDemandJson );
        JsonNode nodeData = nodeDemand.get( dataSubset.getName( ) );
        if ( nodeData != null )
        {
            ( (ObjectNode) nodeDemand ).replace( dataSubset.getName( ), _mapper.valueToTree( dataSubset ) );
        }
        else
        {
            ( (ObjectNode) nodeDemand ).set( dataSubset.getName( ), _mapper.valueToTree( dataSubset ) );
        }
        return nodeDemand.toString( );
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param strDemandJson
     *            The global JSON of the demand
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    static <T> T getDataSubset( String strDemandJson, String strDataSubsetName, Class<T> valueType ) throws IOException
    {
        JsonNode nodeDemand = _mapper.readTree( strDemandJson );
        JsonNode nodeData = nodeDemand.get( strDataSubsetName );
        if ( nodeData != null )
        {
            return _mapper.treeToValue( nodeData, valueType );
        }
        return null;

    }

}
