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

import java.util.ArrayList;
import java.util.List;


public class ApplicationData
{
    
    private Integer _nIdApplicationData;
    private List<Integer> _listIdDemandAssociated=new ArrayList<Integer>();
    private String _strEnvironment;

    /**
     * Return the environment of the application Data
     * @return the envi of the application Data
     */
    public String getEnvironment()
    {
        return _strEnvironment;
    }

    /**
     * Set the environment of the application Data
     * @param strEnvironment the environment
     */
    public void setEnvironment( String strEnvironment )
    {
        _strEnvironment = strEnvironment;
    }

    /**
     * get the list of id demand associated to the Application data
     * @return the list of id demand associated to the Application data
     */
	public List<Integer> getListIdDemandAssociated() {
		return _listIdDemandAssociated;
	}

	/**
	 * set the list of id demand associated to the Application data
	 * @param listIdDemandAssociated the list of id demand associated to the Application data
	 */
	public void setListIdDemandAssociated(List<Integer> listIdDemandAssociated) {
		this._listIdDemandAssociated = listIdDemandAssociated;
	}
	
	/**
	 * add a new demand associated to the application data
	 * @param nIdDemand the demand id
	 */
	public void addDemandAssociated(Integer nIdDemand) {
		this._listIdDemandAssociated.add(nIdDemand);
	}

	/**
	 * 
	 * @return the application Data Id
	 */
	public Integer getIdApplicationData() {
		return _nIdApplicationData;
	}

	/**
	 *the application data id
	 * @param _nIdApllicationData nIdApplication data
	 */
	public void setIdApplicationData(Integer _nIdApllicationData) {
		this._nIdApplicationData = _nIdApllicationData;
	}
    
    
    
}
