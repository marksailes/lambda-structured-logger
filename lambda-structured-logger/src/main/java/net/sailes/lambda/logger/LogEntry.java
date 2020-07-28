package net.sailes.lambda.logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown=true)
public class LogEntry {

    private String timestamp;
    private String level;
    private String service;
    private double samplingRate;
    private String message;

    private String functionName;
    private String functionVersion;
    private String functionArn;
    private int functionMemorySize;
    private String functionRequestId;

    private boolean coldStart;
}
