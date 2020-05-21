package ua.training.regex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegexTest {

	@Test
	// @Ignore
	public void testWeightRegexp() {
		//String weight = "123";
		String weight = "123.9";

		boolean actual = weight.matches("^\\d*\\.?\\d*$");
		boolean expected = true;

		assertEquals(expected, actual);
	}
}
