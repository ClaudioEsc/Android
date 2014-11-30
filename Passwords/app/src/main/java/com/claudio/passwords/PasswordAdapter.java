package com.claudio.passwords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PasswordAdapter extends ArrayAdapter<Password> {
    public PasswordAdapter(Context context, List<Password> passwords) {
        super(context, 0, passwords);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Password password = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_password, parent, false);
        }

        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);

        tvId.setText(Integer.toString(password.getId()));
        tvDescription.setText(password.getDescription());
        tvUsername.setText(password.getUsername());

        // Return the completed view to render on screen
        return convertView;
    }

}
