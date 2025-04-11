package info.preva1l.trashcan.flavor.binder

infix fun <T> FlavorBinder<T>.to(any: Any): FlavorBinder<T> = to(any)

inline fun <reified A : Annotation> FlavorBinder<*>.annotated(
    noinline lambda: (A) -> Boolean
): FlavorBinder<*> = annotated(A::class.java, lambda)