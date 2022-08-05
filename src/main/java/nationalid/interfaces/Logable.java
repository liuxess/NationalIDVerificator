package nationalid.interfaces;

/**
 * An interface for objects that can be used for logging
 */
public interface Logable {

    /**
     * Tries to log the provided message
     * 
     * @param message to log
     * @throws Exception if something goes wrong in the logger
     */
    public void LogMessage(String message) throws Exception;

    /**
     * Compares current instance of Logable to another
     * 
     * @param other instance
     * @return whether or not the types of instances are the same
     */
    public Boolean CompareInstance(Logable other);

}
