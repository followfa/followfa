package org.followfa.defensive;

import java.util.function.Function;

public class Args {
	public static <T> void notNull(T value, String argumentName) {
		is(value, v -> v != null, v -> argumentName + " must not be null!");
	}

	public static void notEmpty(String value, String argumentName) {
		is(value, v -> v != null && !v.isEmpty(), v -> argumentName + " must not be null or empty, but was: \""+v+"\"!");
	}

	public static <T> void is(T value, Function<T, Boolean> predicate, Function<T, String> error) {
		if(!predicate.apply(value)) {
			throw new IllegalArgumentException(error.apply(value));
		}
	}
}
