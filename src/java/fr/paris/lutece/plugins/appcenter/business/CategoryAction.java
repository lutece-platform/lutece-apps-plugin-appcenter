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

import java.util.ArrayList;
import java.util.List;

public class CategoryAction
{
    private String _strLabelKey;
    private String _strCssClass;
    private String _strCategoryCode;
    private List<Action> _listActions;

    public CategoryAction( String strLabelKey, String strCssClass, String strCategoryCode, List<Action> listActions )
    {
        _strLabelKey = strLabelKey;
        _strCssClass = strCssClass;
        _strCategoryCode = strCategoryCode;
        _listActions = listActions;
    }

    public CategoryAction( CategoryAction categoryAction )
    {
        this( categoryAction._strLabelKey, categoryAction._strCssClass, categoryAction._strCategoryCode, null );

        List<Action> listActions = new ArrayList<>( );

        for ( Action action : categoryAction._listActions )
        {
            listActions.add( new Action( action ) );
        }

        _listActions = listActions;
    }

    /**
     * Get the label of the action
     * 
     * @return the label of the action
     */
    public String getLabelKey( )
    {
        return _strLabelKey;
    }

    /**
     * Set the label key of the action
     * 
     * @param strLabelKey
     *            the label key of action
     */
    public void setLabelKey( String strLabelKey )
    {
        _strLabelKey = strLabelKey;
    }

    /**
     * Get the css class of the action
     * 
     * @return the css class of the action
     */
    public String getCssClass( )
    {
        return _strCssClass;
    }

    /**
     * Set the css class of the action
     * 
     * @param strCssClass
     *            the css class of the action
     */
    public void setCssClass( String strCssClass )
    {
        _strCssClass = strCssClass;
    }

    /**
     * Get the category code
     * 
     * @return the category code
     */
    public String getCategoryCode( )
    {
        return _strCategoryCode;
    }

    /**
     * Set the category code
     * 
     * @param strCategoryCode
     *            the category code
     */
    public void setCategoryCode( String strCategoryCode )
    {
        _strCategoryCode = strCategoryCode;
    }

    /**
     * Get the action lists
     * 
     * @return the action list
     */
    public List<Action> getListActions( )
    {
        return _listActions;
    }

    /**
     * Set the action list
     * 
     * @param listActions
     *            the action action
     */
    public void setListActions( List<Action> listActions )
    {
        this._listActions = listActions;
    }

}
