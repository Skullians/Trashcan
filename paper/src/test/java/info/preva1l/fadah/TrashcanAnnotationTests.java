package info.preva1l.fadah;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.*;

/**
 * Created on 25/04/2025
 *
 * @author Preva1l
 */
@DisplayName("Trashcan Annotation Tests")
public class TrashcanAnnotationTests {
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
    @DisplayName("Test Plugin Loads")
    public void testPluginDisables() {
        serverMock.getPluginManager().disablePlugin(plugin);
        Assertions.assertTrue(plugin.disabled);
    }
}