/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.business;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


public class DemandValidation
{
    private int _nId;
    @NotNull( message = "#i18n{appcenter.validation.demandValidation.idDemand.notNull}" )
    private int _nIdDemand;
    @NotNull( message = "#i18n{appcenter.validation.demandValidation.idTask.notNull}" )
    private int _nIdTask;
    @NotNull( message = "#i18n{appcenter.validation.demandValidation.idStatus.notNull}" )
    private int _nStatus;
    @NotEmpty( message = "#i18n{appcenter.validation.demandValidation.idUser.notEmpty}" )
    private String _strIdUser;

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId(  )
    {
        return _nId;
    }

    /**
     * Set the value of id
     *
     * @param nId new value of id
     */
    public void setId( int nId )
    {
        this._nId = nId;
    }

    /**
     * Get the value of demand id
     *
     * @return the value of demand id
     */
    public int getIdDemand(  )
    {
        return _nIdDemand;
    }

    /**
     * Set the value of demand id
     *
     * @param nIdDemand new value of demand id
     */
    public void setIdDemand( int nIdDemand )
    {
        this._nIdDemand = nIdDemand;
    }

    /**
     * Get the value of task id
     *
     * @return the value of task id
     */
    public int getIdTask(  )
    {
        return _nIdTask;
    }

    /**
     * Set the value of task id
     *
     * @param nIdTask new value of task id
     */
    public void setIdTask( int nIdTask )
    {
        this._nIdTask = nIdTask;
    }

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public int getStatus(  )
    {
        return _nStatus;
    }

    /**
     * Set the value of status
     *
     * @param nStatus new value of status
     */
    public void setStatus( int nStatus )
    {
        this._nStatus = nStatus;
    }

    /**
     * Get the value of the user id
     *
     * @return the value of the user id
     */
    public String getIdUser(  )
    {
        return _strIdUser;
    }

    /**
     * Set the value of the user id
     *
     * @param strIdUser new value of the user id
     */
    public void setIdUser( String strIdUser )
    {
        this._strIdUser = strIdUser;
    }
}
