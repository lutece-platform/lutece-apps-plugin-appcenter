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
 * Class for process filtering in BO ApplicationJspBean
 */
public class UserApplicationRoleFilter
{
    private String _strApplicationCodeOrName;
    private String _strIdUser;
    private String _strRoleLabel;
    private boolean _bHasApplicationCodeOrName;
    private boolean _bHasIdUser;
    private boolean _bHasRoleLabel;

    /**
     * Constructor for demand Filter
     */
    public UserApplicationRoleFilter( )
    {
        _bHasApplicationCodeOrName = false;
        _bHasIdUser = false;
        _bHasRoleLabel = false;
    }

    /**
     * Get the search string for application code or name
     * 
     * @return the search string for application code or name
     */
    public String getApplicationCodeOrName( )
    {
        return _strApplicationCodeOrName;
    }

    /**
     * Set the search string for application code or name
     * 
     * @param strApplicationCodeOrName
     *            the search string for application code or name
     */
    public void setApplicationCodeOrName( String strApplicationCodeOrName )
    {
        _strApplicationCodeOrName = strApplicationCodeOrName;
    }

    /**
     * Get the id of user
     * 
     * @return the id of user
     */
    public String getIdUser( )
    {
        return _strIdUser;
    }

    /**
     * Set the id of user
     * 
     * @param nIdUser
     *            the id of user
     */
    public void setIdUser( String nIdUser )
    {
        _strIdUser = nIdUser;
    }

    /**
     * Get the search string for role label
     * 
     * @return the search string for role label
     */
    public String getRoleLabel( )
    {
        return _strRoleLabel;
    }

    /**
     * Set the search string for role label
     * 
     * @param strRoleLabel
     *            the search string for role label
     */
    public void setRoleLabel( String strRoleLabel )
    {
        _strRoleLabel = strRoleLabel;
    }

    /**
     * Has a constraint on application code or name
     * 
     * @return the has application code or name boolean
     */
    public boolean hasApplicationCodeOrName( )
    {
        return _bHasApplicationCodeOrName;
    }

    /**
     * Set the constraint on application code or name
     * 
     * @param bHasApplicationCodeOrName
     *            the has application code or name constaint
     */
    public void setHasApplicationCodeOrName( boolean bHasApplicationCodeOrName )
    {
        this._bHasApplicationCodeOrName = bHasApplicationCodeOrName;
    }

    /**
     * Has a constraint on id user
     * 
     * @return the has id user boolean
     */
    public boolean hasIdUser( )
    {
        return _bHasIdUser;
    }

    /**
     * Set the constraint on id user
     * 
     * @param bHasIdUser
     *            the has id user constaint
     */
    public void setHasIdUser( boolean bHasIdUser )
    {
        this._bHasIdUser = bHasIdUser;
    }

    /**
     * Has a constraint on role label
     * 
     * @return the has role label boolean
     */
    public boolean hasRoleLabel( )
    {
        return _bHasRoleLabel;
    }

    /**
     * Set the constraint on role label
     * 
     * @param bHasRoleLabel
     *            the has role label constaint
     */
    public void setHasRoleLabel( boolean bHasRoleLabel )
    {
        this._bHasRoleLabel = bHasRoleLabel;
    }
}
