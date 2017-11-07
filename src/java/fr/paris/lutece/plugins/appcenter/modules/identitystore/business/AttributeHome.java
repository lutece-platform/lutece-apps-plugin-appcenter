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
 package fr.paris.lutece.plugins.appcenter.modules.identitystore.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class provides instances management methods (create, find, ...) for Attribute objects
 */
public final class AttributeHome
{
    // Static variable pointed at the DAO instance
    private static IAttributeDAO _dao = SpringContextService.getBean( "appcenter.attributeDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private AttributeHome(  )
    {
    }

    /**
     * Create an instance of the attribute class
     * @param attribute The instance of the Attribute which contains the informations to store
     * @return The  instance of attribute which has been created with its primary key.
     */
    public static Attribute create( Attribute attribute )
    {
        _dao.insert( attribute, _plugin );

        return attribute;
    }

    /**
     * Update of the attribute which is specified in parameter
     * @param attribute The instance of the Attribute which contains the data to store
     * @return The instance of the  attribute which has been updated
     */
    public static Attribute update( Attribute attribute )
    {
        _dao.store( attribute, _plugin );

        return attribute;
    }

    /**
     * Remove the attribute whose identifier is specified in parameter
     * @param nKey The attribute Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a attribute whose identifier is specified in parameter
     * @param nKey The attribute primary key
     * @return an instance of Attribute
     */
    public static Attribute findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin);
    }

    /**
     * Load the data of all the attribute objects and returns them as a list
     * @return the list which contains the data of all the attribute objects
     */
    public static List<Attribute> getAttributesList( )
    {
        return _dao.selectAttributesList( _plugin );
    }
    
    /**
     * Load the id of all the attribute objects and returns them as a list
     * @return the list which contains the id of all the attribute objects
     */
    public static List<Integer> getIdAttributesList( )
    {
        return _dao.selectIdAttributesList( _plugin );
    }
    
    /**
     * Load the data of all the attribute objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the attribute objects
     */
    public static ReferenceList getAttributesReferenceList( )
    {
        return _dao.selectAttributesReferenceList(_plugin );
    }
    
    /**
     * Load the attribute from his key
     * @param strAttributeKey the attribute key
     * @return the Attribute loaded from the attribute Key
     */
    public static Attribute findByAttributeKey( String strAttributeKey )
    {
        return _dao.loadByAttributeKey( strAttributeKey, _plugin );
    }
    
    /**
     * Get the map key ==> Attribute 
     * @return a map with key as attribute key, 
     */
    public static Map<String,Attribute> getAttributesMap( )
    {
       return AttributeHome.getAttributesList( ).stream()
               .collect( Collectors.toMap( attribute -> attribute.getKey(), attribute -> attribute ) );
    }
}

