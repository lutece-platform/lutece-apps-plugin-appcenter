package fr.paris.lutece.plugins.appcenter.modules.sources.business;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceUserData
{
    private String _strUserName;
    private String _strEmail;
   
    
    /**
     * get the user name
     * 
     * @return the user name
     */
    @JsonProperty
    public String getUserName( )
    {
        return _strUserName;
    }

    /**
     * Set the user name
     * 
     * @param strUserName
     *            the username
     */
    public void setUserName( String strUserName )
    {
        _strUserName = strUserName;
    }

    /**
     * Get the email
     * 
     * @return the email
     */
    public String getEmail( )
    {
        return _strEmail;
    }

    /**
     * Set the email
     * 
     * @param strEmail
     */
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }
}
