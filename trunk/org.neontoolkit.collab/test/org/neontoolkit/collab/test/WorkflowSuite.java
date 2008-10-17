package org.neontoolkit.collab.test;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.neontoolkit.changelogging.test.ChangeLoggingBefore;
import org.neontoolkit.changelogging.test.ChangeLoggingTest;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ChangeLoggingTest.class,
	WorkflowTest.class,
})

public class WorkflowSuite {

	@AfterClass 
	public static void runAfterClass() throws Exception {
		File tFile = new File("server/localRegistry.owl");
		if (tFile.exists())	tFile.delete();
		tFile = new File(".propertiesOyster");
		if (tFile.exists())	tFile.delete();
		tFile = new File("server");
		if (tFile.exists())	tFile.delete();
		ChangeLoggingBefore.prepareWorkspace();
	}
}
