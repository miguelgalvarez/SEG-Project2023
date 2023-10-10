package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//libraries for dropdown menu
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        //dropdown menu
        Spinner accountDropDown = (Spinner) findViewById(R.id.accountTypeDropDown);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_menu, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        accountDropDown.setAdapter(adapter);

    }

    public void CreateAccount(View view) {


        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivityForResult (intent,0);

    }
}