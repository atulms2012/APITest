package suite;

/**
 * Created by aatul on 7/26/18.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.AccuWeatherTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccuWeatherTests.class,
})

public class AllSuiteTests {
}
