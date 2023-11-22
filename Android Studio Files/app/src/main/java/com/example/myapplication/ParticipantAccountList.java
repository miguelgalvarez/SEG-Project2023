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


/**
 * Custom ArrayAdapter to handle the display and interaction of participant accounts in a ListView.
 * @author linden Sheehy
 * @version 1.0
 */
public class ParticipantAccountList extends ArrayAdapter<ParticipantAccount> {

    private Activity context;
    List<ParticipantAccount> participantAccounts;

    public ParticipantAccountList(Activity context, List<ParticipantAccount> participantAccounts) {
        super(context, R.layout.user_account_list_item, participantAccounts);
        this.context = context;
        this.participantAccounts = participantAccounts;
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

        ParticipantAccount participantAccount = getItem(position);

        viewHolder.participantAccount.setText(participantAccount.getUsername());

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
                String name = viewHolder.participantAccount.getText().toString();

                AdministratorAccount.removeParticipantAccount(name);
                sendToastMessage(name);

                // make button invisible so it doesn't stay open for another item
                viewHolder.deleteButton.setVisibility(View.INVISIBLE);
                viewHolder.arrow.setVisibility(View.INVISIBLE);

                viewHolder.buttonsVisible = false;
            }
        });

        return listViewItem;

    }

    public static class ViewHolder {
        TextView participantAccount;
        Button deleteButton;
        ImageView threeDots;
        ImageView arrow;
        Boolean buttonsVisible;


        public ViewHolder(View itemView) {
            participantAccount = itemView.findViewById(R.id.accountName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            threeDots = itemView.findViewById(R.id.threeDots);
            arrow = itemView.findViewById(R.id.arrow);
            buttonsVisible = false;
        }
    }

    public void sendToastMessage(String name) {
        Toast.makeText(getContext(), name + " Deleted!", Toast.LENGTH_SHORT).show();
    }
}
