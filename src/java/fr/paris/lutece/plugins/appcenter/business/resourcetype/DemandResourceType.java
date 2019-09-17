/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.business.resourcetype;

import fr.paris.lutece.portal.service.i18n.I18nService;
import java.util.Collection;
import java.util.Locale;

/**
 *
 * @author alexandre
 */
public class DemandResourceType extends AbstractAppCenterResourceType
{
    private static final String RESOURCE_TYPE_CODE = "DEMAND";
    private static final String RESOURCE_TYPE_LABEL_KEY = "appcenter.resourceType.demand.label";

    private static final String RESOURCE_TYPE_VALUE_CODE = "DEMAND";
    private static final String RESOURCE_TYPE_VALUE_LABEL_KEY = "appcenter.resourceTypeValue.demand.label";

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
