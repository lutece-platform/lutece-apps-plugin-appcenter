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

import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandType;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.modules.support.business.SupportDemand;
import fr.paris.lutece.plugins.appcenter.modules.support.business.UploadFile;
import fr.paris.lutece.plugins.appcenter.web.ManageAppCenterJspBean;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.portal.business.file.File;
import fr.paris.lutece.portal.business.file.FileHome;
import fr.paris.lutece.portal.business.physicalfile.PhysicalFile;
import fr.paris.lutece.portal.business.physicalfile.PhysicalFileHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.filesystem.FileSystemUtil;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

/**
 * DoAdminDownloadFile
 */
public class DoAdminDownloadFile
{
    private static final String PARAMETER_ID_FILE = "id_file";
    private static final String PARAMETER_ID_DEMAND = "id_demand";
    private static final String VIEW_PERMISSION = "VIEW";
    private static final String MESSAGE_ERROR_DURING_DOWNLOAD_FILE = "module.appcenter.support.error.error_during_download_file";

    /**
     * Private constructor
     */
    private DoAdminDownloadFile( )
    {
    }
    
    /**
     * Write in the http response the file to upload
     * @param request the http request
     * @param response The http response
     * @return Error Message
     *
     */
    public static String doDownloadFile( HttpServletRequest request, HttpServletResponse response )
    {             
        String strIdDemand = request.getParameter( PARAMETER_ID_DEMAND );
        String strIdFile = request.getParameter( PARAMETER_ID_FILE );

        if ( StringUtils.isBlank( strIdFile ) 
                || !StringUtils.isNumeric( strIdFile ) 
                || StringUtils.isBlank( strIdDemand )
                || !StringUtils.isNumeric( strIdDemand ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_DURING_DOWNLOAD_FILE, AdminMessage.TYPE_STOP );
        }

        int nIdDemand = Integer.parseInt( strIdDemand );
        int nIdFile = Integer.parseInt( strIdFile );

        Demand demand = DemandHome.findByPrimaryKey( nIdDemand );
        DemandType demandType = DemandTypeHome.findByIdDemandType( demand.getIdDemandType( ) );
        
        SupportDemand supportDemand = (SupportDemand) demand;
        if ( !isFileInDemand( supportDemand, nIdFile ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.USER_ACCESS_DENIED, AdminMessage.TYPE_STOP );
        }
        
        AdminUser adminUser = AdminUserService.getAdminUser( request );
        
        if( ( adminUser == null ) || ( !adminUser.checkRight( ManageAppCenterJspBean.RIGHT_MANAGEAPPCENTER ) ) || ( !RBACService.isAuthorized( demandType, VIEW_PERMISSION, adminUser ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.USER_ACCESS_DENIED, AdminMessage.TYPE_STOP );
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
        
        return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_DURING_DOWNLOAD_FILE, AdminMessage.TYPE_STOP );
    }
    
    /**
     * Check if a file is present in demand
     * @param supportDemand The demand
     * @param nIdFile The file id
     * @return true if file is present in demand and false otherwise.
     *
     */
    private static boolean isFileInDemand( SupportDemand supportDemand, int nIdFile )
    {
        boolean bFileInDemand = false;
        for (UploadFile uploadFile : supportDemand.getListFilesSupport( ))
        {
            if ( uploadFile.getIdFile(  ) == nIdFile )
            {
                bFileInDemand = true;
            }
        }
        
        return bFileInDemand;
    }
}