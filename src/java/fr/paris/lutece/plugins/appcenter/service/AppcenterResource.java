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

import fr.paris.lutece.portal.service.rbac.RBACResource;

public class AppcenterResource implements RBACResource
{

    // RBAC management
    public static final String RESOURCE_TYPE                                   = "APPCENTER";

    // Perimissions
    public static final String PERMISSION_ADD_FASTDEPLOY_APPLICATION           = "PERMISSION_ADD_FASTDEPLOY_APPLICATION";
    public static final String PERMISSION_ADD_REPO                             = "PERMISSION_ADD_REPO";
    public static final String PERMISSION_ADD_USERS                            = "PERMISSION_ADD_USERS";
    public static final String PERMISSION_CREATE_APPCODE_DEMANDS               = "PERMISSION_CREATE_APPCODE_DEMANDS";
    public static final String PERMISSION_CREATE_GUICHET_PRO_DEMAND            = "PERMISSION_CREATE_GUICHET_PRO_DEMAND";
    public static final String PERMISSION_CREATE_IDENTITYSTORE_DEMAND          = "PERMISSION_CREATE_IDENTITYSTORE_DEMAND";
    public static final String PERMISSION_CREATE_JOBS                          = "PERMISSION_CREATE_JOBS";
    public static final String PERMISSION_CREATE_MONCOMPTESETTINGS_DEMAND      = "PERMISSION_CREATE_MONCOMPTESETTINGS_DEMAND";
    public static final String PERMISSION_CREATE_NOTIFYGRU_DEMANDS             = "PERMISSION_CREATE_NOTIFYGRU_DEMANDS";
    public static final String PERMISSION_CREATE_OPENAM_DEMAND                 = "PERMISSION_CREATE_OPENAM_DEMAND";
    public static final String PERMISSION_CREATE_RHSSO_DEMAND                  = "PERMISSION_CREATE_RHSSO_DEMAND";
    public static final String PERMISSION_CREATE_SUPPORT_DEMAND                = "PERMISSION_CREATE_SUPPORT_DEMAND";
    public static final String PERMISSION_DEPLOY_APP                           = "PERMISSION_DEPLOY_APP";
    public static final String PERMISSION_DEPLOY_MULTIPLE_APPLICATION          = "PERMISSION_DEPLOY_MULTIPLE_APPLICATION";
    public static final String PERMISSION_DEPLOY_SCRIPT                        = "PERMISSION_DEPLOY_SCRIPT";
    public static final String PERMISSION_INITIALIZE_SQL                       = "PERMISSION_INITIALIZE_SQL";
    public static final String PERMISSION_INITIALIZE_TOMCAT                    = "PERMISSION_INITIALIZE_TOMCAT";
    public static final String PERMISSION_MANAGE_FASTDEPLOY_APPLICATION        = "PERMISSION_MANAGE_FASTDEPLOY_APPLICATION";
    public static final String PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION = "PERMISSION_MANAGE_MULTIPLE_DEPLOY_CONFIGURATION";
    public static final String PERMISSION_MODIFY_APPLICATION                   = "PERMISSION_MODIFY_APPLICATION";
    public static final String PERMISSION_MODIFY_REPO_RIGHTS                   = "PERMISSION_MODIFY_REPO_RIGHTS";
    public static final String PERMISSION_REMOVE_APPLICATION                   = "PERMISSION_REMOVE_APPLICATION";
    public static final String PERMISSION_REMOVE_USER                          = "PERMISSION_REMOVE_USER";
    public static final String PERMISSION_SUPPORT_DOWNLOAD_FILE                = "PERMISSION_SUPPORT_DOWNLOAD_FILE";
    public static final String PERMISSION_VIEW_APPCODE_DEMANDS                 = "PERMISSION_VIEW_APPCODE_DEMANDS";
    public static final String PERMISSION_VIEW_APPLICATION                     = "PERMISSION_VIEW_APPLICATION";
    public static final String PERMISSION_VIEW_DEMANDS                         = "PERMISSION_VIEW_DEMANDS";
    public static final String PERMISSION_VIEW_ENVIRONMENT_DEMANDS             = "PERMISSION_VIEW_ENVIRONMENT_DEMANDS";
    public static final String PERMISSION_VIEW_GUICHET_PRO_DEMANDS             = "PERMISSION_VIEW_GUICHET_PRO_DEMANDS";
    public static final String PERMISSION_VIEW_IDENTITYSTORE_DEMANDS           = "PERMISSION_VIEW_IDENTITYSTORE_DEMANDS";
    public static final String PERMISSION_VIEW_JOBS                            = "PERMISSION_VIEW_JOBS";
    public static final String PERMISSION_VIEW_MONCOMPTESETTINGS_DEMANDS       = "PERMISSION_VIEW_MONCOMPTESETTINGS_DEMANDS";
    public static final String PERMISSION_VIEW_NOTIFYGRU_DEMANDS               = "PERMISSION_VIEW_NOTIFYGRU_DEMANDS";
    public static final String PERMISSION_VIEW_OPENAM_DEMANDS                  = "PERMISSION_VIEW_OPENAM_DEMANDS";
    public static final String PERMISSION_VIEW_RHSSO_DEMANDS                   = "PERMISSION_VIEW_RHSSO_DEMANDS";
    public static final String PERMISSION_VIEW_SOURCES_DEMANDS                 = "PERMISSION_VIEW_SOURCES_DEMANDS";
    public static final String PERMISSION_VIEW_SOURCES_RIGHT_DEMANDS           = "PERMISSION_VIEW_SOURCES_RIGHT_DEMANDS";
    public static final String PERMISSION_VIEW_SUPPORT_DEMANDS                 = "PERMISSION_VIEW_SUPPORT_DEMANDS";
    public static final String PERMISSION_VIEW_WSSO_DEMANDS                    = "PERMISSION_VIEW_WSSO_DEMANDS";
    public static final String PERMISSION_VISUALIZE_ENVI                       = "PERMISSION_VISUALIZE_ENVI";
    public static final String PERMISSION_VISUALIZE_HISTORY                    = "PERMISSION_VISUALIZE_HISTORY";

    private String             _strId                                          = "APP";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceTypeCode( )
    {
        return RESOURCE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceId( )
    {
        return _strId;
    }

    public void setResourceId( String strId )
    {
        _strId = strId;
    }
}
