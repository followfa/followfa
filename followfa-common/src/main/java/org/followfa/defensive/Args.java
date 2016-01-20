package org.followfa.defensive;

public class Args {
	public static <T> void notNull(T value, String argumentName) {
		Assert.is(value, v -> v != null, v -> argumentName + " must not be null!");
	}

	public static void notEmpty(String value, String argumentName) {
		Assert.is(value, v -> v != null && !v.isEmpty(), v -> argumentName + " must not be null or empty, but was: \""+v+"\"!");
	}
}
