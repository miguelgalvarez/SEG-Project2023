package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import java.util.List;

public class EventTypeList extends ArrayAdapter<EventType> {

    private Activity context;
    List<EventType> eventTypes;

    public EventTypeList(Activity context, List<EventType> eventTypes) {
        super(context, R.layout.active_event_item, eventTypes);
        this.context = context;
        this.eventTypes = eventTypes;
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

        EventType eventTypeName = getItem(position);

        try {
            viewHolder.eventTypeName.setText(eventTypeName.getName());
        } catch (Exception E) {
            E.printStackTrace();
        }

        // Set click listeners for the item view
        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.buttonsVisible) {
                    // Apply slide-in animation to the edit button
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

        return listViewItem;

    }

    public static class ViewHolder {
        TextView eventTypeName;
        Button editButton;
        Button deleteButton;
        ImageView threeDots;
        ImageView arrow;
        Boolean buttonsVisible;


        public ViewHolder(View itemView) {
            eventTypeName = itemView.findViewById(R.id.textViewName);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            threeDots = itemView.findViewById(R.id.threeDots);
            arrow = itemView.findViewById(R.id.arrow);
            buttonsVisible = false;
        }
    }
}