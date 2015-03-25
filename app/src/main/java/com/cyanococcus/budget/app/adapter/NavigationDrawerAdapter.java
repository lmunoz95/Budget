package com.cyanococcus.budget.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyanococcus.budget.app.R;
import com.cyanococcus.budget.app.model.NavigationDrawerItem;

import java.util.ArrayList;

public class NavigationDrawerAdapter extends ArrayAdapter<NavigationDrawerItem> {
    public NavigationDrawerAdapter(Context context, ArrayList<NavigationDrawerItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.navigation_drawer_item, parent, false);
        }

        NavigationDrawerItem item = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
        TextView textView = (TextView) convertView.findViewById(R.id.title);

        textView.setText(item.getTitle());
        imageView.setImageResource(item.getIconId());

        return convertView;
    }
}
