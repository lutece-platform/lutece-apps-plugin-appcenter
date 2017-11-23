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

import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * GuichetPro Data Subset
 */
public class GuichetProData extends ApplicationData
{
    @NotEmpty( message = "#i18n{module.appcenter.guichetpro.validation.applicationcode.notEmpty}" )
    private String _strApplicationCode;
    @NotEmpty( message = "#i18n{module.appcenter.guichetpro.validation.grudemandtype.notEmpty}" )
    private String _strGuichetProDemandType;
    @NotEmpty( message = "#i18n{module.appcenter.guichetpro.validation.categorygrudemandtype.notEmpty}" )
    private String _strCategoryGuichetProDemandType;
    private String _strIdGuichetProDemandType;
    private String _strNewCategoryGuichetProDemandType;

    /**
     * Get the application code
     * @return the application code
     */
    public String getApplicationCode()
    {
        return _strApplicationCode;
    }

    /**
     * Set the application code
     * @param strApplicationCode the application code 
     */
    public void setApplicationCode( String strApplicationCode )
    {
        _strApplicationCode = strApplicationCode;
    }

    /**
     * Get the gru demand type
     * @return the gru demand type
     */
    public String getGuichetProDemandType()
    {
        return _strGuichetProDemandType;
    }
    
    /**
     * Set the gru demand type
     * @param strGuichetProDemandType 
     */
    public void setGuichetProDemandType( String strGuichetProDemandType )
    {
        _strGuichetProDemandType = strGuichetProDemandType;
    }

    /**
     * Get the category of gru demand type
     * @return the category of gru demand type
     */
    public String getCategoryGuichetProDemandType()
    {
        return _strCategoryGuichetProDemandType;
    }

    /**
     * Set the category of gru demand type
     * @param strCategoryGuichetProDemandType 
     */
    public void setCategoryGuichetProDemandType( String strCategoryGuichetProDemandType )
    {
        _strCategoryGuichetProDemandType = strCategoryGuichetProDemandType;
    }

    /**
     * Get the id of gru demand type
     * @return the id of gru demand type
     */
    public String getIdGuichetProDemandType()
    {
        return _strIdGuichetProDemandType;
    }

    /**
     * Set the id of gru demand type
     * @param strIdGuichetProDemandType 
     */
    public void setIdGuichetProDemandType( String strIdGuichetProDemandType )
    {
        _strIdGuichetProDemandType = strIdGuichetProDemandType;
    }

    /**
     * Get the new category of gru demand type
     * @return the id of gru demand type
     */
    public String getNewCategoryGuichetProDemandType()
    {
        return _strNewCategoryGuichetProDemandType;
    }

    /**
     * Set the new category of gru demand type
     * @param strNewCategoryGuichetProDemandType 
     */
    public void setNewCategoryGuichetProDemandType( String strNewCategoryGuichetProDemandType )
    {
        _strNewCategoryGuichetProDemandType = strNewCategoryGuichetProDemandType;
    }

}
