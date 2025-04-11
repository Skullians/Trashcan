package info.preva1l.trashcan.flavor;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FlavorOptions {
    private final Logger logger;
    private final String mainPackage;
    private final List<String> additionalPackages;

    public FlavorOptions() {
        this(Logger.getAnonymousLogger());
    }

    public FlavorOptions(Logger logger) {
        this(logger, null);
    }

    public FlavorOptions(Logger logger, String mainPackage) {
        this(logger, mainPackage, new ArrayList<>());
    }

    public FlavorOptions(Logger logger, String mainPackage, List<String> additionalPackages) {
        this.logger = logger;
        this.mainPackage = mainPackage;
        this.additionalPackages = additionalPackages;
    }

    public Logger getLogger() {
        return logger;
    }

    public @CheckForNull String getMainPackage() {
        return mainPackage;
    }

    public List<String> getAdditionalPackages() {
        return additionalPackages;
    }
}