package nationalid.loggers;

import nationalid.interfaces.Logable;

public class TestingLogger implements Logable {

    private Boolean messageLogged = false;

    @Override
    public void LogMessage(String message) throws Exception {
        messageLogged = true;

        if (!message.contains("Failure"))
            return;

        messageLogged = false;
        throw new Exception("Logging has purposefully failed");
    }

    @Override
    public Boolean CompareInstance(Logable other) {
        return other instanceof TestingLogger;
    }

    public Boolean IsMessageLogged() {
        return messageLogged;
    }
}
