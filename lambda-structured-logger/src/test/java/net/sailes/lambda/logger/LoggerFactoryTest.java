package net.sailes.lambda.logger;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoggerFactoryTest {

    @Test
    public void testFirstLoggerIsColdStartTrue() throws IOException {
        Logger logger = LoggerFactory.getLogger();
        logger.info("Test");

        Logger logger2 = LoggerFactory.getLogger();
        logger2.info("Test");
    }
}
