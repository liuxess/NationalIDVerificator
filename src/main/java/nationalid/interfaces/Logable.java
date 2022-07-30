package nationalid.interfaces;

public interface Logable {

    public void LogMessage(String message) throws Exception;

    public Boolean CompareInstance(Logable other);

}
