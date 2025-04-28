package info.preva1l.fadah;

import info.preva1l.trashcan.flavor.annotations.Close;
import info.preva1l.trashcan.flavor.annotations.Configure;
import info.preva1l.trashcan.flavor.annotations.Service;
import info.preva1l.trashcan.flavor.annotations.inject.Inject;

/**
 * Created on 28/04/2025
 *
 * @author Preva1l
 */
@Service
public class TestService {
    public static boolean configured = false;
    public static boolean injected = false;
    public static boolean closed = false;

    public static final TestService instance = new TestService();

    @Inject private TestPlugin plugin;

    @Configure
    public void configure() {
        configured = true;
        injected = plugin != null;
    }

    @Close
    public void close() {
        closed = true;
    }
}
