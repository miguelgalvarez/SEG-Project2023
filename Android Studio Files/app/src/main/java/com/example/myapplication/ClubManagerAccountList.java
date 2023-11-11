package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ClubManagerAccountList extends ArrayAdapter<ClubManagerAccount> {

    private Activity context;
    List<ClubManagerAccount> clubManagerAccounts;

    public ClubManagerAccountList(Activity context, List<ClubManagerAccount> clubManagerAccounts) {
        super(context, R.layout.user_account_list_item, clubManagerAccounts);
        this.context = context;
        this.clubManagerAccounts = clubManagerAccounts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        ViewHolder viewHolder;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.user_account_list_item, parent, false);

            viewHolder = new ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        ClubManagerAccount clubManagerAccount = getItem(position);

        viewHolder.accountName.setText(clubManagerAccount.getUsername());

        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.buttonsVisible) {
                    // Apply slide-out animation to the edit button
                    Animation slideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out);

                    viewHolder.deleteButton.startAnimation(slideOut);
                    viewHolder.deleteButton.setVisibility(View.INVISIBLE);

                    viewHolder.arrow.startAnimation(slideOut);
                    viewHolder.arrow.setVisibility(View.INVISIBLE);

                    viewHolder.buttonsVisible = false;
                }

                else {
                    // Apply slide-in animation to the edit button
                    Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);

                    viewHolder.deleteButton.startAnimation(slideIn);
                    viewHolder.deleteButton.setVisibility(View.VISIBLE);

                    viewHolder.arrow.startAnimation(slideIn);
                    viewHolder.arrow.setVisibility(View.VISIBLE);

                    viewHolder.buttonsVisible = true;
                }
            }
        });

        // Add click listener for the delete button
        Button deleteButton = listViewItem.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this gets the event in the database then deletes it
                String name = viewHolder.accountName.getText().toString();
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Club Manager").child(name);
                dR.removeValue();
                sendToastMessage();

                // make button invisible so it doesn't stay open for another item
                viewHolder.deleteButton.setVisibility(View.INVISIBLE);
                viewHolder.arrow.setVisibility(View.INVISIBLE);

                viewHolder.buttonsVisible = false;
            }
        });

        return listViewItem;

    }

    public static class ViewHolder {
        TextView accountName;
        Button deleteButton;
        ImageView threeDots;
        ImageView arrow;
        Boolean buttonsVisible;


        public ViewHolder(View itemView) {
            accountName = itemView.findViewById(R.id.accountName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            threeDots = itemView.findViewById(R.id.threeDots);
            arrow = itemView.findViewById(R.id.arrow);
            buttonsVisible = false;
        }
    }

    public void sendToastMessage() {
        Toast.makeText(getContext(), "Account Deleted!", Toast.LENGTH_SHORT).show();
    }
}
