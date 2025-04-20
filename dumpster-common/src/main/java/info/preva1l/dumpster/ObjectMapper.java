package info.preva1l.dumpster;

import java.util.UUID;

/**
 * Created on 16/04/2025
 *
 * @author Preva1l
 */
public interface ObjectMapper {
    Savable read(UUID identifier);

    void write(Savable object);
}
