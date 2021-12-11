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

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class TestContext implements Context {
    public static final String FUNCTION_NAME = "example-HelloWorldFunction-1P1Z6B39FLU73";
    public static final int MEMORY_LIMIT_IN_MB = 512;
    public static final String FUNCTION_ARN = "arn:aws:lambda:eu-west-1:012345678910:function:example-HelloWorldFunction-1P1Z6B39FLU73";
    public static final String FUNCTION_VERSION = "1.0.0";
    public static final String REQUEST_ID = "899856cb-83d1-40d7-8611-9e78f15f32f4";

    @Override
    public String getAwsRequestId() {
        return REQUEST_ID;
    }

    @Override
    public String getLogGroupName() {
        return null;
    }

    @Override
    public String getLogStreamName() {
        return null;
    }

    @Override
    public String getFunctionName() {
        return FUNCTION_NAME;
    }

    @Override
    public String getFunctionVersion() {
        return FUNCTION_VERSION;
    }

    @Override
    public String getInvokedFunctionArn() {
        return FUNCTION_ARN;
    }

    @Override
    public CognitoIdentity getIdentity() {
        return null;
    }

    @Override
    public ClientContext getClientContext() {
        return null;
    }

    @Override
    public int getRemainingTimeInMillis() {
        return 0;
    }

    @Override
    public int getMemoryLimitInMB() {
        return MEMORY_LIMIT_IN_MB;
    }

    @Override
    public LambdaLogger getLogger() {
        return null;
    }
}
