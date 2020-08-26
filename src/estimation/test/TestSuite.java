package estimation.test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This file serves as the test suite
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CardTest.class,
        PlayerTest.class,
        BidsTest.class,
        RoundTest.class
})

public class TestSuite {
  //This class remains empty, it is used only as a holder for the above annotations
}
