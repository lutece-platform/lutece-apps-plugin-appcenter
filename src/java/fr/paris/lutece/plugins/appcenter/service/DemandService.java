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
package fr.paris.lutece.plugins.appcenter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDatas;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandFilter;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.web.DemandJspBean;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Demand Service
 */
public class DemandService
{
    private static final String PARAMETER_ENVIRONMENT_PREFIX = "environment_prefix";
    private static final String PARAMETER_ID_DEMAND_TYPE = "id_demand_type";
    private static final String PARAMETER_STATE = "state";
    private static final String PARAMETER_ID_APPLICATION = "id_application";

    private static ObjectMapper _mapper = new ObjectMapper( );

    public static void saveDemand( Demand demand, Application application )
    {
        demand.setCreationDate( new java.sql.Timestamp( ( new java.util.Date( ) ).getTime( ) ) );
        demand.setDemandData( getDemandAsString( demand ) );
        DemandHome.create( demand );
        // Run the workflow
        int nIdResource = demand.getId( );
        int nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getDemandType( ) );
        WorkflowService.getInstance( ).getState( nIdResource, Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow, -1 );
        WorkflowService.getInstance( ).executeActionAutomatic( nIdResource, Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow, -1 );

    }

    public static <T extends Demand> List<T> getDemandsListByApplicationAndType( Application application, String strDemandType, Class<T> demandClass )
    {
        return DemandHome.getDemandsListByApplicationAndType( application.getId( ), strDemandType, demandClass );
    }

    public static String getDemandAsString( Demand demand )
    {
        try
        {
            return _mapper.writeValueAsString( demand );
        }
        catch( JsonProcessingException e )
        {
            AppLogService.error( "Unable to convert demand obj to JSON", e );
        }
        return null;
    }

    /**
     * Get the demand filter from the Http request
     * 
     * @param request
     * @return the demand filter
     */
    public static DemandFilter computeDemandFilter( HttpServletRequest request )
    {
        String strEnvironmentPrefix = request.getParameter( PARAMETER_ENVIRONMENT_PREFIX );
        String strIdDemandType = request.getParameter( PARAMETER_ID_DEMAND_TYPE );
        String strState = request.getParameter( PARAMETER_STATE );
        String strApplicationId = request.getParameter( PARAMETER_ID_APPLICATION );

        DemandFilter filter = new DemandFilter( );
        if ( strEnvironmentPrefix != null && !strEnvironmentPrefix.equals( "-1" ) )
        {
            filter.setEnvironmentPrefix( strEnvironmentPrefix );
            filter.setHasEnvironmentPrefix( true );
        }
        if ( strIdDemandType != null && !strIdDemandType.equals( "-1" ) )
        {
            filter.setIdDemandType( strIdDemandType );
            filter.setHasIdDemandType( true );
        }
        if ( strState != null && !strState.equals( "-1" ) )
        {
            filter.setState( strState );
            filter.setHasState( true );
        }
        if ( strApplicationId != null && !strApplicationId.equals( "-1" ) )
        {
            filter.setIdApplication( Integer.parseInt( strApplicationId ) );
            filter.setHasIdApplication( true );
        }

        return filter;
    }

    /**
     * Remove the application whose identifier is specified in parameter with its dependencies
     * 
     * @param nId
     *            The application Id
     */
    public static void remove( int nId )
    {
        Demand demand = DemandHome.findByPrimaryKey( nId );
        Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );

        Class applicationDatasClass = DemandJspBean.getClassApplicationDatasByDemandTypeKey( demand.getDemandType( ) );
        ApplicationDatas datas = null;

        if ( applicationDatasClass != null )
        {
            datas = ApplicationService.loadApplicationDataSubset( application, applicationDatasClass );
        }

        if ( datas != null && demand.getIdApplicationData( ) != null )
        {
            // Modify data
            ListIterator<ApplicationData> itr = datas.getListData( ).listIterator( );
            while ( itr.hasNext( ) )
            {
                ApplicationData it = itr.next( );
                if ( it.getIdApplicationData( ) == demand.getIdApplicationData( ) )
                {
                    List<Integer> listIdDemandAssociated = it.getListIdDemandAssociated( );

                    if ( listIdDemandAssociated.contains( demand.getId( ) ) )
                    {
                        // remove the id in the list of demand associated
                        listIdDemandAssociated.remove( Integer.valueOf( demand.getId( ) ) );
                    }

                    if ( listIdDemandAssociated.size( ) == 0 )
                    {
                        // remove the applicationData
                        itr.remove( );
                    }
                    else
                    {
                        // update the list of demand associated
                        it.setListIdDemandAssociated( listIdDemandAssociated );
                    }
                    break;
                }
            }

            ApplicationService.saveApplicationData( application, datas );
        }

        List<Integer> idResourceList = new ArrayList<Integer>( );
        idResourceList.add( nId );
        int nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getDemandType( ) );
        WorkflowService.getInstance( ).doRemoveWorkFlowResourceByListId( idResourceList, Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow );
        DemandHome.remove( nId );
    }

    /**
     * Get the pretty print JSON data of a demand
     * 
     * @param demand
     *            The demand
     * @return The pretty printed JSON
     * @throws IOException
     *             if an error occurs
     */
    public static String getPrettyPrintDemandData( Demand demand )
    {
        String strDemandJson = demand.getDemandData( );
        try
        {
            Object dataDemand = _mapper.readTree( strDemandJson );
            if ( dataDemand != null )
            {
                strDemandJson = _mapper.writerWithDefaultPrettyPrinter( ).writeValueAsString( dataDemand );
            }
        }
        catch( IOException ex )
        {
            Logger.getLogger( DemandService.class.getName( ) ).log( Level.WARNING, null, ex );
        }

        return strDemandJson;
    }
}
