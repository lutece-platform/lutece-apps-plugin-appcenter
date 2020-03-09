/*
 * Copyright (c) 2002-2020, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.business.organization;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * This is the business class for the object OrganizationManager
 */
public class OrganizationManager implements Serializable
{
    private int _nIdOrganizationManager;
    private int _nIdOrganization;
    @Size( max = 50 , message = "#i18n{appcenter.validation.organizationmanager.FirstName.size}" )
    private String _strFirstName;
    @Size( max = 255 , message = "#i18n{appcenter.validation.organizationmanager.FamilyName.size}" )
    private String _strFamilyName;
    @Size( max = 255 , message = "#i18n{appcenter.validation.organizationmanager.Mail.size}" )
    private String _strMail;

    /**
     * Returns the IdOrganizationManager
     * @return The IdOrganizationManager
     */
    public int getIdOrganizationManager( )
    {
        return _nIdOrganizationManager;
    }

    /**
     * Sets the IdOrganizationManager
     * @param nIdOrganizationManager The IdOrganizationManager
     */ 
    public void setIdOrganizationManager( int nIdOrganizationManager )
    {
        _nIdOrganizationManager = nIdOrganizationManager;
    }

    /**
     * Returns the IdOrganization
     * @return The IdOrganization
     */
    public int getIdOrganization( )
    {
        return _nIdOrganization;
    }

    /**
     * Sets the IdOrganization
     * @param nIdOrganization The IdOrganization
     */
    public void setIdOrganization( int nIdOrganization )
    {
        _nIdOrganization = nIdOrganization;
    }

    /**
     * Returns the FirstName
     * @return The FirstName
     */
    public String getFirstName( )
    {
        return _strFirstName;
    }

    /**
     * Sets the FirstName
     * @param strFirstName The FirstName
     */
    public void setFirstName( String strFirstName )
    {
        _strFirstName = strFirstName;
    }

    /**
     * Returns the FamilyName
     * @return The FamilyName
     */
    public String getFamilyName( )
    {
        return _strFamilyName;
    }

    /**
     * Sets the FamilyName
     * @param strFamilyName The FamilyName
     */
    public void setFamilyName( String strFamilyName )
    {
        _strFamilyName = strFamilyName;
    }

    /**
     * Returns the Mail
     * @return The Mail
     */
    public String getMail( )
    {
        return _strMail;
    }

    /**
     * Sets the Mail
     * @param strMail The Mail
     */
    public void setMail( String strMail )
    {
        _strMail = strMail;
    }
}
