/*
 * Copyright 2020 Mark Sailes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package net.sailes.lambda.logger;

import com.fasterxml.jackson.jr.ob.JSON;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static net.sailes.lambda.logger.TestContext.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoggerTest {

    // Arbitrary fixed time for testing - "2020-06-06T19:56:24.192Z"
    public static final long FIXED_TIME_FOR_TESTING = 1591473384192L;

    private final Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(FIXED_TIME_FOR_TESTING), ZoneId.of("UTC"));

    @Test
    public void testStandardStructuredKeysAreLogged() throws IOException {
        Sink mockSink = mock(Sink.class);
        Logger logger = Logger.standard()
                .sink(mockSink)
                .clock(this.fixedClock)
                .build();

        logger.log("Collecting Payment");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockSink).println(captor.capture());

        LogEntry logEntry = JSON.std.beanFrom(LogEntry.class, captor.getValue());
        assertEquals(logEntry.getMessage(), "Collecting Payment");
        assertEquals(logEntry.getService(), "service_undefined");
        assertEquals(logEntry.getTimestamp(), "2020-06-06T19:56:24.192Z");
    }

    @Test
    public void testLoggingIncludeContextInformation() throws IOException {
        Sink mockSink = mock(Sink.class);
        Logger logger = Logger.standard().sink(mockSink).build();
        logger.setContextKeys(new TestContext());

        logger.log("Collecting Payment");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockSink).println(captor.capture());

        LogEntry logEntry = JSON.std.beanFrom(LogEntry.class, captor.getValue());
        assertEquals(logEntry.getFunctionName(), FUNCTION_NAME);
        assertEquals(logEntry.getFunctionVersion(), FUNCTION_VERSION);
        assertEquals(logEntry.getFunctionArn(), FUNCTION_ARN);
        assertEquals(logEntry.getFunctionMemorySize(), MEMORY_LIMIT_IN_MB);
        assertEquals(logEntry.getFunctionRequestId(), REQUEST_ID);
    }
}