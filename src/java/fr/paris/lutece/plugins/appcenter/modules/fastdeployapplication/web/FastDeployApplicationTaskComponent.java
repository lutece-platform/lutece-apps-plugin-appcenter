package fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

import org.apache.poi.ss.formula.functions.T;

import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationData;
import fr.paris.lutece.plugins.appcenter.modules.fastdeployapplication.business.FastDeployApplicationDemand;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamAgentData;
import fr.paris.lutece.plugins.appcenter.modules.openam.business.OpenamDemand;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import fr.paris.lutece.plugins.workflow.web.task.NoConfigTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;
import fr.paris.lutece.util.html.HtmlTemplate;

public class FastDeployApplicationTaskComponent extends NoConfigTaskComponent
{
    // TEMPLATES
    private static final String TEMPLATE_FASTDEPLOY_TASK_FORM = "admin/plugins/appcenter/modules/fastdeploy/fastdeploy_task_form.html";
    private static final String TEMPLATE_FAST_DEPLOY_DISPLAY_HISTORY = "admin/plugins/appcenter/modules/fastdeploy/fastdeploy_task_history.html";

    // MESSAGES
    private static final String MESSAGE_MANDATORY_FIELD = "portal.util.message.mandatoryFields";

    
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskForm( int nIdResource, String strResourceType, HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<String, Object>( );

        FastDeployApplicationDemand demand = DemandHome.findByPrimaryKey( nIdResource, FastDeployApplicationDemand.class );
        if ( demand != null )
        {
            model.put( Constants.MARK_DEMAND, demand );
            Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );
            model.put( Constants.MARK_APPLICATION, application );
            ReferenceList refServices=DatastoreService.getDataByPrefix( FastDeployApplicationsXPage.DATA_PREFIX_FAST_DEPLOY_SERVICES );
             model.put( FastDeployApplicationsXPage.MARK_SERVICES, refServices);
             model.put( FastDeployApplicationsXPage.MARK_MAP_SERVICES, refServices.toMap( ) );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_FASTDEPLOY_TASK_FORM, locale, model );

        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doValidateTask( int nIdResource, String strResourceType, HttpServletRequest request, Locale locale, ITask task )
    {
        FastDeployApplicationData fastDeployData = new FastDeployApplicationData( );
        BeanUtil.populate( fastDeployData, request );


        //FIXME return real error message here
        
        Set<ConstraintViolation<FastDeployApplicationData>> errors = BeanValidationUtil.validate( fastDeployData );
        if ( !errors.isEmpty() )
        {
        
        	for ( ConstraintViolation<FastDeployApplicationData> constraint : errors )
            {
        	
        		
        		return constraint.getMessage();
            }
        	
           
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdHistory );
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource( ) );
        // FIXME Load datasubset associated with this demand

        Map<String, Object> model = new HashMap<String, Object>( );
        // model.put( MARK_HISTORY_LIST, listHistory );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_FAST_DEPLOY_DISPLAY_HISTORY, locale, model );
        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTaskInformationXml( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }
}
