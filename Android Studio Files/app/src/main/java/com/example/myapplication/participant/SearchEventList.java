package com.example.myapplication.participant;

import com.example.myapplication.R;

import android.app.Activity;
import android.os.Bundle;
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

import java.util.List;

public class SearchEventList extends ArrayAdapter<SearchEvent> {

    private Activity context;
    List<SearchEvent> searchEvents;
    private String accountName;
    private SearchEventList.EventListListener listener;


    public SearchEventList(Activity context, List<SearchEvent> searchEvents, String accountName) {
        super(context, R.layout.search_event_item, searchEvents);
        this.context = context;
        this.searchEvents = searchEvents;
        this.accountName = accountName;
        //this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        SearchEventList.ViewHolder viewHolder;


        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.search_event_item, parent, false);

            viewHolder = new SearchEventList.ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (SearchEventList.ViewHolder) listViewItem.getTag();
        }

        SearchEvent activeEventName = getItem(position);

        try {
            viewHolder.eventName.setText(activeEventName.getEventName());
            viewHolder.eventType.setText(activeEventName.getEventType());
            viewHolder.clubName.setText(activeEventName.getClubName());
            viewHolder.clubUsername = activeEventName.getClubUsername();
        } catch (Exception E) {
            E.printStackTrace();
        }

        viewHolder.joinIcon.setVisibility(View.INVISIBLE);

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

        // Add click listener for the join button
        viewHolder.joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the participant has joined the event
                Log.d("DEBUGGG", "Join button clicked");
                activeEventName.hasParticipantJoined(accountName, new SearchEvent.ParticipantJoinedCallback() {
                    @Override
                    public void onCallback(boolean hasJoined) {
                        Log.d("DEBUGGG", "ONCALLBACK");
                        if (hasJoined) {
                            // They are already in the event
                            Toast.makeText(context, "You have already joined this event.", Toast.LENGTH_SHORT).show();
                        } else {
                            // hide buttons again
                            viewHolder.joinButton.setVisibility(View.INVISIBLE);
                            viewHolder.arrow.setVisibility(View.INVISIBLE);
                            viewHolder.buttonsVisible = false;

                            Log.d("debug", "clicked join");

                            // Open join event page
                            openEventJoinFragment(viewHolder);
                        }
                    }
                });
            }
        });

        // Check if the participant has joined the event
        activeEventName.hasParticipantJoined(accountName, new SearchEvent.ParticipantJoinedCallback() {
            @Override
            public void onCallback(boolean hasJoined) {
                if (hasJoined) {
                    viewHolder.joinIcon.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.joinIcon.setVisibility(View.INVISIBLE);
                }
            }
        });

        return listViewItem;

    }

    public static class ViewHolder {
        public TextView eventName;
        public TextView eventType;
        public TextView clubName;
        public Button joinButton;
        public ImageView arrow;
        public Boolean buttonsVisible;

        public String clubUsername;

        public ImageView joinIcon;

        public ViewHolder(View itemView) {
            this.eventName = itemView.findViewById(R.id.textViewEventName);
            this.eventType = itemView.findViewById(R.id.textViewEventType);
            this.clubName = itemView.findViewById(R.id.textViewClubName);
            this.joinButton = itemView.findViewById(R.id.joinButton);
            this.arrow = itemView.findViewById(R.id.arrow);
            this.joinIcon = itemView.findViewById(R.id.joined_icon);
            this.buttonsVisible = false;
        }

    }

    public interface EventListListener {
        void onEditEventType(ViewHolder VH);
    }

    // Method to set the listener
    public void setEventJoinListener(EventListListener listener) {
        this.listener = listener;
    }

    // this just opens the new window for when edit is pressed on an item
    public void openEventJoinFragment(ViewHolder VH) {
        if (listener != null) {
            listener.onEditEventType(VH);
        } else {
            Log.d("debug", "Listener is null");
        }
    }

}
