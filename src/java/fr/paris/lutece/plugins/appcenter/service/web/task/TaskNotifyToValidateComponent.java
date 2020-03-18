/*
 * Copyright (c) 2002-2020, Mairie de Paris
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
package fr.paris.lutece.plugins.appcenter.service.web.task;

import fr.paris.lutece.plugins.appcenter.business.task.NotifyTaskConfig;
import fr.paris.lutece.plugins.appcenter.business.task.NotifyTaskConfigHome;
import fr.paris.lutece.plugins.appcenter.service.AppcenterPlugin;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.task.ITaskType;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.plugins.workflowcore.service.task.ITaskFactory;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.bean.BeanUtil;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class TaskNotifyToValidateComponent extends NoFormTaskComponent
{
    private static final String MARK_CONFIG = "config";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String TEMPLATE_TASK_NOTIFY_TO_VALIDATION_CONFIG = "admin/plugins/appcenter/notify_to_validate_task_config.html";
    private static final String PARAMETER_APPLY = "apply";

    @Inject
    @Named("appcenter.taskTypeNotifyToValidate")
    private ITaskType _taskType;
    @Inject
    private ITaskFactory _taskFactory;

    @Override
    public String getDisplayTaskInformation( int i, HttpServletRequest hsr, Locale locale, ITask itask )
    {
        return null;
    }

    @Override
    public String getTaskInformationXml( int i, HttpServletRequest hsr, Locale locale, ITask itask )
    {
        return null;
    }

    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String,Object> model = new HashMap<String,Object>();
        NotifyTaskConfig conf = NotifyTaskConfigHome.findByPrimaryKey( task.getId( ), AppcenterPlugin.getPlugin( ) );
        if ( conf != null )
        {
            model.put( MARK_CONFIG, conf );
        }
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        return AppTemplateService.getTemplate( TEMPLATE_TASK_NOTIFY_TO_VALIDATION_CONFIG , locale, model ).getHtml( );
    }

    @Override
    public String doValidateTask( int i, String string, HttpServletRequest hsr, Locale locale, ITask itask )
    {
        return null;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String doSaveConfig( HttpServletRequest request, Locale locale, ITask task )
    {
        // In case there are no errors, then the config is created/updated
        boolean bCreate = false;
        NotifyTaskConfig config = getTaskConfigService().findByPrimaryKey( task.getId(  ) );

        if ( config == null )
        {
            config = (NotifyTaskConfig) _taskFactory.newTaskConfig( _taskType.getKey(  ) );
            if ( config != null )
            {
                config.setIdTask( task.getId(  ) );
                bCreate = true;
            }
        }

        if ( config != null )
        {
            //Customize the populate method for the config
            BeanUtil.populate( config, request );
            String strApply = request.getParameter( PARAMETER_APPLY );
            // Check if the AdminUser clicked on "Apply" or on "Save"
            if ( StringUtils.isEmpty( strApply ) )
            {
                String strJspError = this.validateConfig( config, request );

                if ( StringUtils.isNotBlank( strJspError ) )
                {
                    return strJspError;
                }
            }
            if ( bCreate )
            {
                getTaskConfigService().create( config );
            }
            else
            {
                getTaskConfigService().update( config );
            }
        }
        else
        {
            AppLogService.error( "TaskComponent - could not instanciate a new TaskConfig for type " + _taskType.getKey(  ) );
        }

        return null;
    }
}
