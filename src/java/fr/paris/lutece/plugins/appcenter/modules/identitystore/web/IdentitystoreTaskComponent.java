package fr.paris.lutece.plugins.appcenter.modules.identitystore.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import fr.paris.lutece.plugins.appcenter.business.Application;
import fr.paris.lutece.plugins.appcenter.business.ApplicationHome;
import fr.paris.lutece.plugins.appcenter.business.Demand;
import fr.paris.lutece.plugins.appcenter.business.DemandHome;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.Attribute;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.AttributeHome;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreData;
import fr.paris.lutece.plugins.appcenter.modules.identitystore.business.IdentitystoreDemand;
import fr.paris.lutece.plugins.appcenter.web.Constants;
import fr.paris.lutece.plugins.workflow.web.task.NoConfigTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.bean.BeanUtil;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;
import fr.paris.lutece.util.html.HtmlTemplate;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class IdentitystoreTaskComponent extends NoConfigTaskComponent
{
    // TEMPLATES
    private static final String TEMPLATE_IDENTITYSTORE_TASK_FORM = "admin/plugins/appcenter/modules/identitystore/identitystore_task_form.html";
    private static final String TEMPLATE_IDENTITYSTORE_DISPLAY_HISTORY = "admin/plugins/appcenter/modules/identitystore/identitystore_task_history.html";
    private static final String MARK_ATTRIBUTES = "attributes";
    
    // MESSAGES

    // MARKS

    @Inject
    private IResourceHistoryService _resourceHistoryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskForm( int nIdResource, String strResourceType, HttpServletRequest request,
        Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        IdentitystoreDemand demand = DemandHome.findByPrimaryKey( nIdResource, IdentitystoreDemand.class );
        
        if ( demand != null )
        {
            
            model.put( Constants.MARK_DEMAND, demand );
            Application application = ApplicationHome.findByPrimaryKey( demand.getIdApplication( ) );
            model.put( Constants.MARK_APPLICATION, application );
            model.put( MARK_ATTRIBUTES , AttributeHome.getAttributesList( ) );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_IDENTITYSTORE_TASK_FORM, locale, model );

        return template.getHtml(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doValidateTask( int nIdResource, String strResourceType, HttpServletRequest request, Locale locale,
        ITask task )
    {
        IdentitystoreData identitystoreData = new IdentitystoreData();
        BeanUtil.populate ( identitystoreData, request );

        //FIXME return real error message here
        Set<ConstraintViolation<IdentitystoreData>> errors = BeanValidationUtil.validate( identitystoreData );
        if ( !errors.isEmpty() )
        {
            for ( ConstraintViolation<IdentitystoreData> constraint : errors )
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
        Demand demand = DemandHome.findByPrimaryKey( resourceHistory.getIdResource() );
        //FIXME Load datasubset associated with this demand

        Map<String, Object> model = new HashMap<String, Object>(  );
        //model.put( MARK_HISTORY_LIST, listHistory );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_IDENTITYSTORE_DISPLAY_HISTORY,
                locale, model );
        return template.getHtml(  );
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
