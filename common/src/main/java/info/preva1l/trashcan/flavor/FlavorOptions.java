package info.preva1l.trashcan.flavor;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FlavorOptions {
    private final Logger logger;
    private final String mainPackage;

    public FlavorOptions() {
        this(Logger.getAnonymousLogger());
    }

    public FlavorOptions(Logger logger) {
        this(logger, null);
    }

    public FlavorOptions(Logger logger, String mainPackage) {
        this.logger = logger;
        this.mainPackage = mainPackage;
    }

    public Logger getLogger() {
        return logger;
    }

    public @CheckForNull String getMainPackage() {
        return mainPackage;
    }
}