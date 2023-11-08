package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventTypeList extends ArrayAdapter<EventType> {

    private Activity context;
    List<EventType> eventTypes;

    public EventTypeList(Activity context, List<EventType> eventTypes) {
        super(context, R.layout.activity_event_type_list, eventTypes);
        this.context = context;
        this.eventTypes = eventTypes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        ViewHolder viewHolder;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.activity_event_type_list, parent, false);

            viewHolder = new ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        EventType eventTypeName = getItem(position);

        viewHolder.eventTypeName.setText(eventTypeName.getName());

        return listViewItem;




        /*LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_event_type_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        EventType eventType = eventTypes.get(position);
        textViewName.setText(eventType.getName());
        textViewPrice.setText(eventType.getName());
        return listViewItem;*/
    }

    public class ViewHolder {
        TextView eventTypeName;

        public ViewHolder(View itemView) {
            eventTypeName = itemView.findViewById(R.id.textViewName);
        }
    }
}