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

package fr.paris.lutece.plugins.appcenter.modules.support.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.modules.support.business.SupportDemand;
import fr.paris.lutece.plugins.appcenter.modules.support.business.SupportsData;
import fr.paris.lutece.plugins.appcenter.modules.support.business.UploadFile;
import fr.paris.lutece.plugins.appcenter.modules.support.service.SupportAsynchronousUploadHandler;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.appcenter.web.AppCenterXPage;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import fr.paris.lutece.portal.business.file.File;
import fr.paris.lutece.portal.business.file.FileHome;
import fr.paris.lutece.portal.business.physicalfile.PhysicalFile;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.util.filesystem.FileSystemUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.fileupload.FileItem;

/**
 * SourcesXPage
 */
@Controller( xpageName = "support", pageTitleI18nKey = "module.appcenter.support.xpage.support.pageTitle", pagePathI18nKey = "module.appcenter.support.xpage.support.pagePathLabel" )
public class SupportsXPage extends AppCenterXPage
{
    // Templates
    private static final String TEMPLATE_MANAGE_SUPPORT = "/skin/plugins/appcenter/modules/support/manage_support.html";

    // PARAMETERS
    private static final String PARAMETER_FIELD_NAME = "fieldname";

    // Markers
    private static final String MARK_HANDLER = "handler";
    private static final String MARK_FILE_NAME = "fieldName";
    private static final String MARK_LIST_UPLOADED_FILE = "listFiles";

    // VIEW
    private static final String VIEW_MANAGE_SUPPORT = "managesupport";

    // ACTION
    private static final String ACTION_ADD_SUPPORT = "addSupport";

    @View( value = VIEW_MANAGE_SUPPORT, defaultView = true )
    public XPage getManageSupport( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        Map<String, Object> model = getModel( );
        
        String strFiledName = SupportAsynchronousUploadHandler.getHandler( ).buildFieldName( request );
        
        fillAppCenterCommons( model, request );
        model.put( MARK_HANDLER, SupportAsynchronousUploadHandler.getHandler( ) );
        model.put( MARK_FILE_NAME, strFiledName );
        model.put( MARK_LIST_UPLOADED_FILE, new ArrayList<FileItem>( ) );
        
        return getXPage( TEMPLATE_MANAGE_SUPPORT , request.getLocale( ), model );
    }

    @Action( ACTION_ADD_SUPPORT )
    public XPage doAddSupport( HttpServletRequest request ) throws UserNotSignedException, SiteMessageException
    {
        Application application = getApplication( request );
        SupportDemand supportDemand = new SupportDemand( );
        populate( supportDemand, request );
        
        // Check constraints
        if ( !validateBean( supportDemand, getLocale( request ) ) )
        {
            return redirectView( request, VIEW_MANAGE_SUPPORT );
        }

        String strUploadValue = request.getParameter( PARAMETER_FIELD_NAME );

        List<FileItem> listFiles = SupportAsynchronousUploadHandler.getHandler( ).getListUploadedFiles( strUploadValue, request.getSession( ) );
        List<UploadFile> listUploadFiles = new ArrayList<>();

        if ( !listFiles.isEmpty( ) )
        {         
            for ( FileItem fileitem : listFiles )
            {
                File file = buildFileWithPhysicalFile( fileitem );
                int nidFile = FileHome.create( file );
                
                
                UploadFile uploadFile = new UploadFile( );
                uploadFile.setIdFile( nidFile );
                uploadFile.setTitle( file.getTitle( ) );
                uploadFile.setSize( file.getSize( ) );
                
                listUploadFiles.add( uploadFile );
            }
        }
        
        supportDemand.setListFilesSupport( listUploadFiles );

        SupportAsynchronousUploadHandler.getHandler( ).removeSessionFiles( request.getSession( ).getId( ) );

        DemandService.saveDemand( supportDemand, application );

        return redirect(request, VIEW_MANAGE_SUPPORT, Constants.PARAM_ID_APPLICATION, application.getId() );
    }

    @Override
    protected String getDemandType()
    {
        return SupportDemand.DEMAND_TYPE;
    }

    @Override
    protected Class getDemandClass()
    {
        return SupportDemand.class;
    }

    @Override
    protected String getDatasName()
    {
        return SupportsData.DATA_SUPPORTS_NAME;
    }

    @Override
    protected Class getDatasClass()
    {
        return SupportsData.class;
    }
    
    /**
     * Builds the file with physical file.
     *
     * @param fileItem
     *            the file item
     * @return the file
     */
    private File buildFileWithPhysicalFile( FileItem fileItem )
    {
        File file = new File( );
        file.setTitle( fileItem.getName( ) );
        file.setSize( ( fileItem.getSize( ) < Integer.MAX_VALUE ) ? (int) fileItem.getSize( ) : Integer.MAX_VALUE );
        file.setMimeType( FileSystemUtil.getMIMEType( file.getTitle( ) ) );

        PhysicalFile physicalFile = new PhysicalFile( );
        physicalFile.setValue( fileItem.get( ) );
        file.setPhysicalFile( physicalFile );

        return file;
}

}
