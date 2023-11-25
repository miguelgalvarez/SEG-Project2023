package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
public class MutableAttributeList extends ArrayAdapter<MutableAttribute> {

    private Activity context;
    List<MutableAttribute> activeEvent;

    public MutableAttributeList(Activity context, List<MutableAttribute> attribute) {
        super(context, R.layout.active_event_item, attribute);
        this.context = context;
        this.activeEvent = attribute;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        ViewHolder viewHolder;

        if (listViewItem == null) {
            // Open the custom list item
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.event_attribute_mutable, parent, false);

            // Create a ViewHolder
            viewHolder = new ViewHolder(listViewItem);

            // Set a unique tag for each item
            listViewItem.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        // Gets the current attribute object
        MutableAttribute currentAttribute = getItem(position);

        try {
            viewHolder.attributeType.setText(currentAttribute.getType());
            viewHolder.attributeValue.setText(currentAttribute.getValue());
            viewHolder.attributeValue.setTag(position);
            currentAttribute.setItemView(viewHolder);
        } catch (Exception E) {
            E.printStackTrace();
        }


        // Updates the Item when the text is changed
        viewHolder.attributeValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                // Update the data in the object when the EditText field changes
                int position = (int) viewHolder.attributeValue.getTag();
                activeEvent.get(position).setValue(editable.toString());
            }
        });

        return listViewItem;
    }

    public static class ViewHolder {

        TextView attributeType;

        EditText attributeValue;

        public ViewHolder(View itemView) {
            this.attributeType = itemView.findViewById(R.id.attributeType);
            this.attributeValue = itemView.findViewById(R.id.attributeValue);
        }
    }
}
