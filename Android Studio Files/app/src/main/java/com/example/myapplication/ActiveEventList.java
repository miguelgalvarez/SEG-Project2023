package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
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
 * Custom ArrayAdapter to handle the display and interaction of event types in a ListView.
 * @author linden Sheehy
 * @version 1.0
 */
public class ActiveEventList extends ArrayAdapter<ActiveEvent> {

    private Activity context;
    List<ActiveEvent> activeEvent;
    private String accountName;

    public ActiveEventList(Activity context, List<ActiveEvent> activeEvent, String accountName) {
        super(context, R.layout.active_event_item, activeEvent);
        this.context = context;
        this.activeEvent = activeEvent;
        this.accountName = accountName;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        ViewHolder viewHolder;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.active_event_item, parent, false);

            viewHolder = new ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        ActiveEvent activeEventName = getItem(position);

        try {
            viewHolder.activeEventName.setText(activeEventName.getName());
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

                    viewHolder.editButton.startAnimation(slideOut);
                    viewHolder.editButton.setVisibility(View.INVISIBLE);

                    viewHolder.deleteButton.startAnimation(slideOut);
                    viewHolder.deleteButton.setVisibility(View.INVISIBLE);

                    viewHolder.arrow.startAnimation(slideOut);
                    viewHolder.arrow.setVisibility(View.INVISIBLE);

                    viewHolder.buttonsVisible = false;
                }

                else {
                    // Apply slide-in animation to the edit button
                    Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);
                    viewHolder.editButton.startAnimation(slideIn);
                    viewHolder.editButton.setVisibility(View.VISIBLE);

                    viewHolder.deleteButton.startAnimation(slideIn);
                    viewHolder.deleteButton.setVisibility(View.VISIBLE);

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
        Button editButton = listViewItem.findViewById(R.id.editButton);
        Button deleteButton = listViewItem.findViewById(R.id.deleteButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide buttons again
                viewHolder.editButton.setVisibility(View.INVISIBLE);
                viewHolder.deleteButton.setVisibility(View.INVISIBLE);
                viewHolder.arrow.setVisibility(View.INVISIBLE);

                viewHolder.buttonsVisible = false;

                // opens the edit page for the current item
                openActivityEditActiveEvent(viewHolder);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this gets the event in the database then deletes it
                sendToastMessage();
                String name = viewHolder.activeEventName.getText().toString();
                DatabaseReference valueToRemove = eventsRef.child(name);
                valueToRemove.removeValue();

                // make buttons invisible so they don't stay open for another item
                viewHolder.editButton.setVisibility(View.INVISIBLE);
                viewHolder.deleteButton.setVisibility(View.INVISIBLE);
                viewHolder.arrow.setVisibility(View.INVISIBLE);

                viewHolder.buttonsVisible = false;
            }
        });

        return listViewItem;

    }

    public static class ViewHolder {
        TextView activeEventName;
        Button editButton;
        Button deleteButton;
        ImageView threeDots;
        ImageView arrow;
        Boolean buttonsVisible;


        public ViewHolder(View itemView) {
            activeEventName = itemView.findViewById(R.id.textViewName);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            threeDots = itemView.findViewById(R.id.threeDots);
            arrow = itemView.findViewById(R.id.arrow);
            buttonsVisible = false;
        }
    }

    public interface ActiveEventListListener {
        void onEditActiveEvent(String activeEventName);
    }

    private ActiveEventListListener listener;

    // Method to set the listener
    public void setActiveEventListListener(ActiveEventListListener listener) {
        this.listener = listener;
    }


    // this just opens the new window for when edit is pressed on an item
    public void openActivityEditActiveEvent(ViewHolder VH) {
        if (listener != null) {
            listener.onEditActiveEvent(VH.activeEventName.getText().toString());
        }
    }


    public void sendToastMessage() {
        Toast.makeText(getContext(), "Event Type Deleted!", Toast.LENGTH_SHORT).show();
    }
}
