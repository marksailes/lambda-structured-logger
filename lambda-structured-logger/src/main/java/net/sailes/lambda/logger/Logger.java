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
import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JSONObjectException;

import java.io.IOException;
import java.time.Clock;

public class Logger {

    private static final String DEFAULT_SERVICE_NAME = "service_undefined";
    private final String serviceName = System.getenv("SERVICE_NAME");

    private final Sink sink;
    private Context context;
    private final Clock clock;
    private boolean contextKeysAdded = false;
    private final boolean coldStart;

    Logger(Clock clock, Sink sink, boolean coldStart) {
        this.clock = clock;
        this.sink = sink;
        this.coldStart = coldStart;
    }

    public static Logger create() {
        return standard().build();
    }

    public static LoggerBuilder standard() {
        return LoggerBuilder.builder()
                .clock(Clock.systemUTC())
                .sink(new SystemOutSink())
                .coldStart(true);
    }

    /**
     * Replaces any existing context keys with those passed.
     *
     * @param context
     */
    public void setContextKeys(Context context) {
        if (context == null) {
            throw new LoggerClientException("Context cannot be null");
        }
        this.context = context;
        this.contextKeysAdded = true;
    }

    public void log(String message) throws IOException {
        LogEntry.LogEntryBuilder logEntryBuilder = LogEntry.builder()
                .service(this.serviceName == null ? DEFAULT_SERVICE_NAME : this.serviceName)
                .timestamp(this.clock.instant().toString())
                .message(message)
                .coldStart(this.coldStart);

        if (this.contextKeysAdded) {
            logEntryBuilder.functionArn(this.context.getInvokedFunctionArn())
                .functionMemorySize(this.context.getMemoryLimitInMB())
                .functionName(this.context.getFunctionName())
                .functionRequestId(this.context.getAwsRequestId())
                .functionVersion(this.context.getFunctionVersion());
        }

        try {
            this.sink.println(JSON.std.asString(logEntryBuilder.build()));
        } catch (JSONObjectException e) {
            System.err.println(e.getMessage());
        }
    }

    public static class LoggerBuilder {

        private Clock clock;
        private Sink sink;
        private boolean coldStart;

        public static LoggerBuilder builder() {
            return new LoggerBuilder();
        }

        public LoggerBuilder clock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public LoggerBuilder sink(Sink sink) {
            this.sink = sink;
            return this;
        }

        public LoggerBuilder coldStart(boolean coldStart) {
            this.coldStart = coldStart;
            return this;
        }

        public Logger build() {
            return new Logger(this.clock, this.sink, this.coldStart);
        }
    }
}
