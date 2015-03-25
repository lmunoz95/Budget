package com.cyanococcus.budget.app;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cyanococcus.budget.app.adapter.ExpenseAdapter;
import com.cyanococcus.budget.app.data.BudgetDbHelper;
import com.cyanococcus.budget.app.model.Expense;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private ExpenseAdapter mAdapter;
    private ListView mList;
    private BudgetDbHelper mHelper;
    private boolean simple;
    private long initialDate;
    private long endDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);


        mAdapter = new ExpenseAdapter(getActivity(), new ArrayList<Expense>());
        mList = (ListView) rootView.findViewById(R.id.list);
        mList.setAdapter(mAdapter);
        mHelper = new BudgetDbHelper(getActivity());

        if(getArguments() != null) {
            simple = getArguments().getBoolean("SEARCH");
            initialDate = getArguments().getLong("INITIAL_DATE");
            endDate = getArguments().getLong("END_DATE");
        } else {
            simple = true;
        }

        load().execute();

        return rootView;
    }

    private AsyncTask<Void, Void, ArrayList<Expense>> load() {
        return new AsyncTask<Void, Void, ArrayList<Expense>>() {
            @Override
            protected ArrayList<Expense> doInBackground(Void... params) {
                return (simple)? mHelper.fetchAll() : mHelper.fetchAll(initialDate, endDate);
            }

            @Override
            protected  void onPostExecute(ArrayList<Expense> result) {
                mAdapter.clear();
                mAdapter.addAll(result);
            }
        };
    }
}
