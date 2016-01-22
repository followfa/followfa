package org.followfa.defensive;

public class ReturnValue {
	public static <T> T notNull(T value) {
		Assert.is(value, v -> v != null, v -> new IllegalReturnValueException("This method must always return a non-null value!"));
		return value;
	}

	public static String notEmpty(String value) {
		Assert.is(value, v -> v != null && !v.isEmpty(), v -> new IllegalReturnValueException("This method must always return a non-empty String, but the return value was: \""+v+"\"!"));
		return value;
	}
}
