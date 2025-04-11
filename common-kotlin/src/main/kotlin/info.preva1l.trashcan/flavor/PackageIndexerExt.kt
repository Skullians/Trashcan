package info.preva1l.trashcan.flavor

import org.reflections.Store
import org.reflections.util.QueryFunction
import java.lang.reflect.Method

/**
 * Returns a list of subtypes of the specified type.
 *
 * @param [T] the type whose subtypes are to be retrieved
 * @return a list of subtypes of the specified type
 */
inline fun <reified T : Annotation> PackageIndexer.getSubTypes(): List<Class<*>> = getSubTypes(T::class.java)

/**
 * Returns a list of methods annotated with the specified annotation.
 *
 * @param [T] the annotation type
 * @return a list of methods annotated with the specified annotation
 */
inline fun <reified T : Annotation> PackageIndexer.getMethodsAnnotatedWith(): List<Method> = getMethodsAnnotatedWith(T::class.java)

/**
 * Returns a list of types annotated with the specified annotation.
 *
 * @param [T] the annotation type
 * @return a list of types annotated with the specified annotation
 */
inline fun <reified T : Annotation> PackageIndexer.getTypesAnnotatedWith(): List<Class<*>> = getTypesAnnotatedWith(T::class.java)

/**
 * Returns a query function for methods annotated with the specified annotation.
 *
 * @param [T] the annotation type
 * @return a query function for methods annotated with the specified annotation
 */
inline fun <reified T : Annotation> PackageIndexer.annotated(): QueryFunction<Store, Method> = annotated(T::class.java)

/**
 * Returns a query function for subtypes of the specified type.
 *
 * @param [T] the type whose subtypes are to be retrieved
 * @return a query function for subtypes of the specified type
 */
inline fun <reified T : Annotation> PackageIndexer.subTypes(): QueryFunction<Store, Class<*>> = subTypes(T::class.java)