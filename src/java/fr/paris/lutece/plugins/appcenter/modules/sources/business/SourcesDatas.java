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
package fr.paris.lutece.plugins.appcenter.modules.sources.business;

import java.util.ArrayList;
import java.util.List;
import fr.paris.lutece.plugins.appcenter.service.DataSubset;

/**
 * Sources Data Subset
 */
public class SourcesDatas implements DataSubset
{
    public static final String DATA_SOURCES_NAME = "sources";
    public static final String DEMAND_TYPE_KEY = "source";

    private String _strSiteRepository;
    private List<SourcesData> _listSourcesDatas = new ArrayList<SourcesData>( );

    /**
     * {@inheritDoc }
     * 
     * @return
     */
    @Override
    public String getName( )
    {
        return DATA_SOURCES_NAME;
    }

    /**
     * Get the site repository
     * @return the site repository
     */
    public String getSiteRepository()
    {
        return _strSiteRepository;
    }

    /**
     * Set the site repository
     * @param strSiteRepository the site repository 
     */
    public void setSiteRepository( String strSiteRepository )
    {
        _strSiteRepository = strSiteRepository;
    }    

    /**
     * Returns the SourcesDatas
     *
     * @return The SourcesDatas
     */
    public List<SourcesData> getSourcesDatas( )
    {
        return _listSourcesDatas;
    }

    /**
     * Sets the SourcesData
     *
     * @param sourcesData
     * 
     */
    public void addSourceData( SourcesData sourcesData )
    {
        _listSourcesDatas.add( sourcesData );
    }

}
