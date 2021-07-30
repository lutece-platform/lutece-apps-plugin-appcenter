/*
 * Copyright (c) 2002-2021, Mairie de Paris
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

import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.appcenter.business.Environment;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

public class AppcenterResourceIdService extends ResourceIdService
{
    private static final String PROPERTY_LABEL_PERMISSION_RESOURCE_TYPE                        = "appcenter.rbac.demand_type.appcenter.permission";
    private static final String PROPERTY_LABEL_PERMISSION_ADD_FASTDEPLOY_APPLICATION           = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_ADD_FASTDEPLOY_APPLICATION";
    private static final String PROPERTY_LABEL_PERMISSION_ADD_REPO                             = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_ADD_REPO";
    private static final String PROPERTY_LABEL_PERMISSION_ADD_USERS                            = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_ADD_USERS";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_APPCODE_DEMANDS               = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_APPCODE_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_GUICHET_PRO_DEMAND            = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_GUICHET_PRO_DEMAND";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_IDENTITYSTORE_DEMAND          = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_IDENTITYSTORE_DEMAND";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_JOBS                          = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_JOBS";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_MONCOMPTESETTINGS_DEMAND      = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_MONCOMPTESETTINGS_DEMAND";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_NOTIFYGRU_DEMANDS             = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_NOTIFYGRU_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_OPENAM_DEMAND                 = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_OPENAM_DEMAND";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_RHSSO_DEMAND                  = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_RHSSO_DEMAND";
    private static final String PROPERTY_LABEL_PERMISSION_CREATE_SUPPORT_DEMAND                = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_CREATE_SUPPORT_DEMAND";
    private static final String PROPERTY_LABEL_PERMISSION_DEPLOY_APP                           = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_DEPLOY_APP";
    private static final String PROPERTY_LABEL_PERMISSION_DEPLOY_MULTIPLE_APPLICATION          = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_DEPLOY_MULTIPLE_APPLICATION";
    private static final String PROPERTY_LABEL_PERMISSION_DEPLOY_SCRIPT                        = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_DEPLOY_SCRIPT";
    private static final String PROPERTY_LABEL_PERMISSION_INITIALIZE_SQL                       = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_INITIALIZE_SQL";
    private static final String PROPERTY_LABEL_PERMISSION_INITIALIZE_TOMCAT                    = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_INITIALIZE_TOMCAT";
    private static final String PROPERTY_LABEL_PERMISSION_MANAGE_FASTDEPLOY_APPLICATION        = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_MANAGE_FASTDEPLOY_APPLICATION";
    private static final String PROPERTY_LABEL_PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION";
    private static final String PROPERTY_LABEL_PERMISSION_MODIFY_APPLICATION                   = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_MODIFY_APPLICATION";
    private static final String PROPERTY_LABEL_PERMISSION_MODIFY_REPO_RIGHTS                   = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_MODIFY_REPO_RIGHTS";
    private static final String PROPERTY_LABEL_PERMISSION_REMOVE_APPLICATION                   = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_REMOVE_APPLICATION";
    private static final String PROPERTY_LABEL_PERMISSION_REMOVE_USER                          = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_REMOVE_USER";
    private static final String PROPERTY_LABEL_PERMISSION_SUPPORT_DOWNLOAD_FILE                = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_SUPPORT_DOWNLOAD_FILE";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_APPCODE_DEMANDS                 = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_APPCODE_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_APPLICATION                     = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_APPLICATION";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_DEMANDS                         = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_ENVIRONMENT_DEMANDS             = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_ENVIRONMENT_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_GUICHET_PRO_DEMANDS             = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_GUICHET_PRO_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_IDENTITYSTORE_DEMANDS           = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_IDENTITYSTORE_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_JOBS                            = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_JOBS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_MONCOMPTESETTINGS_DEMANDS       = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_MONCOMPTESETTINGS_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_NOTIFYGRU_DEMANDS               = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_NOTIFYGRU_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_OPENAM_DEMANDS                  = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_OPENAM_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_RHSSO_DEMANDS                   = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_RHSSO_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_SOURCES_DEMANDS                 = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_SOURCES_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_SOURCES_RIGHT_DEMANDS           = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_SOURCES_RIGHT_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_SUPPORT_DEMANDS                 = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_SUPPORT_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VIEW_WSSO_DEMANDS                    = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VIEW_WSSO_DEMANDS";
    private static final String PROPERTY_LABEL_PERMISSION_VISUALIZE_ENVI                       = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VISUALIZE_ENVI";
    private static final String PROPERTY_LABEL_PERMISSION_VISUALIZE_HISTORY                    = "appcenter.rbac.demand_type.appcenter.permission.PERMISSION_VISUALIZE_HISTORY";

    private static final String PLUGIN_NAME                                                    = "appcenter";

    @Override
    public ReferenceList getResourceIdList( Locale locale )
    {
        ReferenceList refList = new ReferenceList( );

        ReferenceItem item = new ReferenceItem( );
        item.setCode( "APP" );
        item.setName( "Application" );
        refList.add( item );

        for ( Environment env : Environment.values( ) )
        {
            item = new ReferenceItem( );
            item.setCode( "ENV_" + env.toString( ) );
            item.setName( env.getLabel( ) );
            refList.add( item );
        }

        return refList;
    }

    @Override
    public String getTitle( String strId, Locale locale )
    {
        ReferenceList refList = getResourceIdList( locale );

        Iterator<ReferenceItem> it = refList.iterator( );

        while ( it.hasNext( ) )
        {
            ReferenceItem item = it.next( );
            if ( item.getCode( ).equals( strId ) )
            {
                return item.getName( );
            }
        }
        return StringUtils.EMPTY;
    }

    @Override
    public void register( )
    {
        ResourceType rt = new ResourceType( );
        rt.setResourceIdServiceClass( AppcenterResourceIdService.class.getName( ) );
        rt.setPluginName( PLUGIN_NAME );
        rt.setResourceTypeKey( AppcenterResource.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_PERMISSION_RESOURCE_TYPE );

        Permission p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_ADD_FASTDEPLOY_APPLICATION );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_ADD_FASTDEPLOY_APPLICATION );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_ADD_REPO );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_ADD_REPO );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_ADD_USERS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_ADD_USERS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_APPCODE_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_APPCODE_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_GUICHET_PRO_DEMAND );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_GUICHET_PRO_DEMAND );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_IDENTITYSTORE_DEMAND );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_IDENTITYSTORE_DEMAND );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_JOBS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_JOBS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_MONCOMPTESETTINGS_DEMAND );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_MONCOMPTESETTINGS_DEMAND );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_NOTIFYGRU_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_NOTIFYGRU_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_OPENAM_DEMAND );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_OPENAM_DEMAND );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_RHSSO_DEMAND );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_RHSSO_DEMAND );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_CREATE_SUPPORT_DEMAND );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_CREATE_SUPPORT_DEMAND );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_DEPLOY_APP );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_DEPLOY_APP );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_DEPLOY_MULTIPLE_APPLICATION );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_DEPLOY_MULTIPLE_APPLICATION );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_DEPLOY_SCRIPT );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_DEPLOY_SCRIPT );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_INITIALIZE_SQL );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_INITIALIZE_SQL );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_INITIALIZE_TOMCAT );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_INITIALIZE_TOMCAT );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_MANAGE_FASTDEPLOY_APPLICATION );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_MANAGE_FASTDEPLOY_APPLICATION );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_MODIFY_APPLICATION );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_MODIFY_APPLICATION );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_MODIFY_REPO_RIGHTS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_MODIFY_REPO_RIGHTS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_REMOVE_APPLICATION );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_REMOVE_APPLICATION );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_REMOVE_USER );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_REMOVE_USER );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_SUPPORT_DOWNLOAD_FILE );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_SUPPORT_DOWNLOAD_FILE );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_APPCODE_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_APPCODE_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_APPLICATION );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_APPLICATION );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_ENVIRONMENT_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_ENVIRONMENT_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_GUICHET_PRO_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_GUICHET_PRO_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_IDENTITYSTORE_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_IDENTITYSTORE_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_JOBS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_JOBS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_MONCOMPTESETTINGS_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_MONCOMPTESETTINGS_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_NOTIFYGRU_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_NOTIFYGRU_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_OPENAM_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_OPENAM_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_RHSSO_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_RHSSO_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_SOURCES_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_SOURCES_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_SOURCES_RIGHT_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_SOURCES_RIGHT_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_SUPPORT_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_SUPPORT_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VIEW_WSSO_DEMANDS );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VIEW_WSSO_DEMANDS );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VISUALIZE_ENVI );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VISUALIZE_ENVI );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( AppcenterResource.PERMISSION_VISUALIZE_HISTORY );
        p.setPermissionTitleKey( PROPERTY_LABEL_PERMISSION_VISUALIZE_HISTORY );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }

}
