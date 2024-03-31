package com.flix.core.services.admin;

import java.util.Objects;
import java.util.function.Function;

public interface TriFunction<T, U, V, R> {

  R apply(T t, U u, V v);

  default <Z> TriFunction<T, U, V, Z> andThen(Function<? super R, ? extends Z> after) {
    Objects.requireNonNull(after);
    return (T t, U u, V v) -> after.apply(apply(t, u, v));
  }
}
