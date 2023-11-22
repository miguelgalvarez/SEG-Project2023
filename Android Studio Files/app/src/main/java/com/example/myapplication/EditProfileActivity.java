package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity{

    FloatingActionButton backButton;

    private EditText phoneNumber;

    private EditText instagram;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        instagram = findViewById(R.id.instagram);

        phoneNumber = findViewById(R.id.editPhoneNumber);

        backButton = findViewById(R.id.backButtonEditProfile);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onBackPressed();
                }
        });
    }
    public void submitButton(View view) {


        //gotta add some valid checking

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Club Manager");
        Intent intent = getIntent();


        DatabaseReference newUserRef = usersRef.child(intent.getStringExtra("username"));

        newUserRef.child("Instagram").setValue(instagram.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(EditProfileActivity.this, "Failed to write Instagram: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        newUserRef.child("Phone Number").setValue(phoneNumber.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(EditProfileActivity.this, "Failed to write Phone Number: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // back to last page
        onBackPressed();

    }
}
