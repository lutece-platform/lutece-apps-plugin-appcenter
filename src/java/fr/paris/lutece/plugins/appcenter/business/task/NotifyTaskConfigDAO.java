/*
 * Copyright (c) 2002-2019, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.business.task;

import fr.paris.lutece.plugins.appcenter.service.AppcenterPlugin;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for NotifyTaskConfig objects
 */

public final class NotifyTaskConfigDAO implements INotifyTaskConfigDAO, ITaskConfigDAO<NotifyTaskConfig>
{

    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_task, notification_type, id_mailing_list , subject, message, sender_name, recipients, recipientsCc FROM appcenter_task_notify_config WHERE id_task = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_task_notify_config ( id_task, notification_type, id_mailing_list ,  subject, message, sender_name, recipients, recipientsCc ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_task_notify_config WHERE id_task = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_task_notify_config SET id_task = ?, notification_type = ?, id_mailing_list = ?, subject = ?, message = ?, sender_name = ?, recipients = ?, recipientsCc = ? WHERE id_task = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_task, notification_type, id_mailing_list , subject, message, sender_name, recipients, recipientsCc FROM appcenter_task_notify_config";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( NotifyTaskConfig notifyTaskConfig, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        daoUtil.setInt( 1, notifyTaskConfig.getIdTask( ) );
        daoUtil.setString( 2, notifyTaskConfig.getNotificationType( ) );
        daoUtil.setInt( 3, notifyTaskConfig.getIdMailingList( ) );
        daoUtil.setString( 4, notifyTaskConfig.getSubject( ) );
        daoUtil.setString( 5, notifyTaskConfig.getMessage( ) );
        daoUtil.setString( 6, notifyTaskConfig.getSenderName( ) );
        daoUtil.setString( 7, notifyTaskConfig.getRecipients( ) );
        daoUtil.setString( 8, notifyTaskConfig.getRecipientsCc( ) );
        
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public NotifyTaskConfig load( int nId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nId );
        daoUtil.executeQuery( );

        NotifyTaskConfig notifyTaskConfig = null;

        if ( daoUtil.next( ) )
        {
            notifyTaskConfig = new NotifyTaskConfig( );
            int nIndex = 0;
            notifyTaskConfig.setIdTask( daoUtil.getInt( ++nIndex ) );
            notifyTaskConfig.setNotificationType( daoUtil.getString( ++nIndex ) );
            notifyTaskConfig.setIdMailingList( daoUtil.getInt( ++nIndex ) );
            notifyTaskConfig.setSubject( daoUtil.getString( ++nIndex ) );
            notifyTaskConfig.setMessage( daoUtil.getString( ++nIndex ) );
            notifyTaskConfig.setSenderName( daoUtil.getString( ++nIndex ) );
            notifyTaskConfig.setRecipients( daoUtil.getString( ++nIndex ) );
            notifyTaskConfig.setRecipientsCc( daoUtil.getString( ++nIndex ) );
        }

        daoUtil.free( );

        return notifyTaskConfig;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nNotifyTaskConfigId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nNotifyTaskConfigId );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( NotifyTaskConfig notifyTaskConfig, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        int nIndex = 0;
        daoUtil.setInt( ++nIndex, notifyTaskConfig.getIdTask( ) );
        daoUtil.setString( ++nIndex, notifyTaskConfig.getNotificationType( ) );
        daoUtil.setInt( ++nIndex, notifyTaskConfig.getIdMailingList( ) );
        daoUtil.setString( ++nIndex, notifyTaskConfig.getSubject( ) );
        daoUtil.setString( ++nIndex, notifyTaskConfig.getMessage( ) );
        daoUtil.setString( ++nIndex, notifyTaskConfig.getSenderName( ) );
        daoUtil.setString( ++nIndex, notifyTaskConfig.getRecipients( ) );
        daoUtil.setString( ++nIndex, notifyTaskConfig.getRecipientsCc( ) );
        daoUtil.setInt( ++nIndex, notifyTaskConfig.getIdTask( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<NotifyTaskConfig> selectNotifyTaskConfigsList( Plugin plugin )
    {
        List<NotifyTaskConfig> listNotifyTaskConfigs = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            NotifyTaskConfig notifyTaskConfig = new NotifyTaskConfig( );

            notifyTaskConfig.setIdTask( daoUtil.getInt( 1 ) );
            notifyTaskConfig.setNotificationType( daoUtil.getString( 2 ) );
            notifyTaskConfig.setIdMailingList( daoUtil.getInt( 3 ) );
            notifyTaskConfig.setSubject( daoUtil.getString( 4 ) );
            notifyTaskConfig.setMessage( daoUtil.getString( 5 ) );
            notifyTaskConfig.setSenderName( daoUtil.getString( 6 ) );
            notifyTaskConfig.setRecipients( daoUtil.getString( 7 ) );
            notifyTaskConfig.setRecipientsCc( daoUtil.getString( 8 ) );
            
            listNotifyTaskConfigs.add( notifyTaskConfig );
        }

        daoUtil.free( );

        return listNotifyTaskConfigs;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( NotifyTaskConfig notifyTaskConfig )
    {
        insert( notifyTaskConfig, AppcenterPlugin.getPlugin( ) );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( NotifyTaskConfig notifyTaskConfig )
    {
        store( notifyTaskConfig, AppcenterPlugin.getPlugin( ) );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public NotifyTaskConfig load( int nIdTaskConfig )
    {
        return load( nIdTaskConfig, AppcenterPlugin.getPlugin( ) );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nIdTaskConfig )
    {
        delete( nIdTaskConfig, AppcenterPlugin.getPlugin( ) );
    }

}
