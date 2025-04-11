package info.preva1l.trashcan.flavor.binder

/**
 * Adds the specified type to the list of types to bind.
 *
 * @param [T] the type to add
 * @return the current [FlavorBinderMultiType] instance
 */
inline fun <reified T> info.preva1l.trashcan.flavor.binder.FlavorBinderMultiType.to(): info.preva1l.trashcan.flavor.binder.FlavorBinderMultiType = to(T::class.java)