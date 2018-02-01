/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.modules.support.web;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.portal.business.file.File;
import fr.paris.lutece.portal.business.file.FileHome;
import fr.paris.lutece.portal.business.physicalfile.PhysicalFile;
import fr.paris.lutece.portal.business.physicalfile.PhysicalFileHome;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.filesystem.FileSystemUtil;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

/**
 * DoAdminDownloadFile
 */
public class DoDownloadFile
{
    private static final String PARAMETER_ID_FILE = "id_file";
    private static final String PARAMETER_ID_APPLICATION = "id_app";
    private static final String MESSAGE_ERROR_DURING_DOWNLOAD_FILE = "module.appcenter.support.error.error_during_download_file";
    private static final String MESSAGE_ERROR_APP_NOT_FOUND = "module.appcenter.support.error.applicationNotFound";
    private static final String MESSAGE_ERROR_USER_NOT_AUTHORIZED = "module.appcenter.support.error.userNotAuthorized";
    
    /**
     * Write in the http response the file to upload
     * @param request the http request
     * @param response The http response
     * @return Error Message
     *
     */
    public static void doDownloadFile( HttpServletRequest request, HttpServletResponse response ) throws UserNotSignedException, SiteMessageException
    {             
        String strIdApp = request.getParameter( PARAMETER_ID_APPLICATION );
        String strIdFile = request.getParameter( PARAMETER_ID_FILE );

        if ( StringUtils.isBlank( strIdFile ) 
                || !StringUtils.isNumeric( strIdFile ) 
                || StringUtils.isBlank( strIdApp )
                || !StringUtils.isNumeric( strIdApp ) )
        {
            SiteMessageService.setMessage( request, MESSAGE_ERROR_DURING_DOWNLOAD_FILE, SiteMessage.TYPE_STOP );
        }

        int nIdApp = Integer.parseInt( strIdApp );
        int nIdFile = Integer.parseInt( strIdFile );

        Application application = ApplicationHome.findByPrimaryKey( nIdApp );

        if ( application == null )
        {
            SiteMessageService.setMessage( request, MESSAGE_ERROR_APP_NOT_FOUND, SiteMessage.TYPE_ERROR );
        }
        
        LuteceUser user = null;

        if ( SecurityService.isAuthenticationEnable( ) )
        {
            user = SecurityService.getInstance( ).getRemoteUser( request );
            if ( user == null )
            {
                throw new UserNotSignedException( );
            }
        }
        
        if ( user != null && !ApplicationHome.isAuthorized( nIdApp, user.getEmail( ) ) )
        {
            SiteMessageService.setMessage( request, MESSAGE_ERROR_USER_NOT_AUTHORIZED, SiteMessage.TYPE_ERROR );
        }

        File file = FileHome.findByPrimaryKey( nIdFile );
        PhysicalFile physicalFile = ( file != null )
            ? PhysicalFileHome.findByPrimaryKey( file.getPhysicalFile(  ).getIdPhysicalFile(  ) ) : null;

        if ( physicalFile != null )
        {
            try
            {
                byte[] byteFileOutPut = physicalFile.getValue(  );

                response.setHeader( "Content-Disposition", "attachment ;filename=\"" + file.getTitle(  ) + "\"" );
                response.setHeader( "Pragma", "public" );
                response.setHeader( "Expires", "0" );
                response.setHeader( "Cache-Control", "must-revalidate,post-check=0,pre-check=0" );

                String strMimeType = file.getMimeType(  );

                if ( strMimeType == null )
                {
                    strMimeType = FileSystemUtil.getMIMEType( file.getTitle(  ) );
                }

                response.setContentType( strMimeType );
                response.setContentLength( byteFileOutPut.length );

                OutputStream os = response.getOutputStream(  );
                os.write( byteFileOutPut );
                os.close(  );
            }
            catch ( IOException e )
            {
                AppLogService.error( e );
            }
        }
    }
}