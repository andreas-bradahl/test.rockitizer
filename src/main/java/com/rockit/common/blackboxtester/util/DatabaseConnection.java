package com.rockit.common.blackboxtester.util;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

public class DatabaseConnection {
	public static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
	
	protected String dbUrl, dbUser, dbPwd;
	
	protected Connection connection;
	
	@Deprecated
	public DatabaseConnection(final String dbUrl, final String dbUser, final String dbPwd) {
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPwd = dbPwd;

	}
	
	
	public DatabaseConnection(String id) {
		DBConnector dbConCfg = (DBConnector) configuration().getConnectorById(id);
		DBDataSource ds = configuration().getDBDataSourceByConnector(dbConCfg);
		
		this.dbUrl = ds.getUrl();
		this.dbUser = ds.getUser();
		this.dbPwd = ds.getPassword();
	}


	protected void createDatabaseConnection() {

		try {
			Class.forName(this.dbUrl.contains("db2") ? Constants.DB2_DRIVER : Constants.ORACLE_DRIVER).newInstance();
			this.connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPwd);

		} catch (final InstantiationException e) {

			LOGGER.error("DB Connection object cannot be instantiated. \n" + "url:" + this.dbUrl + ", user:"
					+ this.dbUser + ", password:" + this.dbPwd, e);
			throw new GenericException(e);

		} catch (final IllegalAccessException | ClassNotFoundException | SQLException e) {

			LOGGER.error("DB Connection is not possible. \n" + "url:" + this.dbUrl + ", user:"
					+ this.dbUser + ", password:" + this.dbPwd, e);
			throw new GenericException(e);

		} 
	}
}
