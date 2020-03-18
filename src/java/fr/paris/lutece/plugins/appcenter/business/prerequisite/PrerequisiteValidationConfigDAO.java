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
package fr.paris.lutece.plugins.appcenter.business.prerequisite;

import fr.paris.lutece.plugins.workflow.service.WorkflowPlugin;
import fr.paris.lutece.plugins.workflowcore.business.prerequisite.IPrerequisiteConfig;
import fr.paris.lutece.plugins.workflowcore.business.prerequisite.IPrerequisiteConfigDAO;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

public class PrerequisiteValidationConfigDAO implements IPrerequisiteConfigDAO
{
    private static final String INSERT_VALIDATION_PREREQUISITE = " INSERT INTO workflow_prerequisite_validation_cf ( id_prerequisite, id_task, status ) VALUES ( ?, ?, ? ) ";
    private static final String UPDATE_VALIDATION_PREREQUISITE = " UPDATE workflow_prerequisite_validation_cf SET id_task = ?, status = ? WHERE id_prerequisite = ? ";
    private static final String DELETE_VALIDATION_PREREQUISITE = " DELETE FROM workflow_prerequisite_validation_cf WHERE id_prerequisite = ? ";
    private static final String SELECT_VALIDATION_PREREQUISITE = " SELECT id_prerequisite, id_task, status FROM workflow_prerequisite_validation_cf WHERE id_prerequisite = ?";

    /**
     * {@inheritDoc}
     */
    @Override
    public void createConfig( IPrerequisiteConfig config )
    {
        DAOUtil daoUtil = new DAOUtil( INSERT_VALIDATION_PREREQUISITE, PluginService.getPlugin( WorkflowPlugin.PLUGIN_NAME ) );
        try
        {
            daoUtil.setInt( 1, config.getIdPrerequisite( ) );
            daoUtil.setInt( 2, ( (PrerequisiteValidationConfig) config ).getIdTask( ) );
            daoUtil.setInt( 3, ( (PrerequisiteValidationConfig) config ).getStatus( ) );
            daoUtil.executeUpdate( );
        }
        finally
        {
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateConfig( IPrerequisiteConfig config )
    {
        DAOUtil daoUtil = new DAOUtil( UPDATE_VALIDATION_PREREQUISITE, PluginService.getPlugin( WorkflowPlugin.PLUGIN_NAME ) );
        daoUtil.setInt( 1, ( (PrerequisiteValidationConfig) config ).getIdTask( ) );
        daoUtil.setInt( 2, ( (PrerequisiteValidationConfig) config ).getStatus( ) );
        daoUtil.setInt( 3, config.getIdPrerequisite( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeConfig( int nIdPrerequisite )
    {
        DAOUtil daoUtil = new DAOUtil( DELETE_VALIDATION_PREREQUISITE, PluginService.getPlugin( WorkflowPlugin.PLUGIN_NAME ) );
        daoUtil.setInt( 1, nIdPrerequisite );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPrerequisiteConfig findByPrimaryKey( int nIdPrerequisite )
    {
        DAOUtil daoUtil = new DAOUtil( SELECT_VALIDATION_PREREQUISITE, PluginService.getPlugin( WorkflowPlugin.PLUGIN_NAME ) );
        daoUtil.setInt( 1, nIdPrerequisite );

        PrerequisiteValidationConfig config = null;
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            config = new PrerequisiteValidationConfig( );
            config.setIdPrerequisite( daoUtil.getInt( 1 ) );
            config.setIdTask( daoUtil.getInt( 2 ) );
            config.setStatus( daoUtil.getInt( 3 ) );
        }

        daoUtil.free( );

        return config;
    }
}