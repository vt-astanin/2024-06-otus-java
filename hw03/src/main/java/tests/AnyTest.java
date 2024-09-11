package tests;

import annotations.After;
import annotations.Before;
import annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnyTest {

    public static final Logger logger = LoggerFactory.getLogger(AnyTest.class);

    @SuppressWarnings("unused")
    public void noAnnotate() {
        logger.info("no annotate");
    }

    @Before
    @SuppressWarnings("unused")
    public void testBefore() {
        logger.info("Executing before test");
    }

    @Test
    @SuppressWarnings("unused")
    public void testSuccess() {
        logger.info("Executing success test");
    }

    @Test
    @SuppressWarnings("unused")
    public void testFailure() {
        throw new RuntimeException("Test failed!");
    }

    @After
    @SuppressWarnings("unused")
    public void testAfter(){
        logger.info("Executing after test");
    }
}
