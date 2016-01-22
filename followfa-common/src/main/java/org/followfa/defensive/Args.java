package org.followfa.defensive;

public class Args {
	public static <T> void notNull(T value, String argumentName) {
		Assert.is(value, v -> v != null, v -> new IllegalArgumentException(argumentName + " must not be null!"));
	}

	public static void notEmpty(String value, String argumentName) {
		Assert.is(value, v -> v != null && !v.isEmpty(), v -> new IllegalArgumentException(argumentName + " must not be null or empty, but was: \""+v+"\"!"));
	}
}
