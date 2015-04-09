package com.cyanococcus.budget.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cyanococcus.budget.app.R;
import com.cyanococcus.budget.app.model.Expense;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    public ExpenseAdapter(Context context, ArrayList<Expense> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_item, parent, false);
        }

        Expense item = getItem(position);

        TextView tvLocation = (TextView) convertView.findViewById(R.id.tv_location);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tv_quantity);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tv_description);

        tvLocation.setText("Location: " + item.getLocation());

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        tvQuantity.setText("Quantity: " + numberFormat.format(item.getQuantity()));

        Date date = new Date(item.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
        tvDate.setText("Date: " + simpleDateFormat.format(date));

        tvDescription.setText("Description: " + item.getDescription());

        return convertView;
    }
}
