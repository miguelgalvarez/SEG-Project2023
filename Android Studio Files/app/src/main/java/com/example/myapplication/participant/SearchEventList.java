package com.example.myapplication.participant;

import com.example.myapplication.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchEventList extends ArrayAdapter<SearchEvent> {

    private Activity context;
    List<SearchEvent> searchEvents;
    private String accountName;

    public SearchEventList(Activity context, List<SearchEvent> searchEvents, String accountName) {
        super(context, R.layout.search_event_item, searchEvents);
        this.context = context;
        this.searchEvents = searchEvents;
        this.accountName = accountName;
        this.listener = listener;
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
        } catch (Exception E) {
            E.printStackTrace();
        }

        // Set click listeners for the item view
        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open join event page

            }
        });

        return listViewItem;

    }

    public static class ViewHolder {
        public TextView eventName;
        public TextView eventType;
        public TextView clubName;


        public ViewHolder(View itemView) {
            this.eventName = itemView.findViewById(R.id.textViewEventName);
            this.eventType = itemView.findViewById(R.id.textViewEventType);
            this.clubName = itemView.findViewById(R.id.textViewClubName);
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
