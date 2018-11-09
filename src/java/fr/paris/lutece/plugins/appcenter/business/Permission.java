/*
 * Copyright (c) 2002-2018, Mairie de Paris
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

import fr.paris.lutece.plugins.appcenter.business.resourcetype.IAppCenterResourceType;
import fr.paris.lutece.plugins.appcenter.service.ResourceTypeService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import java.io.Serializable;
import java.util.Locale;

/**
 * This is the business class for the object Permission
 */ 
public class Permission implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String _strCode;
    private String _strLabel;
    private String _resourceTypeKey;

    /**
     * Returns the Code
     * @return The Code
     */
    public String getCode( )
    {
        return _strCode;
    }

    /**
     * Sets the Code
     * @param strCode The Code
     */ 
    public void setCode( String strCode )
    {
        _strCode = strCode;
    }
    
    /**
     * Returns the Label
     * @return The Label
     */
    public String getLabel( )
    {
        String strLabel = I18nService.getLocalizedString( _strLabel, Locale.getDefault( ) );
        if ( strLabel.isEmpty( )  )
        {
            return _strLabel;
        }
        return strLabel;
        
    }
    
    /**
     * Returns the Label
     * @param locale
     * @return The Label
     */
    public String getLabel( Locale locale )
    {
        String strLabel = I18nService.getLocalizedString( _strLabel, locale );
        if ( strLabel.isEmpty( )  )
        {
            return _strLabel;
        }
        return strLabel;
    }
    
    

    /**
     * Sets the Label
     * @param strLabel The Label
     */ 
    public void setLabel( String strLabel )
    {
        _strLabel = strLabel;
    }
    
    /**
     * Sets the ResourceType
     * @param resourceTypeClass The ResourceType class
     */ 
    public void setResourceTypeKey( String strResourceTypeKey )
    {
        _resourceTypeKey = strResourceTypeKey;
    }
    
    /**
     * Get the IAppcenterResourceType associated to this permission
     * @return the IAppcenterResourceType associated to this permission
     */
    public IAppCenterResourceType getResourceType( )
    {
        return ResourceTypeService.getResourceType( _resourceTypeKey );
    }
}
