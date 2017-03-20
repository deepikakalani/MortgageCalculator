package com.example.deepika.mortgagecalculator;

import android.provider.BaseColumns;

/**
 * Created by deepika on 3/20/17.
 */

public class TableData {
    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns{
        public static final String STREET = "street";
        public static final String CITY = "city";
        public static final String STATE = "state";
        public static final String ZIP = "zip";
        public static final String MONTHLY_PAYMENT = "payment";
        public static final String DATABASE_NAME = "mortgage_info";
        public static final String TABLE_NAME = "payment_info";
    }
}
