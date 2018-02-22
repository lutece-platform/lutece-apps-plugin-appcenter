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

import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import fr.paris.lutece.util.ReferenceList;

/**
 * RoleService
 */
public class RoleService
{
    public static final int ROLE_ADMIN = 0;
    public static final int ROLE_OWNER = 1;
    public static final int ROLE_MODIFY = 2;
    public static final int ROLE_VIEW = 3;
    public static final int ROLE_NONE = 4;

    private static final String KEY_PREFIX = "appcenter.role.";
    private static final String [ ] ROLES_KEYS = {
            "admin", "owner", "modify", "view", "none"
    };
    private static final int [ ] ROLES_VALUES = {
            ROLE_ADMIN, ROLE_OWNER, ROLE_MODIFY, ROLE_VIEW
    };

    /**
     * Get the list of roles
     * 
     * @return The list
     */
    public static ReferenceList getRolesList( int nUserRole )
    {
        ReferenceList list = new ReferenceList( );
        for ( int i = 0; i < ROLES_VALUES.length; i++ )
        {
            if ( nUserRole == ROLE_ADMIN || ROLES_VALUES [i] > nUserRole )
            {
                String strRole = getRoleName( ROLES_VALUES [i] );
                list.addItem( ROLES_VALUES [i], strRole );
            }
        }
        return list;
    }

    /**
     * Get a role name from its ID
     * 
     * @param nRoleId
     *            The role ID
     * @return The role name
     */
    public static String getRoleName( int nRoleId )
    {
        return I18nService.getLocalizedString( KEY_PREFIX + ROLES_KEYS [nRoleId], LocaleService.getDefault( ) );
    }

}
