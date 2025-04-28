package info.preva1l.fadah;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created on 25/04/2025
 *
 * @author Preva1l
 */
@DisplayName("Trashcan Tests")
public class TrashcanTests {
    private static ServerMock serverMock;
    private static TestPlugin plugin;

    @BeforeAll
    @DisplayName("Test Plugin Setup")
    public static void setUpPlugin() {
        serverMock = MockBukkit.mock();
        plugin = MockBukkit.load(TestPlugin.class);
    }

    @AfterAll
    public static void unload() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Test Plugin Loads")
    public void testPluginLoads() {
        Assertions.assertTrue(plugin.loaded);
    }

    @Test
    @DisplayName("Test Plugin Enables")
    public void testPluginEnables() {
        Assertions.assertTrue(plugin.enabled);
    }

    @Test
    @DisplayName("Test Service Configures")
    public void testServiceConfigures() {
        Assertions.assertTrue(TestService.configured);
    }

    @Test
    @DisplayName("Test Dependency Injection")
    public void testDependencyInjection() {
        Assertions.assertTrue(TestService.injected);
    }

    @Test
    @DisplayName("Test Service Logger")
    public void testServiceLogger() {
        String testString = "Hello World!";

        String expected = String.format(
                "[%tT INFO]: [TestPlugin] [TestService] " + testString,
                ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        );

        String actual = TestService.instance.testLogger(testString);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test Plugin Disables")
    public void testPluginDisables() {
        serverMock.getPluginManager().disablePlugin(plugin);
        Assertions.assertTrue(plugin.disabled);
    }

    @Test
    @DisplayName("Test Service Closes")
    public void testServiceCloses() {
        Assertions.assertTrue(TestService.closed);
    }
}