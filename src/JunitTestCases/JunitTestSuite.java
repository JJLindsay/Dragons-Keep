package JunitTestCases;

import Model.DatabaseTest;
import Model.LoadEntityTest;
import Model.UpdateEntityTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * author: JJ Lindsay
 * version: 1.1
 * Course: ITEC 3150 Fall 2014
 * Written: 12/6/2014
 *
 * This class represents JUnit Test Suite
 *
 * Purpose: To call and run all 4 JUnit test classes.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        InventoryTest.class,
        DatabaseTest.class,
        UpdateEntityTest.class,
        LoadEntityTest.class
})
public class JunitTestSuite {
}
