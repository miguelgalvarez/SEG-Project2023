package com.example.myapplication.participant;

import android.widget.EditText;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginValidator {

    /**
     * Validates if the username is not null or empty.
     *
     * @param username The username to be validated.
     * @return True if the username is valid, false otherwise.
     */
    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    /**
     * Validates if the account type is "Participant" (case-insensitive).
     *
     * @param accountType The account type to be validated.
     * @return True if the account type is valid, false otherwise.
     */
    public static boolean isValidAccountType(String accountType) {
        return "Participant".equalsIgnoreCase(accountType);
    }

    /**
     * Validates both username and account type for the ParticipantHomePageFragment.
     *
     * @param username    The username to be validated.
     * @param accountType The account type to be validated.
     * @return True if both username and account type are valid, false otherwise.
     */
    public static boolean isValidParticipantHomePageArguments(String username, String accountType) {
        return isValidUsername(username) && isValidAccountType(accountType);
    }

    /**
     * Example: Validates if the password meets certain criteria.
     *
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    public static boolean isValidPassword(String password) {
        // Add your password validation logic here
        // For example, check if the password has a minimum length
        return password != null && password.length() >= 8;
    }

    /**
     * Example: Validates if the email address is in a valid format.
     *
     * @param email The email address to be validated.
     * @return True if the email address is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        // Add your email validation logic here
        // For example, use a regular expression to check the email format
        return email != null && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");
    }
    public static void validateSearchEvent(SearchEvent searchEvent, String participantAccountName, SearchEventValidationCallback callback) {
        String eventName = searchEvent.getEventName();
        String eventType = searchEvent.getEventType();
        String clubName = searchEvent.getClubName();
        String clubUsername = searchEvent.getClubUsername();

        // Validate individual fields of SearchEvent as needed
        // For simplicity, I'm assuming all fields should not be empty. You can customize this according to your requirements.

        if (eventName.isEmpty() || eventType.isEmpty() || clubName.isEmpty() || clubUsername.isEmpty()) {
            // Validation fails
            callback.onValidationResult(false, "All fields must be filled");
            return;
        }

        // Validate if the participant has joined the event
        searchEvent.hasParticipantJoined(participantAccountName, hasJoined -> {
            if (hasJoined) {
                // Participant has already joined the event
                callback.onValidationResult(false, "You have already joined this event");
            } else {
                // Validation passes
                callback.onValidationResult(true, "Validation successful");
            }
        });
    }

    // Callback to handle asynchronous nature of validation
    public interface SearchEventValidationCallback {
        void onValidationResult(boolean isValid, String message);
    }
    public static boolean validateParticipantClub(ParticipantClub participantClub) {
        String clubName = participantClub.getName();

        if (clubName == null || clubName.isEmpty()) {
            // Club name is empty or null, validation fails
            return false;
        }

        // You can add more validation rules if needed

        // If all validation passes
        return true;
    }
    public static boolean validateParticipantLoginFields(EditText usernameEditText, EditText passwordEditText) {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if username is empty
        if (username.isEmpty()) {
            // Username is empty, return false
            return false;
        }

        // Additional validation logic for password, if needed

        // If username is not empty and other validations pass, return true
        return true;
    }

    public static boolean validateParticipantAccountCreationFields(EditText usernameEditText, EditText passwordEditText, EditText emailEditText) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        if (username.isEmpty()) {
            usernameEditText.setError("Please enter a username");
            return false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Please enter a password");
            return false;
        }

        // You can add more validation for the email field if needed
        if (email.isEmpty()) {
            emailEditText.setError("Please enter an email address");
            return false;
        }

        return true;
    }
}
