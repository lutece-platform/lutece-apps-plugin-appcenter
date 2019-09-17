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

import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

public class NotifyTaskConfig extends TaskConfig
{
    String _strNotificationType;
    int _nIdMailingList;
    String _strSubject;
    String _strMessage;
    String _strSenderName;

    /**
     * Get the subject
     * 
     * @return the subject
     */
    public String getSubject( )
    {
        return _strSubject;
    }

    /**
     * Set the subject
     * 
     * @param strSubject
     *            the subject
     */
    public void setSubject( String strSubject )
    {
        _strSubject = strSubject;
    }

    /**
     * Get the message
     * 
     * @return the message
     */
    public String getMessage( )
    {
        return _strMessage;
    }

    /**
     * Set the message
     * 
     * @param strMessage
     *            the message
     */
    public void setMessage( String strMessage )
    {
        _strMessage = strMessage;
    }

    /**
     * Get the sender name
     * 
     * @return the sender name
     */
    public String getSenderName( )
    {
        return _strSenderName;
    }

    /**
     * Set the sender name
     * 
     * @param strSenderName
     */
    public void setSenderName( String strSenderName )
    {
        _strSenderName = strSenderName;
    }

    /**
     * Get the notification type
     * 
     * @return the notification type
     */
    public String getNotificationType( )
    {
        return _strNotificationType;
    }

    /**
     * Set the notification type
     * 
     * @param strNotificationType
     */
    public void setNotificationType( String strNotificationType )
    {
        _strNotificationType = strNotificationType;
    }

    /**
     * Get the id mailing list
     * 
     * @return the id mailing list
     */
    public int getIdMailingList( )
    {
        return _nIdMailingList;
    }

    /**
     * Set the id mailing list
     * 
     * @param nIdMailingList
     */
    public void setIdMailingList( int nIdMailingList )
    {
        _nIdMailingList = nIdMailingList;
    }

}
