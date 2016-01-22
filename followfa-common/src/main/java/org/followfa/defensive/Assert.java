package org.followfa.defensive;

import java.util.function.Function;

public class Assert {
	public static <T, E extends Throwable> void is(T value, Function<T, Boolean> predicate, Function<T, E> error) throws E {
		if(!predicate.apply(value)) {
			throw error.apply(value);
		}
	}
}
