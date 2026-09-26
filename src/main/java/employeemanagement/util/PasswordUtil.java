package employeemanagement.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final int SALT_LENGTH = 16;

    // Generates a random salt and hashes the password with it.
    // Output format: "salt:hash" (both Base64-encoded) so the salt
    // travels alongside the hash — we need it again to verify later.
    public static String hashPassword(String plainPassword) {
        try {
            byte[] salt = new byte[SALT_LENGTH];
            new SecureRandom().nextBytes(salt);

            byte[] hash = hashWithSalt(plainPassword, salt);

            return Base64.getEncoder().encodeToString(salt) + ":" +
                   Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    // Re-hashes the given plain password using the SAME salt stored
    // alongside the original hash, then compares the two hashes.
    // We never "decrypt" a password — hashing is one-way by design.
    public static boolean verifyPassword(String plainPassword, String storedValue) {
        try {
            String[] parts = storedValue.split(":");
            if (parts.length != 2) return false;

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] originalHash = Base64.getDecoder().decode(parts[1]);

            byte[] testHash = hashWithSalt(plainPassword, salt);

            return MessageDigest.isEqual(originalHash, testHash);

        } catch (Exception e) {
            return false; // malformed stored value, or hashing failure = fail safe
        }
    }

    private static byte[] hashWithSalt(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        return md.digest(password.getBytes());
    }
}
