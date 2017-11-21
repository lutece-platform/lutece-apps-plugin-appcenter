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
package fr.paris.lutece.plugins.appcenter.modules.guichetpro.business;

import java.io.Serializable;

/**
 * This is the business class for the object DemandTypeCategory
 */ 
public class DemandTypeCategoryRight implements Serializable
{
    boolean _bReadable;
    boolean _bWritable;
    boolean _bCertifiable;

    /**
     * Check if readable
     * @return true if readable, false otherwise
     */
    public boolean isReadable()
    {
        return _bReadable;
    }

    /**
     * Set readable 
     * @param bReadable the readable boolean
     */
    public void setReadable( boolean bReadable )
    {
        _bReadable = bReadable;
    }

    /**
     * Check if writable
     * @return true if writable, false otherwise
     */
    public boolean isWritable()
    {
        return _bWritable;
    }

    /**
     * Set writable 
     * @param bWritable the writable boolean
     */
    public void setWritable( boolean bWritable )
    {
        _bWritable = bWritable;
    }

    /**
     * Check if certifiable
     * @return true if certifiable, false otherwise
     */
    public boolean isCertifiable()
    {
        return _bCertifiable;
    }

    /**
     * Set certifiable 
     * @param bCertifiable the certifiable boolean
     */
    public void setCertifiable( boolean bCertifiable )
    {
        _bCertifiable = bCertifiable;
    }
    
    
}
