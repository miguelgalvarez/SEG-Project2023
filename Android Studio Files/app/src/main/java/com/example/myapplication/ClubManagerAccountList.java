package com.example.myapplication;

import android.app.Activity;
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

import java.util.List;


/**
 * Custom ArrayAdapter for displaying club manager accounts in a ListView.
 * Provides functionality for deleting club manager accounts from Firebase.
 *
 * @author zacharysikka
 * @version 1.0
 */
public class ClubManagerAccountList extends ArrayAdapter<ClubManagerAccount> {

    private Activity context;
    List<ClubManagerAccount> clubManagerAccounts;

    // Constructor for the adapter
    public ClubManagerAccountList(Activity context, List<ClubManagerAccount> clubManagerAccounts) {
        super(context, R.layout.user_account_list_item, clubManagerAccounts);
        this.context = context;
        this.clubManagerAccounts = clubManagerAccounts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate a new view
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.user_account_list_item, parent, false);
            viewHolder = new ViewHolder(listViewItem);
            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        // Get the club manager account for this position
        ClubManagerAccount clubManagerAccount = getItem(position);
        viewHolder.accountName.setText(clubManagerAccount.getUsername());

        // Set an onClick listener to handle showing/hiding the delete button
        listViewItem.setOnClickListener(v -> {
            if (viewHolder.buttonsVisible) {
                // Hide the delete button with an animation
                Animation slideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out);
                viewHolder.deleteButton.startAnimation(slideOut);
                viewHolder.deleteButton.setVisibility(View.INVISIBLE);
                viewHolder.arrow.startAnimation(slideOut);
                viewHolder.arrow.setVisibility(View.INVISIBLE);
                viewHolder.buttonsVisible = false;
            } else {
                // Show the delete button with an animation
                Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);
                viewHolder.deleteButton.startAnimation(slideIn);
                viewHolder.deleteButton.setVisibility(View.VISIBLE);
                viewHolder.arrow.startAnimation(slideIn);
                viewHolder.arrow.setVisibility(View.VISIBLE);
                viewHolder.buttonsVisible = true;
            }
        });

        // Set an onClick listener for the delete button
        viewHolder.deleteButton.setOnClickListener(v -> {
            String name = viewHolder.accountName.getText().toString();
            AdministratorAccount.removeClubManagerAccount(name); // Remove the account from Firebase
            sendToastMessage(name); // Show a toast message

            // Hide the buttons to reset the UI state
            viewHolder.deleteButton.setVisibility(View.INVISIBLE);
            viewHolder.arrow.setVisibility(View.INVISIBLE);
            viewHolder.buttonsVisible = false;
        });

        return listViewItem;
    }

    // ViewHolder class to hold views for each list item
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

    // Method to show a toast message when an account is deleted
    public void sendToastMessage(String name) {
        Toast.makeText(getContext(), name + " Deleted!", Toast.LENGTH_SHORT).show();
    }
}
