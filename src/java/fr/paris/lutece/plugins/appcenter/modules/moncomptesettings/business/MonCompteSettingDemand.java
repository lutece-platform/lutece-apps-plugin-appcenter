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

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.Environment;
import java.util.ArrayList;
import java.util.List;
import fr.paris.lutece.plugins.appcenter.service.DataSubset;

/**
 * MonCompteSettingsDemand
 */
public class MonCompteSettingDemand extends Demand
{
    private String _strEnvironment;
    private String _strFavoriteName;
    private String _strFavoriteUrl;
    private String _strBackButtonName;
    private String _strBackButtonUrl;

    /**
     * Returns the Environment
     *
     * @return The Environment
     */
    public String getEnvironment( )
    {
        return _strEnvironment;
    }

    /**
     * Sets the Environment
     *
     * @param environment
     *            The Environment
     */
    public void setEnvironment( String strEnvironment )
    {
        _strEnvironment = strEnvironment;
    }

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
}
