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

package fr.paris.lutece.plugins.appcenter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.paris.lutece.plugins.appcenter.business.User;
import fr.paris.lutece.plugins.appcenter.business.UserApplicationRoleHome;
import fr.paris.lutece.plugins.appcenter.business.UserHome;
import fr.paris.lutece.plugins.appcenter.business.UserInfos;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.util.ReferenceList;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

/**
 * UserService
 */
public class UserService
{
    private static final String MOCK_USER = "john.doe@nowhere.com";
    private static final String CONSTANTE_EMPTY_JSON = "{}";

    private static ObjectMapper _mapper = new ObjectMapper( );
    
    /**
     * Get the list of available users
     * 
     * @return The list
     */
    public static ReferenceList getUserList( )
    {
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            ReferenceList list = new ReferenceList( );
            for ( LuteceUser user : SecurityService.getInstance( ).getUsers( ) )
            {
                list.addItem( getEmailUser( user ), getEmailUser( user ) );
            }
            return list;

        }
        else
        {
            return getMockUserList( );
        }
    }

    /**
     * Gets the Id of the current connected user
     * 
     * @param request
     *            The HTTP request
     * @return The user Id
     */
    public static String getCurrentUserId( HttpServletRequest request )
    {
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            LuteceUser user = SecurityService.getInstance( ).getRegisteredUser( request );
            return getEmailUser( user );
        }
        else
        {
            return MOCK_USER;
        }

    }
    
    /**
     * Gets the current connected user
     * 
     * @param request
     *            The HTTP request
     * @param nApplicationId The applcation Id
     * @return The user Id
     */
    public static User getCurrentUser( HttpServletRequest request ) throws UserNotSignedException
    {
        User user = new User();
        
        if ( SecurityService.isAuthenticationEnable( ) )
        {
            LuteceUser luteceUser = SecurityService.getInstance( ).getRegisteredUser( request );
            if( luteceUser != null )
            {
                user  = UserHome.findByPrimaryKey( getEmailUser( luteceUser ) );
                if ( user == null )
                {
                    user = new User();
                    user.setId( getEmailUser( luteceUser ) );
                    user.setUserInfos( CONSTANTE_EMPTY_JSON );
                    UserHome.create( user );
                    user.setListUserApplicationRoles( UserApplicationRoleHome.getUserApplicationRolesListByIdUser( user.getId( ) ) );
                }
            }
            else
            {
                throw new UserNotSignedException();
            }
        }
        else
        {
            user  = UserHome.findByPrimaryKey( MOCK_USER);
            if ( user != null )
            {
                user.setListUserApplicationRoles( UserApplicationRoleHome.getUserApplicationRolesListByIdUser( user.getId( ) ) );
            }
        }
        return user;

    }

    /**
     * Gets the user's email
     * 
     * @param user
     *            The user
     * @return The mail
     */
    public static String getEmailUser( LuteceUser user )
    {
        String strUserEmail = null;

        if( user != null )
        {
            strUserEmail = user.getEmail( ) == null ? user.getUserInfo( LuteceUser.BUSINESS_INFO_ONLINE_EMAIL ): user.getEmail( );
        }

        return strUserEmail;

    }

  

    /**
     * Mock list for dev and tests without MyLutece
     * 
     * @return a mock list
     */
    private static ReferenceList getMockUserList( )
    {
        ReferenceList list = new ReferenceList( );
        list.addItem( MOCK_USER, MOCK_USER );
        return list;
    }
    
    /**
     * Get the user in the application context (with his specific role for the application)
     * @param request
     * @param nIdApplication
     * @return 
     */
    public static User getCurrentUserInAppContext( HttpServletRequest request, int nIdApplication ) throws UserNotSignedException
    {
        User user = getCurrentUser( request );
        if ( user.getListUserApplicationRoles() != null )
        {
            //Do a Home method instead
            user.setListUserApplicationRoles(
            user.getListUserApplicationRoles()
                .stream()
                .filter( userApplicationRole -> userApplicationRole.getIdApplication() == nIdApplication)
                .collect( Collectors.toList( ) ) );
            
        }
        return user;
    }
    
    /**
     * Save a data subset into the global JSON data of an application
     *
     * @param user
     * @param dataSubset
     *            The data subset
     */
    public static void saveUserInfos( User user, DataSubset dataSubset )
    {
        try
        {
            String strUpdatedJson = getUserInfo( user, dataSubset );
            UserHome.updateUserInfos( user.getId( ), strUpdatedJson );
            user.setUserInfos( strUpdatedJson );
        }
        catch( IOException ex )
        {
            Logger.getLogger( UserService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <UI>
     * @param <T>
     *            The datasubset type
     * @param user
     * @param userInfosClass

     * @return The data subset as an object
     */
    public static <T extends UserInfos> T loadUserInfosDataSubset( User user, Class<T> userInfosClass )
    {
        try
        {
            Method mGetDataSetName = userInfosClass.getMethod("getName");
            String strDataSetName=(String)mGetDataSetName.invoke( userInfosClass.newInstance(), null );
            String strUserInfosJson = user.getUserInfos( );
            return getDataSubset( strUserInfosJson, strDataSetName, userInfosClass );
        }
        catch( IOException|NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException| InstantiationException ex )
        {
            Logger.getLogger( ApplicationService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
        return null;
    }
    
    
    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param user
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    public static <T> T loadUserInfoSubset( User user, String strDataSubsetName, Class<T> valueType )
    {
        try
        {
            String strUserInfosJson = user.getUserInfos( );
            return getDataSubset( strUserInfosJson, strDataSubsetName, valueType );
        }
        catch( IOException ex )
        {
            Logger.getLogger( UserService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
        return null;
}

    /**
     * Build a global JSON data of an application by adding or replacing a data subset
     * 
     * @param application
     *            The application
     * @param dataSubset
     *            The data subset
     * @return The JSON
     * @throws IOException
     *             if an error occurs
     */
    static String getUserInfo( User user, DataSubset dataSubset ) throws IOException
    {
        String strUserInfos = user.getUserInfos( );
        JsonNode nodeUserInfos;
        if ( strUserInfos.isEmpty( ) )
        {
            nodeUserInfos = _mapper.createObjectNode();
        }
        else
        {
            nodeUserInfos = _mapper.readTree( strUserInfos );
        }
        
        JsonNode nodeData = nodeUserInfos.get( dataSubset.getName( ) );
        if ( nodeData != null )
        {
            ( (ObjectNode) nodeUserInfos ).replace( dataSubset.getName( ), _mapper.valueToTree( dataSubset ) );
        }
        else
        {
            ( (ObjectNode) nodeUserInfos ).set( dataSubset.getName( ), _mapper.valueToTree( dataSubset ) );
        }
        return nodeUserInfos.toString( );
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param nodeUserInfos
     *            The global JSON of the application
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    static <T> T getDataSubset( String strUserInfosJson, String strDataSubsetName, Class<T> valueType ) throws IOException
    {
        JsonNode nodeUserInfos = _mapper.readTree( strUserInfosJson );
        JsonNode nodeData = nodeUserInfos.get( strDataSubsetName );
        if ( nodeData != null )
        {
            return _mapper.treeToValue( nodeData, valueType );
        }
        return null;

    }
	

}
