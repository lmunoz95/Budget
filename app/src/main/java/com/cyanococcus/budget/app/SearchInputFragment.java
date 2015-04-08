package com.cyanococcus.budget.app;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import static com.cyanococcus.budget.app.data.BudgetDbHelper.normalize;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchInputFragment extends Fragment implements View.OnClickListener {


    private Button mSearch;
    private DatePicker mInitialDate;
    private DatePicker mEndDate;

    public SearchInputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search_input, container, false);

        mSearch = (Button) root.findViewById(R.id.btn_search);
        mSearch.setOnClickListener(this);

        mInitialDate = (DatePicker) root.findViewById(R.id.initial_date);
        mEndDate = (DatePicker) root.findViewById(R.id.end_date);

        return root;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == mSearch.getId()) {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtra("INITIAL_DATE", normalize(mInitialDate));
            intent.putExtra("END_DATE", normalize(mEndDate));
            startActivity(intent);
        }
    }
}
