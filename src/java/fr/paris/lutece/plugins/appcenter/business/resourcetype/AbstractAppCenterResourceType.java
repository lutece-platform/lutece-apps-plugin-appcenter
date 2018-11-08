/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.business.resourcetype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAppCenterResourceType implements IAppCenterResourceType
{
    private static final String CONSTANT_WILDCARD = "*";
    private static final String CONSTANT_RESOURCE_TYPE_VALUE_CODE_WILDCARD = "WILDCARD";
    protected List<ResourceTypeValue> _listResourceTypeValues = new ArrayList<>()  ;
    
    /**
     * Add Wildcard value to resources types values
     */
    protected void addWildCardValue()
    {
        ResourceTypeValue resourceTypeValue = new ResourceTypeValue();
        resourceTypeValue.setCode( CONSTANT_RESOURCE_TYPE_VALUE_CODE_WILDCARD );
        resourceTypeValue.setLabel( CONSTANT_WILDCARD );
        _listResourceTypeValues.add( resourceTypeValue );
    }
}
