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
package fr.paris.lutece.plugins.appcenter.web;

import fr.paris.lutece.plugins.appcenter.business.User;
import fr.paris.lutece.plugins.appcenter.business.userinfos.GitlabUserInfo;
import fr.paris.lutece.plugins.appcenter.business.userinfos.SvnUserInfo;
import fr.paris.lutece.plugins.appcenter.service.UserService;
import fr.paris.lutece.plugins.appcenter.util.CryptoUtil;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.xpages.XPage;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@Controller( xpageName = "user", pageTitleI18nKey = "appcenter.xpage.user.pageTitle", pagePathI18nKey = "appcenter.xpage.user.pagePathLabel" )
public class UserXPage extends MVCApplication
{
    // Markers
    private static final String MARK_GITLAB_INFOS = "gitlab_infos";
    private static final String MARK_SVN_INFOS = "svn_infos";

    // Template
    private static final String TEMPLATE_VIEW_USER_INFOS = "/skin/plugins/appcenter/user_infos.html";

    // Views
    private static final String VIEW_USER_INFOS = "getUserInfos";

    // Actions
    private static final String ACTION_SAVE_USER_INFOS = "doSaveUserInfos";

    // Infos
    private static final String INFO_USER_INFOS_SAVED = "appcenter.info.user.infos.saved";

    private User _user;
    private GitlabUserInfo _gitlabInfos;
    private SvnUserInfo _svnInfos;

    /**
     * Get the user infos view
     * 
     * @param request
     * @return
     * @throws SiteMessageException
     * @throws UserNotSignedException
     */
    @View( value = VIEW_USER_INFOS, defaultView = true )
    public XPage getUserInfos( HttpServletRequest request ) throws SiteMessageException, UserNotSignedException
    {
        _user = UserService.getCurrentUser( request );

        _gitlabInfos = UserService.loadUserInfoSubset( _user, "gitlab", GitlabUserInfo.class );
        _svnInfos = UserService.loadUserInfoSubset( _user, "svn", SvnUserInfo.class );

        if ( _gitlabInfos == null )
        {
            _gitlabInfos = new GitlabUserInfo( );
        }
        else
        {
            _gitlabInfos.setGitlabPassword( CryptoUtil.decrypt( _gitlabInfos.getGitlabPassword( ) ) );
        }
        if ( _svnInfos == null )
        {
            _svnInfos = new SvnUserInfo( );
        }
        else
        {
            _svnInfos.setSvnPassword( CryptoUtil.decrypt( _svnInfos.getSvnPassword( ) ) );
        }

        Map<String, Object> model = getModel( );

        model.put( MARK_GITLAB_INFOS, _gitlabInfos );
        model.put( MARK_SVN_INFOS, _svnInfos );

        return getXPage( TEMPLATE_VIEW_USER_INFOS, request.getLocale( ), model );
    }

    /**
     * Process the save user infos action
     * 
     * @param request
     * @return the XPage
     * @throws SiteMessageException
     * @throws UserNotSignedException
     */
    @Action( value = ACTION_SAVE_USER_INFOS )
    public XPage doSaveUserInfos( HttpServletRequest request ) throws SiteMessageException, UserNotSignedException
    {
        if ( _gitlabInfos == null )
            _gitlabInfos = new GitlabUserInfo( );
        if ( _svnInfos == null )
            _svnInfos = new SvnUserInfo( );

        populate( _gitlabInfos, request );
        populate( _svnInfos, request );

        _gitlabInfos.setGitlabPassword( CryptoUtil.encrypt( _gitlabInfos.getGitlabPassword( ) ) );
        _svnInfos.setSvnPassword( CryptoUtil.encrypt( _svnInfos.getSvnPassword( ) ) );

        // Modify data
        UserService.saveUserInfos( _user, _gitlabInfos );
        UserService.saveUserInfos( _user, _svnInfos );

        addInfo( INFO_USER_INFOS_SAVED, request.getLocale( ) );
        return redirectView( request, VIEW_USER_INFOS );
    }
}
