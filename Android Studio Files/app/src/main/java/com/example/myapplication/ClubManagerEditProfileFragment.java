package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClubManagerEditProfileFragment extends Fragment {

    private EditText phoneNumber;
    private EditText instagram;
    private EditText contact;
    private int logoNumber;
    private Button submitButton;

    private ImageView logo1;
    private ImageView logo2;
    private ImageView logo3;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_manager_edit_profile, container, false);

        instagram = view.findViewById(R.id.instagram);
        phoneNumber = view.findViewById(R.id.editPhoneNumber);
        contact = view.findViewById(R.id.editContact);

        submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton();
            }
        });

        // create onClick for logos
        logo1 = view.findViewById(R.id.imageView5);
        logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage1();
            }
        });

        logo2 = view.findViewById(R.id.imageView4);
        logo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage2();
            }
        });
        logo3 = view.findViewById(R.id.imageView3);
        logo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage3();
            }
        });


        return view;
    }
    public void setImage1() {
        logoNumber = 1;
        Toast.makeText(getContext(), "Selected Logo 1", Toast.LENGTH_SHORT).show();
    }

    public void setImage2() {
        logoNumber = 2;
        Toast.makeText(getContext(), "Selected Logo 2", Toast.LENGTH_SHORT).show();
    }

    public void setImage3() {
        logoNumber = 3;
        Toast.makeText(getContext(), "Selected Logo 3", Toast.LENGTH_SHORT).show();
    }

    public void submitButton() {
        String inputPhoneNumber = phoneNumber.getText().toString().trim();
        String inputInstagram = instagram.getText().toString().trim();

        // Validate phone number
        if (!isValidPhoneNumber(inputPhoneNumber)) {
            Toast.makeText(getContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            return; // Stop further processing if validation fails
        }

        // Validate Instagram name
        if (!isValidInstagramName(inputInstagram)) {
            Toast.makeText(getContext(), "Invalid Instagram Name", Toast.LENGTH_SHORT).show();
            return; // Stop further processing if validation fails
        }
        // Your existing logic, adapted for fragment
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Club Manager");

        // Retrieve arguments or use a different method to get the username
        String username = getArguments().getString("username");

        DatabaseReference newUserRef = usersRef.child(username);

        newUserRef.child("Instagram").setValue(instagram.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(getContext(), "Failed to write Instagram: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Instagram Updated!: ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newUserRef.child("Phone Number").setValue(phoneNumber.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(getContext(), "Failed to write Phone Number: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Phone Number Updated!: ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newUserRef.child("Main Contact").setValue(contact.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(getContext(), "Failed to update Main Contact: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Main Contact Updated!: ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //add logo if the user chose one
        if (logoNumber!=0){
            newUserRef.child("Logo Number").setValue(logoNumber, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Handle the error
                        Toast.makeText(getContext(), "Failed to create Logo: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Logo Updated!: ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        ClubManagerHomePageFragment fragment = new ClubManagerHomePageFragment();
        Bundle args = new Bundle();
        args.putString("username",username);
        args.putString("accountType", "Club Manager");
        fragment.setArguments(args);
        if (isAdded()) { // Check if fragment is currently added to its activity
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragmentHolderViewClubManager, fragment)
                    .addToBackStack(null)
                    .commit();
        }

    }
    // Helper method to validate phone numbers
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10,15}$");
    }

    // Helper method to validate Instagram names
    private boolean isValidInstagramName(String instagramName) {

        return instagramName.matches("^[a-zA-Z][a-zA-Z0-9_.]*$");

    }

}
