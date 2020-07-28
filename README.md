# Lambda Structured Logger
A simple opinionated structured logger for AWS Lambda

![Java CI with Maven](https://github.com/marksailes/lambda-structured-logger/workflows/Java%20CI%20with%20Maven/badge.svg)

## Example Usage

```java
Logger logger = Logger.defaultLogger();

public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
    logger.addContextKeys(context);
    logger.info("Taking payment");
}
```

