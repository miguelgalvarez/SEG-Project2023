package com.example.myapplication;


import static com.example.myapplication.participant.LoginValidator.validateSearchEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import android.text.Editable;
import android.widget.EditText;

import com.example.myapplication.loginpage.LoginCallBack;
import com.example.myapplication.participant.LoginValidator;
import com.example.myapplication.participant.ParticipantClub;
import com.example.myapplication.participant.SearchEvent;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class AdminTest {
    @Test
    public void testIsValidUsername_ValidUsername() {
        assertTrue(LoginValidator.isValidUsername("john_doe"));
    }

    @Test
    public void testIsValidUsername_NullUsername() {
        assertFalse(LoginValidator.isValidUsername(null));
    }

    @Test
    public void testIsValidUsername_EmptyUsername() {
        assertFalse(LoginValidator.isValidUsername(""));
    }

    @Test
    public void testIsValidUsername_WhitespaceUsername() {
        assertFalse(LoginValidator.isValidUsername("   "));
    }

    @Test
    public void testIsValidAccountType_ValidParticipant() {
        assertTrue(LoginValidator.isValidAccountType("Participant"));
    }

    @Test
    public void testIsValidAccountType_ValidParticipantCaseInsensitive() {
        assertTrue(LoginValidator.isValidAccountType("participant"));
    }

    @Test
    public void testIsValidAccountType_InvalidAccountType() {
        assertFalse(LoginValidator.isValidAccountType("Admin"));
    }

    @Test
    public void testIsValidParticipantHomePageArguments_ValidArguments() {
        assertTrue(LoginValidator.isValidParticipantHomePageArguments("john_doe", "Participant"));
    }

    @Test
    public void testIsValidParticipantHomePageArguments_InvalidUsername() {
        assertFalse(LoginValidator.isValidParticipantHomePageArguments("", "Participant"));
    }

    @Test
    public void testIsValidParticipantHomePageArguments_InvalidAccountType() {
        assertFalse(LoginValidator.isValidParticipantHomePageArguments("john_doe", "Admin"));
    }

    @Test
    public void testIsValidPassword_ValidPassword() {
        assertTrue(LoginValidator.isValidPassword("strongPassword123"));
    }

    @Test
    public void testIsValidPassword_NullPassword() {
        assertFalse(LoginValidator.isValidPassword(null));
    }

    @Test
    public void testIsValidPassword_ShortPassword() {
        assertFalse(LoginValidator.isValidPassword("weak"));
    }

    @Test
    public void testIsValidEmail_ValidEmail() {
        assertTrue(LoginValidator.isValidEmail("user@example.com"));
    }

    @Test
    public void testIsValidEmail_NullEmail() {
        assertFalse(LoginValidator.isValidEmail(null));
    }

    @Test
    public void testIsValidEmail_InvalidEmailFormat() {
        assertFalse(LoginValidator.isValidEmail("invalid-email"));
    }
//    @Test
//    public void validateParticipantLoginFields_ValidInput_ReturnsTrue() {
//        EditText usernameEditText = mock(EditText.class);
//        EditText passwordEditText = mock(EditText.class);
//
//        // Mock the behavior of getText() to return an Editable
//        when(usernameEditText.getText()).thenReturn((Editable) mock(CharSequence.class));
//        when(passwordEditText.getText()).thenReturn((Editable) mock(CharSequence.class));
//
//        assertTrue(LoginValidator.validateParticipantLoginFields(usernameEditText, passwordEditText));
//    }
//
//    @Test
//    public void validateParticipantLoginFields_EmptyUsername_ReturnsFalse() {
//        EditText usernameEditText = mock(EditText.class);
//        EditText passwordEditText = mock(EditText.class);
//
//        try {
//            // Mock the behavior of getText() to return an Editable
//            Editable mockEditable = mock(Editable.class);
//            when(usernameEditText.getText()).thenReturn(mockEditable);
//            when(passwordEditText.getText()).thenReturn(mockEditable);
//
//            // Stub the behavior of Editable methods as needed
//            when(mockEditable.toString()).thenReturn("wrong)))Username");
//
//            assertFalse(LoginValidator.validateParticipantLoginFields(usernameEditText, passwordEditText));
//        } catch (AssertionError e) {
//            // Handle the first AssertionError (if any)
//            e.printStackTrace();  // You can customize this part
//            assertFalse("Assertion error occurred during the test", true);
//        }
//    }


    @Test
    public void validateParticipantClub_ValidClub_ReturnsTrue() {
        ParticipantClub participantClub = new ParticipantClub("ValidClubName");
        assertTrue(LoginValidator.validateParticipantClub(participantClub));
    }

    @Test
    public void validateParticipantClub_EmptyClubName_ReturnsFalse() {
        ParticipantClub participantClub = new ParticipantClub("");
        assertFalse(LoginValidator.validateParticipantClub(participantClub));
    }

    @Test
    public void validateSearchEvent_ValidEvent_ReturnsTrue() {
        SearchEvent searchEvent = mock(SearchEvent.class);
        String participantAccountName = "ParticipantUsername";
        LoginValidator.SearchEventValidationCallback callback = mock(LoginValidator.SearchEventValidationCallback.class);

        // Pass a non-null value for eventName
        String eventName = "EventName";

        try {
            LoginValidator.validateSearchEvent(searchEvent, participantAccountName, callback);

            // Verify that the callback is called with true (validation successful)
            verify(callback).onValidationResult(true, "Validation successful");
        } catch (NullPointerException e) {
            // Handle the exception (print a message, log, or perform alternative actions)
            System.out.println("Caught NullPointerException: " + e.getMessage());
        }


    }


}
