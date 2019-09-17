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
package fr.paris.lutece.plugins.appcenter.service;

import java.util.HashMap;
import java.util.Map;

public class ResourceTypeConfig
{
    Map<String, String> _mapResourceType;

    /**
     * Default constructeur
     */
    public ResourceTypeConfig( )
    {
        _mapResourceType = new HashMap<>( );
    }

    /**
     * Get the map of resource type code/values for the configuration
     * 
     * @return the map of resource type code/values for the configuration
     */
    public Map<String, String> getMapResourceType( )
    {
        return _mapResourceType;
    }

    /**
     * Set the resource type code/values map for the configuration
     * 
     * @param mapResourceType
     */
    public void setMapResourceType( Map<String, String> mapResourceType )
    {
        _mapResourceType = mapResourceType;
    }

    /**
     * Add a resource type config in the map
     * 
     * @param strResourceTypeCode
     * @param strResourceCode
     */
    public void addResourceTypeConfig( String strResourceTypeCode, String strResourceCode )
    {
        _mapResourceType.put( strResourceTypeCode, strResourceCode );
    }

    /**
     * Get the resource code from the resource type code
     * 
     * @param strResourceTypeCode
     * @return The resource code from the resource type code
     */
    public String getResourceCode( String strResourceTypeCode )
    {
        return _mapResourceType.get( strResourceTypeCode );
    }

}
