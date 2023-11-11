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

public class ClubManagerAccountList extends ArrayAdapter<ClubManagerAccount> {

    private Activity context;
    List<ClubManagerAccount> clubManagerAccounts;

    public ClubManagerAccountList(Activity context, List<ClubManagerAccount> clubManagerAccounts) {
        super(context, R.layout.activity_participant_account_list, clubManagerAccounts);
        this.context = context;
        this.clubManagerAccounts = clubManagerAccounts;
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

        ClubManagerAccount clubManagerAccount = getItem(position);

        viewHolder.clubManagerAccount.setText(clubManagerAccount.getUsername());

        return listViewItem;

    }

    public class ViewHolder {
        TextView clubManagerAccount;

        public ViewHolder(View itemView) {
            clubManagerAccount = itemView.findViewById(R.id.textViewUsername);
        }
    }
}
