package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.Payload;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestStep;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class TestCasesHolderCLI extends RuntimeContextCLI {

	public static Logger log = Logger.getLogger(TestCasesHolderCLI.class.getName());

	/**
	 * Reads yam configuration into the holder object
	 * CLI relevant: instantiate Resources from yaml
	 * 
	 * @return
	 * @throws IOException
	 */
	public TestCasesHolder readResources() throws IOException {
		return ConfigUtils.testCasesHolderFromYaml(getRelativePath() + getTestcasesFileName());
	}

	/**
	 * Creates config file <testcase.yaml> based on the directory structure
	 * CLI relevant: generate Resources from yaml
	 * 
	 * @return
	 * @throws IOException
	 */

	public TestCasesHolder generateResources() throws IOException {

		TestCasesHolder holder = new TestCasesHolder();

		FileUtils.listFolders(new File(getFullPath())).forEach(tcFolder -> {
			TestCase testCase = new TestCase(tcFolder);
			holder.getTestCases().add(testCase);

			FileUtils.listFolders(tcFolder).forEach(tsFolder -> {
				TestStep testStep = new TestStep(tsFolder);
				if (testStep.getTestStepName().equalsIgnoreCase(OUTPUT)) {
					return;
				}
				testCase.getTestSteps().add(testStep);

				FileUtils.listFolders(tsFolder).forEach(connFolder -> {
					ConnectorRef connectorRef = new ConnectorRef(connFolder);
					FileUtils.listFiles(connFolder).forEach(payload -> {
						connectorRef.getPayloads().add(new Payload(payload));
					});
					testStep.getConnectorRefs().add(connectorRef);
				});
			});
		});

		ConfigUtils.writeToYaml(holder, getFullPath() + getTestcasesFileName());
		return holder;
	}

	public String contextAsString() {
		return "[ filename: " + getTestcasesFileName() + ", absPath: " + getAbsolutePath() + ", relPath: " + getRelativePath() + "]";

	}
}
