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
package fr.paris.lutece.plugins.appcenter.modules.notificationgru.business;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * NotificationGru Data Subset
 */
public class NotificationGruData
{
    @NotEmpty( message = "#i18n{module.appcenter.notificationgru.validation.environment.notEmpty}" )
    private String _strEnvironment;
    @NotEmpty( message = "#i18n{module.appcenter.notificationgru.validation.applicationcode.notEmpty}" )
    private String _strApplicationCode;
    @NotEmpty( message = "#i18n{module.appcenter.notificationgru.validation.grudemandtype.notEmpty}" )
    private String _strGruDemandType;
    private String _strCategoryGruDemandType;
    private String _strApiManagerKey;
    private String _strIdGruDemandType;

    /**
     * Returns the Environment
     *
     * @return The Environment
     */
    public String getEnvironment() {
        return _strEnvironment;
    }

    /**
     * Sets the Environment
     *
     * @param strEnvironment The Environment
     */
    public void setEnvironment(String strEnvironment) {
        _strEnvironment = strEnvironment;
    }

    /**
     * Get the application code
     * @return the application code
     */
    public String getApplicationCode()
    {
        return _strApplicationCode;
    }

    /**
     * Set the application code
     * @param strApplicationCode the application code 
     */
    public void setApplicationCode( String strApplicationCode )
    {
        _strApplicationCode = strApplicationCode;
    }

    /**
     * Get the gru demand type
     * @return the gru demand type
     */
    public String getGruDemandType()
    {
        return _strGruDemandType;
    }
    
    /**
     * Set the gru demand type
     * @param strGruDemandType 
     */
    public void setGruDemandType( String strGruDemandType )
    {
        _strGruDemandType = strGruDemandType;
    }

    /**
     * Get the category of gru demand type
     * @return the category of gru demand type
     */
    public String getCategoryGruDemandType()
    {
        return _strCategoryGruDemandType;
    }

    /**
     * Set the category of gru demand type
     * @param strCategoryGruDemandType 
     */
    public void setCategoryGruDemandType( String strCategoryGruDemandType )
    {
        _strCategoryGruDemandType = strCategoryGruDemandType;
    }

    /**
     * Get the API Manager Notify key
     * @return the API Manager Notify key
     */
    public String getApiManagerKey()
    {
        return _strApiManagerKey;
    }

    /**
     * Set the API Manager Notify key
     * @param strApiManagerKey 
     */
    public void setApiManagerKey( String strApiManagerKey )
    {
        _strApiManagerKey = strApiManagerKey;
    }

    /**
     * Get the id of gru demand type
     * @return the id of gru demand type
     */
    public String getIdGruDemandType()
    {
        return _strIdGruDemandType;
    }

    /**
     * Set the id of gru demand type
     * @param strIdGruDemandType 
     */
    public void setIdGruDemandType( String strIdGruDemandType )
    {
        _strIdGruDemandType = strIdGruDemandType;
    }

}
