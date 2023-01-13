package DemoTestSuite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoTestSuite {

    private static final String REPORT_MESSAGE = "Check the logs in: ";

    private final int THREAD_COUNT = 5;

    @Test
    @Order(1)
    public void testDemo() {
        String karateLogPath = "build/test-reports/testDemo";
        Results results = Runner.path("../MainTests/DemoTestSuite")
            .relativeTo(getClass())
            .reportDir(karateLogPath)
            .parallel(THREAD_COUNT);
        assertEquals(0, results.getFailCount(), REPORT_MESSAGE + karateLogPath);
    }

}
