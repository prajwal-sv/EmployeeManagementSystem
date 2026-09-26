package employeemanagement.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // 10 digits, optionally prefixed with +countrycode. Adjust if you need a different format.
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+\\d{1,3})?\\d{10}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isValidSalary(BigDecimal salary) {
        return salary != null && salary.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}