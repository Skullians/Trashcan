package info.preva1l.trashcan.plugin.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Created on 28/04/2025
 *
 * @author Preva1l
 */
public class ServiceLogFormatter extends SimpleFormatter {
    private final static String FORMAT = "[%tT %s]: [%s] [%s] %s%s";
    private final static String ANONYMOUS_SERVICE = "Anonymous Service";
    private final static ZoneId ZONE_ID = ZoneId.systemDefault();

    private final String rootLoggerName;

    public ServiceLogFormatter(String rootLoggerName) {
        this.rootLoggerName = rootLoggerName;
    }

    public static ConsoleHandler asConsoleHandler(String rootLoggerName) {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new ServiceLogFormatter(rootLoggerName));
        return handler;
    }

    @Override
    public String format(LogRecord record) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(record.getInstant(), ZONE_ID);

        var sourceClass = record.getSourceClassName();
        String service = (sourceClass != null) ? sourceClass.substring(sourceClass.lastIndexOf('.') + 1) : ANONYMOUS_SERVICE;

        String message = formatMessage(record);

        String throwable = "";
        var thrown = record.getThrown();
        if (thrown != null) {
            var sw = new StringWriter(1024);
            var pw = new PrintWriter(sw);
            pw.println();
            thrown.printStackTrace(pw);
            pw.flush();
            throwable = sw.toString();
        }

        return FORMAT.formatted(
                zdt,
                record.getLevel().getLocalizedName(),
                rootLoggerName,
                service,
                message,
                throwable
        );
    }
}
