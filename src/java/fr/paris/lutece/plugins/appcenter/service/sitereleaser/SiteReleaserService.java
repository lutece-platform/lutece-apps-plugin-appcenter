package fr.paris.lutece.plugins.appcenter.service.sitereleaser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.organization.OrganizationHome;
import fr.paris.lutece.plugins.appcenter.business.sitereleaser.Site;
import fr.paris.lutece.plugins.appcenter.service.ApplicationService;
import fr.paris.lutece.plugins.appcenter.service.DataSubset;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;

public class SiteReleaserService {	

    private static final String JSON_NODE_FDA = AppPropertiesService.getProperty( "appcenter.applicationdata.json.node.fastdeployapplications" );
    private static final String JSON_NODE_APPCODE = AppPropertiesService.getProperty( "appcenter.applicationdata.json.node.appcode" );    
    private static final String JSON_NODE_LIST_DATA = AppPropertiesService.getProperty( "appcenter.applicationdata.json.node.listData" );
    private static final String JSON_NODE_LIST_DATA_ELEMENT = AppPropertiesService.getProperty( "appcenter.applicationdata.json.node.listDataElement" );
    private static final String JSON_NODE_Artifact_ID = AppPropertiesService.getProperty( "appcenter.applicationdata.json.node.artifactId" );
    private static final String JSON_NODE_NAME = AppPropertiesService.getProperty( "appcenter.applicationdata.json.node.name" );
    private static final String JSON_NODE_URL_SITE = AppPropertiesService.getProperty( "appcenter.applicationdata.json.node.urlSite" );
    private static final String JSON_NODE_DESCRIPTION = AppPropertiesService.getProperty( "appcenter.applicationdata.json.node.description" );
    
    private static final String URL_GET_CLUSTERS_IDS = AppPropertiesService.getProperty( "appcenter.applicationdata.releaser.baseUrl" ) 
    		+ AppPropertiesService.getProperty( "appcenter.applicationdata.releaser.path.cluster.getClusters" );
    private static final String URL_POST_ADD_SITE = AppPropertiesService.getProperty( "appcenter.applicationdata.releaser.baseUrl" ) 
    		+ AppPropertiesService.getProperty( "appcenter.applicationdata.releaser.path.site.create" );
    
	public static void addSiteToReleaser( Application application, DataSubset dataSubset ) throws HttpAccessException 
	{
		HttpAccess httpaccess = new HttpAccess();
        ObjectMapper mapper = new ObjectMapper( );
		Site site = new Site();	
		
		try {
			String strDataJson = ApplicationService.getApplicationData( application, dataSubset );	
			JsonNode nodes = mapper.readTree( strDataJson );
			
			// Set site attributes
	        JsonNode dataNode = nodes.get( JSON_NODE_FDA ).get( JSON_NODE_LIST_DATA ).get( Integer.parseInt(JSON_NODE_LIST_DATA_ELEMENT) );
			site.setArtifactId(dataNode.get(JSON_NODE_Artifact_ID).asText( ));
			site.setScmUrl(dataNode.get(JSON_NODE_URL_SITE).asText( ));
			site.setDescription("");
			
			// Set site name
			if (nodes.get( JSON_NODE_APPCODE ) != null)
	        {
	        	dataNode = nodes.get( JSON_NODE_APPCODE ).get( JSON_NODE_LIST_DATA ).get( Integer.parseInt(JSON_NODE_LIST_DATA_ELEMENT) );	

				site.setDescription(dataNode.get(JSON_NODE_DESCRIPTION).asText( ));
	        }
			
			site.setName(dataNode.get(JSON_NODE_NAME).asText( ));
			
			// Set site cluster id 
			HashMap<String, String> clusters = mapper.readValue(mapper.readTree( getClustersIds() ).get("result").toString(), HashMap.class);
			String strOrganization = OrganizationHome.findByPrimaryKey(application.getOrganizationManager().getIdOrganization()).getName();			
			String organisationClusterId = getKey(clusters, strOrganization);					
			site.setIdCluster(Integer.parseInt(organisationClusterId));
			
			// Maping site in json
			String strSiteInJson = mapper.writeValueAsString(site);
			
			// Call site releaser API
			httpaccess.doPostJSON(URL_POST_ADD_SITE, strSiteInJson, null, null);
			
		} catch (IOException e) {
			AppLogService.error( "Error adding site to site-releaser : " + e );
		} 
	}
	
    private static String getClustersIds() 
    {
        HttpAccess httpAccess = new HttpAccess( );
        String strResponse = StringUtils.EMPTY;

        try
        {
            strResponse = httpAccess.doGet( URL_GET_CLUSTERS_IDS, null, null, null );
        }
        catch( HttpAccessException e )
        {
            AppLogService.error( "Error getting clusters ids : " + strResponse );
        }

        return strResponse;
    }
    
    private static <K, V> K getKey(Map<K, V> map, V value)
    {
        for (Map.Entry<K, V> entry: map.entrySet())
        {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        
        return null;
    }
}
