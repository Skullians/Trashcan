package info.preva1l.dumpster.mongo;

import info.preva1l.dumpster.ObjectMapper;

import java.util.UUID;

/**
 * Created on 16/04/2025
 *
 * @author Preva1l
 */
public class MongoObjectMapper implements ObjectMapper {
    @Override
    public Object read(UUID identifier) {
        return null;
    }

    @Override
    public void write(UUID identifier, Object object) {

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

                if (field.isAnnotationPresent(PrimaryKey.class)) {
                    jsonObject.add("_id", gson.toJsonTree(value));
                    primaryKeyField = field;
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
