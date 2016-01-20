package org.followfa.defensive;

import java.util.function.Function;

public class Assert {
	public static <T> void is(T value, Function<T, Boolean> predicate, Function<T, String> error) {
		if(!predicate.apply(value)) {
			throw new IllegalArgumentException(error.apply(value));
		}
	}
}
