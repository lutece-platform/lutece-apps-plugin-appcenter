/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for Documentation objects
 */

public final class DocumentationDAO implements IDocumentationDAO
{
	
	// Constants
	
	private static final String SQL_QUERY_NEW_PK = "SELECT max( id ) FROM appcenter_documentation";
	private static final String SQL_QUERY_SELECT = "SELECT id, id_demand_type, label, url, category FROM appcenter_documentation WHERE id = ?";
	private static final String SQL_QUERY_INSERT = "INSERT INTO appcenter_documentation ( id, id_demand_type, label, url, category ) VALUES ( ?, ?, ?, ?, ? ) ";
	private static final String SQL_QUERY_DELETE = "DELETE FROM appcenter_documentation WHERE id = ? ";
	private static final String SQL_QUERY_UPDATE = "UPDATE appcenter_documentation SET id = ?, id_demand_type = ?, label = ?, url = ?, category = ? WHERE id = ?";
	private static final String SQL_QUERY_SELECTALL = "SELECT id, id_demand_type, label, url, category FROM appcenter_documentation";

	
	/**
	 * Generates a new primary key
         * @param plugin The Plugin
	 * @return The new primary key
	 */
	public int newPrimaryKey( Plugin plugin)
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK  );
		daoUtil.executeQuery();

		int nKey =( daoUtil.next() ) ? daoUtil.getInt( 1 ) + 1 : 1;
		daoUtil.free();

		return nKey;
	}

        /**
         * {@inheritDoc }
         */
        @Override
	public void insert( Documentation documentation, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT , plugin );
                
		documentation.setId( newPrimaryKey( plugin ) );
                
                daoUtil.setInt ( 1, documentation.getId ( ) );
                daoUtil.setInt ( 2, documentation.getIdDemandType ( ) );
                daoUtil.setString ( 3, documentation.getLabel ( ) );
                daoUtil.setString ( 4, documentation.getUrl ( ) );
                daoUtil.setString ( 5, documentation.getCategory ( ) );

		daoUtil.executeUpdate();
		daoUtil.free();
	}

        /**
         * {@inheritDoc }
         */
        @Override
        public Documentation load( int nId, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT , plugin );
		daoUtil.setInt( 1 , nId );
		daoUtil.executeQuery();

		Documentation documentation = null;

		if ( daoUtil.next() )
		{
			documentation = new Documentation();
                        int nIndex = 0;
                        documentation.setId( daoUtil.getInt( ++nIndex ) );
                        documentation.setIdDemandType( daoUtil.getInt( ++nIndex ) );
                        documentation.setLabel( daoUtil.getString( ++nIndex ) );
                        documentation.setUrl( daoUtil.getString( ++nIndex ) );
                        documentation.setCategory( daoUtil.getString( ++nIndex ) );
		}

		daoUtil.free();
                
		return documentation;
	}

        /**
         * {@inheritDoc }
         */
        @Override
	public void delete( int nDocumentationId, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE , plugin );
		daoUtil.setInt( 1 , nDocumentationId );
		daoUtil.executeUpdate();
		daoUtil.free();
	}

        /**
         * {@inheritDoc }
         */
        @Override
	public void store( Documentation documentation, Plugin plugin )
	{
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE , plugin );
                
                int nIndex = 0;
                daoUtil.setInt( ++nIndex , documentation.getId( ) );
                daoUtil.setInt( ++nIndex , documentation.getIdDemandType( ) );
                daoUtil.setString( ++nIndex , documentation.getLabel( ) );
                daoUtil.setString( ++nIndex , documentation.getUrl( ) );
                daoUtil.setString( ++nIndex , documentation.getCategory( ) );
                daoUtil.setInt( ++nIndex , documentation.getId( ) );
                
		daoUtil.executeUpdate( );
		daoUtil.free( );
	}

        /**
         * {@inheritDoc }
         */
        @Override
        public List<Documentation> selectDocumentationsList( Plugin plugin )
	{
		List<Documentation> listDocumentations = new ArrayList<>(  );
		DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL , plugin );
		daoUtil.executeQuery(  );

		while ( daoUtil.next(  ) )
		{
                    Documentation documentation = new Documentation(  );

                    documentation.setId( daoUtil.getInt( 1 ) );
                    documentation.setIdDemandType( daoUtil.getInt( 2 ) );
                    documentation.setLabel( daoUtil.getString( 3 ) );
                    documentation.setUrl( daoUtil.getString( 4 ) );
                    documentation.setCategory( daoUtil.getString( 5 ) );

                    listDocumentations.add( documentation );
		}

		daoUtil.free();
                
		return listDocumentations;
	}

}
