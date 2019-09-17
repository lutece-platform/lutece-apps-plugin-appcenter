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
package fr.paris.lutece.plugins.appcenter.service.task;

import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDatas;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DemandService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;

/**
 * 
 * AppcenterTask
 *
 */
public abstract class AppcenterTask extends SimpleTask
{

    // SERVICES
    @Inject
    protected IResourceHistoryService _resourceHistoryService;

    /**
     * ProcessTask
     * 
     * @param nIdResourceHistory
     *            the resource Id history
     * @param request
     *            the httpservletRequest
     * @param locale
     *            the locale
     * @param applicationDataClass
     *            a class who extend ApplicationData
     * @param applicationDatasClass
     *            a class who extend ApplicationDatas
     * @param strDataSubsetName
     *            the dataSubsetName associate to the applicationDatas
     * @param demandClass
     *            a class who extend DemandObject
     * @param funct
     *            AppCenterTaskFunctional
     */
    public <AD extends ApplicationData, ADS extends ApplicationDatas<AD>, D extends Demand> void processTask( int nIdResourceHistory,
            HttpServletRequest request, Locale locale, Class<AD> applicationDataClass, Class<ADS> applicationDatasClass, Class<D> demandClass,
            AppCenterTaskFunctional funct )
    {
        AD applicationData = null;
        try
        {
            applicationData = applicationDataClass.newInstance( );
        }
        catch( InstantiationException | IllegalAccessException e )
        {
            // TODO Auto-generated catch block
            AppLogService.error( e );
        }
        BeanUtil.populate( applicationData, request );
        // FIXME return real error message here
        if ( !BeanValidationUtil.validate( applicationData ).isEmpty( ) )
        {
            throw new RuntimeException( "Should not happen after validateTask" );
        }

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        D demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ), demandClass );

        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        ADS datas = ApplicationService.loadApplicationDataSubset( application, applicationDatasClass );
        if ( datas == null )
        {
            try
            {
                datas = applicationDatasClass.newInstance( );
            }
            catch( InstantiationException | IllegalAccessException e )
            {
                // TODO Auto-generated catch block
                AppLogService.error( e );
            }
        }

        if ( funct != null )
        {
            funct.treatment( request, locale, applicationData, datas, demand );
        }

        if ( demand.getIdApplicationData( ) == null || demand.getIdApplicationData( ) == 0 )
        {

            applicationData.addDemandAssociated( demand.getId( ) );
            // Add new data
            datas.addData( applicationData );
            demand.setIdApplicationData( applicationData.getIdApplicationData( ) );
            demand.setDemandData( DemandService.getDemandAsString( demand ) );
        }
        else
        {
            // Modify data
            ListIterator<AD> itr = datas.getListData( ).listIterator( );
            while ( itr.hasNext( ) )
            {
                AD it = itr.next( );
                if ( it.getIdApplicationData( ) == demand.getIdApplicationData( ) )
                {
                    List<Integer> listIdDemandAssociated = it.getListIdDemandAssociated( );
                    // update list id demand associated
                    listIdDemandAssociated.add( demand.getId( ) );
                    applicationData.setIdApplicationData( it.getIdApplicationData( ) );
                    applicationData.setListIdDemandAssociated( listIdDemandAssociated );
                    itr.set( applicationData );
                    break;
                }

            }
        }

        ApplicationService.saveApplicationData( application, datas );
        DemandHome.update( demand );
    }

    /**
     * ProcessTask
     * 
     * @param nIdResourceHistory
     *            the resource Id history
     * @param request
     *            the httpservletRequest
     * @param locale
     *            the locale
     * @param applicationDataClass
     *            a class who extend ApplicationData
     * @param applicationDatasClass
     *            a class who extend ApplicationDatas
     * @param strDataSubsetName
     *            the dataSubsetName associate to the applicationDatas
     * @param demandClass
     *            a class who extend DemandObject
     */
    public <AD extends ApplicationData, ADS extends ApplicationDatas<AD>, D extends Demand> void processTask( int nIdResourceHistory,
            HttpServletRequest request, Locale locale, Class<AD> applicationDataClass, Class<ADS> applicationDatasClass, Class<D> demandClass )
    {
        processTask( nIdResourceHistory, request, locale, applicationDataClass, applicationDatasClass, demandClass, null );

    }

}
