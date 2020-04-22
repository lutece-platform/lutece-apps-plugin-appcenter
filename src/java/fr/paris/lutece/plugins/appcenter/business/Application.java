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

import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationManager;
import fr.paris.lutece.plugins.appcenter.business.resourcetype.ResourceTypeValue;
import fr.paris.lutece.plugins.appcenter.business.resourcetype.AbstractAppCenterResourceType;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * This is the business class for the object Application
 */
public class Application implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final String EMPTY_JSON_OBJECT = "{}";

    // Variables declarations
    private int _nId;

    @NotEmpty( message = "#i18n{appcenter.validation.application.Name.notEmpty}" )
    @Size( max = 50, message = "#i18n{appcenter.validation.application.Name.size}" )
    private String _strName;
    private String _strCode;

    @Size( max = 255, message = "#i18n{appcenter.validation.application.Description.size}" )
    private String _strDescription;

    @NotNull( message = "appcenter.validation.application.OrganizationManager.notNull" )
    private OrganizationManager _organizationManager;
    private String _strApplicationData;

    private List<UserApplicationRole> _listAuthorizations;

    private List<Environment> _listEnvironment;

    /**
     * Returns the Id
     * 
     * @return The Id
     */
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
     * Returns the Name
     * 
     * @return The Name
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * Sets the Name
     * 
     * @param strName
     *            The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns the Description
     * 
     * @return The Description
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * 
     * @param strDescription
     *            The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the organizationManager
     * 
     * @return The organizationManager
     */
    public OrganizationManager getOrganizationManager( )
    {
        return _organizationManager;
    }

    /**
     * Sets the organizationManager
     * 
     * @param organizationManager
     *            The organizationManager
     */
    public void setOrganizationManager( OrganizationManager organizationManager )
    {
        _organizationManager = organizationManager;
    }

    /**
     * Returns the ApplicationData
     * 
     * @return The ApplicationData
     */
    public String getApplicationData( )
    {
        return ( _strApplicationData != null ) ? _strApplicationData : EMPTY_JSON_OBJECT;
    }

    /**
     * Sets the ApplicationData
     * 
     * @param strApplicationData
     *            The ApplicationData
     */
    public void setApplicationData( String strApplicationData )
    {
        _strApplicationData = strApplicationData;
    }

    /**
     * Set authorizations
     * 
     * @param listAuthorizations
     *            The authorization list
     */
    public void setAuthorizations( List<UserApplicationRole> listAuthorizations )
    {
        _listAuthorizations = listAuthorizations;
    }

    /**
     * Get authorizations
     * 
     * @return The authorization list
     */
    public List<UserApplicationRole> getAuthorizations( )
    {
        return _listAuthorizations;
    }

    /**
     * Get application Code
     * 
     * @return the application code
     */
    public String getCode( )
    {
        return _strCode;
    }

    /**
     * 
     * @param _strCode
     *            the application code
     */
    public void setCode( String _strCode )
    {
        this._strCode = _strCode;
    }

    /**
     * Get the environment list
     * 
     * @return the environment list
     */
    public List<Environment> getListEnvironment( )
    {
        return _listEnvironment;
    }

    /**
     * Set the environment list
     * 
     * @param listEnvironment
     *            the environment list
     */
    public void setListEnvironment( List<Environment> listEnvironment )
    {
        _listEnvironment = listEnvironment;
    }
}
