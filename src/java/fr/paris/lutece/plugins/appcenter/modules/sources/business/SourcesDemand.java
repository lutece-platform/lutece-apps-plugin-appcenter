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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.portal.service.template.AppTemplateService;

public class SourcesDemand extends Demand
{
    
    
    
    public static final String DEMAND_TYPE = "sources";
    public static final String ID_DEMAND_TYPE = "sources";
    
    private static final String TEMPLATE_SOURCES_DEMAND_INFOS = "skin/plugins/appcenter/modules/sources/sources_demand_infos.html";
    @NotEmpty( message = "#i18n{module.appcenter.sources.validation.repositoryType.notEmpty}" )
    private String _strRepositoryType;
    @NotEmpty( message = "#i18n{module.appcenter.sources.validation.repositoryName.notEmpty}" )
    private String _strRepositoryName;
    @NotEmpty( message = "#i18n{module.appcenter.sources.validation.publicRepository.notEmpty}" )
    private boolean _bPublicRepository;
    private String _strCategory;
    private List<SourceUserDemand> _listSourceUserDemand;
    
     
    /**
     * {@inheritDoc}
     */
    @Override
    public String getComplementaryInfos( )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_DEMAND, this );
        return AppTemplateService.getTemplate( TEMPLATE_SOURCES_DEMAND_INFOS, Locale.FRENCH, model ).getHtml( );
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
        return false;
    }

    public String getRepositoryType( )
    {
        return _strRepositoryType;
    }

    public void setRepositoryType( String _strRepositoryType )
    {
        this._strRepositoryType = _strRepositoryType;
    }

    public String getRepositoryName( )
    {
        return _strRepositoryName;
    }

    public void setRepositoryName( String _strRepositoryName )
    {
        this._strRepositoryName = _strRepositoryName;
    }

    public boolean isPublicRepository( )
    {
        return _bPublicRepository;
    }

    public void setPublicRepository( boolean bPublicRepository )
    {
        this._bPublicRepository = bPublicRepository;
    }

    public String getCategory( )
    {
        return _strCategory;
    }

    public void setCategory( String _strCategory )
    {
        this._strCategory = _strCategory;
    }

    public List<SourceUserDemand> getListSourceUserDemand( )
    {
        return _listSourceUserDemand;
    }

    public void setListSourceUserDemand( List<SourceUserDemand> _listSourceUserDemand )
    {
        this._listSourceUserDemand = _listSourceUserDemand;

    }

}
