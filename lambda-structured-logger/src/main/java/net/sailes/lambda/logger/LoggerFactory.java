package net.sailes.lambda.logger;

public class LoggerFactory {

    private static boolean IS_COLD_START = true;

    public static Logger getLogger() {
        Logger logger = Logger.standard().coldStart(IS_COLD_START).build();
        IS_COLD_START = false;
        return logger;
    }
}
