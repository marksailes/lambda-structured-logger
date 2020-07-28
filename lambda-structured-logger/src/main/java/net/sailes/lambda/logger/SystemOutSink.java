package net.sailes.lambda.logger;

public class SystemOutSink implements Sink {

    public void println(String message) {
        System.out.println(message);
    }
}
