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

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is the business class for the object Demand
 */
public class Demand implements Serializable
{
    public static final String WORKFLOW_RESOURCE_TYPE = "DEMANDCENTER_DEMAND_SOURCE";
    protected static final String MARK_DEMAND = "demand";
    private static final long serialVersionUID = 1L;

    // Variables declarations
    @JsonIgnore
    private int _nId;

    @JsonIgnore
    private String _strStatusText;

    @JsonIgnore
    private String _strIdDemandType;

    @JsonIgnore
    private String _strDemandType;

    @JsonIgnore
    private int _nIdApplication;

    @JsonIgnore
    private String _strDemandData;
   
    @JsonIgnore
    private Timestamp _creationDate;
    
    @JsonIgnore
    private boolean _bIsClosed;

  

	/**
     * Returns the Id
     * 
     * @return The Id
     */
    @JsonIgnore
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the StatusText
     * 
     * @return The StatusText
     */
    @JsonIgnore
    public String getStatusText( )
    {
        return _strStatusText;
    }

    /**
     * Sets the StatusText
     * 
     * @param strStatusText
     *            The StatusText
     */
    public void setStatusText( String strStatusText )
    {
        _strStatusText = strStatusText;
    }

    /**
     * Returns the DemandType
     * 
     * @return The DemandType
     */
    @JsonIgnore
    public String getDemandType( )
    {
        return _strDemandType;
    }

    /**
     * Sets the DemandType
     * 
     * @param strDemandType
     *            The DemandType
     */
    public void setDemandType( String strDemandType )
    {
        _strDemandType = strDemandType;
    }

    /**
     * Returns the IdApplication
     * 
     * @return The IdApplication
     */
    @JsonIgnore
    public int getIdApplication( )
    {
        return _nIdApplication;
    }

    /**
     * Sets the IdApplication
     * 
     * @param nIdApplication
     *            The IdApplication
     */
    public void setIdApplication( int nIdApplication )
    {
        _nIdApplication = nIdApplication;
    }

    /**
     * Get the demand type id
     * 
     * @return the demand type id
     */
    @JsonIgnore
    public String getIdDemandType( )
    {
        return _strIdDemandType;
    }

    /**
     * Set the demand type id of the demand
     * 
     * @param strIdDemandType
     */
    public void setIdDemandType( String strIdDemandType )
    {
        _strIdDemandType = strIdDemandType;
    }

    /**
     * Get the demandContent
     * 
     * @return
     */
    @JsonIgnore
    public String getDemandData( )
    {
        return _strDemandData;
    }

    /**
     * Set the demand content
     * 
     * @param strDemandData
     */
    public void setDemandData( String strDemandData )
    {
        _strDemandData = strDemandData;
    }

    @JsonIgnore
    public String getComplementaryInfos( )
    {
        return "";

    }
    
    @JsonIgnore
    public Timestamp getCreationDate() {
  		return _creationDate;
  	}

  	public void setCreationDate(Timestamp _creationTimestamp) {
  		this._creationDate = _creationTimestamp;
  	}

    /**
     * Return the is closed boolean
     * @return the is closed boolean
     */
    public boolean isClosed()
    {
        return _bIsClosed;
    }

    /**
     * Set the Is Closed boolean
     * @param bIsClosed 
     */
    public void setIsClosed( boolean bIsClosed )
    {
        _bIsClosed = bIsClosed;
    }

    
}
