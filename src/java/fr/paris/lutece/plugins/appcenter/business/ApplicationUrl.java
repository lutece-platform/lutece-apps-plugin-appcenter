/*
 * Copyright (c) 2002-2021, Mairie de Paris
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
 * Class ApplicationUrl
 *
 */
public class ApplicationUrl
{
    private int _nIdApplicationUrl;
    private int _nIdApplication;
    private String _strType;
    private String _strUrl;
    private String _strEnvironment;
    private String _strDescription;
    
    /**
     * @return the _lIdApplicationUrl
     */
    public int getIdApplicationUrl( )
    {
        return _nIdApplicationUrl;
    }
    /**
     * @param nIdApplicationUrl the _nIdApplicationUrl to set
     */
    public void setIdApplicationUrl( int nIdApplicationUrl )
    {
        this._nIdApplicationUrl = nIdApplicationUrl;
    }
    /**
     * @return the _lIdApplication
     */
    public int getIdApplication( )
    {
        return _nIdApplication;
    }
    /**
     * @param nIdApplication the _nIdApplication to set
     */
    public void setIdApplication( int nIdApplication )
    {
        this._nIdApplication = nIdApplication;
    }
    
    /**
     * @return the _strType
     */
    public String getType( )
    {
        return _strType;
    }
    /**
     * @param strType the _strType to set
     */
    public void setType( String strType )
    {
        this._strType = strType;
    }
    /**
     * @return the _strUrl
     */
    public String getUrl( )
    {
        return _strUrl;
    }
    /**
     * @param strUrl the _strUrl to set
     */
    public void setUrl( String strUrl )
    {
        this._strUrl = strUrl;
    }
    /**
     * @return the _strEnvironment
     */
    public String getEnvironment( )
    {
        return _strEnvironment;
    }
    /**
     * @param environment the _environment to set
     */
    public void setEnvironment( String strEnvironment )
    {
        this._strEnvironment = strEnvironment;
    }
    /**
     * @return the _strDescription
     */
    public String getDescription( )
    {
        return _strDescription;
    }
    /**
     * @param strDescription the _strDescription to set
     */
    public void setDescription( String strDescription )
    {
        this._strDescription = strDescription;
    }
}