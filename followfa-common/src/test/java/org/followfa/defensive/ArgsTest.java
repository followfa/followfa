package org.followfa.defensive;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ArgsTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void notNullThrowsExceptionWhenArgumentIsNull() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(is("foo must not be null!"));

		Args.notNull(null, "foo");
	}

	@Test
	public void notNullDoesNotThrowExceptionWhenArgumentIsNotNull() {
		Args.notNull("foo", "foo");
	}

	@Test
	public void notEmptyThrowsExceptionWhenArgumentIsNull() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(is("foo must not be null or empty, but was: \"null\"!"));

		Args.notEmpty(null, "foo");
	}

	@Test
	public void notEmptyThrowsExceptionWhenArgumentIsEmpty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(is("foo must not be null or empty, but was: \"\"!"));

		Args.notEmpty("", "foo");
	}

	@Test
	public void notEmptyDoesNotThrowExceptionWhenArgumentIsNotNullOrEmpty() {
		Args.notEmpty("foo", "foo");
	}

}