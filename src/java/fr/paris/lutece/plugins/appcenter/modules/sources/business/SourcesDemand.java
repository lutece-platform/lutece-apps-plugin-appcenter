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
package fr.paris.lutece.plugins.appcenter.modules.sources.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SourcesDemand extends Demand
{
    private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/sources/sources_demand_infos.html";
    
    private String _strLabelKeyUserName;
    private String _strUserName;
    
    private String _strLabelKeyEmail;
    private String _strEmail;

    /**
     * Get the label Key User Name
     * @return the label key for user name
     */
     @JsonProperty
    public String getLabelKeyUserName()
    {
        return _strLabelKeyUserName;
    }

    /**
     * Set the label key for user name
     * @param strLabelKeyUserName the label key for username
     */
    public void setLabelKeyUserName( String strLabelKeyUserName )
    {
        _strLabelKeyUserName = strLabelKeyUserName;
    }

    /**
     * get the user name
     * @return the user name
     */
     @JsonProperty
    public String getUserName()
    {
        return _strUserName;
    }

    /**
     * Set the user name
     * @param strUserName the username 
     */
    public void setUserName( String strUserName )
    {
        _strUserName = strUserName;
    }

    /**
     * Get the label key for email
     * @return the label key for email
     */
    public String getLabelKeyEmail()
    {
        return _strLabelKeyEmail;
    }

    /**
     * Set the label key for email
     * @param strLabelKeyEmail 
     */
    public void setLabelKeyEmail( String strLabelKeyEmail )
    {
        _strLabelKeyEmail = strLabelKeyEmail;
    }

    /**
     * Get the email
     * @return the email
     */
    public String getEmail()
    {
        return _strEmail;
    }

    /**
     * Set the email
     * @param strEmail 
     */
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }
 
    @Override
    public String getComplementaryInfos ( )
    {
        Map<String,Object> model = new HashMap<String,Object>();
        model.put( MARK_DEMAND, this );
        return AppTemplateService.getTemplate( TEMPLATE_SOURCES_DEMAND_INFOS, Locale.FRENCH , model ).getHtml();
    }
}
