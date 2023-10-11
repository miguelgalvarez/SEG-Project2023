package com.example.myapplication;

import static android.graphics.Color.rgb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.net.Uri;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import android.util.Patterns;

public class CreatingAccountActivity extends AppCompatActivity {
    private EditText userNameEdt, userPasswordEdt, firstNameEdt, lastNameEdt, emailEdt;
    private Button registerButton;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_account);

        userNameEdt = findViewById(R.id.idUserName);
        userPasswordEdt = findViewById(R.id.idUserPassword);
        firstNameEdt = findViewById(R.id.idFirstName);
        lastNameEdt = findViewById(R.id.idLastName);
        emailEdt = findViewById(R.id.idEmail);
        registerButton = findViewById(R.id.idRegisterButton);
        //accountTypeEdt = findViewById(R.id.idAccountType);


        dbHandler = new DBHandler(CreatingAccountActivity.this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String userName = userNameEdt.getText().toString();
                String userPassword = userPasswordEdt.getText().toString();
                String firstName = firstNameEdt.getText().toString();
                String lastName = lastNameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                //String accountType = accountTypeEdt.getText().toString();


                // validating if the text fields are empty or not.
                if (userName.isEmpty() && userPassword.isEmpty() && firstName.isEmpty() && lastName.isEmpty() && email.isEmpty()) {
                    Toast.makeText(CreatingAccountActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                //dbHandler.addNewUser(userName, userPassword, firstName, lastName,email,accountType);

                // after adding the data we are displaying a toast message.
                Toast.makeText(CreatingAccountActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();
                userNameEdt.setText("");
                userPasswordEdt.setText("");
                firstNameEdt.setText("");
                lastNameEdt.setText("");
                emailEdt.setText("");
            }
        });
    }

    public boolean validate(View view){
        EditText email = (EditText) findViewById(R.id.idEmail);
        if (!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            return true;
        }
        return false;
    }

    public void CreateAccount(View view) {

        EditText email = (EditText) findViewById(R.id.idEmail);
        if (!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivityForResult(intent, 0);
        } else {
            email.setHint("Please enter a password");
            email.setHintTextColor(rgb(255,0,0));
        }


    }
}