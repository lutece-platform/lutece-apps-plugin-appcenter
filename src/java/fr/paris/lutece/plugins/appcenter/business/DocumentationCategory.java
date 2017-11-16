/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

public enum DocumentationCategory implements Serializable
{
    DEV( "dev", "appcenter.documentationCategory.dev" ), 
    INTEG( "integ", "appcenter.documentationCategory.integ" ), 
    PROJECT_MANAGER( "project_manager", "appcenter.documentationCategory.project_manager" );

    private final String _strPrefix;
    private final String _strLabelKey;

    /**
     * Constructor for environment
     * 
     * @param strPrefix
     *            the prefix for environment
     * @param strLabelKey
     *            the i18nk for the environment
     */
    private DocumentationCategory( String strPrefix, String strLabelKey )
    {
        _strPrefix = strPrefix;
        _strLabelKey = strLabelKey;
    }

    /**
     * Get the prefix of the environment
     * 
     * @return the prefix for the environment
     */
    public String getPrefix( )
    {
        return _strPrefix;
    }

    /**
     * Get the I18nk of the environment
     * 
     * @return
     */
    public String getLabelKey( )
    {
        return _strLabelKey;
    }
    
    

    /**
     * Get the environment with given prefix
     * 
     * @param strPrefix
     *            the prefix
     * @return the environment with given prefix
     */
    public static DocumentationCategory getDocumentationCategory( String strPrefix )
    {
        for ( DocumentationCategory docCat : DocumentationCategory.values( ) )
        {
            if ( docCat.getPrefix( ).equals( strPrefix ) )
            {
                return docCat;
            }
        }
        return null;
    }

}
