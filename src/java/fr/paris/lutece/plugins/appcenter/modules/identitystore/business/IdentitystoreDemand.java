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
package fr.paris.lutece.plugins.appcenter.modules.identitystore.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Identitystore Demand
 */
public class IdentitystoreDemand extends Demand
{
    @NotEmpty( message = "#i18n{module.appcenter.identitystore.validation.environment.notEmpty}" )
    private Map<String,AttributeRight> _mapAttributeRights;

    public static final String ID_DEMAND_TYPE = "identitystore";
    public static final String DEMAND_TYPE = "identitystore";

    private static final String TEMPLATE_IDENTITYSTORE_DEMAND_INFOS = "skin/plugins/appcenter/modules/identitystore/identitystore_demand_infos.html";
    
    //Markers
    private static final String MARK_MAP_ATTRIBUTES = "mapAttributes";

    /**
     * Get the attribute rights
     * @return the attribute rights
     */
    public Map<String,AttributeRight> getAttributeRights()
    {
        return _mapAttributeRights;
    }

    /**
     * Set the attribute rights
     * @param mapAttributeRights the attribute rights map 
     */
    public void setAttributeRights( Map<String, AttributeRight> mapAttributeRights )
    {
        _mapAttributeRights = mapAttributeRights;
    }

    

    @Override
    public String getComplementaryInfos( )
    {
        Map<String,Attribute> mapAttributes = AttributeHome.getAttributesMap();
        Map<String, Object> model = new HashMap<String, Object>( );
        
        model.put( MARK_MAP_ATTRIBUTES, mapAttributes );
        model.put( MARK_DEMAND, this );
        return AppTemplateService.getTemplate( TEMPLATE_IDENTITYSTORE_DEMAND_INFOS, Locale.FRENCH, model ).getHtml( );
    }

    /**
     * Get the demand type id
     * 
     * @return the demand type id
     */
    @JsonIgnore
    @Override
    public String getIdDemandType( )
    {
        return ID_DEMAND_TYPE;
    }

    /**
     * Get the demand type id
     * 
     * @return the demand type id
     */
    @JsonIgnore
    @Override
    public String getDemandType( )
    {
        return DEMAND_TYPE;
    }

    @JsonIgnore
    @Override
    public boolean isDependingOfEnvironment()
    {
        return true;
    }
}
