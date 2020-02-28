package io.github.rockitconsulting.test.rockitizer.cli;

import java.io.IOException;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "sync",
sortOptions = false,
headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n",
descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working)  cli sync ",
description = "Stores the current contents of the index in a new commit " +
    	"along with a log message from the user describing the changes.")

public class RockitizerSync implements Runnable {

	
	@Parameters(index = "0", arity = "0..1",description = "env.....")
    String env;
	
	@Override
	public void run() {
		try {
			ValidationUtils.syncResources();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
