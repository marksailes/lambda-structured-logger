package net.sailes.lambda.logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Clock;

public class Logger {

    private final String serviceName = System.getProperty("SERVICE_NAME", "service_undefined");

    private final ObjectMapper objectMapper;
    private final Sink sink;
    private Context context;
    private final Clock clock;
    private boolean contextKeysAdded = false;

    Logger(Clock clock, ObjectMapper objectMapper, Sink sink) {
        this.clock = clock;
        this.objectMapper = objectMapper;
        this.sink = sink;
    }

    public static Logger defaultLogger() {
        return standard().build();
    }

    public static LoggerBuilder standard() {
        return LoggerBuilder.builder()
                .clock(Clock.systemUTC())
                .objectMapper(new ObjectMapper())
                .sink(new SystemOutSink());
    }

    public void addContextKeys(Context context) {
        if (context == null) {
            throw new LoggerClientException("Context cannot be null");
        }
        this.context = context;
        this.contextKeysAdded = true;
    }

    public void info(String message) {
        LogEntry.LogEntryBuilder logEntryBuilder = LogEntry.builder()
                .level("INFO")
                .samplingRate(1)
                .service(this.serviceName)
                .timestamp(this.clock.instant().toString())
                .message(message)
                .coldStart(true);

        if (this.contextKeysAdded) {
            logEntryBuilder.functionArn(this.context.getInvokedFunctionArn())
                .functionMemorySize(this.context.getMemoryLimitInMB())
                .functionName(this.context.getFunctionName())
                .functionRequestId(this.context.getAwsRequestId())
                .functionVersion(this.context.getFunctionVersion());
        }

        try {
            this.sink.println(this.objectMapper.writeValueAsString(logEntryBuilder.build()));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new LoggerClientException(e.getMessage());
        }
    }

    public static class LoggerBuilder {

        private Clock clock;
        private ObjectMapper objectMapper;
        private Sink sink;

        public static LoggerBuilder builder() {
            return new LoggerBuilder();
        }

        public LoggerBuilder clock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public LoggerBuilder objectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        public LoggerBuilder sink(Sink sink) {
            this.sink = sink;
            return this;
        }

        public Logger build() {
            return new Logger(this.clock, this.objectMapper, this.sink);
        }

    }
}
