/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.business.resourcetype;

import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.portal.service.i18n.I18nService;
import java.util.Collection;
import java.util.Locale;

public class EnvironmentResourceType extends AbstractAppCenterResourceType
{ 
    private static final String RESOURCE_TYPE_CODE = "ENV";
    private static final String RESOURCE_TYPE_LABEL_KEY = "appcenter.resourceTypeValue.environment.label";
    
    @Override
    public String getRessourceTypeKey()
    {
        return RESOURCE_TYPE_CODE;
    }

    @Override
    public String getRessourceTypeLabel()
    {
        return I18nService.getLocalizedString( RESOURCE_TYPE_LABEL_KEY, Locale.getDefault( ) );
    }
    
    @Override
    public Collection<ResourceTypeValue> getResourceTypeValues( )
    {
        if ( _listResourceTypeValues.isEmpty( ) )
        {
            for ( Environment envi : Environment.values( ) )
            {
                ResourceTypeValue resourceEnvi = new ResourceTypeValue();
                resourceEnvi.setCode( envi.getPrefix( ) );
                resourceEnvi.setLabel( envi.getLabel( ) );
                _listResourceTypeValues.add( resourceEnvi );
            }
            addWildCardValue();
        }
        return _listResourceTypeValues;
    }
    
}
