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
package fr.paris.lutece.plugins.appcenter.modules.moncomptesettings.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import static fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamDemand.ID_DEMAND_TYPE;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * MonCompteSettingsDemand
 */
public class MonCompteSettingDemand extends Demand
{
    private String _strFavoriteName;
    private String _strFavoriteUrl;
    private String _strBackButtonName;
    private String _strBackButtonUrl;

    public static final String ID_DEMAND_TYPE = "moncomptesettings";
    public static final String DEMAND_TYPE = "moncomptesettings";

    private static final String TEMPLATE_moncomptesettings_DEMAND_INFOS = "skin/plugins/appcenter/modules/moncomptesettings/moncomptesettings_demand_infos.html";

    /**
     * Returns the FavoriteName
     *
     * @return The FavoriteName
     */
    public String getFavoriteName( )
    {
        return _strFavoriteName;
    }

    /**
     * Sets the FavoriteName
     *
     * @param strFavoriteName
     *            The FavoriteName
     */
    public void setFavoriteName( String strFavoriteName )
    {
        _strFavoriteName = strFavoriteName;
    }

    /**
     * Returns the FavoriteUrl
     *
     * @return The FavoriteUrl
     */
    public String getFavoriteUrl( )
    {
        return _strFavoriteUrl;
    }

    /**
     * Sets the FavoriteUrl
     *
     * @param strFavoriteUrl
     *            The FavoriteUrl
     */
    public void setFavoriteUrl( String strFavoriteUrl )
    {
        _strFavoriteUrl = strFavoriteUrl;
    }

    /**
     * Returns the BackButtonName
     *
     * @return The BackButtonName
     */
    public String getBackButtonName( )
    {
        return _strBackButtonName;
    }

    /**
     * Sets the BackButtonName
     *
     * @param strBackButtonName
     *            The BackButtonName
     */
    public void setBackButtonName( String strBackButtonName )
    {
        _strBackButtonName = strBackButtonName;
    }

    /**
     * Returns the BackButtonUrl
     *
     * @return The BackButtonUrl
     */
    public String getBackButtonUrl( )
    {
        return _strBackButtonUrl;
    }

    /**
     * Sets the BackButtonUrl
     *
     * @param strBackButtonUrl
     *            The BackButtonUrl
     */
    public void setBackButtonUrl( String strBackButtonUrl )
    {
        _strBackButtonUrl = strBackButtonUrl;
    }

    @Override
    public String getComplementaryInfos( )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_DEMAND, this );
        return AppTemplateService.getTemplate( TEMPLATE_moncomptesettings_DEMAND_INFOS, Locale.FRENCH, model ).getHtml( );
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
