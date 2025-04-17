package info.preva1l.trashcan.flavor;

import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.QueryFunction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PackageIndexer {
    private final FlavorOptions options;
    private final Reflections reflections;

    public PackageIndexer(Class<?> clazz, FlavorOptions options) {
        this.options = options;
        this.reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackage(
                                options.getMainPackage() != null ? options.getMainPackage() : clazz.getPackageName(),
                                clazz.getClassLoader()
                        )
                        .addUrls(
                                options.getAdditionalPackages()
                                        .stream()
                                        .flatMap(it -> ClasspathHelper.forPackage(it, clazz.getClassLoader()).stream())
                                        .toList()
                        )
                        .addScanners(
                                Scanners.MethodsAnnotated,
                                Scanners.TypesAnnotated,
                                Scanners.SubTypes
                        )
        );
    }

    /**
     * Returns a list of subtypes of the specified type.
     *
     * @param type the type whose subtypes are to be retrieved
     * @return a list of subtypes of the specified type
     */
    public <T> List<Class<?>> getSubTypes(Class<T> type) {
        return reflections
                .get(subTypes(type))
                .stream()
                .toList();
    }

    /**
     * Gets all methods annotated with the specified annotation and invokes them.
     *
     * @param annotation the annotation type
     */
    public void invokeMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        getMethodsAnnotatedWith(annotation)
                .forEach(it -> {
                    try {
                        it.invoke(this);
                    } catch (Exception e) {
                        options.getLogger().log(
                                Level.WARNING,
                                String.join(" ",
                                        "Failed to run container part",
                                        it.getClass().getSimpleName(),
                                        "on",
                                        annotation.getSimpleName()
                                ), e
                        );
                    }
                });
    }

    /**
     * Returns a list of methods annotated with the specified annotation.
     *
     * @param annotation the annotation type
     * @return a list of methods annotated with the specified annotation
     */
    public <T extends Annotation> List<Method> getMethodsAnnotatedWith(Class<T> annotation) {
        return reflections
                .get(annotated(annotation))
                .stream()
                .toList();
    }

    /**
     * Returns a list of types annotated with the specified annotation.
     *
     * @param annotation the annotation type
     * @return a list of types annotated with the specified annotation
     */
    public <T extends Annotation> List<Class<?>> getTypesAnnotatedWith(Class<T> annotation) {
        List<Class<?>> result = new ArrayList<>();
        for (String className : reflections.get(Scanners.TypesAnnotated.with(annotation))) {
            try {
                result.add(Class.forName(className));
            } catch (ClassNotFoundException ignored) {}
        }
        return result;
    }

    /**
     * Returns a query function for methods annotated with the specified annotation.
     *
     * @param annotation the annotation type
     * @return a query function for methods annotated with the specified annotation
     */
    public <T> QueryFunction<Store, Method> annotated(Class<T> annotation) {
        return Scanners.MethodsAnnotated
                .with(annotation)
                .as(Method.class);
    }

    /**
     * Returns a query function for subtypes of the specified type.
     *
     * @param <T> the type whose subtypes are to be retrieved
     * @return a query function for subtypes of the specified type
     */
    public <T> QueryFunction<Store, Class<?>> subTypes(Class<T> annotation) {
        return Scanners.SubTypes
                .with(annotation)
                .as();
    }
}