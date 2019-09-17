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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.business.resourcetype;

import fr.paris.lutece.portal.service.i18n.I18nService;
import java.util.Collection;
import java.util.Locale;

public class ApplicationResourceType extends AbstractAppCenterResourceType
{
    private static final String RESOURCE_TYPE_CODE = "APP";
    private static final String RESOURCE_TYPE_LABEL_KEY = "appcenter.resourceType.application.label";

    private static final String RESOURCE_TYPE_VALUE_CODE = "APP";
    private static final String RESOURCE_TYPE_VALUE_LABEL_KEY = "appcenter.resourceTypeValue.application.label";

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRessourceTypeKey( )
    {
        return RESOURCE_TYPE_CODE;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRessourceTypeLabel( )
    {
        return I18nService.getLocalizedString( RESOURCE_TYPE_LABEL_KEY, Locale.getDefault( ) );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<ResourceTypeValue> getResourceTypeValues( )
    {
        if ( _listResourceTypeValues.isEmpty( ) )
        {
            ResourceTypeValue resourceApplication = new ResourceTypeValue( );
            resourceApplication.setCode( RESOURCE_TYPE_VALUE_CODE );
            resourceApplication.setLabel( I18nService.getLocalizedString( RESOURCE_TYPE_VALUE_LABEL_KEY, Locale.getDefault( ) ) );
            _listResourceTypeValues.add( resourceApplication );
        }
        return _listResourceTypeValues;
    }

}
