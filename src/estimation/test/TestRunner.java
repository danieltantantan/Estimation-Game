package estimation.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This file runs all the Test
 */

public class TestRunner {
    public static void main(String[] args){
        Result result = JUnitCore.runClasses(TestSuite.class);
        System.out.print("All tests passed: ");
        for (Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
