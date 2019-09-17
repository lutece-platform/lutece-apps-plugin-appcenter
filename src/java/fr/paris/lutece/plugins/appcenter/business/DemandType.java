/*
 * Copyright (c) 2002-2019, Mairie de Paris
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

import fr.paris.lutece.portal.service.rbac.RBACResource;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the business class for the object DemandType
 */
public class DemandType implements Serializable, RBACResource
{
    private static final long serialVersionUID = 1L;
    public static final String RBAC_RESOURCE_DEMAND_TYPE = "demand_type";

    // Variables declarations
    private int _nId;

    @NotEmpty( message = "#i18n{appcenter.validation.demandtype.IdDemandType.notEmpty}" )
    @Size( max = 255, message = "#i18n{appcenter.validation.demandtype.IdDemandType.size}" )
    private String _strIdDemandType;

    @NotEmpty( message = "#i18n{appcenter.validation.demandtype.Label.notEmpty}" )
    private String _strLabel;

    private String _strDescription;

    private String _strQuestion;

    private int _nIdCategoryDemandType;

    private int _nIdWorkflow;

    private String _strJavaClass;

    private int _nOrder;

    private List<Documentation> _listDocumentation;

    public DemandType( )
    {
        _listDocumentation = new ArrayList<>( );
    }

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the IdDemandType
     * 
     * @return The IdDemandType
     */
    public String getIdDemandType( )
    {
        return _strIdDemandType;
    }

    /**
     * Sets the IdDemandType
     * 
     * @param strIdDemandType
     *            The IdDemandType
     */
    public void setIdDemandType( String strIdDemandType )
    {
        _strIdDemandType = strIdDemandType;
    }

    /**
     * Returns the Label
     * 
     * @return The Label
     */
    public String getLabel( )
    {
        return _strLabel;
    }

    /**
     * Sets the Label
     * 
     * @param strLabel
     *            The Label
     */
    public void setLabel( String strLabel )
    {
        _strLabel = strLabel;
    }

    /**
     * Returns the IdCategoryDemandType
     * 
     * @return The IdCategoryDemandType
     */
    public int getIdCategoryDemandType( )
    {
        return _nIdCategoryDemandType;
    }

    /**
     * Sets the IdCategoryDemandType
     * 
     * @param nIdCategoryDemandType
     *            The IdCategoryDemandType
     */
    public void setIdCategoryDemandType( int nIdCategoryDemandType )
    {
        _nIdCategoryDemandType = nIdCategoryDemandType;
    }

    /**
     * Returns the Order
     * 
     * @return The Order
     */
    public int getOrder( )
    {
        return _nOrder;
    }

    /**
     * Sets the Order
     * 
     * @param nOrder
     *            The Order
     */
    public void setOrder( int nOrder )
    {
        _nOrder = nOrder;
    }

    /**
     * Get the documentation list
     * 
     * @return
     */
    public List<Documentation> getListDocumentation( )
    {
        return _listDocumentation;
    }

    /**
     * Set the documentation list
     * 
     * @param listDocumentation
     */
    public void setListDocumentation( List<Documentation> listDocumentation )
    {
        _listDocumentation = listDocumentation;
    }

    public void addDoc( Documentation doc )
    {
        _listDocumentation.add( doc );
    }

    public String getDescription( )
    {
        return _strDescription;
    }

    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    public String getQuestion( )
    {
        return _strQuestion;
    }

    public void setQuestion( String strQuestion )
    {
        _strQuestion = strQuestion;
    }

    public int getIdWorkflow( )
    {
        return _nIdWorkflow;
    }

    public void setIdWorkflow( int nIdWorkflow )
    {
        _nIdWorkflow = nIdWorkflow;
    }

    public String getJavaClass( )
    {
        return _strJavaClass;
    }

    public void setJavaClass( String strJavaClass )
    {
        _strJavaClass = strJavaClass;
    }

    @Override
    public String getResourceTypeCode( )
    {
        return RBAC_RESOURCE_DEMAND_TYPE;
    }

    @Override
    public String getResourceId( )
    {
        return getIdDemandType( );
    }

}
