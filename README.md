![Java CI with Maven](https://github.com/marksailes/lambda-structured-logger/workflows/Java%20CI%20with%20Maven/badge.svg) ![Maven Central](https://img.shields.io/maven-central/v/net.sailes/lambda-structured-logger)
# Lambda Structured Logger
A simple opinionated structured logger for AWS Lambda

## Purpose

To provide a simple and easy to use structured logger specifically for use with AWS Lambda.

## Example Usage

The following example usage:

```java
import net.sailes.lambda.logger.Logger;

public class PaymentHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    
    private static final Logger LOGGER = Logger.create();

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        LOGGER.setContextKeys(context);
        LOGGER.log("Collecting Payment");
        ...
    }
}
```

Produces a single line JSON formatted output (displayed in multi-line for readability)

```json
{
   "timestamp":"2020-08-09T11:52:34.290Z",
   "service":"Payment Service",
   "message":"Collecting Payment",
   "functionName":"example-HelloWorldFunction-1P1Z6B39FLU73",
   "functionVersion":"1.0.0",
   "functionArn":"arn:aws:lambda:eu-west-1:012345678910:function:example-HelloWorldFunction-1P1Z6B39FLU73",
   "functionMemorySize":512,
   "functionRequestId":"899856cb-83d1-40d7-8611-9e78f15f32f4"
}
```

## Maven

```xml
<dependency>
  <groupId>net.sailes</groupId>
  <artifactId>lambda-structured-logger</artifactId>
  <version>0.2.0</version>
</dependency>
```

## Additional Configuration

Environment Variables

| Name | Description | Default |
|------|-------------|---------|
| `SERVICE_NAME` | Friendly name for your function | `service_undefined` | 