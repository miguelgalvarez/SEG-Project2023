package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Welcome Activity that displays the welcome screen when users sign up, displays their username and account type
 *
 * @author Android Studio, Zachary Sikka, Zachary Kranabetter, Miguel Alvarez
 * @version 1.0
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");

        // Find the TextViews in the UI layout
        TextView usernameTextView = findViewById(R.id.usernameText);
        TextView accountTypeTextView = findViewById(R.id.userRole);

        //setting the UI components(TextViews) to display the username and account type
        usernameTextView.setText(username);
        accountTypeTextView.setText(accountType);


    }


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("users");

    //DatabaseReference newUserRef = usersRef.child(username.getText().toString());

    /*@Override
    protected void onStart() {
        super.onStart();

        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                products.clear();

                for(DataSnapshot postSnapshot: datasnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);

                    products.add(product);
                }

                ProductList productsAdapter = new ProductList(MainActivity.this, products);

                listViewProducts.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }*/
}