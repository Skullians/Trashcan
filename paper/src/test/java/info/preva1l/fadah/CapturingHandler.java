package info.preva1l.fadah;

import info.preva1l.trashcan.plugin.util.ServiceLogFormatter;

import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

/**
 * Created on 28/04/2025
 *
 * @author Preva1l
 */
public class CapturingHandler extends ConsoleHandler {
    private final StringWriter stringWriter = new StringWriter();

    public CapturingHandler() {
        setFormatter(new ServiceLogFormatter("TestPlugin"));
    }

    @Override
    public void publish(LogRecord record) {
        stringWriter.write(getFormatter().format(record));
        super.publish(record);
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }

    public String getCapturedOutput() {
        return stringWriter.toString();
    }
}
