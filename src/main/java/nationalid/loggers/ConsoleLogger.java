package nationalid.loggers;

import nationalid.interfaces.Logable;

public class ConsoleLogger implements Logable {

    @Override
    public void LogMessage(String message) throws Exception {
        System.out.print(message);
    }

    @Override
    public Boolean CompareInstance(Logable other) {
        return other instanceof ConsoleLogger;
    }

}
