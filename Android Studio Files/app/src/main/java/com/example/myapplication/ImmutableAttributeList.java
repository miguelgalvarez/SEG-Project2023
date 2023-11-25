package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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
public class ImmutableAttributeList extends ArrayAdapter<ImmutableAttribute> {

    private Activity context;
    List<ImmutableAttribute> activeEvent;

    public ImmutableAttributeList(Activity context, List<ImmutableAttribute> attribute) {
        super(context, R.layout.active_event_item, attribute);
        this.context = context;
        this.activeEvent = attribute;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        ViewHolder viewHolder;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.event_attribute_immutable, parent, false);

            viewHolder = new ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        ImmutableAttribute currentAttribute = getItem(position);

        try {
            viewHolder.attributeType.setText(currentAttribute.getType());
            viewHolder.attributeValue.setText(currentAttribute.getValue());
        } catch (Exception E) {
            Log.d("failed", "failed");
            E.printStackTrace();
        }

        return listViewItem;
    }

    public static class ViewHolder {

        TextView attributeType;

        TextView attributeValue;

        public ViewHolder(View itemView) {
            this.attributeType = itemView.findViewById(R.id.attributeType);
            this.attributeValue = itemView.findViewById(R.id.attributeValue);
        }
    }
}
