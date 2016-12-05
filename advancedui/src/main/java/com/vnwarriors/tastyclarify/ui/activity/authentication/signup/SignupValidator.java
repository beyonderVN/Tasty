package com.vnwarriors.tastyclarify.ui.activity.authentication.signup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Long on 12/5/2016.
 */

public class SignupValidator {
    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validateEmail(String emailStr) {
        if (emailStr == null) return false;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }
}
