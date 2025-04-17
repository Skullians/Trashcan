package info.preva1l.dumpster;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created on 16/04/2025
 *
 * @author Preva1l
 */
public interface Savable {
    UUID getIdentifier();

    CompletableFuture<Void> save();
}
