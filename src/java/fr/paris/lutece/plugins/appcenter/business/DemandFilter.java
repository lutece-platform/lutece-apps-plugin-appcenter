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
package fr.paris.lutece.plugins.appcenter.business;

/**
 * Class for process filtering in BO DemandJspBean
 */
public class DemandFilter
{
    private int _nIdApplication;
    private String _strEnvironmentPrefix;
    private boolean _bIsClosed;
    private String _strIdDemandType;

    private boolean _bHasIdApplication;
    private boolean _bHasEnvironmentPrefix;
    private boolean _bHasIsClosed;
    private boolean _bHasIdDemandType;

    /**
     * Constructor for demand Filter
     */
    public DemandFilter( )
    {
        _bHasIdApplication = false;
        _bHasEnvironmentPrefix = false;
        _bHasIsClosed = false;
        _bHasIdDemandType = false;
    }

    /**
     * Get the id of application
     * 
     * @return the id of application
     */
    public int getIdApplication( )
    {
        return _nIdApplication;
    }

    /**
     * Set the id of application
     * 
     * @param nIdApplication
     *            the id of application
     */
    public void setIdApplication( int nIdApplication )
    {
        _nIdApplication = nIdApplication;
    }

    /**
     * Get the environment prefix
     * 
     * @return the environment prefix
     */
    public String getEnvironmentPrefix( )
    {
        return _strEnvironmentPrefix;
    }

    /**
     * Set the environment prefix
     * 
     * @param strEnvironmentPrefix
     *            the environment prefix
     */
    public void setEnvironmentPrefix( String strEnvironmentPrefix )
    {
        _strEnvironmentPrefix = strEnvironmentPrefix;
    }

    /**
     * Get the is closed boolean
     * 
     * @return the is closed boolean
     */
    public boolean isClosed( )
    {
        return _bIsClosed;
    }

    /**
     * Set the is closed boolean
     * 
     * @param bIsClosed
     *            the is closed boolean
     */
    public void setIsClosed( boolean bIsClosed )
    {
        _bIsClosed = bIsClosed;
    }

    /**
     * Get the id of the demand type
     * 
     * @return the id of the demand type
     */
    public String getIdDemandType( )
    {
        return _strIdDemandType;
    }

    /**
     * Set the id of the demand type
     * 
     * @param strIdDemandType
     *            the id of the demand type
     */
    public void setIdDemandType( String strIdDemandType )
    {
        _strIdDemandType = strIdDemandType;
    }

    /**
     * Has a constraint on id application
     * 
     * @return the has application boolean
     */
    public boolean hasIdApplication( )
    {
        return _bHasIdApplication;
    }

    /**
     * Set the constraint on id application
     * 
     * @param bHasIdApplication
     *            the has id application constaint
     */
    public void setHasIdApplication( boolean bHasIdApplication )
    {
        this._bHasIdApplication = bHasIdApplication;
    }

    /**
     * has a constaint on environment prefix
     * 
     * @return the boolean of environemnt prefix constraint
     */
    public boolean hasEnvironmentPrefix( )
    {
        return _bHasEnvironmentPrefix;
    }

    /**
     * Set the environment prefix
     * 
     * @param bHasEnvironmentPrefix
     */
    public void setHasEnvironmentPrefix( boolean bHasEnvironmentPrefix )
    {
        _bHasEnvironmentPrefix = bHasEnvironmentPrefix;
    }

    /**
     * Has an is closed constraint
     * 
     * @return the is closed constraint boolean
     */
    public boolean hasIsClosed( )
    {
        return _bHasIsClosed;
    }

    /**
     * Set the is closed constraint boolean
     * 
     * @param bHasIsClosed
     *            the is closed constraint boolean
     */
    public void setHasIsClosed( boolean bHasIsClosed )
    {
        _bHasIsClosed = bHasIsClosed;
    }

    /**
     * Get the id demand type constraint boolean
     * 
     * @return the id demand type constraint boolean
     */
    public boolean hasIdDemandType( )
    {
        return _bHasIdDemandType;
    }

    /**
     * Set the id demand type constraint boolean
     * 
     * @param bHasIdDemandType
     *            the id demand type constraint boolean
     */
    public void setHasIdDemandType( boolean bHasIdDemandType )
    {
        _bHasIdDemandType = bHasIdDemandType;
    }

}
