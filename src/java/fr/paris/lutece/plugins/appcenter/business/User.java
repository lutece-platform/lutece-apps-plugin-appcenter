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

package fr.paris.lutece.plugins.appcenter.business;

import java.util.List;

/**
 * This is the business class for the object User
 */
public class User
{
    // Variables declarations
    private String _strId;

    private List<UserApplicationRole> _listUserApplicationRoles;

    private String _userInfos;

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public String getId( )
    {
        return _strId;
    }

    /**
     * Sets the Id
     * 
     * @param strId
     *            The Id
     */
    public void setId( String strId )
    {
        _strId = strId;
    }

    /**
     * Get the user application roles
     * 
     * @return the
     */
    public List<UserApplicationRole> getListUserApplicationRoles( )
    {
        return _listUserApplicationRoles;
    }

    /**
     * Set the user applcation roles list
     * 
     * @param listUserApplicationRoles
     *            The user application roles list
     */
    public void setListUserApplicationRoles( List<UserApplicationRole> listUserApplicationRoles )
    {
        _listUserApplicationRoles = listUserApplicationRoles;
    }

    /**
     * Get the user infos
     * 
     * @return
     */
    public String getUserInfos( )
    {
        return _userInfos;
    }

    /**
     * Set the user infos
     * 
     * @param userInfos
     */
    public void setUserInfos( String userInfos )
    {
        _userInfos = userInfos;
    }

}
