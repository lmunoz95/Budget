package com.cyanococcus.budget.app.data;

import android.provider.BaseColumns;

public class BudgetContract {
    public static class ExpenseEntry implements BaseColumns {
        public static final String TABLE_NAME = "expense";

        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_INFODATE = "infodate";
        public static final String COLUMN_DESCRIPTION = "description";

    }
}
