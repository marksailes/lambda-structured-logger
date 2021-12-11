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

public class LogEntry {

    private String timestamp;
    private String service;
    private String message;
    private boolean coldStart;

    private String functionName;
    private String functionVersion;
    private String functionArn;
    private int functionMemorySize;
    private String functionRequestId;

    // required for ser/de with jackson
    public LogEntry() {
    }

    public LogEntry(String timestamp, String service, String message, boolean coldStart, String functionName, String functionVersion, String functionArn, int functionMemorySize, String functionRequestId) {
        this.timestamp = timestamp;
        this.service = service;
        this.message = message;
        this.coldStart = coldStart;
        this.functionName = functionName;
        this.functionVersion = functionVersion;
        this.functionArn = functionArn;
        this.functionMemorySize = functionMemorySize;
        this.functionRequestId = functionRequestId;
    }

    public static LogEntryBuilder builder() {
        return new LogEntryBuilder();
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getService() {
        return this.service;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isColdStart() {
        return this.coldStart;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public String getFunctionVersion() {
        return this.functionVersion;
    }

    public String getFunctionArn() {
        return this.functionArn;
    }

    public int getFunctionMemorySize() {
        return this.functionMemorySize;
    }

    public String getFunctionRequestId() {
        return this.functionRequestId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setColdStart(boolean coldStart) {
        this.coldStart = coldStart;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setFunctionVersion(String functionVersion) {
        this.functionVersion = functionVersion;
    }

    public void setFunctionArn(String functionArn) {
        this.functionArn = functionArn;
    }

    public void setFunctionMemorySize(int functionMemorySize) {
        this.functionMemorySize = functionMemorySize;
    }

    public void setFunctionRequestId(String functionRequestId) {
        this.functionRequestId = functionRequestId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof LogEntry)) return false;
        final LogEntry other = (LogEntry) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$timestamp = this.timestamp;
        final Object other$timestamp = other.timestamp;
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        final Object this$service = this.service;
        final Object other$service = other.service;
        if (this$service == null ? other$service != null : !this$service.equals(other$service)) return false;
        final Object this$message = this.message;
        final Object other$message = other.message;
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        if (this.coldStart != other.coldStart) return false;
        final Object this$functionName = this.functionName;
        final Object other$functionName = other.functionName;
        if (this$functionName == null ? other$functionName != null : !this$functionName.equals(other$functionName))
            return false;
        final Object this$functionVersion = this.functionVersion;
        final Object other$functionVersion = other.functionVersion;
        if (this$functionVersion == null ? other$functionVersion != null : !this$functionVersion.equals(other$functionVersion))
            return false;
        final Object this$functionArn = this.functionArn;
        final Object other$functionArn = other.functionArn;
        if (this$functionArn == null ? other$functionArn != null : !this$functionArn.equals(other$functionArn))
            return false;
        if (this.functionMemorySize != other.functionMemorySize) return false;
        final Object this$functionRequestId = this.functionRequestId;
        final Object other$functionRequestId = other.functionRequestId;
        if (this$functionRequestId == null ? other$functionRequestId != null : !this$functionRequestId.equals(other$functionRequestId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LogEntry;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $timestamp = this.timestamp;
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        final Object $service = this.service;
        result = result * PRIME + ($service == null ? 43 : $service.hashCode());
        final Object $message = this.message;
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        result = result * PRIME + (this.coldStart ? 79 : 97);
        final Object $functionName = this.functionName;
        result = result * PRIME + ($functionName == null ? 43 : $functionName.hashCode());
        final Object $functionVersion = this.functionVersion;
        result = result * PRIME + ($functionVersion == null ? 43 : $functionVersion.hashCode());
        final Object $functionArn = this.functionArn;
        result = result * PRIME + ($functionArn == null ? 43 : $functionArn.hashCode());
        result = result * PRIME + this.functionMemorySize;
        final Object $functionRequestId = this.functionRequestId;
        result = result * PRIME + ($functionRequestId == null ? 43 : $functionRequestId.hashCode());
        return result;
    }

    public String toString() {
        return "LogEntry(timestamp=" + this.timestamp + ", service=" + this.service + ", message=" + this.message + ", coldStart=" + this.coldStart + ", functionName=" + this.functionName + ", functionVersion=" + this.functionVersion + ", functionArn=" + this.functionArn + ", functionMemorySize=" + this.functionMemorySize + ", functionRequestId=" + this.functionRequestId + ")";
    }

    public static class LogEntryBuilder {
        private String timestamp;
        private String service;
        private String message;
        private boolean coldStart;
        private String functionName;
        private String functionVersion;
        private String functionArn;
        private int functionMemorySize;
        private String functionRequestId;

        LogEntryBuilder() {
        }

        public LogEntry.LogEntryBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public LogEntry.LogEntryBuilder service(String service) {
            this.service = service;
            return this;
        }

        public LogEntry.LogEntryBuilder message(String message) {
            this.message = message;
            return this;
        }

        public LogEntry.LogEntryBuilder coldStart(boolean coldStart) {
            this.coldStart = coldStart;
            return this;
        }

        public LogEntry.LogEntryBuilder functionName(String functionName) {
            this.functionName = functionName;
            return this;
        }

        public LogEntry.LogEntryBuilder functionVersion(String functionVersion) {
            this.functionVersion = functionVersion;
            return this;
        }

        public LogEntry.LogEntryBuilder functionArn(String functionArn) {
            this.functionArn = functionArn;
            return this;
        }

        public LogEntry.LogEntryBuilder functionMemorySize(int functionMemorySize) {
            this.functionMemorySize = functionMemorySize;
            return this;
        }

        public LogEntry.LogEntryBuilder functionRequestId(String functionRequestId) {
            this.functionRequestId = functionRequestId;
            return this;
        }

        public LogEntry build() {
            return new LogEntry(timestamp, service, message, coldStart, functionName, functionVersion, functionArn, functionMemorySize, functionRequestId);
        }

        public String toString() {
            return "LogEntry.LogEntryBuilder(timestamp=" + this.timestamp + ", service=" + this.service + ", message=" + this.message + ", coldStart=" + this.coldStart + ", functionName=" + this.functionName + ", functionVersion=" + this.functionVersion + ", functionArn=" + this.functionArn + ", functionMemorySize=" + this.functionMemorySize + ", functionRequestId=" + this.functionRequestId + ")";
        }
    }
}
