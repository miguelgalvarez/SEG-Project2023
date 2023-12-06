package com.example.myapplication.participant;

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

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.List;

// This class just reuses the ActiveEvent objects instead of creating a new one
public class ParticipantEventList extends ArrayAdapter<ParticipantEvent> {

    private Activity context;
    List<ParticipantEvent> activeEvent;
    private String accountName;

    public ParticipantEventList(Activity context, List<ParticipantEvent> activeEvent, String accountName) {
        super(context, R.layout.participant_event_item, activeEvent);
        this.context = context;
        this.activeEvent = activeEvent;
        this.accountName = accountName;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        ParticipantEventList.ViewHolder viewHolder;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.participant_event_item, parent, false);

            viewHolder = new ParticipantEventList.ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ParticipantEventList.ViewHolder) listViewItem.getTag();
        }

        ParticipantEvent activeEventName = getItem(position);

        try {
            viewHolder.eventName.setText(activeEventName.eventName);
            viewHolder.eventType.setText(activeEventName.eventType);
            viewHolder.clubName.setText(activeEventName.clubName);
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

                    viewHolder.leaveButton.startAnimation(slideOut);
                    viewHolder.leaveButton.setVisibility(View.INVISIBLE);

                    viewHolder.arrow.startAnimation(slideOut);
                    viewHolder.arrow.setVisibility(View.INVISIBLE);

                    viewHolder.buttonsVisible = false;
                }

                else {
                    // Apply slide-in animation to the edit button
                    Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);

                    viewHolder.leaveButton.startAnimation(slideIn);
                    viewHolder.leaveButton.setVisibility(View.VISIBLE);

                    viewHolder.arrow.startAnimation(slideIn);
                    viewHolder.arrow.setVisibility(View.VISIBLE);

                    viewHolder.buttonsVisible = true;
                }
            }
        });

        // Get database references to be used for the leave button
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventsRef = rootRef.child("Participant").child(this.accountName).child("JoinedEvents");

        // Add click listener for the leave button
        Button leaveButton = listViewItem.findViewById(R.id.leaveButton);

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this gets the event in the database then deletes it
                sendToastMessage();
                String name = viewHolder.eventName.getText().toString();
                DatabaseReference valueToRemove = eventsRef.child(name);
                valueToRemove.removeValue();

                // make buttons invisible so they don't stay open for another item
                viewHolder.leaveButton.setVisibility(View.INVISIBLE);
                viewHolder.arrow.setVisibility(View.INVISIBLE);

                viewHolder.buttonsVisible = false;
            }
        });

        return listViewItem;

    }

    public static class ViewHolder {
        public TextView eventName;
        public TextView eventType;
        public TextView clubName;
        public Button leaveButton;
        public ImageView arrow;
        public Boolean buttonsVisible;


        public ViewHolder(View itemView) {
            this.eventName = itemView.findViewById(R.id.textViewEventName);
            this.eventType = itemView.findViewById(R.id.textViewEventType);
            this.clubName = itemView.findViewById(R.id.textViewClubName);
            this.leaveButton = itemView.findViewById(R.id.leaveButton);
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

    public void sendToastMessage() {
        Toast.makeText(getContext(), "Event Left!", Toast.LENGTH_SHORT).show();
    }

}
