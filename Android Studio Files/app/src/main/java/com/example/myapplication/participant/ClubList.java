package com.example.myapplication.participant;
import com.example.myapplication.R;

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

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ClubList extends ArrayAdapter<Club> {
    private static Activity context;
    List<Club> activeEvent;
    private static String accountName;
    Button leaveClubButton;

    public ClubList(Activity context, List<Club> activeEvent, String accountName, Button leaveClubButton) {
        super(context, R.layout.club_view_item_layout, activeEvent);
        this.context = context;
        this.activeEvent = activeEvent;
        this.accountName = accountName;
        this.listener = listener;
        this.leaveClubButton = leaveClubButton;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        ClubList.ViewHolder viewHolder;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.club_view_item_layout, parent, false);

            viewHolder = new ClubList.ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ClubList.ViewHolder) listViewItem.getTag();
        }

        Club activeEventName = getItem(position);

        try {
            viewHolder.clubName.setText(activeEventName.getName());
        } catch (Exception E) {
            E.printStackTrace();
        }

        // Set click listeners for the item view
        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.buttonsVisible) {
                    // Apply slide-out animation to the edit button
                    Animation slideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out);

                    viewHolder.joinButton.startAnimation(slideOut);
                    viewHolder.joinButton.setVisibility(View.INVISIBLE);

                    viewHolder.arrow.startAnimation(slideOut);
                    viewHolder.arrow.setVisibility(View.INVISIBLE);

                    viewHolder.buttonsVisible = false;
                }

                else {
                    // Apply slide-in animation to the edit button
                    Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);
                    viewHolder.joinButton.startAnimation(slideIn);
                    viewHolder.joinButton.setVisibility(View.VISIBLE);

                    viewHolder.arrow.startAnimation(slideIn);
                    viewHolder.arrow.setVisibility(View.VISIBLE);

                    viewHolder.buttonsVisible = true;
                }
            }
        });

        // Get database references to be used for the edit/delete buttons
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventsRef = rootRef.child("Club Manager").child(this.accountName).child("Events");
        // Add click listener for the edit/delete buttons
        Button joinButton = listViewItem.findViewById(R.id.joinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create args variable
                String name = viewHolder.clubName.getText().toString();

                // write to database
                writeDatabaseJoinClub(name,v);
                // make buttons invisible so they don't stay open for another item
                viewHolder.joinButton.setVisibility(View.INVISIBLE);
                viewHolder.arrow.setVisibility(View.INVISIBLE);
                viewHolder.buttonsVisible = false;
            }
        });

        return listViewItem;

    }
    public void writeDatabaseJoinClub(String name,View v){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference participantRef = rootRef.child("Participant").child(accountName).child("Joined Club");
        participantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(String.class).equals("No club")){
                    participantRef.setValue(name);
                    leaveClubButton.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "You have joined: " + name, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "You have already joined a club!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error joining club", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class ViewHolder {
        public TextView clubName;
        public Button joinButton;
        public ImageView arrow;
        public Boolean buttonsVisible;

        public ViewHolder(View itemView) {
            this.clubName = itemView.findViewById(R.id.clubName);
            this.joinButton = itemView.findViewById(R.id.joinButton);
            this.arrow = itemView.findViewById(R.id.arrow);
            this.buttonsVisible = false;
        }
    }

    public interface ActiveEventListListener {
        void onEditActiveEvent(String activeEventName);
    }

    private ActiveEventList.ActiveEventListListener listener;

    // Method to set the listener
    public void setActiveEventListListener(ActiveEventList.ActiveEventListListener listener) {
        this.listener = listener;
    }


    // this just opens the new window for when edit is pressed on an item
    public void openActivityEditActiveEvent(ClubList.ViewHolder VH) {
        if (listener != null) {
            //listener.onEditActiveEvent(VH.clubName.getText().toString());
        }
    }


    public void sendToastMessage() {
        //Toast.makeText(getContext(), "Event Type Deleted!", Toast.LENGTH_SHORT).show();
    }
}
