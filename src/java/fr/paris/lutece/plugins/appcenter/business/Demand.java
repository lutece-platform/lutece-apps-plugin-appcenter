/*
 * Copyright (c) 2002-2016, Mairie de Paris
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

import fr.paris.lutece.plugins.appcenter.service.ConfigsData;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import java.io.Serializable;
import java.util.List;

/**
 * This is the business class for the object Demand
 */ 
public class Demand implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private int _nId;
    
    private String _strStatusText;
    
    private String _strIdDemandType;
    
    private String _strDemandType;
    
    private int _nIdApplication;
    
    private String _strDemandContent;

    /**
     * Returns the Id
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * @param nId The Id
     */ 
    public void setId( int nId )
    {
        _nId = nId;
    }
    
    /**
     * Returns the StatusText
     * @return The StatusText
     */
    public String getStatusText( )
    {
        return _strStatusText;
    }

    /**
     * Sets the StatusText
     * @param strStatusText The StatusText
     */ 
    public void setStatusText( String strStatusText )
    {
        _strStatusText = strStatusText;
    }
    
    /**
     * Returns the DemandType
     * @return The DemandType
     */
    public String getDemandType( )
    {
        return _strDemandType;
    }

    /**
     * Sets the DemandType
     * @param strDemandType The DemandType
     */ 
    public void setDemandType( String strDemandType )
    {
        _strDemandType = strDemandType;
    }
    
    /**
     * Returns the IdApplication
     * @return The IdApplication
     */
    public int getIdApplication( )
    {
        return _nIdApplication;
    }

    /**
     * Sets the IdApplication
     * @param nIdApplication The IdApplication
     */ 
    public void setIdApplication( int nIdApplication )
    {
        _nIdApplication = nIdApplication;
    }

    /**
     * Get the demand type id
     * @return the demand type id 
     */
    public String getIdDemandType()
    {
        return _strIdDemandType;
    }

    /**
     * Set the demand type id of the demand
     * @param strIdDemandType 
     */
    public void setIdDemandType( String strIdDemandType )
    {
        _strIdDemandType = strIdDemandType;
    }

    /**
     * Get the demandContent
     * @return 
     */
    public String getDemandContent()
    {
        return _strDemandContent;
    }

    /**
     * Set the demand content
     * @param strDemandContent 
     */
    public void setDemandContent( String strDemandContent )
    {
        _strDemandContent = strDemandContent;
    }
    
    /**
     * Get configsData of the demand
     * @return the configsdata
     */
    public ConfigsData getConfigsData( )
    {
        return DemandService.loadDemandDataSubset( this, "configs", ConfigsData.class );
    }
    
}
