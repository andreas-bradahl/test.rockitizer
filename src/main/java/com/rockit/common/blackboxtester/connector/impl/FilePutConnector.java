package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.FileConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;

import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class FilePutConnector implements WriteConnector {

	private String id;
	private String destPath;
	private File contentFile;

	/**
	 * @param id
	 *            - FILEPut and key (connectorFolder)
	 */
	public FilePutConnector(String id) {
		this.id = id;
		FileConnectorCfg cfg = (FileConnectorCfg) configuration().getConnectorById(id);
		this.destPath = cfg.getPath();
	}

	/**
	 * @param destPath
	 *            - junit enabled
	 */
	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	@Override
	public void proceed() {
		try {
			FileUtils.copy(contentFile, new File(destPath));
		} catch (Exception e) {
			throw new ConnectorException("[Connector:" + getId() + "] \t Connector error: " + getType(), e);
		}

	}

	public String getType() {
		return Connectors.FILEPUT.toString();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setRequest(File requestFile) {
		contentFile = PayloadReplacer.interpolate(requestFile);
	}

	@Override
	public void setRequest(String request) {
		throw new ConnectorException("[Connector:" + getId() + "] \t Set method is not allowed");

	}

	public String getDestPath() {
		return destPath;
	}

}
