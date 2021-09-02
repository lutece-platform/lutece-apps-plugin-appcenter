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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationData;
import fr.paris.lutece.plugins.appcenter.business.ApplicationDatas;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.business.DemandType;
import fr.paris.lutece.plugins.appcenter.business.DemandTypeHome;
import fr.paris.lutece.plugins.appcenter.util.AppCenterUtils;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.util.ReferenceList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Application Service
 */
public class ApplicationService
{
    private static ObjectMapper _mapper = new ObjectMapper( );

    /**
     * Save a data subset into the global JSON data of an application
     * 
     * @param application
     *            The application
     * @param dataSubset
     *            The data subset
     */
    public static void saveApplicationData( Application application, DataSubset dataSubset )
    {
        try
        {
            String strUpdatedJson = getApplicationData( application, dataSubset );
            ApplicationHome.updateData( application.getId( ), strUpdatedJson );
        }
        catch( IOException ex )
        {
            Logger.getLogger( ApplicationService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param application
     *            The application
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    public static <R extends ApplicationData, T extends ApplicationDatas<R>> T loadApplicationDataSubset( Application application,
            Class<T> applicationDatasClass )
    {
        try
        {
            Method mGetDataSetName = applicationDatasClass.getMethod( "getName" );
            String strDataSetName = (String) mGetDataSetName.invoke( applicationDatasClass.newInstance( ), null );
            String strApplicationJson = application.getApplicationData( );
            return getDataSubset( strApplicationJson, strDataSetName, applicationDatasClass );
        }
        catch( IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | InstantiationException ex )
        {
            Logger.getLogger( ApplicationService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
        return null;
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param application
     *            The application
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    public static <T> T loadApplicationDataSubset( Application application, String strDataSubsetName, Class<T> valueType )
    {
        try
        {
            String strApplicationJson = application.getApplicationData( );
            return getDataSubset( strApplicationJson, strDataSubsetName, valueType );
        }
        catch( IOException ex )
        {
            Logger.getLogger( ApplicationService.class.getName( ) ).log( Level.SEVERE, null, ex );
        }
        return null;
    }

    /**
     * Build a global JSON data of an application by adding or replacing a data subset
     * 
     * @param application
     *            The application
     * @param dataSubset
     *            The data subset
     * @return The JSON
     * @throws IOException
     *             if an error occurs
     */
    static String getApplicationData( Application application, DataSubset dataSubset ) throws IOException
    {
        String strApplicationJson = application.getApplicationData( );
        JsonNode nodeApplication = _mapper.readTree( strApplicationJson );
        JsonNode nodeData = nodeApplication.get( dataSubset.getName( ) );
        if ( nodeData != null )
        {
            ( (ObjectNode) nodeApplication ).replace( dataSubset.getName( ), _mapper.valueToTree( dataSubset ) );
        }
        else
        {
            ( (ObjectNode) nodeApplication ).set( dataSubset.getName( ), _mapper.valueToTree( dataSubset ) );
        }
        return nodeApplication.toString( );
    }

    /**
     * Get the pretty print JSON data of an application
     * 
     * @param application
     *            The application
     * @return The pretty printed JSON
     * @throws IOException
     *             if an error occurs
     */
    public static String getPrettyPrintApplicationData( Application application )
    {
        String strApplicationJson = application.getApplicationData( );
        try
        {
            Object dataApplication = _mapper.readTree( strApplicationJson );
            if ( dataApplication != null )
            {
                strApplicationJson = _mapper.writerWithDefaultPrettyPrinter( ).writeValueAsString( dataApplication );
            }
        }
        catch( IOException ex )
        {
            Logger.getLogger( ApplicationService.class.getName( ) ).log( Level.WARNING, null, ex );
        }

        return strApplicationJson;
    }

    /**
     * Load a datasubset from the global JSON
     * 
     * @param <T>
     *            The datasubset type
     * @param strApplicationJson
     *            The global JSON of the application
     * @param strDataSubsetName
     *            The subset name
     * @param valueType
     *            The class of the data subset
     * @return The data subset as an object
     */
    static <T> T getDataSubset( String strApplicationJson, String strDataSubsetName, Class<T> valueType ) throws IOException
    {
        JsonNode nodeApplication = _mapper.readTree( strApplicationJson );
        JsonNode nodeData = nodeApplication.get( strDataSubsetName );
        if ( nodeData != null )
        {
            return _mapper.treeToValue( nodeData, valueType );
        }
        return null;

    }

    /**
     * Get application data by id application data
     * 
     * @param application
     *            The application
     * @param dataSubset
     *            The data subset
     * @return The JSON
     * @throws IOException
     *             if an error occurs
     */
    public static <AD extends ApplicationData, ADS extends ApplicationDatas<AD>> ApplicationData loadApplicationDataById( Integer nIdApplicationData,
            Application application, Class<ADS> valueType ) throws IOException
    {

        ADS ads = loadApplicationDataSubset( application, valueType );
        if ( ads != null && ads.getListData( ) != null )
        {
            return ads.getListData( ).stream( ).filter( x -> x.getIdApplicationData( ) == nIdApplicationData ).findFirst( ).orElse( null );

        }
        return null;
    }

    public static <AD extends ApplicationData, ADS extends ApplicationDatas<AD>> ReferenceList getRefLisApplicationDatas( Application application,
            Class<ADS> classAds, Locale locale, boolean bWithEmptyFile, Function<AD, String> functionRefItemCode, Function<AD, String> functionRefItemName )
    {

        ADS ads = ApplicationService.loadApplicationDataSubset( application, classAds );
        ReferenceList referenceList = null;
        if ( ads != null && ads.getListData( ) != null && !CollectionUtils.isEmpty( ads.getListData( ) ) )
        {
            Map<String, String> mapReferenceItem = ads.getListData( ).stream( )
                    .collect( Collectors.toMap( functionRefItemCode, functionRefItemName, ( f, s ) -> f ) );
            referenceList = ReferenceList.convert( mapReferenceItem );
        }
        else
        {
            referenceList = new ReferenceList( );
        }

        if ( bWithEmptyFile )
        {
            AppCenterUtils.addEmptyItem( referenceList, locale );
        }

        return referenceList;

    }

    /**
     * Remove the application whose identifier is specified in parameter with its dependencies
     * 
     * @param nId
     *            The application Id
     */
    public static void remove( int nId )
    {
        List<Demand> demandList = DemandHome.getDemandsListByApplication( nId );

        for ( Demand demand : demandList )
        {
            List<Integer> idResourceList = new ArrayList<Integer>( );
            idResourceList.add( demand.getId( ) );
            int nIdWorkflow = DemandTypeService.getIdWorkflow( demand.getDemandType( ) );
            WorkflowService.getInstance( ).doRemoveWorkFlowResourceByListId( idResourceList, Demand.WORKFLOW_RESOURCE_TYPE, nIdWorkflow );
            DemandHome.remove( demand.getId( ) );
        }

        ApplicationHome.remove( nId );
    }
    
    /**
     * Return map of demands from application data
     * @param <T>
     * @param application
     * @return
     */
    public static <T extends Demand> Map<String, T> getListDemandsApplicationData( Application application )
    {
        _mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        Map<String, T> mapDemandsApplicationData = new HashMap<>( );

        List<DemandType> listDemandsType = DemandTypeHome.getDemandTypesList( );

        try
        {
            for ( DemandType demandType : listDemandsType )
            {
                String strDemandType = demandType.getIdDemandType( ).equals( "fastdeployapplication" ) ? "fastdeployapplications" : demandType.getIdDemandType( );
                JsonNode listDemandsJsonNode = getListDemandsApplicationDataJsonNode(application, strDemandType);

                if ( listDemandsJsonNode != null )
                {
                    Iterator it = listDemandsJsonNode.iterator( );
                    while ( it.hasNext( ) )
                    {
                        String strDemandDataJson = it.next( ).toString( );

                        if ( strDemandDataJson != null )
                        {
                            JsonNode demandDataNode = _mapper.readTree( strDemandDataJson );
                            JsonNode idDemandNode = demandDataNode.get( "listIdDemandAssociated" );
                            upperCaseEnvironment(demandDataNode);
                            
                            T demand = ( T ) _mapper.readValue( demandDataNode.toString( ), DemandTypeService.getClassByDemandTypeId( demandType.getIdDemandType( ), listDemandsType ) );
                            
                            if ( idDemandNode != null && idDemandNode.get( 0 ) != null )
                            {
                                demand.setIdApplication( application.getId( ) );
                                mapDemandsApplicationData.put( idDemandNode.get( 0 ).asText( ), demand );
                            }
                        }
                    }
                }
            }
        } catch ( Exception e )
        {
            AppLogService.error( "ApplicationService:getListDemandsApplicationData " +  e );
        }
        return mapDemandsApplicationData;
    }

	private static JsonNode getListDemandsApplicationDataJsonNode(Application application, String strDemandType) {
		try
		{
		    JsonNode dataApplicationJsonNode = _mapper.readTree( application.getApplicationData( ) );
		    return dataApplicationJsonNode.get( strDemandType ).get( "listData" );
		} catch ( Exception e )
		{
		    AppLogService.info( "ApplicationService:getListDemandsApplicationData " + e );
		}
		return null;
	}

	private static void upperCaseEnvironment(JsonNode demandDataNode)
	{
		JsonNode environnementNode = demandDataNode.get( "environment" );
		
		if ( !environnementNode.isNull( ) )
		{
		    ( ( ObjectNode ) demandDataNode ).put( "environment", environnementNode.textValue( ).toUpperCase( ) );
		}
	}
}
