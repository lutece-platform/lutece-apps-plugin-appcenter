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
 package fr.paris.lutece.plugins.appcenter.modules.guichetpro.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class provides instances management methods (create, find, ...) for DemandTypeCategory objects
 */
public final class DemandTypeCategoryHome
{
    // Static variable pointed at the DAO instance
    private static IDemandTypeCategoryDAO _dao = SpringContextService.getBean( "appcenter.demandTypeCategoryDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "appcenter" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DemandTypeCategoryHome(  )
    {
    }

    /**
     * Create an instance of the demandTypeCategory class
     * @param demandTypeCategory The instance of the DemandTypeCategory which contains the informations to store
     * @return The instance of demandTypeCategory which has been created with its primary key.
     */
    public static DemandTypeCategory create( DemandTypeCategory demandTypeCategory )
    {
        _dao.insert( demandTypeCategory, _plugin );

        return demandTypeCategory;
    }

    /**
     * Update of the demandTypeCategory which is specified in parameter
     * @param demandTypeCategory The instance of the DemandTypeCategory which contains the data to store
     * @return The instance of the demandTypeCategory which has been updated
     */
    public static DemandTypeCategory update( DemandTypeCategory demandTypeCategory )
    {
        _dao.store( demandTypeCategory, _plugin );

        return demandTypeCategory;
    }

    /**
     * Remove the demandTypeCategory whose identifier is specified in parameter
     * @param nKey The demandTypeCategory Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a demandTypeCategory whose identifier is specified in parameter
     * @param nKey The demandTypeCategory primary key
     * @return an instance of DemandTypeCategory
     */
    public static DemandTypeCategory findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin);
    }

    /**
     * Load the data of all the demandTypeCategory objects and returns them as a list
     * @return the list which contains the data of all the demandTypeCategory objects
     */
    public static List<DemandTypeCategory> getDemandTypeCategoriesList( )
    {
        return _dao.selectDemandTypeCategoriesList( _plugin );
    }
    
    /**
     * Load the id of all the demandTypeCategory objects and returns them as a list
     * @return the list which contains the id of all the demandTypeCategory objects
     */
    public static List<Integer> getIdDemandTypeCategoriesList( )
    {
        return _dao.selectIdDemandTypeCategoriesList( _plugin );
    }
    
    /**
     * Load the data of all the demandTypeCategory objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the demandTypeCategory objects
     */
    public static ReferenceList getDemandTypeCategoriesReferenceList( )
    {
        return _dao.selectDemandTypeCategoriesReferenceList(_plugin );
    }
    
    /**
     * Load the demandTypeCategory from his key
     * @param strDemandTypeCategoryKey the demandTypeCategory key
     * @return the DemandTypeCategory loaded from the demandTypeCategory Key
     */
    public static DemandTypeCategory findByDemandTypeCategoryKey( String strDemandTypeCategoryKey )
    {
        return _dao.loadByDemandTypeCategoryKey( strDemandTypeCategoryKey, _plugin );
    }
    
    /**
     * Get the map key ==> DemandTypeCategory 
     * @return a map with key as demandTypeCategory key, 
     */
    public static Map<String,DemandTypeCategory> getDemandTypeCategoriesMap( )
    {
       return DemandTypeCategoryHome.getDemandTypeCategoriesList( ).stream()
               .collect( Collectors.toMap( demandTypeCategory -> demandTypeCategory.getName(), demandTypeCategory -> demandTypeCategory ) );
    }
}

