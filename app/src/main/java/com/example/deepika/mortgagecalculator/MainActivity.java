package com.example.deepika.mortgagecalculator;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button bcalculate, bclear, bsave;
    int pp, dp, rate, period, payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText property_price = (EditText)findViewById(R.id.propertyprice);
        final EditText down_payment = (EditText)findViewById(R.id.downpayment);
        final EditText annual_rate = (EditText)findViewById(R.id.rate);
        final EditText term = (EditText)findViewById(R.id.period);
        bcalculate = (Button)findViewById(R.id.bcalculate);
        bclear = (Button)findViewById(R.id.bclear);
        bsave = (Button) findViewById(R.id.bsave);

        bsave.setEnabled(false);

        final EditText e_street = (EditText) findViewById(R.id.street);
        final EditText e_city = (EditText) findViewById(R.id.city);
        //final EditText e_state = (EditText) findViewById(R.id.state);
        final EditText e_zip = (EditText) findViewById(R.id.zip);
        final Spinner state = (Spinner) findViewById(R.id.state);
        String[] list1 = new String[]{"California", "Alabama", "Alaska"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, list1);
        state.setAdapter(adapter1);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bsave.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                bsave.setEnabled(true);
            }
        };

        //e_street.addTextChangedListener(tw);
        e_zip.addTextChangedListener(tw);





        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        String[] list = new String[]{"House", "TownHouse", "Condo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, list);
        dropdown.setAdapter(adapter);
        final TextView m_payment = (TextView) findViewById(R.id.text_monthly_payment);


        bcalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pp = Integer.parseInt(property_price.getText().toString());
                dp = Integer.parseInt(down_payment.getText().toString());
                rate = Integer.parseInt(annual_rate.getText().toString());
                period = Integer.parseInt(term.getText().toString());


                payment = calculate_mortgage(pp, dp, rate, period);
                m_payment.setText(String.valueOf(payment));

            }
        });

        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_price.setText("");
                down_payment.setText("");
                annual_rate.setText("");
                term.setText("");
                m_payment.setText("");
                e_street.setText("");
                e_city.setText("");
                e_zip.setText("");
            }
        });

        final DatabaseOperations handler = new DatabaseOperations(getBaseContext(), TableData.TableInfo.DATABASE_NAME, null, 1);

        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                final String street = e_street.getText().toString();
                final String city = e_city.getText().toString();
                final String state1 = state.getSelectedItem().toString();
                final String zip = e_zip.getText().toString();
                List<Address> addresses = null;
                try {
                    addresses = validate_address(street, city, state1, zip);
                }
                catch (Exception e){e.printStackTrace();}

                if (addresses.size() >= 1) {

                    handler.getWritableDatabase();
                    long k = handler.insertInfo(handler, street, city, state1, String.valueOf(payment));
                    /*Cursor c = handler.getInfo(handler);
                    int count = c.getCount();
                    c.moveToFirst();
                    String demo = c.getString(0);
                    String demo1 = c.getString(1);
                    String demo3 = c.getString(2);*/
                    handler.close();
                    Log.d("Save", String.valueOf(k));
                }
            }

        });

    }

    public int calculate_mortgage(int pp, int dp, int rate, int period){
        int principal = pp - dp;
        int r = (rate/12)/100;
        int n = period*12;
        int monthly_payment;
        monthly_payment = principal*(((r*(1+r)^n))/((1+r)^n -1));
        return monthly_payment;
    }

    public List<Address> validate_address(String addr, String city, String state, String zip) throws Exception{
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocationName(addr + city + state, 3);
        return addresses;

    }

}