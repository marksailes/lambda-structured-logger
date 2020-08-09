package net.sailes.lambda.logger;

import org.junit.jupiter.api.Test;

public class LoggerFactoryTest {

    @Test
    public void testFirstLoggerIsColdStartTrue() {
        Logger logger = LoggerFactory.getLogger();
        logger.info("Test");

        Logger logger2 = LoggerFactory.getLogger();
        logger2.info("Test");
    }
}
