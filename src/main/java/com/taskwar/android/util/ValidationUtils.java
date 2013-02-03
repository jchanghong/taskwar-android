package com.taskwar.android.util;

public class ValidationUtils {

	public static boolean validateEmail(String emailToValidate) {
		return emailToValidate.matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}");
	}

	public static boolean validateEquals(String string1, String string2) {
		return string1.equals(string2);
	}
}
