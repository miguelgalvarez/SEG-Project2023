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

public class ParticipantAccountList extends ArrayAdapter<ParticipantAccount> {

    private Activity context;
    List<ParticipantAccount> participantAccounts;

    public ParticipantAccountList(Activity context, List<ParticipantAccount> participantAccounts) {
        super(context, R.layout.activity_participant_account_list, participantAccounts);
        this.context = context;
        this.participantAccounts = participantAccounts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        ViewHolder viewHolder;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.activity_participant_account_list, parent, false);

            viewHolder = new ViewHolder(listViewItem);

            listViewItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listViewItem.getTag();
        }

        ParticipantAccount participantAccount = getItem(position);

        viewHolder.participantAccount.setText(participantAccount.getUsername());

        return listViewItem;

    }

    public class ViewHolder {
        TextView participantAccount;

        public ViewHolder(View itemView) {
            participantAccount = itemView.findViewById(R.id.textViewUsername);
        }
    }
}
