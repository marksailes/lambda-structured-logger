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

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoggerTest {

    // Arbitrary fixed time for testing - "2020-06-06T19:56:24.192Z"
    public static final long FIXED_TIME_FOR_TESTING = 1591473384192L;
    public static final String FUNCTION_NAME = "example-HelloWorldFunction-1P1Z6B39FLU73";
    public static final int MEMORY_LIMIT_IN_MB = 512;
    public static final String FUNCTION_ARN = "arn:aws:lambda:eu-west-1:012345678910:function:example-HelloWorldFunction-1P1Z6B39FLU73";
    public static final String FUNCTION_VERSION = "1.0.0";
    public static final String REQUEST_ID = "899856cb-83d1-40d7-8611-9e78f15f32f4";

    private final Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(FIXED_TIME_FOR_TESTING), ZoneId.of("UTC"));
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testStandardStructuredKeysAreLogged() throws JsonProcessingException {
        Sink mockSink = mock(Sink.class);
        Logger logger = Logger.standard()
                .sink(mockSink)
                .clock(this.fixedClock)
                .build();

        logger.info("Collecting Payment");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockSink).println(captor.capture());

        LogEntry logEntry = OBJECT_MAPPER.readValue(captor.getValue(), LogEntry.class);
        assertEquals(logEntry.getLevel(), "INFO");
        assertEquals(logEntry.getMessage(), "Collecting Payment");
        assertEquals(logEntry.getSamplingRate(), 1);
        assertEquals(logEntry.getService(), "service_undefined");
        assertEquals(logEntry.getTimestamp(), "2020-06-06T19:56:24.192Z");
    }

    @Test
    public void testCorrectExceptionThrownIfJacksonBreaks() {

    }

    @Test
    public void testLoggingIncludeContextInformation() throws JsonProcessingException {
        Sink mockSink = mock(Sink.class);
        Logger logger = Logger.standard().sink(mockSink).build();
        logger.addContextKeys(aUsefulTestContext());

        logger.info("Collecting Payment");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockSink).println(captor.capture());

        LogEntry logEntry = OBJECT_MAPPER.readValue(captor.getValue(), LogEntry.class);
        assertEquals(logEntry.getFunctionName(), FUNCTION_NAME);
        assertEquals(logEntry.getFunctionVersion(), FUNCTION_VERSION);
        assertEquals(logEntry.getFunctionArn(), FUNCTION_ARN);
        assertEquals(logEntry.getFunctionMemorySize(), MEMORY_LIMIT_IN_MB);
        assertEquals(logEntry.getFunctionRequestId(), REQUEST_ID);
    }

    @Test
    public void testColdStartFlagTrueOnFirstInvoke() throws JsonProcessingException {
        Sink mockSink = mock(Sink.class);
        Logger logger = Logger.standard().sink(mockSink).build();
        logger.addContextKeys(aUsefulTestContext());

        logger.info("Collecting Payment");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockSink).println(captor.capture());

        LogEntry logEntry = OBJECT_MAPPER.readValue(captor.getValue(), LogEntry.class);
        assertTrue(logEntry.isColdStart());
    }

    @Test
    public void testUseServiceNameIfSetInEnvVar() throws JsonProcessingException {
        System.setProperty("SERVICE_NAME", "Payment Service");
        Sink mockSink = mock(Sink.class);
        Logger logger = Logger.standard().sink(mockSink).build();

        logger.info("Collecting Payment");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockSink).println(captor.capture());

        LogEntry logEntry = OBJECT_MAPPER.readValue(captor.getValue(), LogEntry.class);
        assertEquals(logEntry.getService(), "Payment Service");
    }

    private Context aUsefulTestContext() {
        return TestContext.builder()
                    .functionName(FUNCTION_NAME)
                    .functionVersion(FUNCTION_VERSION)
                    .awsRequestId(REQUEST_ID)
                    .invokedFunctionArn(FUNCTION_ARN)
                    .memoryLimitInMB(MEMORY_LIMIT_IN_MB)
                    .build();
    }
}