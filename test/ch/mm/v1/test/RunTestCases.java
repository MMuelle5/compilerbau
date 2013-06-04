package ch.mm.v1.test;

import java.io.IOException;

import org.junit.Test;

import ch.mm.v1.parser.run.Run;

public class RunTestCases {

	@Test
	public void runFirstTest() throws IOException {
		Run.main(new String[]{"testClasses/FirstTest.java", "junit-stuff.s"});
	}

	@Test
	public void runJumpTest() throws IOException {
		Run.main(new String[]{"testClasses/Jump.java", "junit-stuff.s"});
	}

	@Test
	public void runMathFctTest() throws IOException {
		Run.main(new String[]{"testClasses/MathFct.java", "junit-stuff.s"});
	}

	@Test
	public void runSysInTest() throws IOException {
		Run.main(new String[]{"testClasses/SysIn.java", "junit-stuff.s"});
	}
}
