package com.example.myapplication;

import android.app.Activity;
import android.util.Log;
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

// This class just reuses the ActiveEvent objects instead of creating a new one
public class ParticipantClubList extends ArrayAdapter<ParticipantClub> {

    private Activity context;
    List<ParticipantClub> clubs;
    private String clubName;

    public ParticipantClubList(Activity context, List<ParticipantClub> clubs, String clubName) {
        super(context, R.layout.participant_club_item, clubs);
        this.context = context;
        this.clubs = clubs;
        this.clubName = clubName;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        ParticipantClubList.ViewHolder viewHolder;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.participant_club_item, parent, false);

            viewHolder = new ParticipantClubList.ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ParticipantClubList.ViewHolder) listViewItem.getTag();
        }

        ParticipantClub activeEventName = getItem(position);

        try {
            viewHolder.clubName.setText(activeEventName.getName());
        } catch (Exception E) {
            E.printStackTrace();
        }

        //viewHolder.clubName.setText("worked");

        // Set click listeners for the item view
        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open the club events page
                openActivityClubEvents(viewHolder);

            }
        });

        return listViewItem;

    }

    public static class ViewHolder {
        public TextView clubName;

        public ViewHolder(View itemView) {
            this.clubName = itemView.findViewById(R.id.clubName);
        }
    }

    public interface ParticipantClubListListener {
        void onOpenClubEvents(String clubName);
    }
    private ParticipantClubList.ParticipantClubListListener listener;

    // Method to set the listener
    public void setParticipantClubListListener(ParticipantClubList.ParticipantClubListListener listener) {
        this.listener = listener;
    }

    public void openActivityClubEvents(ParticipantClubList.ViewHolder VH) {
        if (listener != null) {
            listener.onOpenClubEvents(VH.clubName.getText().toString());
        }
    }

}
