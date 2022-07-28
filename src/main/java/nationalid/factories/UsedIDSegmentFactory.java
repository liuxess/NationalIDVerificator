package nationalid.factories;

import nationalid.enums.SupportedNationalFactories;
import nationalid.exceptions.LoggedException;
import nationalid.factories.segmentFactoryImplementations.LithuanianIDSegmentFactory;
import nationalid.interfaces.IDSegmentFactory;

public class UsedIDSegmentFactory {
    static IDSegmentFactory segmentFactory;
    static SupportedNationalFactories factoryTypeInUse;
    static Boolean initialized;

    public static void Setup(SupportedNationalFactories supportedFactory) {
        setInitialized(true);

        segmentFactory = switch (supportedFactory) {
            case LITHUANIAN -> new LithuanianIDSegmentFactory();
            default -> getDefault();
        };
    }

    public static IDSegmentFactory get() {
        if (!getInitialized()) {
            var error = new LoggedException("Segment Factory not initialized");
            throw new RuntimeException(error);
        }
        return segmentFactory;
    }

    private static void setInitialized(Boolean isInit) {
        initialized = isInit;
    }

    private static Boolean getInitialized() {
        return initialized;
    }

    private static IDSegmentFactory getDefault() {
        setInitialized(false);
        return new LithuanianIDSegmentFactory();
    }

    public static SupportedNationalFactories getFactoryTypeInUse() {
        return factoryTypeInUse;
    }
}
