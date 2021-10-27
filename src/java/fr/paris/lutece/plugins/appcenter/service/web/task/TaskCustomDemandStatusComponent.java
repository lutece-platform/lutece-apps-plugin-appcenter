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
package fr.paris.lutece.plugins.appcenter.service.web.task;

import fr.paris.lutece.plugins.appcenter.business.task.CustomDemandStatusTaskConfig;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.plugins.workflowcore.web.task.SimpleTaskComponent;
import fr.paris.lutece.plugins.workflowcore.web.task.TaskComponent;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.beanvalidation.BeanValidationUtil;
import fr.paris.lutece.util.html.HtmlTemplate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

public class TaskCustomDemandStatusComponent extends TaskComponent
{
    private static final String TEMPLATE_CONFIG_CUSTOM_STATUS = "admin/plugins/appcenter/custom_demand_status_task_config.html";

    private static final String MARK_CONFIG = "config";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
        CustomDemandStatusTaskConfig config = getTaskConfigService( ).findByPrimaryKey( task.getId( ) );
        model.put( MARK_CONFIG, config );

        HtmlTemplate page = AppTemplateService.getTemplate( TEMPLATE_CONFIG_CUSTOM_STATUS, locale, model );
        return page.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateConfig( ITaskConfig config, HttpServletRequest hsr )
    {
        Set<ConstraintViolation<ITaskConfig>> errors = BeanValidationUtil.validate( config );
        if ( !errors.isEmpty( ) )
        {
            for ( ConstraintViolation<ITaskConfig> constraint : errors )
            {
                return constraint.getMessage( );
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int i, HttpServletRequest hsr, Locale locale, ITask itask )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getTaskInformationXml( int i, HttpServletRequest hsr, Locale locale, ITask itask )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskForm( int i, String string, HttpServletRequest hsr, Locale locale, ITask itask )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doValidateTask( int i, String string, HttpServletRequest hsr, Locale locale, ITask itask )
    {
        return null;
    }
}
