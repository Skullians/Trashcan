package info.preva1l.trashcan.flavor

import info.preva1l.trashcan.flavor.binder.FlavorBinder

/**
 * Searches for & returns a
 * service matching type [T].
 *
 * @return the service
 * @throws RuntimeException if there is
 * no service matching type [T].
 */
inline fun <reified T> Flavor.service(): T = service(T::class.java)

/**
 * Creates a new [FlavorBinder] for type [T].
 */
inline fun <reified T> Flavor.bind(): FlavorBinder<T> = bind(T::class.java)

/**
 * Creates & inject a new instance of [T];
 *
 * @return the injected instance of [T]
 */
inline fun <reified T> Flavor.injected(vararg params: Any): T = injected(T::class.java, params)