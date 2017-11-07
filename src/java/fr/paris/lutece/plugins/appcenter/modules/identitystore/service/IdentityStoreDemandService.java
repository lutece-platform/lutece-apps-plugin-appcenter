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
package fr.paris.lutece.plugins.appcenter.modules.identitystore.service;

import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.Attribute;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.AttributeHome;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.AttributeRight;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class IdentityStoreDemandService
{
    private static final String CONSTANT_READABLE = "_readable";
    private static final String CONSTANT_WRITABLE = "_writable";
    private static final String CONSTANT_CERTIFIABLE = "_certifiable";
    
    private static final String REGEX_ATTRIBUTE = "(.*)_[^_]*";
    
    /**
     * Get the attribute rights map of the request
     * @param request the httpservletrequest
     * @return the map of attribute/attributeRight contains in the request
     */
    public static Map<String,AttributeRight> getMapAttributeRights( HttpServletRequest request )
    {
        Map<String,AttributeRight> mapAttributeRight = new HashMap<>();
        
        Map<String,String[]> colParameters = request.getParameterMap();
        
        for ( Map.Entry<String,String[]> entry : colParameters.entrySet( ) )
        {
            boolean bIsReadable = false;
            boolean bIsWritable = false;
            boolean bIsCertifiable = false;
            String strAttributeKey = StringUtils.EMPTY;
            
            if ( entry.getKey().endsWith( CONSTANT_READABLE ) )
            {
                bIsReadable = true;
            }
            if ( entry.getKey().endsWith( CONSTANT_WRITABLE ) )
            {
                bIsWritable = true;
            }
            if ( entry.getKey().endsWith( CONSTANT_CERTIFIABLE ) )
            {
                bIsCertifiable = true;
            }
            if ( bIsCertifiable || bIsReadable || bIsWritable )
            {
                Pattern p = Pattern.compile( REGEX_ATTRIBUTE );
                Matcher m = p.matcher( entry.getKey( ) );
                if ( m.matches( ) )
                {
                    if ( m.group(1) != null )
                    {
                        strAttributeKey = m.group( 1 );
                        Attribute attribute = AttributeHome.findByAttributeKey( strAttributeKey );
                        if ( attribute != null )
                        {
                            AttributeRight right = mapAttributeRight.get( attribute.getKey( ) );
                                    
                            if ( right == null )
                            {
                                right = new AttributeRight( );
                            }
                            if ( bIsReadable == true )
                            {
                                right.setReadable( true );
                            }
                            if ( bIsWritable == true )
                            {
                                right.setWritable( true );
                            }
                            if ( bIsCertifiable == true )
                            {
                                right.setCertifiable( true );   
                            }
                            
                            mapAttributeRight.put( attribute.getKey( ) , right );
                            }
                        }
                    }
                }
            }
        return mapAttributeRight;
    }
}
