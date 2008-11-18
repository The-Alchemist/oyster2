package org.neontoolkit.changelogging.test;

import java.io.File;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ChangeLoggingTest.class,
	//AllOWLChangesTest.class,
})

public class ChangeLoggingSuite {

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
