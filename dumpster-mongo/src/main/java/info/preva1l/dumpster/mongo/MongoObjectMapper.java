package info.preva1l.dumpster.mongo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import info.preva1l.dumpster.ObjectMapper;
import info.preva1l.dumpster.Savable;
import info.preva1l.dumpster.annotations.Ignore;
import info.preva1l.dumpster.annotations.SerializeAs;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created on 16/04/2025
 *
 * @author Preva1l
 */
public class MongoObjectMapper implements ObjectMapper {
    private final Gson gson = new GsonBuilder().create();

    @Override
    public Savable read(UUID identifier) {
        return null;
    }

    @Override
    public void write(Savable object) {
        toDocument(object);
        // impl save stuff
    }

    private Document toDocument(Savable obj) {
        JsonObject jsonObject = new JsonObject();
        Field primaryKeyField = null;

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Ignore.class)) continue;

            String name = field.getName();
            if (field.isAnnotationPresent(SerializeAs.class)) {
                name = field.getAnnotation(SerializeAs.class).value();
            }

            try {
                Object value = field.get(obj);
                if (value == null) continue;

                if (field.getType().isAssignableFrom(UUID.class) && name.equals("identifier")) {
                    jsonObject.add("_id", gson.toJsonTree(value));
                } else {
                    jsonObject.add(name, gson.toJsonTree(value));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return Document.parse(jsonObject.toString());
    }
}
