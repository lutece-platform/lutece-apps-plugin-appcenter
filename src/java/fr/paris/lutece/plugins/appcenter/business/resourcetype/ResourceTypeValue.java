/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.appcenter.business.resourcetype;

public class ResourceTypeValue
{
    private String _strCode;
    private String _strLabel;

    /**
     * Get the resource type value code
     * 
     * @return the resource type value code
     */
    public String getCode( )
    {
        return _strCode;
    }

    /**
     * Set the resource type value code
     * 
     * @param strCode
     *            the resource type value code
     */
    public void setCode( String strCode )
    {
        _strCode = strCode;
    }

    /**
     * Get the label
     * 
     * @return the label
     */
    public String getLabel( )
    {
        return _strLabel;
    }

    /**
     * Set the label
     * 
     * @param strLabel
     *            the label
     */
    public void setLabel( String strLabel )
    {
        _strLabel = strLabel;
    }

}
