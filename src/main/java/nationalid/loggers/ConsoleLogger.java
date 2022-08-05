package nationalid.loggers;

import nationalid.interfaces.Logable;

/**
 * Designed to log messages in the Console
 * 
 * @see nationalid.interfaces.Logable
 */
public class ConsoleLogger implements Logable {

    /*
     * (non-Javadoc)
     * 
     * @see nationalid.interfaces.Logable#LogMessage(java.lang.String)
     */
    @Override
    public void LogMessage(String message) throws Exception {
        System.err.print(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nationalid.interfaces.Logable#CompareInstance(nationalid.interfaces.Logable)
     */
    @Override
    public Boolean CompareInstance(Logable other) {
        return other instanceof ConsoleLogger;
    }

}
