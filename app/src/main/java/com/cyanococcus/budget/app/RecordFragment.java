package com.cyanococcus.budget.app;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.cyanococcus.budget.app.data.BudgetDbHelper;
import com.cyanococcus.budget.app.model.Expense;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment implements View.OnClickListener {
    private BudgetDbHelper helper;

    private EditText mLocation;
    private EditText mQuantity;
    private DatePicker mPicker;
    private EditText mDescription;
    private Button mClear;
    private Button mSave;

    public RecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_record, container, false);

        mLocation = (EditText) root.findViewById(R.id.et_location);
        mQuantity = (EditText) root.findViewById(R.id.et_quantity);
        mPicker = (DatePicker) root.findViewById(R.id.date_picker);
        mDescription = (EditText) root.findViewById(R.id.et_description);

        mClear = (Button) root.findViewById(R.id.btn_clear);
        mClear.setOnClickListener(this);
        mSave = (Button) root.findViewById(R.id.btn_save);
        mSave.setOnClickListener(this);
        helper = new BudgetDbHelper(getActivity());

        return root;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == mClear.getId()) {
            clear();
        } else if(v.getId() == mSave.getId()) {
            save().execute();
        }
    }

    private void clear() {
        mLocation.setText("");
        mQuantity.setText("");
        mPicker.getCalendarView().setDate((new Date()).getTime());
        mDescription.setText("");
    }

    public AsyncTask<Void, Void, Long> save() {
        return new AsyncTask<Void, Void, Long>() {
            private String msg;

            @Override
            protected Long doInBackground(Void... params) {
                long id = -1;

                try {
                    id = helper.insert(createExpense());
                } catch (SQLException ex) {
                    msg = ex.getMessage();
                }

                return id;
            }

            @Override
            protected void onPostExecute(Long result) {

                if(result != -1) {
                    Toast.makeText(getActivity(), getString(R.string.successful), Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("ERROR")
                            .setMessage(msg)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        };
    }

    public Expense createExpense() {
        Expense e = new Expense();

        String location = mLocation.getText().toString();
        if(!location.isEmpty())
            e.setLocation(location);

        String quantity = mQuantity.getText().toString();
        if(!quantity.isEmpty()) {
            e.setQuantity(Double.parseDouble(quantity));
        }

        e.setDate(mPicker.getCalendarView().getDate());

        String description = mDescription.getText().toString();
        if(!description.isEmpty()) {
            e.setDescription(description);
        }

        return e;
    }
}
